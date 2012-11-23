package edu.gatech.sophia;

/**
 * Sophia's internal representation of Vectors
 */
public class Vector3D {
    public double x, y, z;

    /**
     * Construct a vector from its coordinate representation
     * 
     * @param x the x magnitude
     * @param y the y magnitude
     * @param z the z magnitude
     */
    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Default constructor for a zero vector
     */
    public Vector3D() {
        zero();
    }

    /**
     * Zeros this vector
     */
    public void zero() {
        x = 0.0;
        y = 0.0;
        z = 0.0;
    }

    /**
     * Calculates the magnitude if this vector squared
     *
     * @return the magnitude squared
     */
    public double magnitudeSquared() {
        return x * x + y * y + z * z;
    }

    /**
     * Calculates the magnitude of the vector
     *
     * @return magnitude of the vector
     */
    public double magnitude() {
        return Math.sqrt(magnitudeSquared());
    }
}
