package edu.gatech.sophia;

import ApplicationFrame;
import ChartPanel;
import JFreeChart;
import NumberAxis;
import XYDataset;
import XYLineAndShapeRenderer;
import XYPlot;
import XYSeries;
import XYSeriesCollection;

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

import Controllers.Double;
import Controllers.HashMap;
import Controllers.String;
import TestView.EnergyGraph;

/**
 *Contains image boxes for plots
 */
public class PlotView extends JPanel {
	private static final long serialVersionUID = 1L;
	private EnergyGraph eg; ;
	
	public PlotView() {
		this.setPreferredSize(new Dimension(200,200));
		this.eg = new EnergyGraph("Energy Graph");
		eg.pack();
        RefineryUtilities.centerFrameOnScreen(eg);
        eg.setVisible(true);
	}
	
	private class EnergyGraph extends ApplicationFrame {
		private static final long serialVersionUID = 1L;


		public EnergyGraph(String title) {
			super(title);
		}
		
		public void plotEnergies(HashMap<String, Double> energies){
			final XYDataset dataset = createDataset(energies);
	        final JFreeChart chart = createChart(dataset);
	        final ChartPanel chartPanel = new ChartPanel(chart);
	        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
	        setContentPane(chartPanel);
		}
		
		private XYDataset createDataset(HashMap<String, Double> energyFile) {
			 	final XYSeries potentialEnergy = new XYSeries("Potential Energy");
		     	final XYSeries kineticEnergy = new XYSeries("Kinetic Energy");
		     	final XYSeries totalEnergy = new XYSeries("Total Energy");
		      		   	
		     	//
		        //potentialEnergy.add(energies.p);

		        final XYSeriesCollection energySet= new XYSeriesCollection();
		        energySet.addSeries(potentialEnergy);
		        energySet.addSeries(kineticEnergy);
		        energySet.addSeries(totalEnergy);
		                
		        return energySet;
		}
		
		
		private JFreeChart createChart(XYDataset ds){
		       
			//Creates the actual chart 
			//Title, X-axis, Y-axis, Dataset, Orientation, Legend, ToolTips, URLs					
			final JFreeChart chart = ChartFactory.createXYLineChart("Energy Graph", "Time (ps)", "Energy in (?)", ds, PlotOrientation.VERTICAL, true, false, false);
			chart.setBackgroundPaint(Color.white);
	        final XYPlot plot = chart.getXYPlot();
	        plot.setBackgroundPaint(Color.lightGray);
	        plot.setDomainGridlinePaint(Color.white);
	        plot.setRangeGridlinePaint(Color.white);
	        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
	        renderer.setSeriesLinesVisible(0, false);
	        renderer.setSeriesShapesVisible(1, false);
	        plot.setRenderer(renderer);
	        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
	        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	        return chart;     
	    }
	   
	}
}
