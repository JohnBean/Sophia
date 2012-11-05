package edu.gatech.sophia;

public class Angle extends AtomAssociation {
   
	public Angle(Atom atom1, Atom atom2, Atom atom3, double force, double equilibriumAngle){
        //Angle constructor
       type="Angle";//Three atoms means this is an angle not a single bond
       atoms.add(atom1);
       atoms.add(atom2);
       atoms.add(atom3);
       forceConstant=force;
       angle=equilibriumAngle;
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
