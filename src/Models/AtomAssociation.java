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
    protected String type;
        
    public abstract Atom[] getAtoms();

    /**
     * Applys the force of the association to each involved atom
     */
    public abstract void applyForces();
   
    public boolean isBond(){
        boolean rVal=false;
        if(type.compareTo("BOND")==0){
            rVal=true;
        }
        return rVal;
    }
    
    public boolean isAngle(){
        boolean rVal=false;
        if(type.compareTo("ANGLE")==0){
            rVal=true;
        }
        return rVal;
    }
}
