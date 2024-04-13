package enginetest.EngineFunctions;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.ssao.SSAOFilter;
import com.jme3.shadow.DirectionalLightShadowFilter;
import com.jme3.shadow.DirectionalLightShadowRenderer;

public class LightingManager {

    private SimpleApplication app;

    public LightingManager(SimpleApplication app) {
        this.app = app;
    }

    public void addSun(ColorRGBA sunColor) {
        DirectionalLight sun = new DirectionalLight();
        sun.setColor(sunColor);
        sun.setDirection(new Vector3f(100f, -75f, 0.5f).normalizeLocal());
        app.getRootNode().addLight(sun);

        final int SHADOWMAP_SIZE=1024;
        DirectionalLightShadowRenderer dlsr = new DirectionalLightShadowRenderer(app.getAssetManager(), SHADOWMAP_SIZE, 3);
        dlsr.setLight(sun);
        app.getViewPort().addProcessor(dlsr);

        DirectionalLightShadowFilter dlsf = new DirectionalLightShadowFilter(app.getAssetManager(), SHADOWMAP_SIZE, 3);
        dlsf.setLight(sun);
        dlsf.setEnabled(true);
        FilterPostProcessor fpp = new FilterPostProcessor(app.getAssetManager());
        fpp.addFilter(dlsf);

        SSAOFilter ssaoFilter = new SSAOFilter(0.3f, 1000f, 0.2f, 0.35f);
        fpp.addFilter(ssaoFilter);
        app.getViewPort().addProcessor(fpp);
    }
}