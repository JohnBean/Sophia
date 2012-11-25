package edu.gatech.sophia;

import java.awt.event.*;
import java.util.ArrayList;

public class PlotController {
    /**
     * Reference to the view that displays plots
     */
    private PlotView pView = null;

    /**
     * Recording object with data to use for plotting
     */
    private Recording recording = null;

    /**
     * Current plot being displayed
     */
    private Plot plot = null;

    /**
     * Construct a new Plot Controller
     */
    public PlotController() {

    }

    /**
     * Set the plot view where controller can update the plot display
     *
     * @param pView the plot view
     */
    public void setPlotView(PlotView pView) {
        this.pView = pView;
    }

    /**
     * Sets the recording after simulation so that plots can be created
     *
     * @param recording the recording to use for plotting
     */
    public void setRecording(Recording recording) {
        this.recording = recording;

        //TODO: remove temporary
        ArrayList<String> lines = new ArrayList<String>();
        lines.add("Potential Energy");
        lines.add("Kinetic Energy");
        setupTimePlot(lines);
    }

    /**
     * Sets up a time plot with the provided lines
     *
     * @param lines the lines to create on the time plot
     */
    public void setupTimePlot(ArrayList<String> lines) {
        TimePlot tp = new TimePlot();

        for(String line : lines)
            tp.addLine(line);

        tp.generate(recording);

        plot = (Plot)tp;
        pView.setPlot(tp.getChart());
    }
}
