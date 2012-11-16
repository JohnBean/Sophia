package edu.gatech.sophia;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.io.*;
/**
 * Contains frames that can be rendered by the 3D view and
 * has playback controls to allow playing, pausing, seeking.
 */
public class Recording {
    private ArrayList<Frame> frames;
    private Cluster cluster;

    public Recording(Cluster c) {
        cluster = c;
        frames = new ArrayList<Frame>();
    }

    public int getNumFrames() {
        return frames.size();
    }

    public void addFrame(Frame frame) {
        frames.add(frame);
    }

    public Frame getFrame(int index) {
        return frames.get(index);
    }

    public Cluster getCluster() {
        return cluster;
    }
    public void writeRecord(){
        //String curLine;//line being read in
        int frameNumber;
        int atomNumber;
        String curLine;//line being read in
        String pdbFile=cluster.pdbFile;
        ArrayList<Atom> atoms= this.getCluster().getAtoms();
        Atom curAtom;
        Frame curFrame;
         try{
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("foo.txt")));
            for(frameNumber=0; frameNumber<this.getNumFrames();frameNumber++){
                curFrame=this.getFrame(frameNumber);
                out.println("HEADER	Coordinates at Step "+frameNumber);
                for(atomNumber=0; atomNumber<atoms.size();atomNumber++){
                   curAtom= atoms.get(atomNumber);
                   out.print("ATOM\t"+atomNumber + "\t" + curAtom.atomType + "\t" +curAtom.moloculeName + " " + curAtom.chainId + "\t" + curAtom.sequenceId);
                   out.print("\t"+curFrame.locations.get(atomNumber).x+ "\t"+ curFrame.locations.get(atomNumber).y+ "\t"+curFrame.locations.get(atomNumber).z+ "\t" );
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
         }
         catch(IOException e){
            e.printStackTrace();
        }
    }
}
