package edu.gatech.sophia;

/**
 * Sophia's internal representation of Vectors
 * TODO: handle conversion of molecular units?
 */
public class Vector3D {
    public double x, y, z;

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D() {
        
    }

    public void zero() {
        x = 0.0;
        y = 0.0;
        z = 0.0;
    }

    public double magnitudeSquared() {
        return x * x + y * y + z * z;
    }

    public double magnitude() {
        return Math.sqrt(magnitudeSquared());
    }
}
