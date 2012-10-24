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
        path = path.substring(0,path.length()-16);
        System.out.println("************Cluster Test Initiated***************");
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
        try{
            FileReader fr = new FileReader(coordinateFilename);
            BufferedReader br = new BufferedReader(fr);
            System.out.println("reading");
            while ((curLine = br.readLine()) != null) {
                String[] atomInfo = curLine.split("[ ]+");
               
                 if(atomInfo[0].compareTo("ATOM")==0){
                    //              Atom     Molocule   ChainID     sequenceID                  location.x                      location.y              location.z                      Occupancy                   Temperature Factor              Mass                                Radius                      Charge
                    atom= new Atom(atomInfo[2],atomInfo[3],atomInfo[4],Integer.parseInt(atomInfo[5]),Float.parseFloat(atomInfo[6]),Float.parseFloat(atomInfo[7]),Float.parseFloat(atomInfo[8]),Double.parseDouble(atomInfo[9]),Double.parseDouble(atomInfo[10]),Double.parseDouble(atomInfo[11]),Double.parseDouble(atomInfo[12]),Double.parseDouble(atomInfo[13]));
                    atoms.add(atom);
                    System.out.println(atom.toString());
                   // atom.printString();
                   
                 }
            }
            System.out.println(atoms.size()+" total atoms created.");
        }
        catch(IOException e){
            e.printStackTrace();
        }
        curLine=null;
        try{
            BufferedReader br = new BufferedReader(new FileReader(structureFilename));
            while ((curLine = br.readLine()) != null) {
                String[] structInfo = curLine.split("[ \t\n\f\r]");
                if(structInfo[0].compareTo("BOND")==0){
                    //System.out.println(curLine);
                }
                else if(structInfo[0].compareTo("ANGLE")==0){
                    //System.out.println(curLine);
                }
             //  System.out.println(struct[1]);
		
                //TODO build atoms connections from data red in
            }       
        }
        catch(IOException e){
            e.printStackTrace();
        }
        System.out.println("************Cluster Test Completed Successfully***************");
    }
}
