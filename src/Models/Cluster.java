package edu.gatech.sophia;

import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/**
 * Contains information about atoms and their associations (bonds, torsions, etc)
 * Can be thought of as a graph stored as an edgelist where atoms are nodes and
 * associations are edges.
 */
public class Cluster {
    private ArrayList<Atom> atoms;
    private ArrayList<AtomAssociation> associations;

    public Cluster() {

    }

    public Cluster(String coordinateFilename, String structureFilename) {
        System.out.println("************Cluster Initiated***************");
        readFromFiles(coordinateFilename, structureFilename); 
    }

    /**
     * Should read cluster information from a coordinate file and structure file into the current object
     *
     * @param coordinateFilename The name of the file to read atoms from (.pdb file)
     * @param structureFilename The name of the file to read associations from (.sf file)
     */
   public void readFromFiles(String coordinateFilename, String structureFilename) {
        String sCurrentLine;
        try (BufferedReader br = new BufferedReader(new FileReader(coordinateFilename))){
            while ((sCurrentLine = br.readLine()) != null) {
		System.out.println(sCurrentLine);
                //TODO build atoms from data red in
            }       
        }
        catch(IOException e){
            e.printStackTrace();
        }
        sCurrentLine=null;
        try (BufferedReader br = new BufferedReader(new FileReader(structureFilename))){
            while ((sCurrentLine = br.readLine()) != null) {
		System.out.println(sCurrentLine);
                //TODO build atoms connections from data red in
            }       
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
