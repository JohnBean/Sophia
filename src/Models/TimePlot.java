package edu.gatech.sophia;

import java.util.ArrayList;
import java.awt.Color;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;

/**
 * Plots one or more variables against time with a line graph
 */
public class TimePlot extends Plot {
    private ArrayList<String> lines;
    private boolean temperature;
    private boolean energy;

    /**
     * Constructs a new time plot
     */
    public TimePlot() {
        lines = new ArrayList<String>();
        temperature = false;
        energy = false;
    }

    /**
     * Add a new line to the plot by the name of the variable
     *
     * @param variable name of the variable to add a line for
     */
    public void addLine(String variable) {
        if(variable.equals("Temperature"))
            temperature = true;
        else
            energy = true;

        lines.add(variable);
    }

    /**
     * Generate the time plot from data in a recording
     *
     * @param recording the recording to pull data from
     */
    public void generate(Recording recording) {
        XYSeriesCollection dataset = new XYSeriesCollection();

        //Iterate through each line to put on the plot
        for(String variable : lines) {
            XYSeries series = new XYSeries(variable);

            //Make sure that the recording has this variable
            if(!recording.hasVariable(variable))
                continue;

            //Iterate through each frame
            int numFrames = recording.getNumFrames();
            for(int index = 0; index < numFrames; index++) {
                Frame currentFrame = recording.getFrame(index);

                series.add((double)index, currentFrame.getVariableValue(variable));
            }

            //Add the series to the dataset
            dataset.addSeries(series);
        }

        //Determine Y-axis label
        String label;
        if(temperature && energy)
            label = "Kelvins / kcal/mol";
        else if(temperature)
            label = "Temperature (K)";
        else if(energy)
            label = "kcal/mol";
        else
            label = "";

        //Create the chart
        chart = ChartFactory.createXYLineChart(
            "Time Plot",
            "Frame Number",
            label,
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
    }
}
