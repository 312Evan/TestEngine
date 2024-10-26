package enginetest.EngineFunctions;

import java.io.InputStream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.post.filters.ToneMapFilter;

    public class PostProcessing {
        private SimpleApplication app;

        public PostProcessing(SimpleApplication app) {
            this.app = app;
        }

        public void addBloom(float intensity) {
            FilterPostProcessor fpp = new FilterPostProcessor(app.getAssetManager());
            BloomFilter bloom = new BloomFilter();
            ToneMapFilter toneMap = new ToneMapFilter();
            fpp.addFilter(toneMap);
            bloom.setBloomIntensity(intensity);
            fpp.addFilter(bloom);
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

                if (enableBloom) {
                    addBloom((float)bloomIntensity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
