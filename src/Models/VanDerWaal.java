package edu.gatech.sophia;

import java.util.ArrayList;

/**
 * Atom association code for Van Der Waals which are interactions between non-bonded atoms.
 * Uses the Lennard Jones approximation
 */
public class VanDerWaal extends AtomAssociation {
    private double equilibriumSeparation;
    private double wellDepth;
    private double r06;
    private double[] f12;
    private double[] f21;

    /**
     * Constructs a new Van Der Waal association
     *
     * @param atom1 the first atom in the association
     * @param atom2 the second atom in the association
     * @param equilibriumSeparation the separation between the atoms at which there is no force
     * @param wellDepth the maximum repelling force
     */
    public VanDerWaal(Atom atom1, Atom atom2, double equilibriumSeparation, double wellDepth) {
        this.type = "VDW";
        this.atoms = new ArrayList<Atom>();
        this.atoms.add(atom1);
        this.atoms.add(atom2);
        this.equilibriumSeparation = equilibriumSeparation;
        this.wellDepth = wellDepth;

        this.r06 = Math.pow(equilibriumSeparation, 6);

        this.f12 = new double[3];
        this.f21 = new double[3];
    }

    /**
     * Common Getter for the Array List of Atoms in the Bond
     * @return The Atoms before the interaction
     */
    public Atom[] getAtoms(){
        return atoms.toArray(new Atom[atoms.size()]);
    }

    /**
     * Applys the force of the association to each involved atom
     *
     * F = 12* well depth * ( (r_min^12/r^13) - (r_min^6/r^7) )
     */
    public void applyForces() {
        double dx = atoms.get(1).location.x - atoms.get(0).location.x;
        double dy = atoms.get(1).location.y - atoms.get(0).location.y;
        double dz = atoms.get(1).location.z - atoms.get(0).location.z;

        double r2 = dx*dx + dy*dy + dz*dz;
        double r = Math.sqrt(r2);
        double r2i = 1.0 / r2;
        double r6i = r2i * r2i * r2i;

        //TODO: from original
        //energy = myWellDepth*r06*r6i*(r06*r6i-2.0);

        double r12i = r6i * r6i;
        double r012 = r06 * r06;
        double ff = 4 * wellDepth * ((r012 * r12i) - (r06 * r6i));
        double ffn = ff / r;

        f12[0] = ffn * dx;
        f12[1] = ffn * dy;
        f12[2] = ffn * dz;

        f21[0] = -f12[0];
        f21[1] = -f12[1];
        f21[2] = -f12[2];

        atoms.get(0).addForce(f21);
        atoms.get(1).addForce(f12);

        //TODO: from original
        //return (ff*r2);
    }

    /**
     * Calculates the energy of this van der waal
     *
     * @return The potential energy of this van der waal
     */
    public double getEnergy() {
        double dx = atoms.get(1).location.x - atoms.get(0).location.x;
        double dy = atoms.get(1).location.y - atoms.get(0).location.y;
        double dz = atoms.get(1).location.z - atoms.get(0).location.z;

        double r2 = dx*dx + dy*dy + dz*dz;
        double r = Math.sqrt(r2);
        double r2i = 1.0 / r2;
        double r6i = r2i * r2i * r2i;

        return (wellDepth * r06 * r6i * (r06 * r6i - 2.0));
    }
}
