package edu.gatech.sophia;

import java.util.ArrayList;

/**
 * Data Model for holding the relevant bond information from the Abstract Atom Association class
 * @author JARVIS
 *
 */
public class Bond extends AtomAssociation {
    /**
     * equilibrium length of the bond
     */
    private double bondLength;
    /**
     * in kcal/(mol A) for Bonds. in kcal/(mol rad^2)for Angles
     */
    private double forceConstant;
    /**
     * Used in force calculations
     */
    private double[] f12;
    private double[] f21;
    /**
     * Must convert kcal/mol into CEU
     */
    static final double CEU_CONVERSION_FACTOR = 418.68;

	public Bond(Atom atom1, Atom atom2, double force, double length){
		   this.type="Bond";
           this.atoms = new ArrayList<Atom>();
	       this.atoms.add(atom1);
	       this.atoms.add(atom2);
	       this.forceConstant=force * CEU_CONVERSION_FACTOR;
	       this.bondLength=length;

           f12 = new double[3];
           f21 = new double[3];
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
     * F = -forceConstant * (length - equilibriumLength)
     */
	public void applyForces() {
        double x = atoms.get(1).location.x - atoms.get(0).location.x;
        double y = atoms.get(1).location.y - atoms.get(0).location.y;
        double z = atoms.get(1).location.z - atoms.get(0).location.z;
        double r = Math.sqrt(x*x + y*y + z*z);
        double diff = r - bondLength;

        //TODO: from original code
        //energy = 0.5 * myForceConstant * diff * diff;

        double f = -forceConstant * diff;
        double fri = f / r;
        
        f12[0] = fri * x;
        f12[1] = fri * y;
        f12[2] = fri * z;

        f21[0] = -f12[0];
        f21[1] = -f12[1];
        f21[2] = -f12[2];

        atoms.get(0).addForce(f21);
        atoms.get(1).addForce(f12);
        
        //TODO: from original code
        //return (f*r);
	}

    /**
     * Calculates the energy of this bond
     *
     * @return The potential energy of this bond
     */
    public double getEnergy() {
        double x = atoms.get(1).location.x - atoms.get(0).location.x;
        double y = atoms.get(1).location.y - atoms.get(0).location.y;
        double z = atoms.get(1).location.z - atoms.get(0).location.z;
        double r = Math.sqrt(x*x + y*y + z*z);
        double diff = r - bondLength;

        return (0.5 * forceConstant * diff * diff);
    }
}
