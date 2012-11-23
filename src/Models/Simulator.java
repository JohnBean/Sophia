package edu.gatech.sophia;

import javax.swing.JProgressBar;

/**
 * Abstract class defines what methods a simulator must implement
 */
abstract class Simulator {
    /**
     * This is the type of simulator and must be unique for all simulators.
     * Should be set in the simulator's constructor
     */
    protected int type;

    /**
     * This is the seconds per timestep in the simulation
     */
    protected double timestep;

    /**
     * The number of steps to simulation
     */
    protected int numSteps;

    /**
     * The number of steps between each frame
     */
    protected int outputInterval;

    /**
     * Returns the type of simulator
     *
     * @return The unique id of this simulator type
     */
    public int getType() {
        return type;
    }

    /**
     * Runs through the simulation
     *
     * Steps through each frame and updates locations, velocities, etc
     * of the atoms in the cluster. Should output each frame into a recording
     * to be played back and analyzed with plots.
     *
     * @param cluster The cluster object containing all atoms and associations to simulate
     * @return A recording object with each simulated frame
     */
    abstract Recording run(Cluster cluster, JProgressBar prog);

    /**
     * Set the timestep
     *
     * @param timestep the amount to increment time by for each step in picoseconds
     */
    public void setTimeStep(double timestep) {
        this.timestep = timestep;
    }

    /**
     * Set the total number of steps to calculate
     *
     * @param numSteps the number of steps to run through
     */
    public void setNumSteps(int numSteps) {
        this.numSteps = numSteps;
    }

    /**
     * Sets the interval at which frames are written
     *
     * @param outputInterval the number of steps in between each frame
     */
    public void setOutputInterval(int outputInterval) {
        this.outputInterval = outputInterval;
    }
}
