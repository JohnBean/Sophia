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
import com.jme3.renderer.Camera;
import com.jme3.input.ChaseCamera;

/**
 * The jME application class responsible for rendering atoms
 * during the playback of a recording
 */
public class PlaybackView extends SimpleApplication {
    private Frame currentFrame = null;
    private Cluster currentCluster = null;
    private boolean updateFlag = false;
    private boolean setupFlag = false;
    private boolean dmChangeFlag = false;
    private boolean colorChangeFlag = false;
    private boolean vdwEnabled = false;
    private ChaseCamera chaseCam = null;

    /**
     * Stores endpoints for bonds
     */
    private ArrayList<Integer> end1 = null;
    private ArrayList<Integer> end2 = null;

    /**
     * Stores statitstics for currently displayed objects
     */
    private int numAtoms = 0;
    private int numBonds = 0;

    /**
     * Initializes the scene and camera
     */
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

        //Set up a chase camera which allows the viewer to rotate around the center of the scene
        chaseCam = new ChaseCamera(cam, rootNode, inputManager);
        chaseCam.setDefaultDistance(20.0f);
        chaseCam.setDefaultHorizontalRotation((float)Math.PI * 0.5f);
        chaseCam.setDefaultVerticalRotation(0.0f);
        chaseCam.setMaxDistance(100.0f);
        chaseCam.setMinDistance(5.0f);
        chaseCam.setInvertHorizontalAxis(false);
        chaseCam.setInvertVerticalAxis(false);

        viewPort.setBackgroundColor(new ColorRGBA(0.9f, 0.9f, 0.9f, 1.0f));
    }

    /**
     * Contains code that updates frames.
     *
     * Frame information is set by other threads, but the rendering thread must
     * update the scenegraph so flags are used to signal the the scene should
     * be updated with the new frame information
     *
     * @param tpf time since last frame?
     */
    @Override
    public void simpleUpdate(float tpf) {
        if(setupFlag) {
            //Delete previous geometry from scene if any
            Spatial currentGeom;
            for(int i = 0; i < numAtoms; i++) {
                currentGeom = rootNode.getChild("Atom" + i);
                currentGeom.removeFromParent();
            }

            numAtoms = 0;

            for(int i = 0; i < numBonds; i++) {
                currentGeom = rootNode.getChild("Bond" + i);
                currentGeom.removeFromParent();
            }

            numBonds = 0;

            //Set up spheres for each atom
            ArrayList<Atom> atoms = currentCluster.getAtoms();
            numAtoms = atoms.size();
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
            numBonds = 0;
            currentId = 0;
            if(!associations.isEmpty()){
                for(AtomAssociation aa : associations) {
                    if(aa.isBond()) {
                        numBonds++;

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
                if(currentFrame.locations!=null){
                    location = currentFrame.locations.get(currentId);
                

                     //Get the atom geometry
                    currentAtom = rootNode.getChild("Atom" + currentId);

                    currentAtom.setLocalTranslation(new Vector3f((float)location.x, (float)location.y, (float)location.z));
                }
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
               if(currentFrame.locations!=null){ location1 = currentFrame.locations.get(end1.get(currentId));
                    location2 = currentFrame.locations.get(end2.get(currentId));
                    bondVector.x = location1.x - location2.x;
                    bondVector.y = location1.y - location2.y;
                    bondVector.z = location1.z - location2.z;

                    //Determine the midpoint of the bond
                    bondLocation.x = (location1.x + location2.x) * 0.5;
                    bondLocation.y = (location1.y + location2.y) * 0.5;
                    bondLocation.z = (location1.z + location2.z) * 0.5;
               }
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

        //Run when drawing method is changed
        if(dmChangeFlag) {
            ArrayList<Atom> atoms = currentCluster.getAtoms();
            int numAtoms = atoms.size();
            //Switch to VDW
            if(vdwEnabled) {
                Spatial currentAtom;
                float scale;
                for(int currentId = 0; currentId < numAtoms; currentId++) {
                    currentAtom = rootNode.getChild("Atom" + currentId);

                    scale = (float)atoms.get(currentId).radius * 2.0f;
                    currentAtom.setLocalScale(new Vector3f(scale, scale, scale));
                }
            }

            //Switch to CPK
            if(!vdwEnabled) {
                Spatial currentAtom;
                for(int currentId = 0; currentId < numAtoms; currentId++) {
                    currentAtom = rootNode.getChild("Atom" + currentId);

                    currentAtom.setLocalScale(new Vector3f(1.0f, 1.0f, 1.0f));
                }
            }

            dmChangeFlag = false;
        }

        //Run when colors of the atoms need to be updated
        if(colorChangeFlag) {
            ArrayList<Atom> atoms = currentCluster.getAtoms();
            int numAtoms = atoms.size();
            Geometry currentAtom;
            
            for(int currentId = 0; currentId < numAtoms; currentId++) {
                currentAtom = (Geometry)rootNode.getChild("Atom" + currentId);

                float[] color = new float[3];
                atoms.get(currentId).color.getRGBColorComponents(color);

                Material mat = currentAtom.getMaterial();
                mat.setColor("Diffuse",new ColorRGBA(color[0], color[1], color[2], 1.0f));
            }

            colorChangeFlag = false;
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    /**
     * Sets up a new recording in the visualization.
     *
     * Triggers code that builds the atomic representation from spheres and cylinders
     *
     * @param frame the initial frame to show
     * @param cluster the cluster to use for atom and bond information
     */
    public void showInitialFrame(Frame frame, Cluster cluster) {
        currentFrame = frame;
        currentCluster = cluster;
        updateFlag = true;
        setupFlag = true;
        System.out.println("Cluster has " + cluster.getAtoms().size() + " atoms");
    }

    /**
     * Sets up a new frame to visualize. showInitialFrame must have already been called with the appropriate cluster
     *
     * @param frame the new frame to show
     */
    public void showFrame(Frame frame) {
        currentFrame = frame;
        updateFlag = true;
    }

    /**
     * Switches between VDW and CPK drawing methods
     *
     * @param value set to true to enable VDW and false to enable CPK
     */
    public void toggleVDW(boolean value) {
        if(vdwEnabled != value) {
            vdwEnabled = value;
            dmChangeFlag = true;
        }
    }

    /**
     * Call to update colors of the atoms
     */
    public void updateColors() {
        colorChangeFlag = true;
    }
}
