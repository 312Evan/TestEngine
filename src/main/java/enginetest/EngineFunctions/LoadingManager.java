package enginetest.EngineFunctions;

import com.jme3.app.SimpleApplication;

public class LoadingManager {
    SimpleApplication app;
    ModelManager modelManager;
    LightingManager lightManager;
    Skybox skybox;
    PostProcessing postProcessing;
    WaterManager waterManager;
    private LoadingScreen loadingScreen;
    UiManager uiManager;

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

    public LoadingManager(SimpleApplication app) {
        this.app = app;
        this.modelManager = new ModelManager(app);
        this.lightManager = new LightingManager(app);
        this.skybox = new Skybox(app);
        this.postProcessing = new PostProcessing(app);
        this.waterManager = new WaterManager(app);
        this.uiManager = new UiManager(app);
    }

    public void loadGameFromJson() {
        if (isWindows()) {
            loadingScreen = new LoadingScreen(app);
        }

        if (isWindows()) {
            loadingScreen.setProgress(14);
        }

        modelManager.loadCubesFromJson(app.getAssetManager());
        modelManager.loadSpheresFromJson(app.getAssetManager());
        modelManager.loadPyramidsFromJson(app.getAssetManager());
        modelManager.loadConesFromJson(app.getAssetManager());
        if (isWindows()) {
            loadingScreen.setProgress(20);
        }

        modelManager.loadModelsFromJson(app.getAssetManager());
        if (isWindows()) {
            loadingScreen.setProgress(64);
        }

        lightManager.loadLightingFromJson(app.getAssetManager());
        if (isWindows()) {
            loadingScreen.setProgress(76);
        }
        skybox.loadSkyboxFromJson(app.getAssetManager());
        if (isWindows()) {
            loadingScreen.setProgress(85);
        }

        postProcessing.loadPostProcessingFromJson(app.getAssetManager());
        if (isWindows()) {
            loadingScreen.setProgress(95);
        }
        waterManager.loadWaterFromJson(app.getAssetManager());
        if (isWindows()) {
            loadingScreen.setProgress(100);
        }

        uiManager.loadTextFromJson(app.getAssetManager());

        if (isWindows()) {
            loadingScreen.close();
        }
    }
}
