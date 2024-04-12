package enginetest;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import enginetest.EngineFunctions.*;

public class EngineTest extends SimpleApplication {
    public CubeSpawner cubeSpawner;

    public static void main(String[] args) {
        EngineTest app = new EngineTest();
        app.setShowSettings(false); // Settings dialog not supported on mac
        app.start();
    }

    @Override
    public void simpleInitApp() {
        cubeSpawner = new CubeSpawner(this);
        cubeSpawner.CreateCube(1, 1, 1, 4, 5, 0, ColorRGBA.Blue);
        cubeSpawner.CreateCube(3, 2, 5, 0, 0, 0, ColorRGBA.Red);
    }

    @Override
    public void simpleUpdate(float tpf) {
        // this method will be called every game tick and can be used to make
        // updates
    }

    @Override
    public void simpleRender(RenderManager rm) {
        // add render code here (if any)
    }
}