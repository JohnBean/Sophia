package edu.gatech.sophia;
import java.io.*;
import java.security.CodeSource;
import javax.swing.JFileChooser;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sarah
 */
public class EMSimulationSettingsView extends javax.swing.JPanel {
    private SimulationController controller;
    private static JFileChooser jFileChooser1;
    /**
     * Creates new form EMSimulationSettingsView
     */
    public EMSimulationSettingsView(SimulationController controller) {
        this.controller = controller;
        jFileChooser1 = new JFileChooser();
        initComponents();
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        loadDefaultSettings();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {//GEN-BEGIN:initComponents

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel1.setText("EM Simulation Setup");

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 1, 10)); // NOI18N
        jLabel2.setText("Minimization Method");

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 1, 10)); // NOI18N
        jLabel3.setText("Initial Step Size");

        jLabel4.setFont(new java.awt.Font("Lucida Grande", 1, 10)); // NOI18N
        jLabel4.setText("Number of Steps");

        jLabel5.setFont(new java.awt.Font("Lucida Grande", 1, 10)); // NOI18N
        jLabel5.setText("Convergence Criterion");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Steepest Descent", "Conjugate Gradient" }));

        jTextField1.setText("0.0001");

        jTextField2.setText("10000");

        jTextField3.setText("0.00001");

        jButton1.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jButton1.setText("Next");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jButton2.setText("Back");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jLabel6.setText("steps");

        jTextField4.setText("100");

        jLabel7.setFont(new java.awt.Font("Lucida Grande", 1, 10)); // NOI18N
        jLabel7.setText("Output Interval");

        jButton3.setText("Save as Default");

        jButton4.setText("Load");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(124, 124, 124)
                        .add(jLabel1))
                    .add(layout.createSequentialGroup()
                        .add(22, 22, 22)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(layout.createSequentialGroup()
                                .add(jLabel2)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jComboBox1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 180, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(1, 1, 1))
                            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                                .add(layout.createSequentialGroup()
                                    .add(jButton3)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .add(jButton4))
                                .add(layout.createSequentialGroup()
                                    .add(jButton2)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .add(jButton1))
                                .add(layout.createSequentialGroup()
                                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                        .add(jLabel4)
                                        .add(jLabel3)
                                        .add(jLabel5)
                                        .add(jLabel7))
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                        .add(jTextField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 180, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(jTextField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 180, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(jTextField3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 180, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(jTextField4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 180, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel6)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(14, 14, 14)
                .add(jLabel1)
                .add(24, 24, 24)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(jComboBox1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jTextField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel3))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jTextField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel4)
                    .add(jLabel6))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jTextField3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel5))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jTextField4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel7))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 31, Short.MAX_VALUE)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jButton3)
                    .add(jButton4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButton1)
                    .add(jButton2))
                .add(21, 21, 21))
        );
    }//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
    //Button click actions
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        controller.finish();
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        controller.simSettingsPrev();
    }
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        writeDefaultSettings();
    }
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
        int choice = jFileChooser1.showOpenDialog(null);
        if(choice == JFileChooser.APPROVE_OPTION) {
            File chosenFile = jFileChooser1.getSelectedFile();
            String fname = chosenFile.getPath();
            if(fname.lastIndexOf('.') != -1)
                fname = fname.substring(0, fname.lastIndexOf('.'));
            writeSettings(fname);
        }
    }
    //variable getters
    public String getMethod() {
        return (String)(jComboBox1.getSelectedItem());
    }

    public double getStepSize() {
        return Double.parseDouble(jTextField1.getText());
    }

    public int getNumSteps() {
        return Integer.parseInt(jTextField2.getText());
    }

    public double getConvergenceCriterion() {
        return Double.parseDouble(jTextField3.getText());
    }

    public int getOutputInterval() {
        return Integer.parseInt(jTextField4.getText());
    }
    /**
     * Loads  settings into othe fields of this class
     */
    public void loadSettings(String settingsName){
        try{
           String curLine;
           FileReader fr = new FileReader(settingsName+".txt");//reads in the pdb
            BufferedReader br = new BufferedReader(fr);
           //populate the fields in the settings view from the default settings file
          while ((curLine = br.readLine()) != null) {
               String[] setting = curLine.split("[\t]+");//split by whitespace into an array to read
               if(setting[0].toString().compareTo("Minim Method")==0){
                  jComboBox1.setSelectedIndex(Integer.parseInt(setting[1]));
               }
			   
               if(setting[0].compareTo("Step Size")==0){
                   if(setting[1].compareTo("0.0")!=0&&setting[1].compareTo("0")!=0)jTextField1.setText(setting[1]);
               }
               
               if(setting[0].compareTo("Numsteps")==0){
                   jTextField2.setText(setting[1]);
               }
               
               if(setting[0].compareTo("Convergence")==0){
                   jTextField3.setText(setting[1]);
               }
			   
		if(setting[0].compareTo("Interval")==0){
                  jTextField4.setText(setting[1]);
               }
           }
           this.setVisible(true);
           br.close();
           fr.close();
        }
        catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }
     /**
     * Load the default settings into the fields from _EMSettings.txt
     */
    public void loadDefaultSettings(){
        try{
           File propFile;
           String curLine;
           CodeSource codeSource = EMSimulationSettingsView.class.getProtectionDomain().getCodeSource();
           File jarFile = new File(codeSource.getLocation().toURI().getPath());
           File jarDir = jarFile.getParentFile();
 
           propFile = new File(jarDir, "default_EMSettings.txt");
           FileReader fr = new FileReader(propFile);//reads in the pdb
           BufferedReader br = new BufferedReader(fr);
           while ((curLine = br.readLine()) != null) {
               String[] setting = curLine.split("[\t]+");//split by whitespace into an array to read
               if(setting[0].toString().compareTo("Minim Method")==0){
                  jComboBox1.setSelectedIndex(Integer.parseInt(setting[1]));
               }
			   
               if(setting[0].compareTo("Step Size")==0){
                   if(setting[1].compareTo("0.0")!=0&&setting[1].compareTo("0")!=0)jTextField1.setText(setting[1]);
               }
               
               if(setting[0].compareTo("Numsteps")==0){
                   jTextField2.setText(setting[1]);
               }
               
               if(setting[0].compareTo("Convergence")==0){
                   jTextField3.setText(setting[1]);
               }
			   
		if(setting[0].compareTo("Interval")==0){
                  jTextField4.setText(setting[1]);
               }
           }
           this.setVisible(true);
           br.close();
           fr.close();
        }
        catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }
    /**
     * Save the simulation settings into the fields from _EMSettings.txt
     */
    public void writeSettings(String name){
        try{
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(name + "_EMSettings.txt")));
            out.println("Minim Method\t"+jComboBox1.getSelectedIndex()); 

            if(jTextField1.getText().equals("")){
                out.println("Step Size\t0");
            }
            else{
                out.println("Step Size\t"+jTextField1.getText().trim());
            }

            if(jTextField2.getText().equals("")){
                out.println("Numsteps\t0");
            }
            else{
                out.println("Numsteps\t"+jTextField2.getText().trim());
            }

            if(jTextField3.getText().equals("")){
                out.println("Convergence\t0");
            }
            else{
                out.println("Convergence\t"+jTextField3.getText().trim());
            }

            if(jTextField4.getText().equals("")){
                out.println("Interval\t0");
            }
            else{
                out.println("Interval\t"+jTextField4.getText().trim());
            } 
            out.close();
            
        }
        catch(Exception e){
            System.out.println("Exception caught writting settings");
            e.printStackTrace();
        }
        
    }
    /**
     * Save the simulation settings into the fields from _EMSettings.txt
     */
    public void writeDefaultSettings(){
        try{
            File propFile;
            CodeSource codeSource = EMSimulationSettingsView.class.getProtectionDomain().getCodeSource();
            File jarFile = new File(codeSource.getLocation().toURI().getPath());
            File jarDir = jarFile.getParentFile();
            propFile = new File(jarDir, "default_EMSettings.txt");

            // Does the file already exist 
            if(!propFile.exists()){ 
                try{ 
                    // Try creating the file 
                    propFile.createNewFile(); 
                }
                catch(IOException ioe) { 
                    ioe.printStackTrace(); 
                } 
            }
            try{
                PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(propFile)));
                out.println("Minim Method\t"+jComboBox1.getSelectedIndex()); 

                if(jTextField1.getText().equals("")){
                    out.println("Step Size\t0");
                }
                else{
                    out.println("Step Size\t"+jTextField1.getText().trim());
                }

                if(jTextField2.getText().equals("")){
                    out.println("Numsteps\t0");
                }
                else{
                    out.println("Numsteps\t"+jTextField2.getText().trim());
                }

                if(jTextField3.getText().equals("")){
                    out.println("Convergence\t0");
                }
                else{
                    out.println("Convergence\t"+jTextField3.getText().trim());
                }

                if(jTextField4.getText().equals("")){
                    out.println("Interval\t0");
                }
                else{
                    out.println("Interval\t"+jTextField4.getText().trim());
                } 
                out.close();
            }
            catch(Exception e){
                System.out.println("Exception caught writting settings");
                e.printStackTrace();
            }
        }
        catch(Exception e){
            System.out.println("Exception caught writting settings");
            e.printStackTrace();
        }
        
    }
}
