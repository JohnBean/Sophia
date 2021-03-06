package edu.gatech.sophia;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JProgressBar;

/**
 * Simulator for energy minimization algorithms.
 *
 * Implements steepest descent
 */
public class EnergyMinimizationSimulator extends Simulator {
    /**
     * Gas constant used in temperature computations
     */
    public static double GAS_CONSTANT = 0.830936;

    /**
     * Conversion factor from CEU to kcal/mol
     */
    public static final double CEU_TO_KCAL = 1.0 / 418.68;

    /**
     * Threshold for convergence
     */
    private double convergenceCriterion;

    /**
     * Algorithm to use for energy minimization
     */
    private String algorithm;

    /**
     * Current cluster the algorithm is running on
     */
    private Cluster cluster;

    /**
     * Saved locations of atoms
     */
    private Vector3D[] savedLocations;

    /**
     * Saved forces for conjugate gradient
     */
    private Vector3D[] savedForces;

    /**
     * Saved search vectors for conjucate gradient
     */
    private Vector3D[] savedVectors;

    /**
     * Starting step size
     */
    private double stepSize;

    /**
     * Current step size for energy minimization
     */
    private double currentStep;

    /**
     * Indicates whether system has converged
     */
    private boolean isConverged;

    /**
     * Total time offset from beginning of simulation
     */
    private double totalTime;

    /**
     * Constructs a new molecular dynamics simulator
     */
    public EnergyMinimizationSimulator(double convergenceCriterion, double stepSize, String algorithm) {
        type = 2;
        this.convergenceCriterion = convergenceCriterion;
        this.stepSize = stepSize;
        this.algorithm = algorithm;
    }

    /**
     * Runs a simulation on the specified cluster
     *
     * @param cluster the cluster of molecules to simulate
     * @param prog a progress bar on the GUI to update with the progress of the simulation
     * @return a Recording object containing an array of frames generated by the simulation
     */
    public Recording run(Cluster cluster, JProgressBar prog) {
        ArrayList<Atom> atoms = cluster.getAtoms();
        int step = 0;
        int progress = 0;
        double potentialEnergy;
        totalTime = 0.0;

        //Set current step to parameter passed by user
        currentStep = stepSize;
        isConverged = false;

        //Set the cluster and initialize space for temporary saved data
        this.cluster = cluster;

        if(algorithm.equals("Conjugate Gradient")) {
            savedLocations = new Vector3D[atoms.size()];
            savedForces = new Vector3D[atoms.size()];
            savedVectors = new Vector3D[atoms.size()];
            for(int i = 0; i < atoms.size(); i++) {
                savedLocations[i] = new Vector3D();
                savedForces[i] = new Vector3D();
                savedVectors[i] = new Vector3D();
            }
        } else {
            savedLocations = new Vector3D[atoms.size()];
            for(int i = 0; i < atoms.size(); i++)
                savedLocations[i] = new Vector3D();
        }

        //Set up the output recording
        Recording output = new Recording(cluster, outputInterval);

        output.setType(type);
        output.setStep(timestep);
        //Set up the progress bar
        prog.setMinimum(0);
        prog.setMaximum(numSteps - 1);
        prog.setValue(0);

        //Add the initial frame
        Frame initial = new Frame();
        ArrayList<Point3D> ilocations = new ArrayList<Point3D>();

        for(Atom a : atoms) {
            ilocations.add(new Point3D(a.location.x, a.location.y, a.location.z));
        }

        HashMap<String, Double> ienergies = cluster.getEnergies();
        potentialEnergy = 0.0;
        for(String eName : ienergies.keySet()) {
            //Convert each energy from CEU to kcal
            double energyTerm = ienergies.get(eName).doubleValue() * CEU_TO_KCAL;
            ienergies.put(eName, energyTerm);

            //Accumulate to total potential
            potentialEnergy += energyTerm;
        }

        initial.setLocations(ilocations);
        initial.setEnergies(ienergies);
        initial.setTime(totalTime);
        initial.potentialEnergy = potentialEnergy;
        initial.totalEnergy = potentialEnergy;
        initial.temperature = 0.0;
        initial.kineticEnergy = 0.0;
        output.addFrame(initial);

        //Advance the simulation by each timestep
        for(step = 1; step < numSteps; step++) {
            if(algorithm.equals("Steepest Descent"))
                steepestDescent();
            else if(algorithm.equals("Conjugate Gradient"))
                conjugateGradient(step);

            //If the step is on the output interval, add a frame
            if((step % outputInterval) == 0 || isConverged) {
                //Calculate potential energies for each association and add accumulate to get total potential energy
                HashMap<String, Double> energies = cluster.getEnergies();
                potentialEnergy = 0.0;
                for(String eName : energies.keySet()) {
                    //Convert each energy from CEU to kcal
                    double energyTerm = energies.get(eName).doubleValue() * CEU_TO_KCAL;
                    energies.put(eName, energyTerm);

                    //Accumulate to total potential
                    potentialEnergy += energyTerm;
                }

                //Create a frame
                Frame f = new Frame();
                ArrayList<Point3D> locations = new ArrayList<Point3D>();

                for(Atom a : atoms) {
                    locations.add(new Point3D(a.location.x, a.location.y, a.location.z));
                }

                f.setLocations(locations);
                f.setEnergies(energies);
                f.setTime(totalTime);
                f.potentialEnergy = potentialEnergy;
                f.totalEnergy = potentialEnergy;
                f.temperature = 0.0;
                f.kineticEnergy = 0.0;

                output.addFrame(f);
            }

            //Check for convergence and exit if finished
            if(isConverged)
                break;
        }

        //Indicate completion
        prog.setValue(numSteps - 1);

        return output;
    }

    /**
     * Runs steepest descent algorithm
     *
     *  Steepest Descent:
     *  1. Compute trial configuration for atoms in the system
     *      x_trial = x + Fx * step size
     *  2. If the trial configuration reduces the energy of the system...
     *      a. save trial configuration
     *      b. increase step size by multiplicative factor (1.2)
     *  3. If the trial configuration increases the energy of the system...
     *      a. restore previous configuration
     *      b. decrease step size by multiplicative factor (0.5)
     */
    public void steepestDescent() {
        ArrayList<Atom> atoms = cluster.getAtoms();
        Atom a;
        int numAtoms = atoms.size();
        double potentialEnergy;
        double trialPotential;
        int i;

        //Increment time
        totalTime += currentStep;

        //Calculate force on each atom
        cluster.calculateForces();

        //Get energy of current configuration
        HashMap<String, Double> energies = cluster.getEnergies();
        potentialEnergy = 0.0;
        for(String eName : energies.keySet())
            potentialEnergy += energies.get(eName).doubleValue();

        //Save current atom positions and advance atoms to trial positions
        for(i = 0; i < numAtoms; i++) {
            a = atoms.get(i);

            savedLocations[i].x = a.location.x;
            savedLocations[i].y = a.location.y;
            savedLocations[i].z = a.location.z;

            //Compute trial locations
            a.location.x += a.force.x * currentStep;
            a.location.y += a.force.y * currentStep;
            a.location.z += a.force.z * currentStep;
        }

        //Get energy of the trial configuration
        energies = cluster.getEnergies();
        trialPotential = 0.0;
        for(String eName : energies.keySet())
            trialPotential += energies.get(eName).doubleValue();

        //Decide whether to keep trial configuration based on convergence
        if(trialPotential < potentialEnergy) {
            //Keep positions and check for convergence
            if(Math.abs((potentialEnergy - trialPotential) / potentialEnergy) < convergenceCriterion)
                isConverged = true;

            currentStep *= 1.2;
        } else {
            //Restore old positions
            for(i = 0; i < numAtoms; i++) {
                a = atoms.get(i);

                a.location.x = savedLocations[i].x;
                a.location.y = savedLocations[i].y;
                a.location.z = savedLocations[i].z;
            }

            currentStep *= 0.5;
        }
    }

    /**
     * Runs the conjugate gradient algorithm for one step
     *
      * Conjugate Gradient:
     * 0. The search vector in the first step is the negative gradient of potential
     *    energy (i.e. force), the same as it is in steepest descent
     * 1. Compute scalar constant, gamma
     *    gamma = (-current force DOT -current force)
     *            / (-previous Force DOT -previous Force)
     * 2. Compute search vector, V
     *    V = current force + gamma * previous V
     * 3. Compute new position for atom
     *    new position = current position + V_k
     */
    public void conjugateGradient(int stepNumber) {
        ArrayList<Atom> atoms = cluster.getAtoms();
        Atom a;
        int numAtoms = atoms.size();
        int i;
        double potentialEnergy;
        double trialPotential;
        double gamma;
        Vector3D searchVector = new Vector3D();
        boolean isFirstStep;

        //Increment time
        totalTime += currentStep;

        //Calculate force on each atom
        cluster.calculateForces();

        //Calculate potential energy of the current configuration
        HashMap<String, Double> energies = cluster.getEnergies();
        potentialEnergy = 0.0;
        for(String eName : energies.keySet())
            potentialEnergy += energies.get(eName).doubleValue();

        //Check if this is the first step since there will be no previous information
        isFirstStep = (stepNumber == 1);

        //Choose either steepest descent or conjugate gradient
        if(stepNumber % (numDimensions * numAtoms) == 0) {
            for(i = 0; i < numAtoms; i++) {
                a = atoms.get(i);

                //Save locations and forces and search vectors
                savedLocations[i].x = a.location.x;
                savedLocations[i].y = a.location.y;
                savedLocations[i].z = a.location.z;

                savedVectors[i].x = savedForces[i].x = a.force.x;
                savedVectors[i].y = savedForces[i].y = a.force.y;
                savedVectors[i].z = savedForces[i].z = a.force.z;

                //Compute trial location using steepest descent
                a.location.x += a.force.x * currentStep;
                a.location.y += a.force.y * currentStep;
                a.location.z += a.force.z * currentStep;
            }
        } else {
            for(i = 0; i < numAtoms; i++) {
                a = atoms.get(i);

                //Save locations and forces
                savedLocations[i].x = a.location.x;
                savedLocations[i].y = a.location.y;
                savedLocations[i].z = a.location.z;

                //Compute trial location using conjugate gradient
                searchVector = computeSearchVector(isFirstStep, a.force, savedForces[i], savedVectors[i], searchVector);
                a.location.x += searchVector.x * currentStep;
                a.location.y += searchVector.y * currentStep;
                a.location.z += searchVector.z * currentStep;

                //Save force and search vector from this step
                savedForces[i].x = a.force.x;
                savedForces[i].y = a.force.y;
                savedForces[i].z = a.force.z;

                savedVectors[i].x = searchVector.x;
                savedVectors[i].y = searchVector.y;
                savedVectors[i].z = searchVector.z;
            }
        }

        //Calculate potential energy of the new configuration
        energies = cluster.getEnergies();
        trialPotential = 0.0;
        for(String eName : energies.keySet())
            trialPotential += energies.get(eName).doubleValue();

        //Decide whether to keep trial configuration based on convergence
        if(trialPotential < potentialEnergy) {
            //Keep positions and check for convergence
            if(Math.abs((potentialEnergy - trialPotential) / potentialEnergy) < convergenceCriterion)
                isConverged = true;

            currentStep *= 1.2;
        } else {
            //Restore old positions
            for(i = 0; i < numAtoms; i++) {
                a = atoms.get(i);

                a.location.x = savedLocations[i].x;
                a.location.y = savedLocations[i].y;
                a.location.z = savedLocations[i].z;
            }

            currentStep *= 0.5;
        }
    }

    /**
     * Computes a search vector for conjugate gradient method
     *
     * @param output the vector to use for output
     */
    public Vector3D computeSearchVector(boolean isFirstStep, Vector3D currentForce, Vector3D oldForce, Vector3D previousVector, Vector3D output) {
        double gamma;

        //Compute gamma
        if(isFirstStep) {
            gamma = 0.0;
        } else {
            double magnitudeCurrentForce = currentForce.magnitudeSquared();
            double magnitudeOldForce = oldForce.magnitudeSquared();

            gamma = magnitudeCurrentForce / magnitudeOldForce;
        }

        //Compute the search vector
        output.x = currentForce.x;
        output.y = currentForce.y;
        output.z = currentForce.z;

        if(!isFirstStep) {
            output.x += (gamma * previousVector.x);
            output.y += (gamma * previousVector.y);
            output.z += (gamma * previousVector.z);
        }

        return output;
    }
}
