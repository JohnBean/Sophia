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
        path = path.substring(0,path.length()-16);
        System.out.println("************Cluster Test Initiated***************");
        System.out.println(path);
        readFromFiles(path+"ethane_5.pdb", path+"ethane_5.sf");
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
        String curLine;//line being read in
       atoms= new ArrayList();// saved atoms
        Atom atom;
        try (FileReader fr = new FileReader(coordinateFilename) ){
            BufferedReader br = new BufferedReader(fr);
            System.out.println("reading");
            while ((curLine = br.readLine()) != null) {
                String[] tokens = curLine.split("[ ]+");
               
                 if(tokens[0].compareTo("ATOM")==0){
                    //              Atom     Molocule   ChainID     sequenceID                  location.x                      location.y              location.z                      Occupancy                   Temperature Factor              Mass                                Radius                      Charge
                    atom= new Atom(tokens[2],tokens[3],tokens[4],Integer.parseInt(tokens[5]),Float.parseFloat(tokens[6]),Float.parseFloat(tokens[7]),Float.parseFloat(tokens[8]),Double.parseDouble(tokens[9]),Double.parseDouble(tokens[10]),Double.parseDouble(tokens[11]),Double.parseDouble(tokens[12]),Double.parseDouble(tokens[13]));
                    atoms.add(atom);
                    System.out.println(atom.toString());
                   // atom.printString();
                   System.out.println(atoms.size()+" total atoms created.");
                 }
            }       
        }
        catch(IOException e){
            e.printStackTrace();
        }
        curLine=null;
        try (BufferedReader br = new BufferedReader(new FileReader(structureFilename))){
            while ((curLine = br.readLine()) != null) {
                String[] tokens = curLine.split("[ ]+");
		System.out.println(curLine);
                //TODO build atoms connections from data red in
            }       
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
