package edu.gatech.sophia;

/**
 * Defines a relationship between two or more atoms
 */
abstract class AtomAssociation {
    /**
     * Get the IDs of the atoms involved in this association
     *
     * @return An array of atom indexes of involved atoms
     */
    public Atom atoms[];
    public String type;
    public double angle;
    public double bondUnknown1;
    public double bondUnknown2;
    public double angleUnkown1;
    public AtomAssociation(Atom atom1, Atom atom2, double unknown1, double unknown2){
       //Bond constructor
    }
    public AtomAssociation(Atom atom1, Atom atom2, Atom atom3, double unknown){
        //Angle constructor
    }
    abstract public Atom[] getAtoms();

    /**
     * Applys the force of the association to each involved atom
     *
     * @param atoms The atoms involved in their pre-interaction state
     * @return The atoms involved in their post-interaction state
     */
    abstract public Atom[] applyForces(Atom[] atoms);
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
