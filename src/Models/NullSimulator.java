package edu.gatech.sophia;

public class NullSimulator extends Simulator {
    public NullSimulator() {
        type = 0;
    }

    public Recording run(Cluster cluster) {
        //Create single frame with all current state information for the cluster
        Frame single = new Frame();
        single.setAtoms(cluster.getAtoms());

        //Create recording with the single frame
        Recording output = new Recording();
        output.addFrame(single);

        return output;
    }
}
