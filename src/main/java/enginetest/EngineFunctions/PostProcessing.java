package enginetest.EngineFunctions;

import java.io.InputStream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.post.filters.LightScatteringFilter;
import com.jme3.post.filters.ToneMapFilter;

public class PostProcessing {
    private SimpleApplication app;

    public PostProcessing(SimpleApplication app) {
        this.app = app;
    }

    public void addPost(float intensity, Boolean enableBloom, Boolean enableSunrays) {
        FilterPostProcessor fpp = new FilterPostProcessor(app.getAssetManager());
        if (enableBloom) {
            BloomFilter bloom = new BloomFilter();
            bloom.setBloomIntensity(intensity);
            fpp.addFilter(bloom);
        }

        if (enableSunrays) {
            LightScatteringFilter lightScatteringFilter = new LightScatteringFilter(new Vector3f(-1, -1, -1).normalizeLocal());
            fpp.addFilter(lightScatteringFilter);
        }

        ToneMapFilter toneMap = new ToneMapFilter();
        fpp.addFilter(toneMap);
        app.getViewPort().addProcessor(fpp);
    }

    public void loadPostProcessingFromJson(AssetManager assetManager) {
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream is = assetManager.locateAsset(new com.jme3.asset.AssetKey<>("Data/game.json")).openStream()) {
            JsonNode root = mapper.readTree(is);
            for (JsonNode lightingNode : root.get("lighting")) {
                JsonNode addLight = lightingNode.get("addlight");

                boolean enableBloom = addLight.get("enableBloom").asBoolean();
                double bloomIntensity = addLight.get("bloomIntensity").asDouble();
                boolean enableSunrays = addLight.get("enableSunrays").asBoolean();

                addPost((float) bloomIntensity, enableBloom, enableSunrays);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
