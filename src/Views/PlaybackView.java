package edu.gatech.sophia;

import java.util.*;
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
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    public void showInitialFrame(Frame frame) {
        //Set up spheres for each atom
        ArrayList<Atom> atoms = frame.getAtoms();
        int currentId = 0;
        for(Atom a : atoms) {
            //Add a sphere to the scene
            Sphere s = new Sphere(20, 20, 1.0f);
            Geometry geom = new Geometry("Atom" + currentId, s);

            Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
            mat.setColor("Specular",ColorRGBA.Blue);
            mat.setColor("Diffuse",ColorRGBA.White);
            mat.setFloat("Shininess", 5f); 
            geom.setMaterial(mat);

            geom.move((float)a.location.x, (float)a.location.y, (float)a.location.z);

            rootNode.attachChild(geom);

            currentId++;
        }
    }
}
