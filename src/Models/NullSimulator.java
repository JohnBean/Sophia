package edu.gatech.sophia;

import java.util.ArrayList;

public class NullSimulator extends Simulator {
    public NullSimulator() {
        type = 0;
    }

    public Recording run(Cluster cluster) {
        //Set up the output recording
        Recording output = new Recording(cluster);

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
