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
        String path= System.getProperty("java.class.path");
         System.out.println(path);
        path = path.substring(0,path.length()-16)+"test.pdb";
        System.out.println("************Cluster Test Initiated***************");
        System.out.println(System.getProperty("user.dir"));
        System.out.println(path);
        readFromFiles(path, "structureFilename");
    }

    public Cluster(String coordinateFilename, String structureFilename) {
        System.out.println("************Cluster Initiated***************");
         
    }

    /**
     * Should read cluster information from a coordinate file and structure file into the current object
     *
     * @param coordinateFilename The name of the file to read atoms from (.pdb file)
     * @param structureFilename The name of the file to read associations from (.sf file)
     */
   public void readFromFiles(String coordinateFilename, String structureFilename) {
        String curLine;
        
        try (FileReader fr = new FileReader(coordinateFilename) ){
            BufferedReader br = new BufferedReader(fr);
            System.out.println("reading");
            while ((curLine = br.readLine()) != null) {
             /*   if(curLine.substring(0,4).compareTo("ATOM")==0){
                    System.out.println("this is an atom:"+curLine);
                }*/
		System.out.println(curLine);
                //TODO build atoms from data red in
            }       
        }
        catch(IOException e){
            e.printStackTrace();
        }
      /*  sCurrentLine=null;
        try (BufferedReader br = new BufferedReader(new FileReader(structureFilename))){
            while ((sCurrentLine = br.readLine()) != null) {
		System.out.println(sCurrentLine);
                //TODO build atoms connections from data red in
            }       
        }
        catch(IOException e){
            e.printStackTrace();
        }*/
    }
}
