package edu.gatech.sophia;
import java.awt.Color;

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
    
    public double occupancy;
    public double temperatureFactor;
    public double mass;
    public double inverseMass;
    public double radius;
    public double charge;
    public Color color;
    public Atom(String atom, String molName, String chainID, int sequenceID,double x, double y, double z, double occupancy,double temperatureFactor,double mass,double radius, double charge, Color atomicColor){
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
}
