package edu.gatech.sophia;

/**
 * Abstract Model Class for Energy Terms such as
 * 		• VanDerWaals
 * 
 * Originally created by Geoff Rollins - changed for the purposes of SOPHIA
 * Framework for potential energy terms.
 * Each energy term has:
 * 		1) A list of the atoms that it governs
 *  	2) A method for calculating the distance or angle between its atoms
 *  	3) A method for calculating the interaction energy
 *  	4) A method for calculating the force on each of its atoms
 * 
 * @author JARVIS
 *
 */
public abstract class EnergyTerm {
	
	//TO-DO What is the CEU Conversion Factor
	static final double CEU_CONVERSION_FACTOR = 418.0;
	
	//Type of Energy 
	private String energyTermType;
	
	//Attoms invovled in the energy term
	public Atom[] atoms;
	
	//Energy produced
	public double energy;
	
	/**
	   * Calculate the energy of this term based on the current
	   * positions of its constituent atoms.
	   * 
	   * Will change when creating new Energy Term
	   */
	abstract public double calculateEnergy();

	 /**
	   * Calculate the force of this term based on the current
	   * positions of its constituent atoms. Add this force to
	   * the atoms' current force values. Return the value of
	   * the virial coefficient.
	   * 
	   * Will change when creating new Energy Term
	   */
	  abstract public double calculateForce();

	  /**
	   * Simple getter for the atoms
	   * @return Atoms involed with energy term
	   */
	  public Atom[] getAtoms( ) {
	    return atoms;
	  }

	  /**
	   * Simple setter for the atoms invovled
	   * @param newAtoms The atoms involed with the Energy Term
	   */
	  public void setAtoms( Atom[] newAtoms ) {
	    this.atoms = newAtoms;
	  }

	  /**
	   * Simple getter
	   * @return String identifier to the type of energy term used
	   */
	  public String getEnergyTermType( ) {
	    return energyTermType;
	  }

	  /**
	   * Simple setter for energy type
	   * @param energyType String identifier for the type Energy Term
	   */
	  public void setEnergyTermType( String energyType) {
	    this.energyTermType = energyType;
	  }

	  /**
	   * Simple getter for getting the energy from the term
	   * @return Energy of the term
	   */
	  public double getEnergy() {
	    return energy;
	  }
}
