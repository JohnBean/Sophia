package edu.gatech.sophia;

/**
 * Controller for setting up and running simulations.
 * Allows users to select files to load cluster information from.
 */
public class SimulationController {
    /**
     * Currently loaded cluster
     */
    private Cluster cluster;

    /**
     * References to the views to allow sending and requesting data
     */
    private FilePickerView fpView;
    private MDSimulationSettingsView mssView;
    private EMSimulationSettingsView essView;
    private SimulationView smView;
    private TemperatureProtocolView tpView;

    /**
     * Reference to the visualization controller so that we can send the recording over one finished
     */
    private VisualizationController vController;

    /**
     * Reference to the plot controller so that we can send the recording over one finished
     */
    private PlotController pController;

    /*
     * Simulator object hold sim params and implements equations
     */
    private Simulator simulator;

    /**
     * Output from the simulator
     */
    private Recording output;

    /**
     * Construct a new simulation controller
     */
    public SimulationController() {
        
    }

    /**
     * Gets a reference to the recording generated by the simulator
     */
    public Recording getRecording(){
        return output;
    }

    /**
     * Setter for file picker view
     *
     * @param fpView the file picker view visible in the window
     */
    public void setFilePickerView(FilePickerView fpView) {
        this.fpView = fpView;
    }

    /**
     * Setter for simulation settings view
     *
     * @param mssView the molecular dynamics simulation settings view visible in the window
     * @param essView the energy minimization simulation settings view visible in the window
     */
    public void setSimulationSettingsView(MDSimulationSettingsView mssView, EMSimulationSettingsView essView) {
        this.mssView = mssView;
        this.essView = essView;
    }

    /**
     * Setter for temperature protocol view
     *
     * @param tpView the temperature protocol view visible in the window
     */
    public void setTemperatureProtocolView(TemperatureProtocolView tpView) {
        this.tpView = tpView;
    }

    /**
     * Setter for the simulation progress view
     *
     * @param smView the simulation progress view visible in the window
     */
    public void setSimulationProgressView(SimulationView smView) {
        this.smView = smView;
    }

    /**
     * Setter for the visualization controller
     *
     * @param vController the visualization controller
     */
    public void setVisualizationController(VisualizationController vController) {
        this.vController = vController;
    }

    /**
     * Setter for the plot controller
     *
     * @param pController the plot controller
     */
    public void setPlotController(PlotController pController) {
        this.pController = pController;
    }

    /**
     * Action called when the next button in the file picker is selected
     */
    public void filePickerNext() {
        //Switch to next view
        if(fpView.getSimulatorType().equals("EnergyMinimization"))
            SophiaView.switchView(SophiaView.EMSIMSETTINGPANEL);
        else
            SophiaView.switchView(SophiaView.SIMSETTINGPANEL);
    }

    /**
     * Action called when next button in the settings view is selected
     */
    public void simSettingsNext() {
        //Switch to next view
        SophiaView.switchView(SophiaView.SIMTEMPPANEL);
    }

    /**
     * Action called when prev button in the settings view is selected
     */
    public void simSettingsPrev() {
        //Switch to previous view
        SophiaView.switchView(SophiaView.SFILEPICKERPANEL);
    }

    /**
     * Action called when the next button is pressed on the temperature protocol view
     */
    public void finish() {
        //Switch to previous view
        SophiaView.switchView(SophiaView.SIMPROGRESSPANEL);

        //Kick off the simulation
        simulate();
    }

    /**
     * Action called when the next button is pressed on the temperature protocol view
     */
    public void tpSettingsPrev() {
        //Switch to previous view
        SophiaView.switchView(SophiaView.SIMSETTINGPANEL);
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
        pController.setRecording(output);

        //Switch to visualization view
        SophiaView.switchView(SophiaView.PLAYBACKPANEL);
    }

    /**
     * Called by simulation thread to signal that simulation is complete
     *
     * @param output the recording generated by the simulator
     */
    public void signalFinished(Recording output) {
        this.output = output;
        
        //Set the name of the recording
        output.setName(fpView.getSimulationName());
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

            //Set up properties not applicable to all simulators
            simulator.setTimeStep(mssView.getTimeStep());
            simulator.setNumSteps(mssView.getNumSteps());
            simulator.setOutputInterval(mssView.getOutputInterval());

            double initialTemp = mssView.getInitialTemp();
            int numDimensions = mssView.getNumDimensions();
            simulator.setInitialTemp(initialTemp);
            simulator.setNumDimensions(numDimensions);
            
            //Re-assign velocities based on initial temperature
            cluster.zeroVelocities();
            cluster.setVelocities("Reassignment", initialTemp, 0.0, numDimensions);

            //Set up bounding box if specified
            if(mssView.useBox())
                cluster.addAssociation(new BoundingBox(cluster.getAtoms(), mssView.getBoxSideLength()));

            //Set up the temperature protocol
            TemperatureProtocol tp = new TemperatureProtocol();
            TemperatureCycle tc;

            //Step 1
            tc = tpView.getCycle1();
            if(tc != null)
                tp.addCycle(tc);

            //Step 2
            tc = tpView.getCycle2();
            if(tc != null)
                tp.addCycle(tc);

            //Step 3
            tc = tpView.getCycle3();
            if(tc != null)
                tp.addCycle(tc);

            //Step 4
            tc = tpView.getCycle4();
            if(tc != null)
                tp.addCycle(tc);

            //Step 5
            tc = tpView.getCycle5();
            if(tc != null)
                tp.addCycle(tc);

            simulator.setTemperatureProtocol(tp);
        } else if(simulatorType == "EnergyMinimization") {
            //Get simulator specific properties
            double stepSize = essView.getStepSize();
            double convergenceCriteria = essView.getConvergenceCriterion();
            String method = essView.getMethod();

            //Set up new energy minimization simulator
            EnergyMinimizationSimulator emSim = new EnergyMinimizationSimulator(convergenceCriteria, stepSize, method);
            simulator = (Simulator)emSim;

            //Get any common properties
            simulator.setNumSteps(essView.getNumSteps());
            //TODO: add this to EM settings view
            simulator.setOutputInterval(10);
            simulator.setNumDimensions(3);
        } else {
            simulator = new NullSimulator();
        }

        //Start the simulation thread
        new Thread((new SimulationRunner(simulator, cluster, smView.getProgressBar(), this))).start();
    }
}
