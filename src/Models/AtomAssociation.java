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
    protected double angle;//equilibrium atom
    protected double forceConstant;//in kcal/(mol A) for Bonds. in kcal/(mol rad^2)for Angles
    protected double bondLength;//equilibrium length of the bond
        
    abstract public Atom[] getAtoms();

    /**
     * Applys the force of the association to each involved atom
     */
    abstract public void applyForces();

   
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
