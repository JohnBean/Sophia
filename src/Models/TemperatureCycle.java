package edu.gatech.sophia;

/**
 * Holds information that describes a temperature cycle. A temperature
 * cycle defines behavior that scales the temperature as time progresses
 *
 * Example:
 *   Cycle 1. Heat from 300K to 500K (the first 20% of simulation)
 *   Cycle 2. Hold at 500K (the next 30% of simulation)
 *   Cycle 3. Cool to 0K (the remaining 50% of simulation)
 */
public class TemperatureCycle {
    /**
     * The temperature at the beginning of the cycle in Kelvins
     */
    private double initialTemp;

    /**
     * The temperature at the end of the cycle in Kelvins
     */
    private double targetTemp;

    /**
     * The percentage of the simulation that this temperature cycle lasts
     */
    private int percentSim;

    /**
     * The interval in steps at which temperature re-scaling is performed
     */
    private int interval;

    /**
     * The method for altering the temperature
     */
    private String method;

    /**
     * Constructs a new temperature cycle
     *
     * @param initialTemp the initial temperature
     * @param targetTemp the target temperature for the end of the cycle
     * @param percentSim the percentage of the simulation the cycle lasts
     * @param method the method to use for temperature setting (Reassignment, Rescaling)
     */
    public TemperatureCycle(double initialTemp, double targetTemp, int percentSim, String method, int interval) {
        this.initialTemp = initialTemp;
        this.targetTemp = targetTemp;
        this.percentSim = percentSim;
        this.method = method;
        this.interval = interval;
    }

    /**
     * Gets the length of this cycle given a total number of steps
     *
     * @param numSteps the total number of steps in the simulation
     * @return the length of this cycle in steps
     */
    public int getCycleLength(int numSteps) {
        return (numSteps * percentSim / 100);
    }

    /**
     * Gets the temperature setting for a particular step in the cycle
     *
     * @param startStep the step that this cycle starts at
     * @param currentStep the current step number
     * @param totalSteps the total number of steps in the simulation
     * @return the termperature to set to on this step
     */
    public double getTemperature(int startStep, int currentStep, int totalSteps) {
        int cycleLength = getCycleLength(totalSteps);

        double progress = ((double)(currentStep - startStep)) / cycleLength;
        double temperature = initialTemp;
        temperature += (progress * (targetTemp - initialTemp));

        return temperature;
    }

    /**
     * Checks if this is a therm step that requires temperature scaling
     *
     * @param currentStep the current step in the simulation
     * @return true if this is a therm step
     */
    public boolean temperatureScalingRequired(int currentStep) {
        if((currentStep % interval) == 0)
            return true;

        return false;
    }

    /**
     * Gets the scaling method for this cycle
     *
     * @return the scaling method
     */
    public String getMethod() {
        return method;
    }

    /**
     * Formats cycle as String
     *
     * @return string representation
     */
    public String toString() {
        return (method + " every " + interval + " steps from " + initialTemp + "K to " + targetTemp + "K for " + percentSim + "% of simulation\n");
    }
}
