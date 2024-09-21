package enginetest;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;

import enginetest.EngineFunctions.*;

public class EngineTest extends SimpleApplication {
    private BulletAppState bulletAppState;
    Skybox skybox = new Skybox(this);
    LightingManager lightingManager = new LightingManager(this);
    ModelManager modelManager = new ModelManager(this);
    WaterManager waterManager = new WaterManager(this);
    PostProcessing post = new PostProcessing(this);


    ColorRGBA ambientColor = new ColorRGBA(0.2f, 0.2f, 0.2f, 1.0f);
    ColorRGBA diffuseColor = new ColorRGBA(0.5f, 0.5f, 0.5f, 1.0f);
    ColorRGBA specularColor = new ColorRGBA(1.0f, 1.0f, 1.0f, 1.0f);
    public int carNum = 0;

    public static void main(String[] args) {
        EngineTest app = new EngineTest();
        AppSettings settings = new AppSettings(true);
        // settings.setFrameRate(100);
        settings.setVSync(false);
        settings.setResolution(1200, 720);
        settings.setResizable(true);
        settings.setFullscreen(false);
        settings.setGammaCorrection(true);
        app.setSettings(settings);
        app.setShowSettings(false); // Settings dialog not supported on mac
        app.start();
        
    }

    @Override
    public void simpleInitApp() {
        // init physics
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);

        
        // flyCam.setMoveSpeed(100);

        //Objects go here.
        modelManager.CreateTexturedCube(new Vector3f(200, 0.1f, 200), new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), "Textures/concrete.png", 75, 75, 0);

        //Lighting and Skybox
        skybox.setHDRSky("Textures/clearsky.hdr");
        lightingManager.addSun();

        //Post Processing goes here.
        post.addBloom(0.9f);

        //Water goes here.
            
        //player logic
        modelManager.createThirdPersonController(new Vector3f(0,0,0));
    }

    @Override
    public void simpleUpdate(float tpf) {
        if (modelManager != null) {
            modelManager.update(tpf);
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        
    }
}