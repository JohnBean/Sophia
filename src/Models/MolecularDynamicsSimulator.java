package edu.gatech.sophia;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JProgressBar;

public class MolecularDynamicsSimulator extends Simulator {
    private int numDimensions = 2;

    public static double GAS_CONSTANT = 0.830936;
    public static final double CEU_TO_KCAL = 1.0 / 418.68;

    public MolecularDynamicsSimulator() {
        type = 1;
    }

    public Recording run(Cluster cluster, JProgressBar prog) {
        ArrayList<Atom> atoms = cluster.getAtoms();
        int step = 0;
        int progress = 0;
        double kineticEnergy;
        double potentialEnergy;
        double totalEnergy;
        double halfStep = 0.5 * timestep;
        double temperature;

        //Set up the output recording
        Recording output = new Recording(cluster);

        //Set up the progress bar
        prog.setMinimum(0);
        prog.setMaximum(numSteps);
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
        initial.kineticEnergy = 0.0;
        initial.potentialEnergy = potentialEnergy;
        initial.temperature = 0.0;
        initial.totalEnergy = potentialEnergy;
        output.addFrame(initial);

        //Advance the simulation by each timestep
        for(step = 0; step < numSteps; step++) {
            //Calculate current forces on each atom
            cluster.calculateForces();

            //Update acceleration, velocity, and position for each atom
            for(Atom a : atoms) {
                a.calculateAcceleration();

                a.velocity.x += a.acceleration.x * timestep;
                a.velocity.y += a.acceleration.y * timestep;
                a.velocity.z += a.acceleration.z * timestep;

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
                f.potentialEnergy = potentialEnergy;
                f.kineticEnergy = kineticEnergy;
                f.totalEnergy = totalEnergy;
                f.temperature = temperature;

                output.addFrame(f);
            }

            prog.setValue(++progress);
        }

        return output;
    }
}
