package enginetest.EngineFunctions;

import java.io.InputStream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
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

    public void loadSkyboxFromJson(AssetManager assetManager) {
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream is = assetManager.locateAsset(new com.jme3.asset.AssetKey<>("Data/game.json")).openStream()) {
            JsonNode root = mapper.readTree(is);
            for (JsonNode lightingNode : root.get("lighting")) {
                JsonNode addLight = lightingNode.get("addlight");

                boolean hdrSkybox = addLight.get("hdrSkybox").asBoolean();
                String skyboxPath = addLight.get("skyboxPath").asText();

                if (hdrSkybox && skyboxPath != null) {
                    setHDRSky(skyboxPath);
                } else if (!hdrSkybox && skyboxPath != null) {
                    setSkyBox(skyboxPath);
                } else {
                    SetDefaultSkybox();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
