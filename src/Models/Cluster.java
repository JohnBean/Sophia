package edu.gatech.sophia;

import java.util.*;

/**
 * Contains information about atoms and their associations (bonds, torsions, etc)
 * Can be thought of as a graph stored as an edgelist where atoms are nodes and
 * associations are edges.
 */
public class Cluster {
    private ArrayList<Atom> atoms;
    private ArrayList<AtomAssociation> associations;

    public Cluster() {

    }

    public Cluster(String coordinateFilename, String structureFilename) {
        readFromFiles(coordinateFilename, structureFilename); 
    }

    /**
     * Should read cluster information from a coordinate file and structure file into the current object
     *
     * @param coordinateFilename The name of the file to read atoms from (.pdb file)
     * @param structureFilename The name of the file to read associations from (.sf file)
     */
    public void readFromFiles(String coordinateFilename, String structureFilename) {

    }
}
