package edu.gatech.sophia;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FilePickerView extends JPanel {
	private JTextField sfNameField;
	private JFileChooser sfChooser;
	private JTextField cfNameField;
	private JFileChooser cfChooser;
	
	public FilePickerView() {
		//Set panel layout to box layout
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		//Initialize file choosers
		sfChooser = new JFileChooser();
		cfChooser = new JFileChooser();
		
		//Add coordinate file picker
		JLabel cfpLabel = new JLabel("Choose coordinate file");
		cfpLabel.setHorizontalTextPosition(JLabel.LEFT);
		this.add(cfpLabel);
		
		JPanel picker1 = new JPanel();
		picker1.setLayout(new BoxLayout(picker1, BoxLayout.LINE_AXIS));
		
		cfNameField = new JTextField(20);
		cfNameField.setEditable(false);
		picker1.add(cfNameField);
		
		JButton picker1Button = new JButton("...");
		picker1Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
            	cfChooser.showOpenDialog(null);
            }
        });
		picker1.add(picker1Button);
		picker1.setMaximumSize(picker1.getPreferredSize());
		this.add(picker1);
		
		//Add structure file picker
		JLabel sfpLabel = new JLabel("Choose structure file");
		sfpLabel.setHorizontalTextPosition(JLabel.LEFT);
		this.add(sfpLabel);
		
		JPanel picker2 = new JPanel();
		picker2.setLayout(new BoxLayout(picker2, BoxLayout.LINE_AXIS));
		
		sfNameField = new JTextField(20);
		sfNameField.setEditable(false);
		picker2.add(sfNameField);
		
		JButton picker2Button = new JButton("...");
		picker2Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
            	sfChooser.showOpenDialog(null);
            }
        });
		picker2.add(picker2Button);
		picker2.setMaximumSize(picker2.getPreferredSize());
		this.add(picker2);
	}
}
