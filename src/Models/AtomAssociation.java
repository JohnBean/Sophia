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
    private ArrayList<Atom> atoms;
    public String type;
    public double angle;//equilibrium atom
    public double forceConstant;//in kcal/(mol A) for Bonds. in kcal/(mol rad^2)for Angles
    public double bondLength;//equilibrium length of the bond
    
    public AtomAssociation(Atom atom1, Atom atom2, double force, double length){
       //Bond constructor
       type="BOND";//There are only two atoms so it is a bond not an angle
       atoms.add(atom1);
       atoms.add(atom2);
       forceConstant=force;
       bondLength=length;
    }
    public AtomAssociation(Atom atom1, Atom atom2, Atom atom3, double force, double equilibriumAngle){
        //Angle constructor
       type="Angle";//Three atoms means this is an angle not a single bond
       atoms.add(atom1);
       atoms.add(atom2);
       atoms.add(atom3);
       forceConstant=force;
       angle=equilibriumAngle;
    }
    abstract public Atom[] getAtoms();
    
    public ArrayList<Atom> getAtomList(){
        return atoms;
    }
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
