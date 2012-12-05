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
    private ArrayList<String> chains;
    private ArrayList<Integer> sequences;
    public ArrayList<Torsion> torsions;
    static final double VDW_Radius = 3.5; // angstroms
    static final double wellDepth = 41.8; // CEU
    public String pdbFile;
    private String coloring = null;
    private Random randomGenerator = null;

    public static double GAS_CONSTANT = 0.830936;
    
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
     * 
     * @param colorAlgorithim "Element","ResID", "Chain", "Atom"
     */
    public void setColors(String colorAlgorithim){
        Integer atomNumber;
        Integer chain;
        Object[] atomArray = atoms.toArray();
        Atom atom=(Atom)atomArray[0];
         Color atomicColor=Color.gray;
         
         if(colorAlgorithim.contains("Atom") || colorAlgorithim.contains("Element") || colorAlgorithim.contains("Chain") || colorAlgorithim.contains("Sequence")){
             coloring=colorAlgorithim;
         }
         if(coloring==null){coloring="Atom";}
         if(coloring=="Element"){//colors elements by typical atomic representation
                for(atomNumber=0; atomNumber<atoms.size();atomNumber++){
                   atom= (Atom)atomArray[atomNumber];
                    atomicColor=Color.pink;//Default for unspecified elements
                    if(atom.atomType.equals("C")){ 
                            atomicColor = Color.black;//Carbon
                    } 
                    else if (atom.atomType.equals("H")) {
                            atomicColor = Color.white;//Hydrogen
                    } 
                    else if (atom.atomType.equals("Br") || atom.atomType.equals("O")) { 
                            atomicColor = Color.red;
                    } 
                    else if (atom.atomType.equals("N")){
                            atomicColor = Color.blue;
                    } 
                    else if (atom.atomType.equals("F") || atom.atomType.equals("Cl")){
                            atomicColor = Color.green;
                    } 
                    else if (atom.atomType.equals("Fe") || atom.atomType.equals("P")){
                            atomicColor=Color.orange;
                    }
                    else if (atom.atomType.equals("Ti")){
                            atomicColor=Color.gray;
                    } 
                    else if (atom.atomType.equals("S")){
                            atomicColor= Color.yellow;
                    } 
                    else if (atom.atomType.equals("I")){
                            atomicColor=Color.magenta;
                    } 
                    else if (atom.atomType.equals("He") || atom.atomType.equals("Ne") || atom.atomType.equals("Ar") || atom.atomType.equals("Kr") || atom.atomType.equals("Xe")){
                            atomicColor= Color.cyan;
                    }
                    atom.setColor(atomicColor);
               }
        }
        
       
        Integer redValue=254;
        Integer blueValue=0;
        //fades from red to blue
        if(coloring=="Atom"){    
            Integer step=Math.round(506/atoms.size());//step from 0-256 twice with some slack
            
            for(atomNumber=0; atomNumber<atoms.size();atomNumber++){//color each atom
                atom= (Atom)atomArray[atomNumber];
                
                if(redValue<=0){//if there is no red left then increase the blue amount to create various shades
                    blueValue+=step;
                }
                if(redValue>0){//until there is red left decrease it
                    redValue-=step;
                }
                
                if(blueValue<0)blueValue=0;
                if(blueValue>254)blueValue=254;
        
                if(redValue<0)redValue=0;
                if(redValue>254)redValue=254;
                //fades by going from red to white to blue. 
                if(redValue>blueValue){
                    atom.setColor(new Color(255,255-redValue,255-redValue));  
                }   
                else{
                    atom.setColor(new Color(255-blueValue,255-blueValue,255));  
                }
                
            }
        }

        Color[] colors = {  Color.green, Color.blue, Color.red, Color.orange,Color.magenta, Color.cyan, Color.yellow, Color.pink ,Color.black,Color.DARK_GRAY};
        if(coloring=="Chain"){
             atomicColor=Color.pink;//Default for unspecified elements
             for(atomNumber=0; atomNumber<atomArray.length;atomNumber++){//color each atom       
                 atom= (Atom)atomArray[atomNumber];       
                 for(chain=0;chain<chains.size();chain++){//find what chain that atom is in
                    if(atom.chainId==chains.get(chain)){ //if the proper one is found
                        atomicColor = colors[chain%colors.length];//select the color from the list
                        if(chain>colors.length){//if there are more chains than colors brighten the color to make a new one
                            for(int lightLevel=0; lightLevel<Math.floor(chain%colors.length);lightLevel++){
                                atomicColor.brighter();
                            }
                        }
                    }    
                 }
                 atom.setColor(atomicColor);
             }
        }
        
        if(coloring=="Sequence"){        
             atomicColor=Color.pink;//Default for unspecified elements
             for(atomNumber=0; atomNumber<atoms.size();atomNumber++){//color each atom
                 atom= (Atom)atomArray[atomNumber];                
                 for(int sequence=0;sequence<sequences.size();sequence++){//Find what sequence that atom is in
                    if(atom.sequenceId==sequences.get(sequence)){ //if the proper one is found
                        atomicColor = colors[sequence%colors.length];//select the proper color from the list
                        if(sequence>colors.length){//if there are more sequences than colors britghten it to make a new one
                            for(int lightLevel=0; lightLevel<Math.floor(sequence%colors.length);lightLevel++){
                                atomicColor.brighter();
                            }
                        }
                    }
                    atom.setColor(atomicColor);
                 }
             }
        }
        colors=null;
        atomArray=null;
        
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
        chains=  new ArrayList();// saved chains
        sequences=  new ArrayList();// saved chains
        Atom atom;
        try{
            FileReader fr = new FileReader(coordinateFilename);//reads in the pdb
            BufferedReader br = new BufferedReader(fr);
            pdbFile=coordinateFilename;
            System.out.println("reading");
            while ((curLine = br.readLine()) != null) {
                String[] atomInfo = curLine.split("[ ]+");//split by whitespace into an array to read
               
                 if(atomInfo[0].compareTo("ATOM")==0){
                    //                             Atom ID              Atom     Molocule   ChainID     sequenceID                  location.x                      location.y              location.z                      Occupancy                   Temperature Factor              Mass                                Radius                      Charge
                    atom= new Atom(Integer.parseInt(atomInfo[1]) - 1, atomInfo[2],atomInfo[3],atomInfo[4],Integer.parseInt(atomInfo[5]),Float.parseFloat(atomInfo[6]),Float.parseFloat(atomInfo[7]),Float.parseFloat(atomInfo[8]),Double.parseDouble(atomInfo[9]),Double.parseDouble(atomInfo[10]),Double.parseDouble(atomInfo[11]),Double.parseDouble(atomInfo[12]),Double.parseDouble(atomInfo[13]));
                    atoms.add(atom);//add each atom as its made

                    if(!this.chains.contains(atom.chainId)){this.chains.add(atom.chainId);}//add new chains

                    if(!sequences.contains(atom.sequenceId)){sequences.add(atom.sequenceId);}//add new sequences
                 }
            }
            this.setColors("Type");//sets all the colors using the desired algorithim
            System.out.println(atoms.size()+" total atoms created.");
            br.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        curLine=null;
        associations = new ArrayList<AtomAssociation>();
        if(!structureFilename.equals("")) {
            try{
                BufferedReader br = new BufferedReader(new FileReader(structureFilename));
                while ((curLine = br.readLine()) != null) {
                    String[] structInfo = curLine.split("[ \t\n\f\r]");
                    if(structInfo[0].compareTo("BOND")==0){
                        Atom atom1 = atoms.get(Integer.parseInt(structInfo[1]) - 1);//add each atom as a bond in the other
                        Atom atom2 = atoms.get(Integer.parseInt(structInfo[2]) - 1);
                        atom1.addBond(atom2);
                        atom2.addBond(atom1);
                        Bond newBond = new Bond(atom1, atom2, Double.valueOf(structInfo[3]), Double.valueOf(structInfo[4]));//create the bond for associations
                        associations.add(newBond);//add it
                    }
                    else if(structInfo[0].compareTo("ANGLE")==0){
                        Atom atom1 = atoms.get(Integer.parseInt(structInfo[1]) - 1);
                        Atom atom2 = atoms.get(Integer.parseInt(structInfo[2]) - 1);
                        Atom atom3 = atoms.get(Integer.parseInt(structInfo[3]) - 1);

                        atom1.addBond(atom2);//add each atom to each other, they all have a reference to each other in the atom
                        atom1.addBond(atom3);

                        atom2.addBond(atom1);
                        atom2.addBond(atom3);

                        atom3.addBond(atom1);
                        atom3.addBond(atom2);

                        Angle newAngle = new Angle(atom1, atom2, atom3, Double.valueOf(structInfo[4]), Double.valueOf(structInfo[5]));//make the angle and add
                        associations.add(newAngle);
                    }
                    else if(structInfo[0].compareTo("TORSION")==0){
                        String[] torsionInfo = curLine.split("[ ]+");
                        Atom atom1 = atoms.get(Integer.parseInt(torsionInfo[1]) - 1);
                        Atom atom2 = atoms.get(Integer.parseInt(torsionInfo[2]) - 1);
                        Atom atom3 = atoms.get(Integer.parseInt(torsionInfo[3]) - 1);
                        Atom atom4 = atoms.get(Integer.parseInt(torsionInfo[4]) - 1);

                        //add each atom to each other, they all have a reference to each other in the atom
                        atom1.addBond(atom2);
                        atom1.addBond(atom3);
                        atom1.addBond(atom4);

                        atom2.addBond(atom1);
                        atom2.addBond(atom3);
                        atom2.addBond(atom4);

                        atom3.addBond(atom1);
                        atom3.addBond(atom2);
                        atom3.addBond(atom4);

                        atom4.addBond(atom1);
                        atom4.addBond(atom2);
                        atom4.addBond(atom3);

                        associations.add(new Torsion(atom1 ,atom2, atom3, atom4, Double.parseDouble(torsionInfo[5]), Integer.parseInt(torsionInfo[6]), Double.parseDouble(torsionInfo[7])));
                    }
                    //TODO build atoms connections from data red in
                }   
                br.close();
            }
            catch(Exception e){
                System.out.println("Exception caught when reading atom associations Cluster.java:106");
                e.printStackTrace();
            }
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
    * Adds an association to the list stored in the cluster
    *
    * @param aa Atom association to add
    */
   public void addAssociation(AtomAssociation aa) {
        associations.add(aa);
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

    /**
     * Relates a random number (normalDistributionValue) from the normal
     * distribution to its counterpart from the Maxwell-Boltzmann distribution.
     *
     * MB Value = (MB average) + (MB std. dev) * (normal distribution value)
     * = 0 + sqrt(RT/m) * (normal distribution value)
     * Author: Geoff Rollins
     */
    public double sampleMaxwellBoltzmann(double normalDistributionValue, double mass, double targetTemperature) {
        return normalDistributionValue * Math.sqrt(GAS_CONSTANT * targetTemperature / mass);
    }

    /**
     * Zeros the velocities of all atoms in the system
     */
    public void zeroVelocities() {
        for(Atom a : atoms) {
            a.velocity.zero();
        }
    }

    /**
     * Sets atom velocities based on a target temperature. Adapted frome Oscar code by Geoff Rollins.
     *
     * @param thermMethod Method for re-assigning velocities. Rescaling or Reassignment
     * @param targetTemp the temperature in kelvins to use
     * @param currentTemp the current temperature of the system
     * @param numDimensions number of dimensions the simulation is run in
     */
    public void setVelocities(String thermMethod, double targetTemp, double currentTemp, int numDimensions) {
        double[] totalMomentum = {0.0, 0.0, 0.0};
        double totalMass = 0.0;

        //Set up random generator if none exists
        if(randomGenerator == null)
            randomGenerator = new Random();

        if(thermMethod.equals("Rescaling")) {
            //Rescaling used to change the temperature of the system by scaling
            double scaleFactor = 0.0;

            //Determine scaling factor to multiply each velocity by
            if(currentTemp != 0.0)
                scaleFactor = Math.sqrt(targetTemp / currentTemp);

            for(Atom a : atoms) {
                //Multiplies the current velocity by the scaling factor
                a.velocity.scale(scaleFactor);
            }
        } else if(thermMethod.equals("Reassignment")) {
            //Re-assign velocities based on the Maxwell Boltzmann distribution
            for(Atom a : atoms) {
                a.velocity.x = sampleMaxwellBoltzmann(randomGenerator.nextGaussian(), a.mass, targetTemp);
                a.velocity.y = 0.0;
                a.velocity.z = 0.0;

                //Calculate velocity in other dimensions if necessary
                if(numDimensions > 1)
                    a.velocity.y = sampleMaxwellBoltzmann(randomGenerator.nextGaussian(), a.mass, targetTemp);

                if(numDimensions > 2)
                    a.velocity.z = sampleMaxwellBoltzmann(randomGenerator.nextGaussian(), a.mass, targetTemp);

                //Keep track of total momentum and mass
                totalMomentum[0] += a.velocity.x * a.mass;
                totalMomentum[1] += a.velocity.y * a.mass;
                totalMomentum[2] += a.velocity.z * a.mass;

                totalMass += a.mass;
            }

            //Adjust velocities so that total system momentum is 0
            double xAdjustment = totalMomentum[0] / totalMass;
            double yAdjustment = totalMomentum[1] / totalMass;
            double zAdjustment = totalMomentum[2] / totalMass;
            for(Atom a : atoms) {
                a.velocity.x -= xAdjustment;
                a.velocity.y -= yAdjustment;
                a.velocity.z -= zAdjustment;
            }
        }
    }
}
