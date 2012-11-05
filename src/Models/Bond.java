package edu.gatech.sophia;

import java.util.ArrayList;

/**
 * Data Model for holding the relevant bond information from the Abstract Atom Association class
 * @author JARVIS
 *
 */
public class Bond extends AtomAssociation {

	public Bond(Atom atom1, Atom atom2, double force, double length){
		   this.type="BOND";//There are only two atoms so it is a bond not an angle
	       this.atoms.add(atom1);
	       this.atoms.add(atom2);
	       this.forceConstant=force;
	       this.bondLength=length;
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
     * @param atoms The atoms involved in their pre-interaction state
     * @return The atoms involved in their post-interaction state
     */
	public Atom[] applyForces(Atom[] atoms){
		return this.atoms.toArray(new Atom[this.atoms.size()]);
	}
	
	
}
