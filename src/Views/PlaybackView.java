package edu.gatech.sophia;

import java.util.*;
import java.awt.Color;
import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;
import com.jme3.scene.shape.Cylinder;
import com.jme3.scene.Spatial;
import com.jme3.math.Quaternion;

/**
 * The jME application class responsible for rendering atoms
 * during the playback of a recording
 */
public class PlaybackView extends SimpleApplication {
    private Frame currentFrame = null;
    private Cluster currentCluster = null;
    private boolean updateFlag = false;
    private boolean setupFlag = false;

    /**
     * Stores endpoints for bonds
     */
    private ArrayList<Integer> end1 = null;
    private ArrayList<Integer> end2 = null;

    @Override
    public void simpleInitApp() {
        //Add a light to the scene
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(1,0,-2).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);
        
        //Turn off stats displays
        this.setDisplayFps(false);
        this.setDisplayStatView(false);

        viewPort.setBackgroundColor(new ColorRGBA(0.9f, 0.9f, 0.9f, 1.0f));
    }

    @Override
    public void simpleUpdate(float tpf) {
        if(setupFlag) {
            //Set up spheres for each atom
            ArrayList<Atom> atoms = currentCluster.getAtoms();
            int currentId = 0;
            Point3D location;
            for(Atom a : atoms) {
                //Add a sphere to the scene
                Sphere s = new Sphere(20, 20, 0.5f);
                Geometry geom = new Geometry("Atom" + currentId, s);

                float[] color = new float[3];
                a.color.getRGBColorComponents(color);

                Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
                mat.setBoolean("UseMaterialColors",true);
                mat.setColor("Diffuse",new ColorRGBA(color[0], color[1], color[2], 1.0f));
                mat.setColor("Specular",ColorRGBA.Black);
                mat.setColor("Ambient", ColorRGBA.Black);
                mat.setFloat("Shininess", 5f); 

                geom.setMaterial(mat);
                rootNode.attachChild(geom);

                currentId++;
            }

            //All bonds share a material
            Material bondMat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
            bondMat.setBoolean("UseMaterialColors",true);
            bondMat.setColor("Diffuse",new ColorRGBA(0.5f, 0.5f, 0.5f, 1.0f));
            bondMat.setColor("Specular",ColorRGBA.Black);
            bondMat.setColor("Ambient", ColorRGBA.Black);
            bondMat.setFloat("Shininess", 5f);

            //Add cylinders for each bond
            end1 = new ArrayList<Integer>(atoms.size() / 2);
            end2 = new ArrayList<Integer>(atoms.size() / 2);
            ArrayList<AtomAssociation> associations = currentCluster.getAtomAssociation();
            currentId = 0;
            for(AtomAssociation aa : associations) {
                if(aa.isBond()) {
                    Bond b = (Bond)aa;

                    //Set up endpoints for frame transformations
                    end1.add(new Integer(b.getEnd1()));
                    end2.add(new Integer(b.getEnd2()));

                    //Create a unit length cylinder that can be scaled, rotated, and transformed for this bond
                    Cylinder c = new Cylinder(2, 20, 0.1f, 1.0f);
                    Geometry geom = new Geometry("Bond" + currentId, c);
                    //geom.rotate((float)Math.PI / 2.0f, 0.0f, 0.0f);
                    geom.setMaterial(bondMat);
                    rootNode.attachChild(geom);

                    currentId++;
                }
            }

            setupFlag = false;
        }

        if(updateFlag) {
            //update positions of all atoms
            int numAtoms = currentCluster.getAtoms().size();
            Point3D location;
            Spatial currentAtom;
            for(int currentId = 0; currentId < numAtoms; currentId++) {
                //Get location from frame
                location = currentFrame.locations.get(currentId);

                //Get the atom geometry
                currentAtom = rootNode.getChild("Atom" + currentId);

                currentAtom.setLocalTranslation(new Vector3f((float)location.x, (float)location.y, (float)location.z));
            }

            //Update positions, rotations, and scalings of all bonds
            Spatial currentBond;
            Vector3D bondVector = new Vector3D();
            Point3D bondLocation = new Point3D();
            Point3D location1, location2;
            double bondLength;
            double invBondLength;
            int numBonds = end1.size();
            for(int currentId = 0; currentId < numBonds; currentId++) {
                //Get the bond geometry
                currentBond = rootNode.getChild("Bond" + currentId);

                //Create a vector to represent the bond
                location1 = currentFrame.locations.get(end1.get(currentId));
                location2 = currentFrame.locations.get(end2.get(currentId));
                bondVector.x = location1.x - location2.x;
                bondVector.y = location1.y - location2.y;
                bondVector.z = location1.z - location2.z;

                //Determine the midpoint of the bond
                bondLocation.x = (location1.x + location2.x) * 0.5;
                bondLocation.y = (location1.y + location2.y) * 0.5;
                bondLocation.z = (location1.z + location2.z) * 0.5;

                //Find length of this bond
                bondLength = bondVector.magnitude();

                //Normalize the bond vector to only preserve direction
                invBondLength = 1.0 / bondLength;
                bondVector.x *= invBondLength;
                bondVector.y *= invBondLength;
                bondVector.z *= invBondLength;

                //Scale to cylinder to bond length
                currentBond.setLocalScale(new Vector3f(1.0f, (float)bondLength, 1.0f));

                //Rotate the cylinder to the calculated up vector
                currentBond.rotateUpTo(new Vector3f((float)bondVector.x, (float)bondVector.y, (float)bondVector.z));
                currentBond.rotate((float)Math.PI / 2.0f, 0.0f, 0.0f);

                //Translate the cylinder to the bond midpoint
                currentBond.setLocalTranslation(new Vector3f((float)bondLocation.x, (float)bondLocation.y, (float)bondLocation.z));
            }

            updateFlag = false;
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    public void showInitialFrame(Frame frame, Cluster cluster) {
        currentFrame = frame;
        currentCluster = cluster;
        updateFlag = true;
        setupFlag = true;
        System.out.println("Cluster has " + cluster.getAtoms().size() + " atoms");
    }

    public void showFrame(Frame frame) {
        currentFrame = frame;
        updateFlag = true;
    }
}
