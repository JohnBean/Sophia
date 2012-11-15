package edu.gatech.sophia;

public class SimulationController {
    /**
     * Currently loaded cluster
     */
    private Cluster cluster;
    private FilePickerView fpView;
    private SimulationSettingsView ssView;
    private VisualizationController vController;
    private SimulationView smView;

    /*
     * Simulator object hold sim params and implements equations
     */
    private Simulator simulator;

    /**
     * Output from the simulator
     */
    private Recording output;

    public SimulationController() {
        
    }
    public Recording getRecording(){
        return output;
    }
    /**
     * Setter for file picker view
     */
    public void setFilePickerView(FilePickerView fpView) {
        this.fpView = fpView;
    }

    /**
     * Setter for simulation settings view
     */
    public void setSimulationSettingsView(SimulationSettingsView ssView) {
        this.ssView = ssView;
    }

    /**
     * Setter for the simulation progress view
     */
    public void setSimulationProgressView(SimulationView smView) {
        this.smView = smView;
    }

    /**
     * Setter for the visualization controller
     */
    public void setVisualizationController(VisualizationController vController) {
        this.vController = vController;
    }

    /**
     * Action called when the next button in the file picker is selected
     */
    public void filePickerNext() {
        //Switch to next view
        SophiaView.switchView(SophiaView.SIMSETTINGPANEL);
    }

    /**
     * Action called when next button in the settings view is selected
     */
    public void simSettingsNext() {
        //Switch to next view
        SophiaView.switchView(SophiaView.SIMPROGRESSPANEL);

        //Kick off the simulation
        simulate();
    }

    /**
     * Action called when prev button in the settings view is selected
     */
    public void simSettingsPrev() {
        //Switch to previous view
        SophiaView.switchView(SophiaView.SFILEPICKERPANEL);
    }

    /**
     * Action called when the cancel button in the file picker is selected
     */
    public void filePickerCancel() {

    }

    /**
     * Action called when the visualize button is pressed in the simulator
     */
    public void simViewVisualize() {
        vController.setRecording(output);

        //Switch to visualization view
        SophiaView.switchView(SophiaView.PLAYBACKPANEL);
    }

    /**
     * Called by simulation thread to signal that simulation is complete
     */
    public void signalFinished(Recording output) {
        this.output = output;
        
    }

    /**
     * Creates the simulator and cluster from GUI selections then runs the simulation
     */
    public void simulate() {
        //Construct the cluster
        cluster = new Cluster(fpView.getCoordinateFileName(), fpView.getStructureFileName());

        //Determine type of simulator and construct
        String simulatorType = fpView.getSimulatorType();
        if(simulatorType == "MolecularDynamics") {
            MolecularDynamicsSimulator mdSim = new MolecularDynamicsSimulator();
            simulator = (Simulator)mdSim;
        } else {
            simulator = new NullSimulator();
        }

        //Set common simulation properties
        simulator.setTimeStep(ssView.getTimeStep());
        simulator.setNumSteps(ssView.getNumSteps());
        simulator.setOutputInterval(ssView.getOutputInterval());

        //Start the simulation thread
        new Thread((new SimulationRunner(simulator, cluster, smView.getProgressBar(), this))).start();
    }
}
