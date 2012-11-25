package edu.gatech.sophia;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;

/**
 *Contains image boxes for plots
 */
public class PlotView extends ChartPanel {
    /**
     * Constructs a new PlotView
     */
    public PlotView(JFreeChart initialChart) {
        //Initialize the ChartPanel
        super(initialChart);

        //Set the size of the panel
        setPreferredSize(new java.awt.Dimension(1000, 250));
    }
}
