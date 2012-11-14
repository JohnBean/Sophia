package edu.gatech.sophia;

import javax.swing.JProgressBar;

public class SimulationRunner implements Runnable {
    private Simulator simulator;
    private Cluster cluster;
    private JProgressBar progress;
    private SimulationController controller;

    public SimulationRunner(Simulator simulator, Cluster cluster, JProgressBar progress, SimulationController controller) {
        this.simulator = simulator;
        this.cluster = cluster;
        this.progress = progress;
        this.controller = controller;
    }

    public void run() {
        Recording output = simulator.run(cluster, progress);

        controller.signalFinished(output);
    }
}
