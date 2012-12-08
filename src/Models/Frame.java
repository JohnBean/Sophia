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

    /**
     * Total offset from the beginning of the simulation in picoseconds
     */
    private double time;

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

    /**
     * Checks if the recording has values for a certain variable
     *
     * @param variable the name of the variable to check for
     * @return true if the variable exists in this frame
     */
    public boolean hasVariable(String variable) {
        //Check for basic variables
        if(variable.equals("Kinetic Energy"))
            return true;

        if(variable.equals("Potential Energy"))
            return true;

        if(variable.equals("Total Energy"))
            return true;

        if(variable.equals("Temperature"))
            return true;

        //Check for specific energies with this name
        if(energies != null && energies.get(variable) != null)
            return true;

        return false;
    }

    /**
     * Gets the value of a variable in this frame
     *
     * @param variable name of the variable to get
     * @return value for the variable
     */
    public double getVariableValue(String variable) {
        //Return default variables
        if(variable.equals("Kinetic Energy"))
            return kineticEnergy;

        if(variable.equals("Potential Energy"))
            return potentialEnergy;

        if(variable.equals("Total Energy"))
            return totalEnergy;

        if(variable.equals("Temperature"))
            return temperature;

        //Return a specific energy
        Double value;
        if(energies != null && (value = energies.get(variable)) != null)
            return value.doubleValue();

        return 0.0;
    }

    /**
     * Gets the time of this frame in ps
     *
     * @return the time
     */
    public double getTime() {
        return time;
    }

    /**
     * Sets the time offset of this frame
     *
     * @param time the offset from the beginning of the simulation
     */
    public void setTime(double time) {
        this.time = time;
    }
}
