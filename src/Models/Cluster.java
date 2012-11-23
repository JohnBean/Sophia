package edu.gatech.sophia;

import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.awt.Color;

/**
 * Contains information about atoms and their associations (bonds, torsions, etc)
 * Can be thought of as a graph stored as an edgelist where atoms are nodes and
 * associations are edges.
 */
public class Cluster {
    private ArrayList<Atom> atoms;
    private ArrayList<AtomAssociation> associations;
    static final double VDW_Radius = 3.5; // angstroms
    static final double wellDepth = 41.8; // CEU
    public String pdbFile;
    
    /**
     * Default constructor for cluster
     */
    public Cluster() {
        
    }

    /**
     * Constructs a new cluster from files
     *
     * @param coordinateFilename the PDB (protein data bank) file to use for atom information
     * @param structureFilename the SF (structure file) file to use for associations between atoms
     */
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
        
        String curLine;//line being read in
        atoms= new ArrayList();// saved atoms
        Atom atom;
        try{
            FileReader fr = new FileReader(coordinateFilename);
            BufferedReader br = new BufferedReader(fr);
            pdbFile=coordinateFilename;
            System.out.println("reading");
            while ((curLine = br.readLine()) != null) {
                String[] atomInfo = curLine.split("[ ]+");
               
                 if(atomInfo[0].compareTo("ATOM")==0){
                     
                     //if(Color picking algorthing set to CPK (basic)){
                        Color atomicColor=Color.pink;//Default for unspecified elements
                        if(atomInfo[2].equals("C")){ 
                        	atomicColor = Color.black;//Carbon
                        } else if (atomInfo[2].equals("H")) {
                        	atomicColor = Color.white;//Hydrogen
                        } else if (atomInfo[2].equals("Br") || atomInfo[2].equals("O")) { 
                        	atomicColor = Color.red;
                        } else if (atomInfo[2].equals("N")){
                        	atomicColor = Color.blue;
                        } else if (atomInfo[2].equals("F") || atomInfo[2].equals("Cl")){
                        	atomicColor = Color.green;
                        } else if (atomInfo[2].equals("Fe") || atomInfo[2].equals("P")){
                        	atomicColor=Color.orange;
                        } else if (atomInfo[2].equals("Ti")){
                        	atomicColor=Color.gray;
                        } else if (atomInfo[2].equals("S")){
                        	atomicColor= Color.yellow;
                        } else if (atomInfo[2].equals("I")){
                        	atomicColor=Color.magenta;
                        } else if (atomInfo[2].equals("He") || atomInfo[2].equals("Ne") || atomInfo[2].equals("Ar") || atomInfo[2].equals("Kr") || atomInfo[2].equals("Xe")){
                        	atomicColor= Color.cyan;
                        }
                        //System.out.println(atomicColor.toString());
                    // }
                    //                             Atom ID              Atom     Molocule   ChainID     sequenceID                  location.x                      location.y              location.z                      Occupancy                   Temperature Factor              Mass                                Radius                      Charge
                    atom= new Atom(Integer.parseInt(atomInfo[1]) - 1, atomInfo[2],atomInfo[3],atomInfo[4],Integer.parseInt(atomInfo[5]),Float.parseFloat(atomInfo[6]),Float.parseFloat(atomInfo[7]),Float.parseFloat(atomInfo[8]),Double.parseDouble(atomInfo[9]),Double.parseDouble(atomInfo[10]),Double.parseDouble(atomInfo[11]),Double.parseDouble(atomInfo[12]),Double.parseDouble(atomInfo[13]),atomicColor);
                    atoms.add(atom);
                 }
            }
            System.out.println(atoms.size()+" total atoms created.");
            br.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        curLine=null;
        associations = new ArrayList<AtomAssociation>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(structureFilename));
            while ((curLine = br.readLine()) != null) {
                String[] structInfo = curLine.split("[ \t\n\f\r]");
                if(structInfo[0].compareTo("BOND")==0){
                	Atom atom1 = atoms.get(Integer.parseInt(structInfo[1]) - 1);
                	Atom atom2 = atoms.get(Integer.parseInt(structInfo[2]) - 1);
                        atom1.addBond(atom2);
                        atom2.addBond(atom1);
                	Bond newBond = new Bond(atom1, atom2, Double.valueOf(structInfo[3]), Double.valueOf(structInfo[4]));
                    associations.add(newBond);
                }
                else if(structInfo[0].compareTo("ANGLE")==0){
                	Atom atom1 = atoms.get(Integer.parseInt(structInfo[1]) - 1);
                	Atom atom2 = atoms.get(Integer.parseInt(structInfo[2]) - 1);
                	Atom atom3 = atoms.get(Integer.parseInt(structInfo[3]) - 1);

                    atom1.addBond(atom2);
                    atom1.addBond(atom3);

                    atom2.addBond(atom1);
                    atom2.addBond(atom3);

                    atom3.addBond(atom1);
                    atom3.addBond(atom2);

                	Angle newAngle = new Angle(atom1, atom2, atom3, Double.valueOf(structInfo[4]), Double.valueOf(structInfo[5]));
                    associations.add(newAngle);
                }
		
                //TODO build atoms connections from data red in
            }   
            br.close();
        }
        catch(Exception e){
            System.out.println("Exception caught when reading atom associations Cluster.java:106");
            e.printStackTrace();
        }
        
        // van der Waals Associations
        int i = 0;
        int count = atoms.size();
        Object[] atomArray=atoms.toArray();
        Atom curAtom;
        for(i = 0; i < count; i++) {
            curAtom=(Atom)atomArray[i];
            curAtom.setVDW((ArrayList<Atom>)atoms.clone());
        }

        //Add Van Der Waals to associations array
        int counter = 0;
        for(Atom a : atoms) {
            for(Atom b : a.vdwAssoc) {
                associations.add(new VanDerWaal(a, b, VDW_Radius, wellDepth));
                counter++;

                //Remove a from b's associations to prevent duplicate vdw
                b.vdwAssoc.remove(a);
            }
        }

        System.out.println("Added a total of " + counter + " VDWs");

        //Release VDW arrays in atoms to limit redundant data
        for(Atom a : atoms)
            a.releaseVDWs();

        System.out.println("************Cluster Completed Successfully***************");
    }
   
   /**
    * Getter for Atoms
    * @return List of atoms
    */
   public ArrayList<Atom> getAtoms() {
	   return atoms;
   }
   
   /**
    * Getter for Associations
    * @return List of Associations
    */
   public ArrayList<AtomAssociation> getAtomAssociation(){
	   return associations;
   }

    /**
     * Calculates and applies force on each atom using associations
     */
    public void calculateForces() {
        int i;

        //Zero forces on all atoms
        int count = atoms.size();
        for(i = 0; i < count; i++) {
            atoms.get(i).force.zero();
        }

        count = associations.size();
        for(i = 0; i < count; i++) {
            //Applies force of the association
            associations.get(i).applyForces();
        }
    }

    /**
     * Calculates potential energies for each association and returns a collection
     */
    public HashMap<String, Double> getEnergies() {
        HashMap<String, Double> output = new HashMap<String, Double>();

        int i;
        int count = associations.size();
        AtomAssociation currentAssociation;
        Double currentValue;
        for(i = 0; i < count; i++) {
            currentAssociation = associations.get(i);

            //Add energy of this association to total for its type
            currentValue = output.get(currentAssociation.type);
            if(currentValue == null) {
                output.put(currentAssociation.type, currentAssociation.getEnergy());
            } else {
                output.put(currentAssociation.type, currentValue.doubleValue() + currentAssociation.getEnergy());
            }
        }

        return output;
    }
}
