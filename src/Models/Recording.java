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
     * The size of each step for the output
     */
    protected double stepSize;
    /**
     * The unique id of the type of simulation
     */
    protected int type;
    /**
     * The number of steps between each frame
     */
    protected int outputInterval;
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
    public Recording(Cluster c, int outputInterval) {
        cluster = c;
        this.outputInterval=outputInterval;
        frames = new ArrayList<Frame>();
    }
    /**
     * Constructor used when loading an old recording
     *
     * @param c The cluster containing atoms and associations represented
     */
    public Recording(Cluster c) {
        cluster = c;
        frames = new ArrayList<Frame>();
        if(cluster.recordName!=null){
            this.loadRecord(cluster.recordName);
        }
        
    }
    /**
     * Sets the name of the recording
     *
     * @param Int type
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * Gets the type of the recording
     *
     * @return type
     */
    public Integer getType() {
        return type;
    }
    /**
     * Sets the size of the timestep
     *
     * @param Double step size
     */
    public void setStep(Double timeStep) {
        this.stepSize = timeStep;
    }

    /**
     * Gets the time between frames
     *
     * @return timestep
     */
    public Double getStep() {
        return stepSize;
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
        if(frames.size()>0){
            return frames.get(index);
        }
        return new Frame();
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
    public void loadRecord(String recordName){
        String curLine;//line being read in
        Frame curFrame=null;
        ArrayList<Point3D> locations= new ArrayList<Point3D>();
        try{
            FileReader fr = new FileReader(recordName+".pdb");//reads in the pdb
            BufferedReader br = new BufferedReader(fr);
            System.out.println("Reading in frames from recording");
            if(br!=null){
                curFrame= new Frame();
            }
            while ((curLine = br.readLine()) != null) {
                String[] atomInfo = curLine.split("[ ]+");//split by whitespace into an array to read
                if(atomInfo[0].compareTo("END")==0){
                     curFrame.setLocations(locations);
                     addFrame(curFrame);
                     curFrame= new Frame();
                     locations= new ArrayList<Point3D>();
                 }
                if(curFrame!=null){//only start 
                    if(atomInfo[0].compareTo("ATOM")==0){
                        locations.add(new Point3D(Double.parseDouble(atomInfo[6]),Double.parseDouble(atomInfo[7]),Double.parseDouble(atomInfo[8])));
                    }
                }  
            }
            br.close();
            fr.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    /**
     * Saves the recording to files
     */
    public void writeRecord(String name){
        //String curLine;//line being read in
        int frameNumber;
        int stepNumber;
        int atomNumber;
        String curLine;//line being read in
        String pdbFile=cluster.pdbFile;
        ArrayList<Atom> atoms= this.getCluster().getAtoms();
        Atom curAtom;

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
            final File file = new File(".");//write the outputs
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(name + "_trajectory.pdb")));
            PrintWriter energyOut = new PrintWriter(new BufferedWriter(new FileWriter(name + "_energy.txt")));
            energyOut.print("Step");//write all fields that are populated
            if(energyHeaders.contains("Bond")){
                energyOut.print("\tBond");
            }
            if(energyHeaders.contains("Angle")){
                energyOut.print("\tAngle");
            }
            
            if(energyHeaders.contains("Torsion")){
                energyOut.print("\tTorsion");
            }
            if(energyHeaders.contains("VDW")){
                energyOut.print("\tVDW");
            }
            if(energyHeaders.contains("Coulomb")){
                energyOut.print("\tElectro");
            }
            energyOut.print("\tPotn\tKinetic\tTotal\t");
            if(type==1){
                energyOut.println("Temperature");
            }
            else if(type==2){
                energyOut.println("Step Size");
            }
            //print the energies at each frame
            for(frameNumber=0; frameNumber<this.getNumFrames();frameNumber++){
                curFrame=this.getFrame(frameNumber);
                
                energyOut.print((frameNumber)*outputInterval);

                if(energyHeaders.contains("Bond")){
                    energyOut.print("\t"+String.valueOf(curFrame.energies.get("Bond")).trim().substring(0,Math.min(6,String.valueOf(curFrame.energies.get("Bond")).trim().length())));
                }
                if(energyHeaders.contains("Angle")){
                    energyOut.print("\t"+String.valueOf(curFrame.energies.get("Angle")).trim().substring(0,Math.min(6,String.valueOf(curFrame.energies.get("Angle")).trim().length())));
                }
                if(energyHeaders.contains("Torsion")){
                    energyOut.print("\t"+String.valueOf(curFrame.energies.get("Torsion")).trim().substring(0,Math.min(6,String.valueOf(curFrame.energies.get("Torsion")).trim().length())));
                }
                if(energyHeaders.contains("VDW")){
                    energyOut.print("\t"+String.valueOf(curFrame.energies.get("VDW")).trim().substring(0,Math.min(6,String.valueOf(curFrame.energies.get("VDW")).trim().length())));
                }
                if(energyHeaders.contains("Coulomb")){
                    energyOut.print("\t"+String.valueOf(curFrame.energies.get("Coulomb")).trim().substring(0,Math.min(6,String.valueOf(curFrame.energies.get("Coulomb")).trim().length())));
                }
                energyOut.print("\t"+String.valueOf(curFrame.potentialEnergy).trim().substring(0,Math.min(6,String.valueOf(curFrame.potentialEnergy).trim().length()))+"\t");
                energyOut.print(String.valueOf(curFrame.kineticEnergy).trim().substring(0,Math.min(6,String.valueOf(curFrame.kineticEnergy).trim().length()))+"\t");
                energyOut.print(String.valueOf(curFrame.totalEnergy).trim().substring(0,Math.min(6,String.valueOf(curFrame.totalEnergy).trim().length()))+"\t");
                if(type==1){
                   energyOut.println(String.valueOf(curFrame.temperature).trim().substring(0,Math.min(6,String.valueOf(curFrame.temperature).trim().length())));
                }
                else if(type==2){
                    energyOut.println(String.valueOf(curFrame.getTime()).trim().substring(0,Math.min(6,String.valueOf(curFrame.getTime()).trim().length())));
                }
                out.println("HEADER	Coordinates at Step "+(frameNumber)*outputInterval);
                String spaces = "             ";//lets you pull a variable number of spaces
                for(atomNumber=0; atomNumber<atoms.size();atomNumber++){
                   curAtom= atoms.get(atomNumber);
                   out.print("ATOM");//each part uses a specific number of spaces. This requires being kept in Fortran standareds
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
                   out.print("  "  + occupancyOut );
                   
                   String tempOut =String.valueOf(curAtom.temperatureFactor);
                   if(tempOut.length()<4){
                       tempOut=tempOut+"0";
                   }
                   out.print("  "  + tempOut);
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
