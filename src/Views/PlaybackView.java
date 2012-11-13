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

/**
 * The jME application class responsible for rendering atoms
 * during the playback of a recording
 */
public class PlaybackView extends SimpleApplication {
    private Frame currentFrame = null;
    private Cluster currentCluster = null;
    private boolean updateFlag = false;
    private boolean setupFlag = false;

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

                geom.move((float)a.location.x, (float)a.location.y, (float)a.location.z);

                rootNode.attachChild(geom);

                currentId++;
            }

            setupFlag = false;
        }

        if(updateFlag) {
            //TODO: update positions of all atoms

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
