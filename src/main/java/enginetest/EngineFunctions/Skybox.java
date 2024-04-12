package enginetest.EngineFunctions;

import com.jme3.app.SimpleApplication;

import com.jme3.util.SkyFactory;
import com.jme3.util.SkyFactory.EnvMapType;

public class Skybox {

    private SimpleApplication app;

    public Skybox(SimpleApplication app) {
        this.app = app;
    }

    public void setSkyBox(String filepath) {
        app.getRootNode().attachChild(SkyFactory.createSky(app.getAssetManager(), filepath, EnvMapType.CubeMap));
    }

    public void SetDefaultSkybox() {
        app.getRootNode().attachChild(SkyFactory.createSky(app.getAssetManager(), "Textures/BrightSky.dds", EnvMapType.CubeMap));
    }

    public void SetDefaultSkybox2() {
        app.getRootNode().attachChild(SkyFactory.createSky(app.getAssetManager(), "Textures/BlueSky.dds", EnvMapType.CubeMap));
    }
}
