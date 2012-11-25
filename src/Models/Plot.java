package edu.gatech.sophia;

import org.jfree.chart.JFreeChart;

/**
 * Abstract class for plots in sophia. Handles generation from recording data.
 */
abstract class Plot {
    /**
     * The JFreeChart object that can be used for display
     */
    protected JFreeChart chart = null;

    /**
     * Get the chart for drawing
     *
     * @return a reference to the internal chart
     */
    public JFreeChart getChart() {
        return chart;
    }

    /**
     * Method to generate the plot from a recording. This should prepare the internal chart object for drawing.
     *
     * @param Recording a recording of a simulation with data to use for plotting
     */
    abstract void generate(Recording recording);
}
