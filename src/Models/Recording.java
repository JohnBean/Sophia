package edu.gatech.sophia;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.io.*;

/**
 * Contains frames that can be rendered by the 3D view and
 * supports playback by fetching frames and information about the recording
 */
public class Recording {
    /**
     * A list of frames constaining per-frame data such as locations
     */
    private ArrayList<Frame> frames;

    /**
     * The cluster object describing the atoms and associations in this recording
     */
    private Cluster cluster;

    /**
     * Name of the recording for writing to files
     */
    private String name;

    /**
     * Constructs a new recording
     *
     * @param c The cluster containing atoms and associations represented
     */
    public Recording(Cluster c) {
        cluster = c;
        frames = new ArrayList<Frame>();
    }

    /**
     * Sets the name of the recording
     *
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the recording
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the number of frames stored in the recording
     *
     * @return number of frames
     */
    public int getNumFrames() {
        return frames.size();
    }

    /**
     * Adds a frame to the end of the recording
     *
     * @param frame the frame object to add
     */
    public void addFrame(Frame frame) {
        frames.add(frame);
    }

    /**
     * Gets the frame with requested index
     *
     * @param index Sequential number of the frame (starting from 0)
     * @return the frame object at this index
     */
    public Frame getFrame(int index) {
        return frames.get(index);
    }

    /**
     * Returns the cluster object represented by this recording
     *
     * @return cluster
     */
    public Cluster getCluster() {
        return cluster;
    }

    /**
     * Checks if the recording has values for a certain variable
     *
     * @param variable the name of the variable to check for
     * @return true if the variable exists in the recording
     */
    public boolean hasVariable(String variable) {
        if(frames != null && frames.size() > 0)
            return frames.get(0).hasVariable(variable);

        return false;
    }

    /**
     * Gets all variables that are available for plotting
     *
     * @return array of all variable names
     */
    public ArrayList<String> getAvailableVariables() {
        ArrayList<String> variables = new ArrayList<String>();
        
        //Defaults
        variables.add("Kinetic Energy");
        variables.add("Potential Energy");
        variables.add("Total Energy");
        variables.add("Temperature");

        Iterator it = frames.get(0).energies.keySet().iterator();
        while(it.hasNext()) {
            String key = (String)it.next();
            variables.add(key);
        }

        return variables;
    }

    /**
     * Saves the recording to files
     */
    public void writeRecord(){
        //String curLine;//line being read in
        int frameNumber;
        int stepNumber;
        int atomNumber;
        String curLine;//line being read in
        String pdbFile=cluster.pdbFile;
        ArrayList<Atom> atoms= this.getCluster().getAtoms();
        Atom curAtom;
        String name=this.getName();
        if(name==null||name.length()<2){
            name="test";
        }

        //Get all headers for other energy terms
        ArrayList<String> energyHeaders = new ArrayList<String>();
        Iterator it = frames.get(0).energies.keySet().iterator();
        while(it.hasNext()) {
            String key = (String)it.next();
            energyHeaders.add(key);
        }

        Frame curFrame;
         try{
           
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(name +".txt")));
            PrintWriter energyOut = new PrintWriter(new BufferedWriter(new FileWriter(name+"_at_0_K_energy.txt")));
            energyOut.print("Step\t");
            for(String energyType : energyHeaders)
                energyOut.print(energyType + "\t");
            energyOut.println("Potential\tKinetic\tTotal\tTemperature\tPressure");
            for(frameNumber=0; frameNumber<this.getNumFrames();frameNumber++){
                curFrame=this.getFrame(frameNumber);
                
                energyOut.print(frameNumber);
                for(String energyType : energyHeaders)
                    energyOut.print("\t"+String.valueOf(curFrame.energies.get(energyType)).trim().substring(0,Math.min(6,String.valueOf(curFrame.energies.get(energyType)).trim().length())));
                energyOut.print("\t");
                
                energyOut.print(String.valueOf(curFrame.potentialEnergy).trim().substring(0,Math.min(6,String.valueOf(curFrame.potentialEnergy).trim().length()))+"\t\t");
                energyOut.print(String.valueOf(curFrame.kineticEnergy).trim().substring(0,Math.min(6,String.valueOf(curFrame.kineticEnergy).trim().length()))+"\t");
                energyOut.print(String.valueOf(curFrame.totalEnergy).trim().substring(0,Math.min(6,String.valueOf(curFrame.totalEnergy).trim().length()))+"\t");
                energyOut.println(String.valueOf(curFrame.temperature).trim().substring(0,Math.min(6,String.valueOf(curFrame.temperature).trim().length()))+"\t\t"+"0");
                
                out.println("HEADER	Coordinates at Step "+frameNumber);
                String spaces = "             ";
                for(atomNumber=0; atomNumber<atoms.size();atomNumber++){
                   curAtom= atoms.get(atomNumber);
                   out.print("ATOM");
                   out.print(spaces.substring(0, 7-String.valueOf(atomNumber+1).length())+(atomNumber+1)); //7
                   out.print(spaces.substring(0, 5-curAtom.atomType.length())+ curAtom.atomType); //5
                   out.print(spaces.substring(0, 4-curAtom.moloculeName.length()) +curAtom.moloculeName); //4
                   out.print(" " + curAtom.chainId);//2
                   out.print(spaces.substring(0, 4-String.valueOf(curAtom.sequenceId).trim().length())+curAtom.sequenceId);//4
                   
                   String xOut=String.valueOf(Math.abs(curFrame.locations.get(atomNumber).x)).trim().substring(0,Math.min(5,String.valueOf(Math.abs(curFrame.locations.get(atomNumber).x)).trim().length()));
                   if(curFrame.locations.get(atomNumber).x<0){
                       xOut="-"+xOut;
                   }
                   String yOut=String.valueOf(Math.abs(curFrame.locations.get(atomNumber).y)).trim().substring(0,Math.min(5,String.valueOf(Math.abs(curFrame.locations.get(atomNumber).y)).trim().length()));
                   if(curFrame.locations.get(atomNumber).y<0){
                       yOut="-"+yOut;
                   }
                   String zOut=String.valueOf(Math.abs(curFrame.locations.get(atomNumber).z)).trim().substring(0,Math.min(5,String.valueOf(Math.abs(curFrame.locations.get(atomNumber).z)).trim().length()));
                   if(curFrame.locations.get(atomNumber).z<0){
                       zOut="-"+zOut;
                   }
                   out.print(spaces.substring(0, 12-xOut.length())+xOut);   //12                 
                   out.print(spaces.substring(0, 8-yOut.length())+yOut);  //8
                   out.print(spaces.substring(0, 8-zOut.length())+zOut); //8
                   
                   String occupancyOut =String.valueOf(curAtom.occupancy);
                   if(occupancyOut.length()<4){
                       occupancyOut=occupancyOut+"0";
                   }
                   out.print("  "  + occupancyOut );// "  0.00"
                   
                   String tempOut =String.valueOf(curAtom.temperatureFactor);
                   if(tempOut.length()<4){
                       tempOut=tempOut+"0";
                   }
                   out.print("  "  + tempOut);//"  1.00"
                   out.print("  "  + String.valueOf(curAtom.mass).substring(0,Math.min(4, String.valueOf(curAtom.mass).length())));
                   out.print("  "  + String.valueOf(curAtom.radius).substring(0,Math.min(4, String.valueOf(curAtom.radius).length())));
                   
                   String chargeOut =String.valueOf(curAtom.charge);
                   if(chargeOut.length()<4){
                       chargeOut=chargeOut+"0";
                   }
                   out.println("  "  + chargeOut);
                }
                if(frameNumber==0){//after the first set of atoms display the connections
                    FileReader fr = new FileReader(pdbFile);
                    BufferedReader br = new BufferedReader(fr);
                    while ((curLine = br.readLine()) != null) {
                        String[] atomInfo = curLine.split("[ ]+");
               
                        if(atomInfo[0].compareTo("CONECT")==0){
                            out.println(curLine);
                        }
                    }
                    
                }

                out.println("END");
            }
            out.close();
            energyOut.close();
         }
         catch(IOException e){
            e.printStackTrace();
        }
    }
}
