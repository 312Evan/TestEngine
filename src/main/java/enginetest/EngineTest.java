package enginetest;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.ToneMapFilter;
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
    private LoadingScreen loadingScreen;

    public int carNum = 0;

    public static void main(String[] args) {
        EngineTest app = new EngineTest();
        AppSettings settings = new AppSettings(true);
        settings.setVSync(false);
        settings.setResolution(1200, 720);
        settings.setResizable(true);
        settings.setFullscreen(false);
        settings.setGammaCorrection(true);
        settings.setTitle("Engine Test");
        app.setSettings(settings);
        app.setShowSettings(false);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        // Initialize the loading screen
        loadingScreen = new LoadingScreen();

        // Initialize physics
        loadingScreen.setProgress(10);
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        flyCam.setMoveSpeed(100);

        // Load models
        loadingScreen.setProgress(30);
        modelManager.CreateTexturedCube(new Vector3f(200, 200f, 200), new Vector3f(0, -150, 0), new Vector3f(0, 0, 0), "Textures/Grass.png", 150, 150, 0);
        modelManager.CreateTexturedCube(new Vector3f(50, 1f, 50), new Vector3f(0, 49.01f, 0), new Vector3f(0, 0, 0), "Textures/asphalt.jpg", 75, 75, 0);
        // modelManager.createModel("Models/brickbuilding.obj","Textures/brickbuilding.jpeg", new Vector3f(0, 37.3f, 0), new Vector3f(2, 2, 2), new Vector3f(0, 0, 0), 5);

        // Lighting and skybox
        loadingScreen.setProgress(50);
        skybox.setHDRSky("Textures/cloudy.hdr");
        lightingManager.addSun();

        // Post Processing
        loadingScreen.setProgress(70);
        post.addBloom(1f);


        //KEEP HERE
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        ToneMapFilter toneMap = new ToneMapFilter();
        fpp.addFilter(toneMap);
        viewPort.addProcessor(fpp);

        // Water
        loadingScreen.setProgress(90);
        waterManager.createWater(-1.5f);

        // Finish loading
        loadingScreen.setProgress(100);

        // Close the loading screen
        loadingScreen.close();

        // modelManager.createThirdPersonController(new Vector3f(0,0,0));
    }

    @Override
    public void simpleUpdate(float tpf) {
                // if (modelManager != null) {
        //     modelManager.update(tpf);
        // }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        // Render additional elements
    }
}