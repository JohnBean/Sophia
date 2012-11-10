package edu.gatech.sophia;


public class VanDerWaal extends AtomAssociation {
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
    public void applyForces() {
    
    }
}
