package edu.gatech.sophia;

import java.util.ArrayList;
import javax.swing.JProgressBar;

/**
 * Default stand in simulator when no other implemented simulator is chosen.
 * Just generates a single frame snapshot of the system.
 */
public class NullSimulator extends Simulator {
    /**
     * Constructs a new null simulator
     */
    public NullSimulator() {
        type = 0;
    }

    /**
     * Runs a simulation on the specified cluster
     *
     * @param cluster the cluster of molecules to simulate
     * @param prog a progress bar on the GUI to update with the progress of the simulation
     * @return a Recording object containing a single frame
     */
    public Recording run(Cluster cluster, JProgressBar prog) {
        //Set up the output recording
        Recording output = new Recording(cluster, outputInterval);

        //Add the initial frame
        Frame initial = new Frame();
        ArrayList<Point3D> ilocations = new ArrayList<Point3D>();

        ArrayList<Atom> atoms = cluster.getAtoms();
        for(Atom a : atoms) {
            ilocations.add(new Point3D(a.location.x, a.location.y, a.location.z));
        }

        initial.setLocations(ilocations);
        output.addFrame(initial);

        return output;
    }
}
