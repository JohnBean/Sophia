package edu.gatech.sophia;

/**
 * Stores information about each Atom at a discreet point
 * in time such as locations, energy, etc
 */
public class Frame {
    /**
     * Location of each atom. Index into this should be the same as the
     * corresponding atom in the cluster array
     */
    private Point3D[] positions;

    public Frame() {
        
    }
}
