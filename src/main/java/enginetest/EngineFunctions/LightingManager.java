package enginetest.EngineFunctions;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.shadow.CompareMode;
import com.jme3.shadow.DirectionalLightShadowRenderer;
import com.jme3.renderer.queue.RenderQueue;

public class LightingManager {

    private SimpleApplication app;

    public LightingManager(SimpleApplication app) {
        this.app = app;
    }

    public void addSun(ColorRGBA sunColor) {
        DirectionalLight sun = new DirectionalLight();
        sun.setColor(sunColor);
        sun.setDirection(new Vector3f(100f, -100f, 0.5f).normalizeLocal());
        app.getRootNode().addLight(sun);

        int shadowMapSize = 1024;
        int shadowMapQuality = 2;
        DirectionalLightShadowRenderer dlsr = new DirectionalLightShadowRenderer(app.getAssetManager(), shadowMapSize, shadowMapQuality);
        dlsr.setShadowIntensity(0.5f);
        dlsr.setShadowCompareMode(CompareMode.Hardware);
        dlsr.setLight(sun);
        app.getViewPort().addProcessor(dlsr);
        app.getRootNode().setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
    }
}
