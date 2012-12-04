package edu.gatech.sophia;

import javax.swing.JProgressBar;

/**
 * Shell thread for running simulations so GUI elements still respond
 */
public class SimulationRunner implements Runnable {
    private Simulator simulator;
    private Cluster cluster;
    private JProgressBar progress;
    private SimulationController controller;

    /**
     * Constructs a new simulation runner
     *
     * @param simulator the already setup simulator object to run
     * @param cluster the cluster to run the simulation on
     * @param progress the progress bar to update with simulation progress
     * @param controller the simulation controller to signal when simulation is completed
     */
    public SimulationRunner(Simulator simulator, Cluster cluster, JProgressBar progress, SimulationController controller) {
        this.simulator = simulator;
        this.cluster = cluster;
        this.progress = progress;
        this.controller = controller;
    }

    /**
     * This is where the thread enters and runs the simulation then signals the controller it is done
     */
    public void run() {
        Recording output = simulator.run(cluster,simulator.getOutputInterval(), progress);

        controller.signalFinished(output);
    }
}
