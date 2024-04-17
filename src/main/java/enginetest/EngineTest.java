package enginetest;

import com.jme3.app.SimpleApplication;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;

import enginetest.EngineFunctions.*;

public class EngineTest extends SimpleApplication {
    public CubeManager cubeSpawner = new CubeManager(this);
    public Skybox skybox = new Skybox(this);
    public LightingManager lightingManager = new LightingManager(this);
    public ModelManager modelManager = new ModelManager(this);
    public WaterManager waterManager = new WaterManager(this);

    ColorRGBA ambientColor = new ColorRGBA(0.2f, 0.2f, 0.2f, 1.0f);
    ColorRGBA diffuseColor = new ColorRGBA(0.5f, 0.5f, 0.5f, 1.0f);
    ColorRGBA specularColor = new ColorRGBA(1.0f, 1.0f, 1.0f, 1.0f);
    public int carNum = 0;

    public static void main(String[] args) {
        EngineTest app = new EngineTest();

        AppSettings settings = new AppSettings(true);
        // settings.setFrameRate(100);
        settings.setVSync(false);
        settings.setResolution(1600, 900);
        settings.setResizable(true);
        app.setSettings(settings);

        app.setShowSettings(false); // Settings dialog not supported on mac
        app.start();
        
    }

    @Override
    public void simpleInitApp() {
        flyCam.setMoveSpeed(100);

        //code testing
        cubeSpawner.CreateTexturedCube(new Vector3f(300, 0.1f, 300), new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), "Textures/Grass.jpg", 120, 120, 1);
        cubeSpawner.CreateTexturedCube(new Vector3f(35, 0.01f, 200), new Vector3f(0, 0.1f, 0), new Vector3f(0, 0, 0), "Textures/asphalt.jpg", 40, 7, 1);
        cubeSpawner.CreateTexturedCube(new Vector3f(35, 50f, 200), new Vector3f(80, 0f, 0), new Vector3f(0, 0, 0), "Textures/asphalt.jpg", 40, 7, 1);
        
        // modelManager.createModel("Models/building.fbx", "Textures/buildingTexture.png", new Vector3f(50, -0.1f, 0), new Vector3f(0.01f, 0.01f, 0.01f), new Vector3f(300, 0, 0), 1);
        // modelManager.createModel("Models/oldcar2.fbx", "Textures/oldcar2.png", new Vector3f(3.5f, 0, 0), new Vector3f(0.025f, 0.025f, 0.025f), new Vector3f(300, 300, 0), 1);
        // modelManager.createModel("Models/building.fbx", "Textures/buildingTexture.png", new Vector3f(30, -0.1f, 70), new Vector3f(0.01f, 0.01f, 0.01f), new Vector3f(300, 180, 0),
        //         1);

        lightingManager.addSun();
        skybox.setSkyBox("Textures/Sunset.dds");
    }

    @Override
    public void simpleUpdate(float tpf) {
        
    }

    @Override
    public void simpleRender(RenderManager rm) {
        
    }
}