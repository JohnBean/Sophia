package edu.gatech.sophia;

/**
 * Sophia's internal representation of Points
 * TODO: handle conversion of molecular units?
 */
public class Point3D {
    public double x, y, z;

    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
