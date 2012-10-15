package edu.gatech.sophia;

/**
 * Contains state information about an atom
 */
public class Atom {
    private Point3D location;
    private Vector3D velocity;
    private String atomName;
    public Atom(String name, double x, double y, double z){
        atomName=name;
        location= new Point3D(x,y,z);
    }
}
