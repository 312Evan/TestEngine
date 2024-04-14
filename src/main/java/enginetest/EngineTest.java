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
        app.setSettings(settings);

        app.setShowSettings(false); // Settings dialog not supported on mac
        app.start();
        
    }

    @Override
    public void simpleInitApp() {
        flyCam.setMoveSpeed(100);

        //code testing
        cubeSpawner.CreateTexturedCube(new Vector3f(100, 0.1f, 100), new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), "Textures/Grass.jpg", 40, 40, 1);
        cubeSpawner.CreateTexturedCube(new Vector3f(35, 0.01f, 200), new Vector3f(0, 0.1f, 0), new Vector3f(0, 0, 0), "Textures/asphalt.jpg", 40, 7, 1);
        
        modelManager.createModel("Models/oldcar.fbx", "Textures/oldcar.png", new Vector3f(-3, 0, 1), new Vector3f(0.01f, 0.01f, 0.01f), new Vector3f(300, 186, 0), 5);
        modelManager.applyNormalMap("Textures/oldcarNormal.png", 5);
        
        modelManager.createModel("Models/building.fbx", "Textures/buildingTexture.png", new Vector3f(50, -0.1f, 0), new Vector3f(0.01f, 0.01f, 0.01f), new Vector3f(300, 0, 0), 1);
        modelManager.createModel("Models/oldcar2.fbx", "Textures/oldcar2.png", new Vector3f(3.5f, 0, 0), new Vector3f(0.025f, 0.025f, 0.025f), new Vector3f(300, 300, 0), 1);
        modelManager.createModel("Models/building.fbx", "Textures/buildingTexture.png", new Vector3f(30, -0.1f, 70), new Vector3f(0.01f, 0.01f, 0.01f), new Vector3f(300, 180, 0), 1);


        skybox.setSkyBox("Textures/Sunset.dds");
        lightingManager.addSun(ColorRGBA.White);
    }

    @Override
    public void simpleUpdate(float tpf) {
        // this method will be called every game tick and can be used to make
        // updates
    }

    @Override
    public void simpleRender(RenderManager rm) {
        
    }
}