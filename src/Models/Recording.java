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
        Frame curFrame;
         try{
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(name +".txt")));
            PrintWriter energyOut = new PrintWriter(new BufferedWriter(new FileWriter(name+"_at_0_K_energy.txt")));
            energyOut.println("Step\tVDW\tBond\tAngle\tPotential      Kinetic\tTotal\tTemperature\tPressure");
            for(frameNumber=0; frameNumber<this.getNumFrames();frameNumber++){
                curFrame=this.getFrame(frameNumber);
                
                energyOut.print(frameNumber+"\t"+String.valueOf(curFrame.energies.get("VDW")).trim().substring(0,Math.min(6,String.valueOf(curFrame.energies.get("VDW")).trim().length())));
                energyOut.print("\t"+String.valueOf(curFrame.energies.get("Bond")).trim().substring(0,Math.min(6,String.valueOf(curFrame.energies.get("Bond")).trim().length())));
                energyOut.print("\t"+String.valueOf(curFrame.energies.get("Angle")).trim().substring(0,Math.min(6,String.valueOf(curFrame.energies.get("Angle")).trim().length()))+"\t");
                energyOut.print(String.valueOf(curFrame.potentialEnergy).trim().substring(0,Math.min(6,String.valueOf(curFrame.potentialEnergy).trim().length()))+"\t\t");
                energyOut.print(String.valueOf(curFrame.kineticEnergy).trim().substring(0,Math.min(6,String.valueOf(curFrame.kineticEnergy).trim().length()))+"\t");
                energyOut.print(String.valueOf(curFrame.totalEnergy).trim().substring(0,Math.min(6,String.valueOf(curFrame.totalEnergy).trim().length()))+"\t");
                energyOut.println(String.valueOf(curFrame.temperature).trim().substring(0,Math.min(6,String.valueOf(curFrame.temperature).trim().length()))+"\t\t"+"0");
                
                out.println("HEADER	Coordinates at Step "+frameNumber);
                for(atomNumber=0; atomNumber<atoms.size();atomNumber++){
                   curAtom= atoms.get(atomNumber);
                   out.print("ATOM\t"+atomNumber + "\t" + curAtom.atomType + "\t" +curAtom.moloculeName + " " + curAtom.chainId + "\t" + curAtom.sequenceId);
                   out.print("\t"+String.valueOf(curFrame.locations.get(atomNumber).x).trim().substring(0,Math.min(6,String.valueOf(curFrame.locations.get(atomNumber).x).trim().length()))+ "\t");
                   out.print(String.valueOf(curFrame.locations.get(atomNumber).y).trim().substring(0,Math.min(6,String.valueOf(curFrame.locations.get(atomNumber).y).trim().length()))+ "\t");
                   out.print(String.valueOf(curFrame.locations.get(atomNumber).z).trim().substring(0,Math.min(6,String.valueOf(curFrame.locations.get(atomNumber).z).trim().length()))+ "\t" );
                   out.println(curAtom.occupancy +"\t"+ curAtom.temperatureFactor+ "\t" + curAtom.mass + "\t"+ curAtom.radius + "\t" +curAtom.charge);
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
            }
            out.close();
            energyOut.close();
         }
         catch(IOException e){
            e.printStackTrace();
        }
    }
}
