package edu.gatech.sophia;

import java.util.ArrayList;

/**
 * Defines a relationship between two or more atoms
 */
abstract class AtomAssociation {
    /**
     * Get the IDs of the atoms involved in this association
     *
     * @return An array of atom indexes of involved atoms
     */
    protected ArrayList<Atom> atoms;
    public String type;
        
    public abstract Atom[] getAtoms();

    /**
     * Applies the force of the association to each involved atom
     */
    public abstract void applyForces();

    /**
     * Calculates the energy of this association
     *
     * @return The potential energy of this association
     */
    public abstract double getEnergy();
   
    public boolean isBond(){
        boolean rVal=false;
        if(type.compareTo("Bond")==0){
            rVal=true;
        }
        return rVal;
    }
    
    public boolean isAngle(){
        boolean rVal=false;
        if(type.compareTo("Angle")==0){
            rVal=true;
        }
        return rVal;
    }
}
