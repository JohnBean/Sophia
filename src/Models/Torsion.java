package edu.gatech.sophia;

import java.util.ArrayList;

/**
 * CHARMM-style Torsion K_chi*(1+cos(n(chi) - delta).
 * Adapted from Oscar code by Geoff Rollins
 */
public class Torsion extends AtomAssociation {
    static final double RADIAN_CONVERSION_FACTOR = Math.PI / 180.0;
    static final double MIN_ANGLE = 0.000001;
    static final double NEARLY_1 = ( 1.0 - 0.5 * MIN_ANGLE * MIN_ANGLE );

    /**
     * Must convert kcal/mol into CEU
     */
    static final double CEU_CONVERSION_FACTOR = 418.68;

    private double delta;
    private double forceConstant;
    private int multiplicity;

    /**
     * Constructs a new torsion interaction
     *
     * @param atom1 first atom in the torsion
     * @param atom2 second atom in the torsion
     * @param atom3 third atom in the torsion
     * @param atom4 fourth atom in the torsion
     * @param force the force constant used in calculating forces
     * @param multiplicity ?
     * @param delta ?
     */
    public Torsion(Atom atom1, Atom atom2, Atom atom3, Atom atom4, double force, int multiplicity, double delta){
        //Torsion constructor
        type = "Torsion";
        this.atoms = new ArrayList<Atom>();
        atoms.add(atom1);
        atoms.add(atom2);
        atoms.add(atom3);
        atoms.add(atom4);
        forceConstant = force * CEU_CONVERSION_FACTOR;
        this.multiplicity = multiplicity;
        this.delta = delta * RADIAN_CONVERSION_FACTOR;
    }

    /**
     * Calculate the angle measure (the distance between the two atoms
     * connected by this angle).
     *
     * @return current angle measure (radians)
     */
    public double calculateSeparation( ) {
        double dx1 = atoms.get(1).location.x - atoms.get(0).location.x; 
        double dy1 = atoms.get(1).location.y - atoms.get(0).location.y;
        double dz1 = atoms.get(1).location.z - atoms.get(0).location.z;
        double dx2 = atoms.get(2).location.x - atoms.get(1).location.x; 
        double dy2 = atoms.get(2).location.y - atoms.get(1).location.y;
        double dz2 = atoms.get(2).location.z - atoms.get(1).location.z;
        double dx3 = atoms.get(3).location.x - atoms.get(2).location.x; 
        double dy3 = atoms.get(3).location.y - atoms.get(2).location.y;
        double dz3 = atoms.get(3).location.z - atoms.get(2).location.z;

        double dot11 = (dx1 * dx1) + (dy1 * dy1) + (dz1 * dz1);
        double dot12 = (dx1 * dx2) + (dy1 * dy2) + (dz1 * dz2);
        double dot13 = (dx1 * dx3) + (dy1 * dy3) + (dz1 * dz3);
        double dot22 = (dx2 * dx2) + (dy2 * dy2) + (dz2 * dz2);
        double dot23 = (dx2 * dx3) + (dy2 * dy3) + (dz2 * dz3);
        double dot33 = (dx3 * dx3) + (dy3 * dy3) + (dz3 * dz3);

        double cA  = (dot13 * dot22) - (dot12 * dot23);
        double cB1 = (dot11 * dot22) - (dot12 * dot12);
        double cB2 = (dot22 * dot33) - (dot23 * dot23);
        double cD  = Math.sqrt( cB1 * cB2 );
        double c   = cA / cD;

        double torsionAngle = 0.0;

        if(Math.abs(c) <= NEARLY_1) {
            torsionAngle = Math.acos(c);
        } else if(c > NEARLY_1) {
            torsionAngle = MIN_ANGLE;
        } else {
            torsionAngle = Math.PI - MIN_ANGLE;
        }

        return Math.PI - torsionAngle;
    }

    /**
     * Common Getter for the Array List of Atoms in the Bond
     * @return The Atoms before the interaction
     */
    public Atom[] getAtoms(){
        return atoms.toArray(new Atom[atoms.size()]);
    }

    /**
     * Calculate the energy of this angle
     * E = 1/2 * forceConstant * (measure - equilibriumAngle)^2
     *
     * @return energy of this angle
     */
    public double getEnergy( ) {
        double currentAngle = calculateSeparation();
        return (forceConstant * (1 + Math.cos(multiplicity * currentAngle - delta)));
    }

    /**
     * Calculate the force of this term based on the current
     * positions of its constituent atoms. Add this force to
     * the atoms' current force values.
     *
     */
    public void applyForces() {
        double dx1 = atoms.get(1).location.x - atoms.get(0).location.x; 
        double dy1 = atoms.get(1).location.y - atoms.get(0).location.y;
        double dz1 = atoms.get(1).location.z - atoms.get(0).location.z;
        double dx2 = atoms.get(2).location.x - atoms.get(1).location.x; 
        double dy2 = atoms.get(2).location.y - atoms.get(1).location.y;
        double dz2 = atoms.get(2).location.z - atoms.get(1).location.z;
        double dx3 = atoms.get(3).location.x - atoms.get(2).location.x; 
        double dy3 = atoms.get(3).location.y - atoms.get(2).location.y;
        double dz3 = atoms.get(3).location.z - atoms.get(2).location.z;

        double dot11 = (dx1 * dx1) + (dy1 * dy1) + (dz1 * dz1);
        double dot12 = (dx1 * dx2) + (dy1 * dy2) + (dz1 * dz2);
        double dot13 = (dx1 * dx3) + (dy1 * dy3) + (dz1 * dz3);
        double dot22 = (dx2 * dx2) + (dy2 * dy2) + (dz2 * dz2);
        double dot23 = (dx2 * dx3) + (dy2 * dy3) + (dz2 * dz3);
        double dot33 = (dx3 * dx3) + (dy3 * dy3) + (dz3 * dz3);

        double cA  = (dot13 * dot22) - (dot12 * dot23);
        double cB1 = (dot11 * dot22) - (dot12 * dot12);
        double cB2 = (dot22 * dot33) - (dot23 * dot23);
        double cD  = Math.sqrt( cB1 * cB2 );
        double c   = cA / cD;

        double torsionAngle = 0.0;

        if(Math.abs(c) <= NEARLY_1) {
            torsionAngle = Math.acos(c);
        } else if(c > NEARLY_1) {
            torsionAngle = MIN_ANGLE;
        } else {
            torsionAngle = Math.PI - MIN_ANGLE;
        }

        double angle = Math.PI - torsionAngle;

        double currentAngle = calculateSeparation();

        double force = forceConstant * multiplicity * Math.sin(multiplicity * angle - delta);

        double t1 = cA;
        double t2 = (dot11 * dot23) - (dot12 * dot13);
        double t3 = -cB1;
        double t4 = cB2;
        double t5 = (dot13 * dot23) - (dot12 * dot33);
        double t6 = -cA;

        double cR1 = dot12 / dot22;
        double cR2 = dot23 / dot22;

        double f1x = force * dot22 * (t1 * dx1 + t2 * dx2 + t3 * dx3) / (cD * cB1);
        double f1y = force * dot22 * (t1 * dy1 + t2 * dy2 + t3 * dy3) / (cD * cB1);
        double f1z = force * dot22 * (t1 * dz1 + t2 * dz2 + t3 * dz3) / (cD * cB1);

        double f2x = force * dot22 * (t4 * dx1 + t5 * dx2 + t6 * dx3) / (cD * cB2);
        double f2y = force * dot22 * (t4 * dy1 + t5 * dy2 + t6 * dy3) / (cD * cB2);
        double f2z = force * dot22 * (t4 * dz1 + t5 * dz2 + t6 * dz3) / (cD * cB2);

        double xForceOnAtom1 = f1x;
        double yForceOnAtom1 = f1y;
        double zForceOnAtom1 = f1z;

        double xForceOnAtom2 = (-(1+cR1) * f1x) + (cR2 * f2x);
        double yForceOnAtom2 = (-(1+cR1) * f1y) + (cR2 * f2y);
        double zForceOnAtom2 = (-(1+cR1) * f1z) + (cR2 * f2z);

        double xForceOnAtom3 = (-(1+cR2) * f2x) + (cR1 * f1x);
        double yForceOnAtom3 = (-(1+cR2) * f2y) + (cR1 * f1y);
        double zForceOnAtom3 = (-(1+cR2) * f2z) + (cR1 * f1z);

        double xForceOnAtom4 = f2x;
        double yForceOnAtom4 = f2y;
        double zForceOnAtom4 = f2z;

        double[] forceOnAtom1 = {xForceOnAtom1, yForceOnAtom1, zForceOnAtom1};
        double[] forceOnAtom2 = {xForceOnAtom2, yForceOnAtom2, zForceOnAtom2};
        double[] forceOnAtom3 = {xForceOnAtom3, yForceOnAtom3, zForceOnAtom3};
        double[] forceOnAtom4 = {xForceOnAtom4, yForceOnAtom4, zForceOnAtom4};

        atoms.get(0).addForce(forceOnAtom1);
        atoms.get(1).addForce(forceOnAtom2);
        atoms.get(2).addForce(forceOnAtom3);
        atoms.get(3).addForce(forceOnAtom4);

        //return 0.0;
    }
}
