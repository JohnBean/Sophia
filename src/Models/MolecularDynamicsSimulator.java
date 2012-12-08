package edu.gatech.sophia;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JProgressBar;

/**
 * Basic simulator for the molecular dynamics algorithm. Steps through each frame
 * and increments time step then computes forces and offsets velocities of each atom.
 */
public class MolecularDynamicsSimulator extends Simulator {
    /**
     * Gas constant used in temperature computations
     */
    public static double GAS_CONSTANT = 0.830936;

    /**
     * Conversion factor from CEU to kcal/mol
     */
    public static final double CEU_TO_KCAL = 1.0 / 418.68;

    /**
     * Constructs a new molecular dynamics simulator
     */
    public MolecularDynamicsSimulator() {
        type = 1;
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
        double kineticEnergy = 0.0;
        double potentialEnergy;
        double totalEnergy;
        double dt = timestep;
        double halfStep = 0.5 * timestep;
        double temperature;
        double totalTime = 0.0;

        //Set up the output recording
        Recording output = new Recording(cluster, outputInterval);

        output.setType(type);
        output.setStep(timestep);
        //Set up the progress bar
        prog.setMinimum(0);
        prog.setMaximum(numSteps - 1);
        prog.setValue(0);

        //Set up the temperature protocol
        intializeTemperatureProtocol();

        //Add the initial frame
        Frame initial = new Frame();
        ArrayList<Point3D> ilocations = new ArrayList<Point3D>();

        for(Atom a : atoms) {
            ilocations.add(new Point3D(a.location.x, a.location.y, a.location.z));

            //Calculate kinetic energy
            kineticEnergy += (0.5 * a.mass * a.velocity.magnitudeSquared());
        }

        //Calculate the temperature
        temperature = (2.0 * kineticEnergy / atoms.size()) / (numDimensions * GAS_CONSTANT);

        //Convert kinetic energy
        kineticEnergy *= CEU_TO_KCAL;

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
        initial.kineticEnergy = kineticEnergy;
        initial.potentialEnergy = potentialEnergy;
        initial.temperature = temperature;
        initial.totalEnergy = potentialEnergy + kineticEnergy;
        output.addFrame(initial);

        //Adjust temperatures to beginning of protocol
        scaleTemperatures(cluster, 0, temperature);

        dt = timestep * 0.5;

        //Advance the simulation by each timestep
        for(step = 1; step < numSteps; step++) {
            //Increment time
            totalTime += timestep;

            //Calculate current forces on each atom
            cluster.calculateForces();

            //Update acceleration, velocity, and position for each atom
            for(Atom a : atoms) {
                a.calculateAcceleration();

                a.velocity.x += a.acceleration.x * dt;
                a.velocity.y += a.acceleration.y * dt;
                a.velocity.z += a.acceleration.z * dt;

                a.location.x += a.velocity.x * timestep;
                a.location.y += a.velocity.y * timestep;
                a.location.z += a.velocity.z * timestep;
            }

            //If the step is on the output interval, add a frame
            if((step % outputInterval) == 0) {
                //Calculate kinetic energy at the half time step
                kineticEnergy = 0.0;
                Vector3D halfStepVelocity = new Vector3D();

                for(Atom a : atoms) {
                    halfStepVelocity.x = a.velocity.x - a.acceleration.x * halfStep;
                    halfStepVelocity.y = a.velocity.y - a.acceleration.y * halfStep;
                    halfStepVelocity.z = a.velocity.z - a.acceleration.z * halfStep;

                    kineticEnergy += (0.5 * a.mass * halfStepVelocity.magnitudeSquared());
                }

                //Calculate the temperature
                temperature = (2.0 * kineticEnergy / atoms.size()) / (numDimensions * GAS_CONSTANT);

                //Convert kinetic energy from CEU to kcal
                kineticEnergy *= CEU_TO_KCAL;

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

                //Calculate total energy
                totalEnergy = kineticEnergy + potentialEnergy;

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
                f.kineticEnergy = kineticEnergy;
                f.totalEnergy = totalEnergy;
                f.temperature = temperature;

                output.addFrame(f);
            }

            //Adjust temperatures
            scaleTemperatures(cluster, step, temperature);

            dt = timestep;

            prog.setValue(++progress);
        }

        return output;
    }
}
