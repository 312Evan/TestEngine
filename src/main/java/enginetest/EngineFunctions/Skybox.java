package enginetest.EngineFunctions;

import enginetest.*;
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
