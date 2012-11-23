package edu.gatech.sophia;

import java.util.ArrayList;

/**
 * Defines a relationship between two or more atoms
 */
abstract class AtomAssociation {
    /**
     * List of atoms involved in this association
     */
    protected ArrayList<Atom> atoms;

    /**
     * Identifies the type of association
     */
    public String type;
        
    /**
     * Gets the atoms in this association in array form
     *
     * @return array of involved atoms
     */
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
