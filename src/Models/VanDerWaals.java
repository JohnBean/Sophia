
/**
 * Van der Waals, non-bonded energy term. 12-6 Lennard-Jones potential.
 * Originally developed by Geoff Rollins
 * @author JARVIS 
 */
public class VanDerWaals extends AbstractEnergyTerm {

  //String identifier for the Energy Term
  static final String IDENTIFIER = "VDW";
  // the depth of the energy minimum
  private double myWellDepth;
  // the separation with the minimum energy
  private double myEquilibriumSeparation;

  private double r06;
  private double dx;
  private double dy;
  private double dz;
  private double r2;
  private double r2i;
  private double r6i;
  private double[] f12;
  private double[] f21;
  /** 
   * Class constructor.
   */
  public VanDerWaals( Atom[] atoms, double equilibriumSeparation,
                      double wellDepth  ) {
    this.setAtoms( atoms );
    myWellDepth = wellDepth;
    myEquilibriumSeparation = equilibriumSeparation;
    setName( IDENTIFIER );

    f12 = new double[3];
    f21 = new double[3];
    r06 = Math.pow(myEquilibriumSeparation, 6);
    calculateEnergy();
  }


  /**
   * Calculate the energy of this VDW term.
   * E = well depth * ( (r_min/r)^12 - 2 * (r_min/r)^6 )
   *
   * @return energy of this van der Waals term
   */
  public double calculateEnergy() {

      dx = myAtoms[1].getX() - myAtoms[0].getX();
      dy = myAtoms[1].getY() - myAtoms[0].getY();
      dz = myAtoms[1].getZ() - myAtoms[0].getZ();

      r2 = dx*dx + dy*dy + dz*dz;
      r2i = 1.0/r2;
      r6i = r2i*r2i*r2i;

      energy = myWellDepth*r06*r6i*(r06*r6i-2.0);

      return energy;
    }


  /**
   * Calculate the force of this VDW term. Add this force to atoms' current
   * force values. Return virial (force DOT separation).
   *
   * F = 12* well depth * ( (r_min^12/r^13) - (r_min^6/r^7) )
   */
  public double calculateForce() {

    dx = myAtoms[1].getX() - myAtoms[0].getX();
    dy = myAtoms[1].getY() - myAtoms[0].getY();
    dz = myAtoms[1].getZ() - myAtoms[0].getZ();

    r2 = dx*dx + dy*dy + dz*dz;
    r2i = 1.0/r2;
    r6i = r2i*r2i*r2i;

    energy = myWellDepth*r06*r6i*(r06*r6i-2.0);

    double ff = 12*myWellDepth*r2i*r06*r6i*(r06*r6i-1.0);

    f12[0] = ff*dx;
    f12[1] = ff*dy;
    f12[2] = ff*dz;

    f21[0] = -f12[0];
    f21[1] = -f12[1];
    f21[2] = -f12[2];

    myAtoms[0].addForce(f21);
    myAtoms[1].addForce(f12);

    return (ff*r2);
  }

  @Override 
  public String toString() {
    return myAtoms[0].getSerialNumber() + "\t"
        + myAtoms[1].getSerialNumber() + "\t"
        + myWellDepth/CEU_CONVERSION_FACTOR + "\t"
        + myEquilibriumSeparation;
  }
}
