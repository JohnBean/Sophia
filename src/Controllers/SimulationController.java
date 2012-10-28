package edu.gatech.sophia;

public class SimulationController {
    /**
     * Currently loaded cluster
     */
    private Cluster cluster;
    private FilePickerView fpView;
    private VisualizationController vController;

    public SimulationController() {
        
    }

    /**
     * Setter for file picker view
     */
    public void setFilePickerView(FilePickerView fpView) {
        this.fpView = fpView;
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
        cluster = new Cluster(fpView.getCoordinateFileName(), fpView.getStructureFileName());

        //TODO: Remove test code
        //Set up test simulator and generate a single frame to show in viewer
        NullSimulator simulator = new NullSimulator();
        vController.setRecording(simulator.run(cluster));
    }

    /**
     * Action called when the cancel button in the file picker is selected
     */
    public void filePickerCancel() {

    }
}
