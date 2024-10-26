package enginetest.EngineFunctions;

import java.io.InputStream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.water.WaterFilter;

public class WaterManager {
    private SimpleApplication app;

    private FilterPostProcessor fpp;
    private WaterFilter water;
    private Vector3f lightDir = new Vector3f(-4.9f, -1.3f, 5.9f);

      public WaterManager(SimpleApplication app) {
        this.app = app;
    }
    
    public void createWater(float waterHeight) {
        fpp = new FilterPostProcessor(app.getAssetManager());
        water = new WaterFilter(app.getRootNode(), lightDir);
        water.setWaterHeight(waterHeight);
        fpp.addFilter(water);
        app.getViewPort().addProcessor(fpp);
    }

    public void Addwaves(float tpf, float initialWaterHeight) { //add to simple update
        float time = 0.0f;
        float waterHeight = 0.0f;

        time += tpf;
        waterHeight = (float) Math.cos(((time * 0.6f) % FastMath.TWO_PI)) * 1.5f;
        water.setWaterHeight(initialWaterHeight + waterHeight);
        water.setMaxAmplitude(0.3f);
    }

    public void Foam(boolean active) {
            water.setUseFoam(active);
    }

    public void setWaterColor(ColorRGBA mainColor, ColorRGBA deepWater) {
        water.setWaterColor(mainColor);
        water.setDeepWaterColor(deepWater);
    }

    public void setWaterTransparency(float transparency) {
        water.setWaterTransparency(transparency);
    }

    public WaterFilter getWater() {
        return water;
    }

    public void loadWaterFromJson(AssetManager assetManager) {
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream is = assetManager.locateAsset(new com.jme3.asset.AssetKey<>("Data/game.json")).openStream()) {
            JsonNode root = mapper.readTree(is);
            for (JsonNode waterNode : root.get("water")) {
                JsonNode addWater = waterNode.get("addocean");

                double transparency = addWater.get("waterTransparency").asDouble();
                double waterHeight = addWater.get("waterHeight").asDouble();
                int colorR = addWater.get("waterColor").get("r").asInt();
                int colorG = addWater.get("waterColor").get("g").asInt();
                int colorB = addWater.get("waterColor").get("b").asInt();
                int colorA = addWater.get("waterColor").get("a").asInt();
                int deepR = addWater.get("deepWaterColor").get("r").asInt();
                int deepG = addWater.get("deepWaterColor").get("g").asInt();
                int deepB = addWater.get("deepWaterColor").get("b").asInt();
                int deepA = addWater.get("deepWaterColor").get("a").asInt();
                boolean foam = addWater.get("foam").asBoolean();

                createWater((float) waterHeight);
                setWaterTransparency((float) transparency);
                setWaterColor(ColorRGBA.fromRGBA255(colorR, colorG, colorB, colorA), ColorRGBA.fromRGBA255(deepR, deepG, deepB, deepA));
                Foam(foam);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
