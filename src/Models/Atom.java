package edu.gatech.sophia;
import java.awt.Color;
import java.util.*;

/**
 * Contains state information about an atom
 */
public class Atom {
    public Point3D location;
    public Vector3D velocity;
    public Vector3D acceleration;
    public Vector3D force;
    public String atomType;
    public String moloculeName;
    public String chainId;
    public int sequenceId;
    public int id;
    
    public double occupancy;
    public double temperatureFactor;
    public double mass;
    public double inverseMass;
    public double radius;
    public double charge;
    public Color color;
    public ArrayList<Atom> bonds;
    public ArrayList<Atom> vdwAssoc;
    public Atom(int id, String atom, String molName, String chainID, int sequenceID,double x, double y, double z, double occupancy,double temperatureFactor,double mass,double radius, double charge, Color atomicColor){
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
        this.color= atomicColor;

        this.inverseMass = 1.0 / this.mass;

        this.velocity = new Vector3D();
        this.acceleration = new Vector3D();
        this.force = new Vector3D();
    } 
    public void addBond(Atom curAtom){
        if(bonds==null){
           bonds= new ArrayList(20);
        }
        bonds.add(curAtom);
    }
    public void killVDW(Atom connectedAtom){
        if(vdwAssoc!=null && connectedAtom!=null && vdwAssoc.contains(connectedAtom))vdwAssoc.remove(connectedAtom);
    }
    public void releaseVDWs() {
        //VDW array is just used to create associations array, but is still in scope
        vdwAssoc = null;
    }
    /**Requires all bonds to be complete
     * Takes in all of the atoms.
     * Creates the initail van der Waals asocciations, Removes itself, all bonded atoms, and all atoms
     * attached directly to those bonded atoms. 
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
    public String toString(){
        String rVal="Atom of " +atomType + " Pos:"+location.x+","+location.y+","+location.z;
        rVal= rVal+ " Part of an "+moloculeName + " molocule of chain " + chainId + " seqeunce "+ sequenceId + ". Occupancy:"+ occupancy;
        return rVal+ " Temperature Factor:" + temperatureFactor + " Mass:"+mass + " Radius:"+radius + " Charge:"+charge;
    }

    public void printString(){
        System.out.println("Atom of " +atomType + " Pos:"+location.x+","+location.y+","+location.z);
    }

    public void calculateAcceleration() {
        acceleration.x = force.x * inverseMass;
        acceleration.y = force.y * inverseMass;
        acceleration.z = force.z * inverseMass;
    }

    public void addForce(double[] df) {
        force.x += df[0];
        force.y += df[1];
        force.z += df[2];
    }
}
