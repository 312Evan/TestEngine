package enginetest;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
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
    int time;

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
        settings.setResolution(1200, 700);
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
        cam.setFrustumFar(3000);

        // Initialize the loading screen
        if (isWindows()) {loadingScreen = new LoadingScreen();}

        // Initialize physics
        if(isWindows()){loadingScreen.setProgress(14);}
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        flyCam.setMoveSpeed(100);

        // Load models
        if(isWindows()){loadingScreen.setProgress(80);}

        modelManager.loadCubesFromJson(assetManager);
        modelManager.loadModelsFromJson(assetManager);

        // Lighting and skybox
        if(isWindows()){loadingScreen.setProgress(85);}
        lightingManager.loadLightingFromJson(assetManager);
        skybox.loadSkyboxFromJson(assetManager);


        //KEEP HERE
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        ToneMapFilter toneMap = new ToneMapFilter();
        fpp.addFilter(toneMap);
        viewPort.addProcessor(fpp);

        // Post Processing
        if (isWindows()) {
            loadingScreen.setProgress(90);
        }
        post.loadPostProcessingFromJson(assetManager);

        // Water
        if(isWindows()){loadingScreen.setProgress(97);}
        waterManager.loadWaterFromJson(assetManager);

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