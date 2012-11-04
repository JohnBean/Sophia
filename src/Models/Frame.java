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
    private ArrayList<Point3D> locations;

    public Frame() {
        
    }

    public void setLocations(ArrayList<Point3D> locations) {
        this.locations = locations;
    }

    public ArrayList<Point3D> getLocations() {
        return locations;
    }
}
