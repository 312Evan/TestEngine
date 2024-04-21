package enginetest.EngineFunctions;

import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.light.SpotLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.ssao.SSAOFilter;
import com.jme3.shadow.DirectionalLightShadowFilter;
import com.jme3.shadow.DirectionalLightShadowRenderer;
import com.jme3.shadow.EdgeFilteringMode;

public class LightingManager {

    private SimpleApplication app;
    DirectionalLight sun = new DirectionalLight();
    private DirectionalLightShadowRenderer dlsr;
    private DirectionalLightShadowFilter dlsf;

    public LightingManager(SimpleApplication app) {
        this.app = app;
    }

    public void addSun() {
        AmbientLight ambientLight = new AmbientLight();
        ambientLight.setColor(ColorRGBA.White.mult(.6f));
        app.getRootNode().addLight(ambientLight);

        sun.setColor(new ColorRGBA(1.0f, 0.95f, 0.85f, 1.0f));
        sun.setDirection(new Vector3f(-0.9f, -50f, -0.5f).normalizeLocal());
        app.getRootNode().addLight(sun);

        dlsr = new DirectionalLightShadowRenderer(app.getAssetManager(), 2048, 4);
        dlsr.setLight(sun);
        dlsr.setLambda(0.55f);
        dlsr.setShadowIntensity(0.8f);
        dlsr.setEdgeFilteringMode(EdgeFilteringMode.PCF8);
        dlsr.setEnabledStabilization(true);
        dlsr.setShadowZExtend(1000);
        app.getViewPort().addProcessor(dlsr);

        dlsf = new DirectionalLightShadowFilter(app.getAssetManager(), 2048, 4);
        dlsf.setLight(sun);
        dlsf.setLambda(0.55f);
        dlsf.setEdgesThickness(2);
        dlsf.setShadowIntensity(0.8f);
        dlsf.setEdgeFilteringMode(EdgeFilteringMode.PCF8);
        dlsf.setEnabled(false);

        FilterPostProcessor fpp = new FilterPostProcessor(app.getAssetManager());
        fpp.addFilter(dlsf);

        app.getViewPort().addProcessor(fpp);
        
        SSAOFilter ssaoFilter = new SSAOFilter(5.1f, 4.5f, 0.2f, 0.1f);
        fpp.addFilter(ssaoFilter);
        app.getViewPort().addProcessor(fpp);
    }
    
    public DirectionalLight getSun() {
        return sun;
    }

    public void addPointLight(Vector3f position, ColorRGBA color, float radius) {
        PointLight pointLight = new PointLight();
        pointLight.setColor(color);
        pointLight.setRadius(radius);
        pointLight.setPosition(position);
        app.getRootNode().addLight(pointLight);
    }

    public void addSpotLight(Vector3f position, float innerAngle, float outerAngle, ColorRGBA color, float range) {
        SpotLight spotLight = new SpotLight();
        spotLight.setSpotRange(range);
        spotLight.setSpotInnerAngle(innerAngle * FastMath.DEG_TO_RAD);
        spotLight.setSpotOuterAngle(outerAngle * FastMath.DEG_TO_RAD);
        spotLight.setColor(color);
        spotLight.setPosition(position);
        spotLight.setDirection(new Vector3f(-0.9f, -50f, -0.5f).normalizeLocal());
        app.getRootNode().addLight(spotLight);
    }
}