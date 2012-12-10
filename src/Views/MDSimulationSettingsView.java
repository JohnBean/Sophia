package edu.gatech.sophia;

import java.io.*;
import java.security.CodeSource;
import javax.swing.JPanel;

/**
 *Contains GUI elements to set up simulation
 */
public class MDSimulationSettingsView extends JPanel {
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    // End of variables declaration//GEN-END:variables

    private SimulationController controller;

    public MDSimulationSettingsView(SimulationController simController) {
        jTextField6 = new javax.swing.JTextField();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jComboBox2 = new javax.swing.JComboBox();
        


        this.controller = simController;

        setPreferredSize(new java.awt.Dimension(688, 371));
        setSize(new java.awt.Dimension(1200, 1600));

        jButton1.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jButton1.setText("Next");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jButton3.setText("Back");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel1.setText("MD Simulation Setup");

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 1, 10)); // NOI18N
        jLabel2.setText("Integration Method");

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 1, 10)); // NOI18N
        jLabel3.setText("Time Step");

        jLabel4.setFont(new java.awt.Font("Lucida Grande", 1, 10)); // NOI18N
        jLabel4.setText("Number of Steps");

        jLabel5.setFont(new java.awt.Font("Lucida Grande", 1, 10)); // NOI18N
        jLabel5.setText("Initial Temperature");

        jLabel6.setFont(new java.awt.Font("Lucida Grande", 1, 10)); // NOI18N
        jLabel6.setText("Box Side Length");

        jLabel7.setFont(new java.awt.Font("Lucida Grande", 1, 10)); // NOI18N
        jLabel7.setText("Number of Dimensions");

        jLabel8.setFont(new java.awt.Font("Lucida Grande", 1, 10)); // NOI18N
        jLabel8.setText("Output Interval");

        jLabel9.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jLabel9.setText("picoseconds");

        jLabel10.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jLabel10.setText("steps");

        jLabel11.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jLabel11.setText("K");

        jLabel12.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jLabel12.setText("angstroms");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Default" }));
        jComboBox1.setSelectedIndex(-1);

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3" }));
        jComboBox2.setSelectedIndex(2);
        
        jButton2.setText("Save as Default");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jButton4.setText("Load");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(16, 16, 16)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(jLabel8)
                            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                    .add(jLabel2)
                                    .add(jLabel4))
                                .add(layout.createSequentialGroup()
                                    .add(18, 18, 18)
                                    .add(jLabel6)))
                            .add(jLabel7)
                            .add(jLabel5)
                            .add(jLabel3))
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                                .add(layout.createSequentialGroup()
                                    .add(6, 6, 6)
                                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                        .add(jTextField7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 130, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                            .add(jButton1)
                                            .add(layout.createSequentialGroup()
                                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                                    .add(org.jdesktop.layout.GroupLayout.LEADING, jComboBox2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 130, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                    .add(org.jdesktop.layout.GroupLayout.LEADING, jTextField5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 130, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                    .add(org.jdesktop.layout.GroupLayout.LEADING, jTextField4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 130, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                                    .add(jLabel11)
                                                    .add(jLabel12)))
                                            .add(jButton4))))
                                .add(layout.createSequentialGroup()
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                        .add(jTextField3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 130, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .add(jTextField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 130, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                        .add(jLabel9)
                                        .add(jLabel10))))
                            .add(layout.createSequentialGroup()
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jComboBox1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 130, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                    .add(layout.createSequentialGroup()
                        .add(jLabel1)
                        .add(67, 67, 67)))
                .add(0, 350, Short.MAX_VALUE))
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jButton3)
                    .add(jButton2))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(jComboBox1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel3)
                    .add(jTextField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel9))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel4)
                    .add(jTextField3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel10))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jTextField4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel5)
                    .add(jLabel11))
                .add(10, 10, 10)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel12, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 13, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jTextField5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel6)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jComboBox2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel7))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jTextField7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel8))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 16, Short.MAX_VALUE)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButton2)
                    .add(jButton4))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButton3)
                    .add(jButton1))
                .add(41, 41, 41))
        );
        loadDefaultSettings();
    }
    //Actions from button presses
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        controller.simSettingsNext();
    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        writeDefaultSettings();
    }
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        controller.simSettingsPrev();
    }
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
       loadDefaultSettings();
    }
    //getters from the entry fields
    public double getTimeStep() {
        return Double.parseDouble(jTextField2.getText());
    }

    public int getNumSteps() {
        return Integer.parseInt(jTextField3.getText());
    }

    public int getOutputInterval() {
        return Integer.parseInt(jTextField7.getText());
    }

    public double getBoxSideLength() {
        return Double.parseDouble(jTextField5.getText());
    }

    public int getNumDimensions() {
        return Integer.parseInt((String)jComboBox2.getSelectedItem());
    }

    public double getInitialTemp() {
        return Double.parseDouble(jTextField4.getText());
    }
    /**
     * Loads  settings into othe fields of this class
     */
    public void loadSettings(String recordName){
        try{
           String curLine;
           FileReader fr = new FileReader(recordName+".txt");//reads in the pdb
            BufferedReader br = new BufferedReader(fr);
           //populate the fields in the settings view from the default settings file
           while ((curLine = br.readLine()) != null) {
               String[] setting = curLine.split("[\t]+");//split by whitespace into an array to read
               if(setting[0].toString().compareTo("Timestep")==0){
                  jTextField2.setText(setting[1].toString());
               }
               if(setting[0].compareTo("Numsteps")==0){
                   jTextField3.setText(setting[1]);
               }
               if(setting[0].compareTo("Interval")==0){
                  jTextField7.setText(setting[1]);
               }
               if(setting[0].compareTo("Box Size")==0){
                   if(setting[1].compareTo("0.0")!=0&&setting[1].compareTo("0")!=0)jTextField5.setText(setting[1]);
               }
               if(setting[0].compareTo("Dimensions")==0){
                   jComboBox2.setSelectedIndex(Integer.parseInt(setting[1])-1);
               }
               if(setting[0].compareTo("Initial Temp")==0){
                   if(setting[1].compareTo("0.0")!=0&&setting[1].compareTo("0")!=0){
                       jTextField4.setText(setting[1]+"");
                   }
                   else{
                       jTextField4.setText("");
                   }
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
     * Loads the default settings into othe fields of this class
     */
    public void loadDefaultSettings(){
        try{
           File propFile;
           String curLine;
           CodeSource codeSource = MDSimulationSettingsView.class.getProtectionDomain().getCodeSource();
           File jarFile = new File(codeSource.getLocation().toURI().getPath());
           File jarDir = jarFile.getParentFile();
           
           propFile = new File(jarDir, "default_MDSettings.txt");
           FileReader fr = new FileReader(propFile);//reads in the pdb
           BufferedReader br = new BufferedReader(fr);
           //populate the fields in the settings view from the default settings file
           while ((curLine = br.readLine()) != null) {
               String[] setting = curLine.split("[\t]+");//split by whitespace into an array to read
               if(setting[0].toString().compareTo("Timestep")==0){
                  jTextField2.setText(setting[1].toString());
               }
               if(setting[0].compareTo("Numsteps")==0){
                   jTextField3.setText(setting[1]);
               }
               if(setting[0].compareTo("Interval")==0){
                  jTextField7.setText(setting[1]);
               }
               if(setting[0].compareTo("Box Size")==0){
                   if(setting[1].compareTo("0.0")!=0&&setting[1].compareTo("0")!=0)jTextField5.setText(setting[1]);
               }
               if(setting[0].compareTo("Dimensions")==0){
                   jComboBox2.setSelectedIndex(Integer.parseInt(setting[1])-1);
               }
               if(setting[0].compareTo("Initial Temp")==0){
                   if(setting[1].compareTo("0.0")!=0&&setting[1].compareTo("0")!=0){
                       jTextField4.setText(setting[1]+"");
                   }
                   else{
                       jTextField4.setText("");
                   }
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
            if(jTextField2.getText().equals("")){
                 out.println("Timestep\t0");
            }
            else{
                 out.println("Timestep\t"+jTextField2.getText().trim()); 
            }
            if(jTextField3.getText().equals("")){
                 out.println("Numsteps\t0");
            }
            else{
                out.println("Numsteps\t"+jTextField3.getText().trim());
            }
            if(jTextField3.getText().equals("")){
                 out.println("Interval\t0");
            }
            else{
                out.println("Interval\t"+jTextField7.getText().trim());
            }
            if(jTextField4.getText().equals("")){
                 out.println("Initial Temp\t0");
            }
            else{
                out.println("Initial Temp\t"+jTextField4.getText().trim());
            } 
            out.println("Dimensions\t"+getNumDimensions());
            if(jTextField5.getText().equals("")){
                 out.println("Box Size\t0");
             }
             else{
                out.println("Box Size\t"+jTextField5.getText().trim());
             } 
            out.close();
            
        }
        catch(Exception e){
            System.out.println("Exception caught writting settings");
            e.printStackTrace();
        }
        
    }
     /**
     * Write all of the settings to a file designated _MDSettings.txt
     * 
     */
    public void writeDefaultSettings(){
        try{
            File propFile;
            CodeSource codeSource = MDSimulationSettingsView.class.getProtectionDomain().getCodeSource();
            File jarFile = new File(codeSource.getLocation().toURI().getPath());
            File jarDir = jarFile.getParentFile();
            propFile = new File(jarDir, "default_MDSettings.txt");
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
            //write the contents of each field to the settings file
            try{
                 PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(propFile)));
                 if(jTextField2.getText().equals("")){
                     out.println("Timestep\t0");
                 }
                 else{
                     out.println("Timestep\t"+jTextField2.getText().trim()); 
                 }
                 if(jTextField3.getText().equals("")){
                     out.println("Numsteps\t0");
                 }
                 else{
                    out.println("Numsteps\t"+jTextField3.getText().trim());
                 }
                 if(jTextField3.getText().equals("")){
                     out.println("Interval\t0");
                 }
                 else{
                    out.println("Interval\t"+jTextField7.getText().trim());
                 }
                if(jTextField4.getText().equals("")){
                     out.println("Initial Temp\t0");
                 }
                 else{
                    out.println("Initial Temp\t"+jTextField4.getText().trim());
                 } 
                out.println("Dimensions\t"+getNumDimensions());
                if(jTextField5.getText().equals("")){
                     out.println("Box Size\t0");
                 }
                 else{
                    out.println("Box Size\t"+jTextField5.getText().trim());
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
    public boolean useBox() {
        if(jTextField5.getText().equals(""))
            return false;

        if(Double.parseDouble(jTextField5.getText()) <= 0.0)
            return false;

        return true;
    }
}
