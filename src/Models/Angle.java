package edu.gatech.sophia;

public class Angle extends AtomAssociation {
    private double angle;
    /**
     * in kcal/(mol A) for Bonds. in kcal/(mol rad^2)for Angles
     */
    private double forceConstant;

    static final double RADIAN_CONVERSION_FACTOR = Math.PI / 180.0;
    private double theta0;

    /**
     * Used in calculations
     */
    private double[] r12;
    private double[] r32;
    private double[] force1;
    private double[] force2;
    private double[] force3;

    public Angle(Atom atom1, Atom atom2, Atom atom3, double force, double equilibriumAngle){
        //Angle constructor
        type="Angle";
        atoms.add(atom1);
        atoms.add(atom2);
        atoms.add(atom3);
        forceConstant=force;
        angle=equilibriumAngle;
        theta0 = equilibriumAngle * RADIAN_CONVERSION_FACTOR;

        r12 = new double[3];
        r32 = new double[3];
        force1 = new double[3];
        force2 = new double[3];
        force3 = new double[3];
    }

    /**
     * Common Getter for the Array List of Atoms in the Bond
     * @return The Atoms before the interaction
     */
    public Atom[] getAtoms(){
        return atoms.toArray(new Atom[atoms.size()]);
    }

    /**
     * Applies the force of the angle to each involved atom
     *
     * F = -forceConstant * (angle - equilibriumAngle)
     * Atoms 0, 1, and 2. Atom 1 is the vertex.
     */
    public void applyForces() {
        r12[0] = atoms.get(0).location.x - atoms.get(1).location.x;
        r12[1] = atoms.get(0).location.y - atoms.get(1).location.y;
        r12[2] = atoms.get(0).location.z - atoms.get(1).location.z;

        r32[0] = atoms.get(2).location.x - atoms.get(1).location.x;
        r32[1] = atoms.get(2).location.y - atoms.get(1).location.y;
        r32[2] = atoms.get(2).location.z - atoms.get(1).location.z;

        double d12 = Math.sqrt(r12[0] * r12[0] + r12[1] * r12[1] + r12[2] * r12[2]);
        double d32 = Math.sqrt(r32[0] * r32[0] + r32[1] * r32[1] + r32[2] * r32[2]);
        double cosTheta = (r12[0] * r32[0] + r12[1] * r32[1] + r12[2] * r32[2]) / (d12 * d32);
        double theta = Math.acos(cosTheta);
        double diff = theta - theta0;
      
        //TODO: from original
        //energy = 0.5 * myForceConstant * diff * diff;

        double d12i = 1.0 / d12;
        double d32i = 1.0 / d32;

        //Note:  NAMD divides by sinTheta
        //double sinTheta = Math.sqrt(1.0 - cosTheta*cosTheta);
        //diff *= (-myForceConstant) / sinTheta;
        diff *= (-forceConstant);
        double c1 = diff * d12i;
        double c2 = diff * d32i;

        force1[0] = c1 * (r12[0] * (d12i * cosTheta) - r32[0] * d32i);
        force1[1] = c1 * (r12[1] * (d12i * cosTheta) - r32[1] * d32i);
        force1[2] = c1 * (r12[2] * (d12i * cosTheta) - r32[2] * d32i);

        force2[0] = force1[0];
        force2[1] = force1[1];
        force2[2] = force1[2];

        force3[0] = c2 * (r32[0] * (d32i * cosTheta) - r12[0] * d12i);
        force3[1] = c2 * (r32[1] * (d32i * cosTheta) - r12[1] * d12i);
        force3[2] = c2 * (r32[2] * (d32i * cosTheta) - r12[2] * d12i);

        force2[0] += force3[0];
        force2[1] += force3[1];
        force2[2] += force3[2];

        force2[0] *= -1.0;
        force2[1] *= -1.0;
        force2[2] *= -1.0;

        atoms.get(0).addForce(force1);
        atoms.get(1).addForce(force2);
        atoms.get(2).addForce(force3);

        //TODO: from original
        //return 0.0;
    }
}
