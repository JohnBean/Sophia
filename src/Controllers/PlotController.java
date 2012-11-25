package edu.gatech.sophia;

import java.awt.event.*;

public class PlotController {
    /**
     * Reference to the view that displays plots
     */
    private PlotView pView = null;

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
}
