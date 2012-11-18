package edu.gatech.sophia;

import java.util.ArrayList;

/**
 * Bounding box prevents atoms from passing through the walls
 * @author JARVIS
 *
 */
public class BoundingBox extends AtomAssociation {
    /**
     * 10 kcal/(mol*A^2) = 4180 CEU/(mol*A^2)
     */
    static final double BOX_FORCE_CONST = 4180.0;
    /**
     * dimension of the box
     */
    private double r;

    /**
     * Constructor for bounding box. Holds all atoms since the box affects all atoms
     */
    public BoundingBox(ArrayList<Atom> atoms, double boxLength) {
        this.atoms = atoms;
        this.r = 0.5 * boxLength;
    }
    
    /**
     * Common Getter for the Array List of Atoms in the Bond
     * @return The Atoms before the interaction
     */
    public Atom[] getAtoms(){
        return atoms.toArray(new Atom[atoms.size()]);
    }

    /**
     * Applys the force of the association to each involved atom
     *
     * @param atoms The atoms involved in their pre-interaction state
     * @return The atoms involved in their post-interaction state
     */
    public void applyForces() {
        double dx, dy, dz;

        for(Atom a : atoms) {
            dx = Math.abs(a.location.x) - r;
            dy = Math.abs(a.location.y) - r;
            dz = Math.abs(a.location.z) - r;

            double[] force = {0.0, 0.0, 0.0};

            //TODO: from original code
            //energy = 0.0;

            if (dx > 0.0) {
                //TODO: from original code
                //energy += 0.5 * BOX_FORCE_CONST * dx * dx;
                force[0] = -Math.signum(a.location.x) * BOX_FORCE_CONST * dx;
            }
            if (dy > 0.0) {
                //TODO: from original code
                //energy += 0.5 * BOX_FORCE_CONST * dy * dy;
                force[1] = -Math.signum(a.location.y) * BOX_FORCE_CONST * dy;
            }
            if (dz > 0.0) {
                //TODO: from original code
                //energy += 0.5 * BOX_FORCE_CONST * dz * dz;
                force[2] = -Math.signum(a.location.z) * BOX_FORCE_CONST * dz;
            }

            a.addForce(force);
        }

        //TODO: from original code
        //return 0.0;
    }

    /**
     * Calculates the energy of this bounding box
     *
     * @return The potential energy of this bounding box
     */
    public double getEnergy() {
        double dx, dy, dz;
        double energy = 0.0;

        for(Atom a : atoms) {
            dx = Math.abs(a.location.x) - r;
            dy = Math.abs(a.location.y) - r;
            dz = Math.abs(a.location.z) - r;

            double[] force = {0.0, 0.0, 0.0};

            if (dx > 0.0) {
                energy += 0.5 * BOX_FORCE_CONST * dx * dx;
                force[0] = -Math.signum(a.location.x) * BOX_FORCE_CONST * dx;
            }
            if (dy > 0.0) {
                energy += 0.5 * BOX_FORCE_CONST * dy * dy;
                force[1] = -Math.signum(a.location.y) * BOX_FORCE_CONST * dy;
            }
            if (dz > 0.0) {
                energy += 0.5 * BOX_FORCE_CONST * dz * dz;
                force[2] = -Math.signum(a.location.z) * BOX_FORCE_CONST * dz;
            }
        }

        return energy;
    }
}
