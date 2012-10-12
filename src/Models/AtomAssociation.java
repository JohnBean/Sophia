package edu.gatech.sophia;

/**
 * Defines a relationship between tewo or more atoms
 */
abstract class AtomAssociation {
    /**
     * Get the IDs of the atoms involved in this association
     *
     * @return An array of atom indexes of involved atoms
     */
    abstract public int[] getAtoms();

    /**
     * Applys the force of the association to each involved atom
     *
     * @param atoms The atoms involved in their pre-interaction state
     * @return The atoms involved in their post-interaction state
     */
    abstract public Atom[] applyForces(Atom[] atoms);
}
