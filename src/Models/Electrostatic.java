package edu.gatech.sophia;

import java.util.ArrayList;

/**
 * Atom interaction for electrostatic force between two atoms
 * using Coulomb's law
 */
public class Electrostatic extends AtomAssociation {
    /**
     * ELEC_CONST is C, a constant that depends on the units
     * C = 332 if energy is in units of kcal/mol 
     * C = (332 * CEU_CONVERSION_FACTOR) if energy is in CEU
     */
    private static final double ELEC_CONST = 138776.0;

    /**
     * Dielectric constant to use in calculations
     */
    private double dielectric;

    private double[] f12;
    private double[] f21;

    /**
     * Constructs a new electrostatic interaction
     *
     * @param atom1 the first atom in the association
     * @param atom2 the second atom in the association
     * @param dielectric the dielectric constant to use
     */
    public Electrostatic(Atom atom1, Atom atom2, double dielectric){
        //Angle constructor
        type="Coulomb";
        this.atoms = new ArrayList<Atom>();
        atoms.add(atom1);
        atoms.add(atom2);
        
        this.dielectric = dielectric;

        f12 = new double[3];
        f21 = new double[3];
    }

    /**
     * Common Getter for the Array List of Atoms in the electrostatic interaction
     * @return The Atoms before the interaction
     */
    public Atom[] getAtoms(){
        return atoms.toArray(new Atom[atoms.size()]);
    }

    /**
     * Applies the force of coulomb's law to each involved atom
     *
     * F = ELEC_CONST * ( q_0 * q_1 ) / (r^2 * dielectricConstant)
     */
    public void applyForces() {
        double q1 = atoms.get(1).charge;
        double q2 = atoms.get(2).charge;

        double dx = atoms.get(1).location.x - atoms.get(0).location.x;
        double dy = atoms.get(1).location.y - atoms.get(0).location.y;
        double dz = atoms.get(1).location.z - atoms.get(0).location.z;

        double r2 = dx * dx + dy * dy + dz * dz;
        double r = Math.sqrt(r2);

        double f = ELEC_CONST * q1 * q2 / (dielectric * r2);
        double fri = f / r;

        f12[0] = fri * dx;
        f12[1] = fri * dy;
        f12[2] = fri * dz;

        f21[0] = -f12[0];
        f21[1] = -f12[1];
        f21[2] = -f12[2];

        atoms.get(0).addForce(f21);
        atoms.get(1).addForce(f12);
    }

    /**
     * Calculates the energy of this electrostatic
     *
     * E = ELEC_CONST * ( q_0 * q_1 ) / (r*dielectricConstant)
     * @return The potential energy of this electrostatic
     */
    public double getEnergy() {
        double q1 = atoms.get(1).charge;
        double q2 = atoms.get(2).charge;

        double dx = atoms.get(1).location.x - atoms.get(0).location.x;
        double dy = atoms.get(1).location.y - atoms.get(0).location.y;
        double dz = atoms.get(1).location.z - atoms.get(0).location.z;

        double r2 = dx * dx + dy * dy + dz * dz;
        double r = Math.sqrt(r2);

        return (ELEC_CONST * q1 * q2 / (dielectric * r));
    }
}
