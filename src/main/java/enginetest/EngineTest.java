package enginetest;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.ToneMapFilter;
import com.jme3.renderer.Camera;
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

    private static String OS = null;

    public static String getOsName() {
        if (OS == null) {
            OS = System.getProperty("os.name");
        }
        return OS;
    }

    public static boolean isWindows() {
        return getOsName().startsWith("Windows");
    }

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
        if (isWindows()) {loadingScreen = new LoadingScreen();}

        // Initialize physics
        if(isWindows()){loadingScreen.setProgress(14);}
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        flyCam.setMoveSpeed(100);

        // Load models
        if(isWindows()){loadingScreen.setProgress(80);}
        modelManager.CreateTexturedCube(new Vector3f(200, 200f, 200), new Vector3f(0, -300, 0), new Vector3f(0, 0, 0), "Textures/sand.jpg", 70, 70, 0);
        // modelManager.CreateTexturedCube(new Vector3f(50, 1f, 50), new Vector3f(0, 49.01f, 0), new Vector3f(0, 0, 0), "Textures/brick.jpg", 20, 20, 0);
        modelManager.createModel("Models/stone.obj", "Textures/stone.png", new Vector3f(0, -3f, 0), new Vector3f(40, 40, 40), new Vector3f(0, 0, 0), 5);
        modelManager.createModel("Models/stone.obj", "Textures/stone.png", new Vector3f(24, -3f, 100), new Vector3f(40, 40, 40), new Vector3f(0, 0, 0), 5);
        modelManager.createModel("Models/rubberduck.obj", "Textures/rubberduck.jpg", new Vector3f(0, -3f, 100), new Vector3f(40, 40, 40), new Vector3f(0, 0, 0), 5);
        modelManager.createModel("Models/boulder.obj", "Textures/boulder.jpg", new Vector3f(-24, -3f, 50), new Vector3f(40, 40, 40), new Vector3f(0, 1.5f, 0), 5);
        modelManager.createModel("Models/picnictable.obj", "Textures/wood.jpg", new Vector3f(-24, 62f, 50), new Vector3f(4, 4, 4), new Vector3f(-0.1f, 1.5f, 0), 5);
        

        // Lighting and skybox
        if(isWindows()){loadingScreen.setProgress(85);}
        skybox.setHDRSky("Textures/sunset.hdr");
        lightingManager.addSun();

        // Post Processing
        if(isWindows()){loadingScreen.setProgress(90);}
        post.addBloom(1f);


        //KEEP HERE
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        ToneMapFilter toneMap = new ToneMapFilter();
        fpp.addFilter(toneMap);
        viewPort.addProcessor(fpp);

        // Water
        if(isWindows()){loadingScreen.setProgress(97);}
        waterManager.createWater(-1.5f);
        waterManager.setWaterColor(ColorRGBA.fromRGBA255(56, 107, 79, 1), ColorRGBA.fromRGBA255(56, 107, 79, 1));
        waterManager.setWaterTransparency(0.1f);

        // Finish loading
        if(isWindows()){loadingScreen.setProgress(100);}

        // Close the loading screen
        if(isWindows()){loadingScreen.close();}

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