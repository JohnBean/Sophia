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
    public ArrayList<Point3D> locations;
    /**
     * Total kinetic energy at this frame
     */
    public double kineticEnergy;
    /**
     * Total potential energy at this frame
     */
    public double potentialEnergy;
    /**
     * Total energy at this frame
     */
    public double totalEnergy;
    /**
     * Temperature at this frame
     */
    public double temperature;
    /**
     * A hash map of energies for different types of interations in the form "Name" => energy
     */
    public HashMap<String, Double> energies;

    public Frame() {
        
    }

    public void setLocations(ArrayList<Point3D> locations) {
        this.locations = locations;
    }

    public void setEnergies(HashMap<String, Double> energies) {
        this.energies = energies;
    }

    public ArrayList<Point3D> getLocations() {
        return locations;
    }
}
