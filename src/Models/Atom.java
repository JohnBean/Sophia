package edu.gatech.sophia;
import java.awt.Color;
import java.util.*;

/**
 * Contains state information about an atom
 */
public class Atom {
    /**
     * Location of the atom in molecular units
     */
    public Point3D location;
    
    /**
     * Net force acting on the atom in its current state
     */
    public Vector3D force;

    /**
     * Global unique ID for this atom
     */
    public int id;
    
    public Vector3D velocity;
    public Vector3D acceleration;
    public String atomType;
    public String moloculeName;
    public String chainId;
    public int sequenceId;
    public double occupancy;
    public double temperatureFactor;
    public double mass;
    public double inverseMass;
    public double radius;
    public double charge;
    public Color color;
    public ArrayList<Atom> bonds;
    public ArrayList<Atom> vdwAssoc;

    /**
     * Constructs a new atom object
     *
     * @param id the globaly unique ID of this atom
     * @param atom abbreviated element name of this atom
     * @param molName name of the molecule that this atom is a member of
     * @param chainID chain ID as defined in PDB file
     * @param sequenceID sequence ID as defined in PDB file
     * @param x location of atom on the x axis in molecular units
     * @param y location of atom on the y axis in molecular units
     * @param z location of atom on the z axis in molecular untis
     * @param occupancy TODO
     * @param temperatureFactor TODO
     * @param mass molecular mass of the element used for acceleration calculations
     * @param radius radius of the atom in the visualization
     * @param charge the charge on the atom used for electrostatic force computation
     */
    public Atom(int id, String atom, String molName, String chainID, int sequenceID,double x, double y, double z, double occupancy,double temperatureFactor,double mass,double radius, double charge){
        this.id = id;
        this.atomType=atom;
        this.moloculeName=molName;
        this.chainId=chainID;
        this.sequenceId=sequenceID;
        this.location= new Point3D(x,y,z);
        this.occupancy=occupancy;
        this.temperatureFactor= temperatureFactor;
        this.mass=mass;
        this.radius=radius;
        this.charge=charge;

        this.inverseMass = 1.0 / this.mass;

        this.velocity = new Vector3D();
        this.acceleration = new Vector3D();
        this.force = new Vector3D();
    }
    public void setColor(Color newColor){
        this.color=newColor;
    }
    /**
     * Used to keep track of bonded atoms for generating VDWs
     *
     * @param curAtom the atom to add a bond to
     */
    public void addBond(Atom curAtom){
        if(bonds==null){
           bonds= new ArrayList(20);
        }
        bonds.add(curAtom);
    }

    /**
     * Removes an atom from the list of atoms associated with VDWs
     *
     * @param connectedAtom atom to remove
     */
    public void killVDW(Atom connectedAtom){
        if(vdwAssoc!=null && connectedAtom!=null && vdwAssoc.contains(connectedAtom))vdwAssoc.remove(connectedAtom);
    }

    /**
     * Hints to the garbage collector to free memory for the VDW list since it is not needed after generation
     */
    public void releaseVDWs() {
        //VDW array is just used to create associations array, but is still in scope
        vdwAssoc = null;
    }

    /**
     * Requires all bonds to be complete
     *
     * Takes in all of the atoms.
     * Creates the initail van der Waals asocciations, Removes itself, all bonded atoms, and all atoms
     * attached directly to those bonded atoms.
     *
     * @param initialAssociations all atoms
     */
    public void setVDW(ArrayList<Atom> initialAssociations){
        if(bonds!=null){
            vdwAssoc=initialAssociations;
            vdwAssoc.remove(this);
            int bondIndex=0;//bond index, James Bond Index
            int numBonds=bonds.size();
            int extendedBondIndex;
            int numExtendedBonds;
            Object[] bondArray= bonds.toArray();
            Object[] extendedBondArray;
            Atom curAtom;
            Atom extendedAtom;
            for(bondIndex=0;bondIndex<numBonds;bondIndex++){
                curAtom=(Atom)bondArray[bondIndex];
                this.killVDW(curAtom);
                curAtom.killVDW(this);
                if(curAtom.bonds!=null){
                    numExtendedBonds=curAtom.bonds.size();
                    for(extendedBondIndex=0;extendedBondIndex<numExtendedBonds;extendedBondIndex++){
                        extendedBondArray=curAtom.bonds.toArray();
                        extendedAtom=(Atom)extendedBondArray[extendedBondIndex];
                        this.killVDW(extendedAtom);
                        extendedAtom.killVDW(this);
                        extendedAtom.killVDW(curAtom);
                    }
                }
            }
        }
    }

    /**
     * Outputs a string representation of this atom object
     *
     * @return Atom string representation
     */
    public String toString(){
        String rVal="Atom of " +atomType + " Pos:"+location.x+","+location.y+","+location.z;
        rVal= rVal+ " Part of an "+moloculeName + " molecule of chain " + chainId + " sequence "+ sequenceId + ". Occupancy:"+ occupancy;
        return rVal+ " Temperature Factor:" + temperatureFactor + " Mass:"+mass + " Radius:"+radius + " Charge:"+charge;
    }

    public void printString(){
        System.out.println("Atom of " +atomType + " Pos:"+location.x+","+location.y+","+location.z);
    }

    /**
     * Calculates acceleration from net force and mass F=ma and stores result internally
     */
    public void calculateAcceleration() {
        acceleration.x = force.x * inverseMass;
        acceleration.y = force.y * inverseMass;
        acceleration.z = force.z * inverseMass;
    }

    /**
     * Adds a force to the atom's net force
     *
     * @param df a three dimensional force vector
     */
    public void addForce(double[] df) {
        force.x += df[0];
        force.y += df[1];
        force.z += df[2];
    }
}
