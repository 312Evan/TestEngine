package enginetest.EngineFunctions;

import com.jme3.app.SimpleApplication;
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

}
