package edu.gatech.sophia;

import java.util.*;

/**
 * Stores information about each Atom at a discreet point
 * in time such as locations, energy, etc
 */
public class Frame {
    /**
     * List of all atoms
     */
    private ArrayList<Atom> atoms;

    public Frame() {
        
    }

    public void setAtoms(ArrayList<Atom> atoms) {
        this.atoms = atoms;
    }

    public ArrayList<Atom> getAtoms() {
        return atoms;
    }
}
