package enginetest.EngineFunctions;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

public class LightingManager {

    private SimpleApplication app;

    public LightingManager(SimpleApplication app) {
        this.app = app;
    }

    public void addSun(ColorRGBA sunColor) {
        DirectionalLight sun = new DirectionalLight();
        sun.setColor(sunColor);
        sun.setDirection(new Vector3f(-.5f, -.5f, -.5f).normalizeLocal());
        app.getRootNode().addLight(sun);

    }
}
