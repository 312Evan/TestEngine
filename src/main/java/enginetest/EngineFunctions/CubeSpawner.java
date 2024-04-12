package enginetest.EngineFunctions;

import enginetest.EngineTest;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

public class CubeSpawner {
    private SimpleApplication app;

    public CubeSpawner(SimpleApplication app) {
        this.app = app;
    }

    public void CreateCube(float length, float height, float width, float posX, float posY, float posZ, ColorRGBA color) {
        try {
            Box b = new Box(length, height, width);
            Geometry geom = new Geometry("Box", b);
            geom.setLocalTranslation(new Vector3f(posX, posY, posZ));

            Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
            mat.setColor("Color", color);
            geom.setMaterial(mat);

            app.getRootNode().attachChild(geom);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}