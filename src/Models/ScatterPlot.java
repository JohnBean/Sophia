package edu.gatech.sophia;

import java.util.ArrayList;
import java.awt.Color;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

/**
 * Plots two variables against eachother in a scatter plot
 */
public class ScatterPlot extends Plot {
    private String variable1;
    private String variable2;

    /**
     * Constructs a new time plot
     */
    public ScatterPlot() {
        variable1 = null;
        variable2 = null;
    }

    /**
     * Sets the variable on the x-axis
     *
     * @param variable of variable to use on the x-axis
     */
    public void setXVariable(String variable) {
        variable1 = variable;
    }

    /**
     * Sets the variable on the y-axis
     *
     * @param variable of variable to use on the y-axis
     */
    public void setYVariable(String variable) {
        variable2 = variable;
    }

    /**
     * Generate the time plot from data in a recording
     *
     * @param recording the recording to pull data from
     */
    public void generate(Recording recording) {
        XYSeriesCollection dataset = new XYSeriesCollection();

        //Add points to a new series
        XYSeries series = new XYSeries("series");

        //Make sure that the recording has this variable
        if(!recording.hasVariable(variable1) || !recording.hasVariable(variable2))
            return;

        //Iterate through each frame
        int numFrames = recording.getNumFrames();
        for(int index = 0; index < numFrames; index++) {
            Frame currentFrame = recording.getFrame(index);

            series.add(currentFrame.getVariableValue(variable1), currentFrame.getVariableValue(variable2));
        }

        //Add the series to the dataset
        dataset.addSeries(series);

        //Add units to the variables for labels
        String xlabel;
        if(variable1.equals("Temperature"))
            xlabel = "" + variable1 + " (Kelvins)";
        else
            xlabel = "" + variable1 + " (kcal/mol)";

        String ylabel;
        if(variable2.equals("Temperature"))
            ylabel = "" + variable2 + " (Kelvins)";
        else
            ylabel = "" + variable2 + " (kcal/mol)";

        //Create the chart
        chart = ChartFactory.createXYLineChart(
            "Scatter Plot",
            xlabel,
            ylabel,
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false);
        chart.setBackgroundPaint(Color.white);

        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        //Change render style to just points
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, false);
        plot.setRenderer(renderer);
    }
}
