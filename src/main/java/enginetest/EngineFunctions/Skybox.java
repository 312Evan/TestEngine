package enginetest.EngineFunctions;

import com.jme3.app.SimpleApplication;
import com.jme3.scene.Spatial;
import com.jme3.util.SkyFactory;
import com.jme3.util.SkyFactory.EnvMapType;

public class Skybox {

    private SimpleApplication app;
    private Spatial currentSkybox;

    public Skybox(SimpleApplication app) {
        this.app = app;
    }

    public void setSkyBox(String filepath) {
        removeCurrentSkybox();
        currentSkybox = SkyFactory.createSky(app.getAssetManager(), filepath, EnvMapType.CubeMap);
        app.getRootNode().attachChild(currentSkybox);
    }

    public void setHDRSky(String filepath) {
        removeCurrentSkybox();
        currentSkybox = SkyFactory.createSky(app.getAssetManager(), filepath, SkyFactory.EnvMapType.EquirectMap);
        app.getRootNode().attachChild(currentSkybox);
    }

    public void SetDefaultSkybox() {
        removeCurrentSkybox();
        currentSkybox = SkyFactory.createSky(app.getAssetManager(), "Textures/BrightSky.dds", EnvMapType.CubeMap);
        app.getRootNode().attachChild(currentSkybox);
    }

    public void SetDefaultSkybox2() {
        removeCurrentSkybox();
        currentSkybox = SkyFactory.createSky(app.getAssetManager(), "Textures/BlueSky.dds", EnvMapType.CubeMap);
        app.getRootNode().attachChild(currentSkybox);
    }

    private void removeCurrentSkybox() {
        if (currentSkybox != null) {
            app.getRootNode().detachChild(currentSkybox);
            currentSkybox = null;
        }
    }
}
