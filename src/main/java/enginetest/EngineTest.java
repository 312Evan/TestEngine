package enginetest;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.util.SkyFactory;
import com.jme3.util.SkyFactory.EnvMapType;

import enginetest.EngineFunctions.*;

public class EngineTest extends SimpleApplication {
    public CubeSpawner cubeSpawner = new CubeSpawner(this);
    public Skybox skybox = new Skybox(this);
    public LightingManager lightingManager = new LightingManager(this);

    ColorRGBA ambientColor = new ColorRGBA(0.2f, 0.2f, 0.2f, 1.0f);
    ColorRGBA diffuseColor = new ColorRGBA(0.5f, 0.5f, 0.5f, 1.0f);
    ColorRGBA specularColor = new ColorRGBA(1.0f, 1.0f, 1.0f, 1.0f);

    public static void main(String[] args) {
        EngineTest app = new EngineTest();
        app.setShowSettings(false); // Settings dialog not supported on mac
        app.start();
    }

    @Override
    public void simpleInitApp() {
        cubeSpawner.CreateUnshadedCube(1, 1, 1, 4, 5, 0, ColorRGBA.Blue);
        cubeSpawner.CreateShadedSolidCube(3, 2, 5, 0, 0, 0, ambientColor, diffuseColor, specularColor);
        skybox.SetDefaultSkybox();

        lightingManager.addSun();
    }

    @Override
    public void simpleUpdate(float tpf) {
        // this method will be called every game tick and can be used to make
        // updates
    }

    @Override
    public void simpleRender(RenderManager rm) {
        // add render code here (if any)
    }
}