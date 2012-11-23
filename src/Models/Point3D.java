package edu.gatech.sophia;

/**
 * Sophia's internal representation of Points
 */
public class Point3D {
    /**
     * Coordinate representation of point
     */
    public double x, y, z;

    /**
     * Constructs a new point with specified coordinates
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param z the z coordinate
     */
    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Constructs a new point at the origin
     */
    public Point3D() {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
    }
}
