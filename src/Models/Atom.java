package edu.gatech.sophia;

/**
 * Contains state information about an atom
 */
public class Atom {
    public Point3D location;
    public Vector3D velocity;
    public String atomType;
    public String moloculeName;
    public String chainId;
    public int sequenceId;
    
    public double occupancy;
    public double temperatureFactor;
    public double mass;
    public double radius;
    public double charge;
    public Atom(String atom, String molName, String chainID, int sequenceID,double x, double y, double z, double occupancy,double temperatureFactor,double mass,double radius, double charge){
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
    } 

    public String toString(){
        String rVal="Atom of " +atomType + " Pos:"+location.x+","+location.y+","+location.z;
        rVal= rVal+ " Part of an "+moloculeName + " molocule of chain " + chainId + " seqeunce "+ sequenceId + ". Occupancy:"+ occupancy;
        return rVal+ " Temperature Factor:" + temperatureFactor + " Mass:"+mass + " Radius:"+radius + " Charge:"+charge;
    }
    public void printString(){
        System.out.println("Atom of " +atomType + " Pos:"+location.x+","+location.y+","+location.z);
    }
}
