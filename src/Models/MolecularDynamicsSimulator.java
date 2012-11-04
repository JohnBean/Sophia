package edu.gatech.sophia;

import java.util.ArrayList;

public class MolecularDynamicsSimulator extends Simulator {
    public MolecularDynamicsSimulator() {
        type = 1;
    }

    public Recording run(Cluster cluster) {
        ArrayList<Atom> atoms = cluster.getAtoms();
        int step = 0;

        //Set up the output recording
        Recording output = new Recording(cluster);

        //Add the initial frame
        Frame initial = new Frame();
        ArrayList<Point3D> ilocations = new ArrayList<Point3D>();

        for(Atom a : atoms) {
            ilocations.add(new Point3D(a.location.x, a.location.y, a.location.z));
        }

        initial.setLocations(ilocations);
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
                Frame f = new Frame();
                ArrayList<Point3D> locations = new ArrayList<Point3D>();

                for(Atom a : atoms) {
                    locations.add(new Point3D(a.location.x, a.location.y, a.location.z));
                }

                f.setLocations(locations);
                output.addFrame(f);
            }
        }

        return output;
    }
}
