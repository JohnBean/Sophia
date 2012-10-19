package edu.gatech.sophia;

/**
 * Contains state information about an atom
 */
public class Atom {
    public Point3D location;
    public Vector3D velocity;
    public String atomName;
    public Atom(String name, double x, double y, double z){
        atomName=name;
        location= new Point3D(x,y,z);
    }
    public void printString(){
        System.out.println("Atom of " +atomName + " Pos:"+location.x+","+location.y+","+location.z);
    }
}
