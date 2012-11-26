package edu.gatech.sophia;

import java.util.ArrayList;

/**
 * Holds information that describes a temperature protocol. A temperature
 * protocol consists of a number of temperature cycles
 *
 * Example:
 *   Cycle 1. Heat from 300K to 500K (the first 20% of simulation)
 *   Cycle 2. Hold at 500K (the next 30% of simulation)
 *   Cycle 3. Cool to 0K (the remaining 50% of simulation)
 */
public class TemperatureProtocol {
    /**
     * The list of cycles in the protocol
     */
    private ArrayList<TemperatureCycle> cycles = null;

    /**
     * The starting step index for each cycle
     */
    private int[] startSteps = null;

    /**
     * Total number of steps covered by the protocol
     */
    private int protocolLength = 0;

    /**
     * Total steps in the simulation
     */
    private int numSteps = 0;

    /**
     * Construct a new temperature protocol
     */
    public TemperatureProtocol() {
        cycles = new ArrayList<TemperatureCycle>();
    }

    /**
     * Adds a cycle to the temperature protocol
     *
     * @param cycle the cycle to add to the end of the current protocol
     */
    public void addCycle(TemperatureCycle cycle) {
        cycles.add(cycle);
    }

    /**
     * Finalizes a temperature protocol by pre-calculating the start steps
     * Should be called between building and using the protocol
     *
     * @param totalSteps the total number of steps in this simulation
     */
    public void finalize(int totalSteps) {
        if(cycles.size() == 0) {
            protocolLength = 0;
            return;
        }

        startSteps = new int[totalSteps];

        //Start step of the first cycle is 0
        startSteps[0] = 0;

        //Iterate over each cycle
        int numCycles = cycles.size();
        for(int cycleIndex = 1; cycleIndex < numCycles; cycleIndex++) {
            TemperatureCycle cycle = cycles.get(cycleIndex - 1);

            int cycleLength = cycle.getCycleLength(totalSteps);
            startSteps[cycleIndex] = startSteps[cycleIndex - 1] + cycleLength;
            protocolLength += cycleLength;
        }

        protocolLength += cycles.get(cycles.size() - 1).getCycleLength(totalSteps);

        numSteps = totalSteps;
    }

    /**
     * Checks if scaling is required at a particular step
     *
     * @param stepIndex the current step
     * @return true if temperature scaling is required
     */
    public boolean temperatureScalingRequired(int stepIndex) {
        if(stepIndex < protocolLength)
            return true;

        return false;
    }

    /**
     * Gets the temperature to set to for a particular step. finalize must be called
     * before using this.
     *
     * @param stepIndex the current step
     * @return the temperature to set to
     */
    public double getTemperature(int stepIndex) {
        if(stepIndex < protocolLength)
            return 0.0;

        int cycleId = 0;
        while(cycleId < (cycles.size() - 1) && startSteps[cycleId + 1] <= stepIndex)
            cycleId++;

        return cycles.get(cycleId).getTemperature(startSteps[cycleId], stepIndex, numSteps);
    }

    /**
     * Gets the method of scaling for a step. finalize must be called
     * before using this.
     *
     * @param stepIndex the current step
     * @return the temperature scaling method
     */
    public String getMethod(int stepIndex) {
        if(stepIndex < protocolLength)
            return "";

        int cycleId = 0;
        while(cycleId < (cycles.size() - 1) && startSteps[cycleId + 1] <= stepIndex)
            cycleId++;

        return cycles.get(cycleId).getMethod();
    }
}
