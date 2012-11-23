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

    /**
     * Set the array of locations for this frame
     *
     * @param locations An array of locations with one for each atom in the cluster
     */
    public void setLocations(ArrayList<Point3D> locations) {
        this.locations = locations;
    }

    /**
     * Set the values for potential energy of each association type
     *
     * @param energies A hash map String -> Double with values for potential energy
     */
    public void setEnergies(HashMap<String, Double> energies) {
        this.energies = energies;
    }

    /**
     * Get the array of locations
     *
     * @return The array of atoms locations stored in this frame
     */
    public ArrayList<Point3D> getLocations() {
        return locations;
    }
}
