package enginetest.EngineFunctions;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;

public class ModelManager {
    private SimpleApplication app;

    public ModelManager(SimpleApplication app) {
        this.app = app;
    }

    public void createModel(String modelPath, String texturePath, Vector3f position, Vector3f scale, Vector3f rotation, int objectId) {
        Spatial model = app.getAssetManager().loadModel(modelPath);
        model.setLocalTranslation(position);
        model.setLocalScale(scale);
        model.rotate(rotation.x, rotation.y, rotation.z);
        model.setShadowMode(ShadowMode.CastAndReceive);  
        model.setUserData("objectId", objectId);

        Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        Texture texture = app.getAssetManager().loadTexture(texturePath);
        mat.setTexture("DiffuseMap", texture);
        model.setMaterial(mat);
        app.getRootNode().attachChild(model);
    }

    public void applyNormalMap(String normalMapPath, int objectId) {
        Texture normalMap = app.getAssetManager().loadTexture(normalMapPath);

        for (Spatial child : app.getRootNode().getChildren()) {
            if (child.getUserData("objectId") != null && child.getUserData("objectId").equals(objectId)) {
                if (child instanceof com.jme3.scene.Geometry) {
                    com.jme3.scene.Geometry geom = (com.jme3.scene.Geometry) child;

                    Material mat = geom.getMaterial();

                    mat.setTexture("NormalMap", normalMap);

                    geom.setMaterial(mat);
                } else if (child instanceof com.jme3.scene.Node) {
                    com.jme3.scene.Node node = (com.jme3.scene.Node) child;
                    for (Spatial grandChild : node.getChildren()) {
                        if (grandChild instanceof com.jme3.scene.Geometry) {
                            com.jme3.scene.Geometry geom = (com.jme3.scene.Geometry) grandChild;
                            Material mat = geom.getMaterial();
                            mat.setTexture("NormalMap", normalMap);
                            geom.setMaterial(mat);
                        }
                    }
                }
                break;
            }
        }
    }   


    public void removeModel(int objectId) {
        for (Spatial child : app.getRootNode().getChildren()) {
            if (child.getUserData("objectId") != null && child.getUserData("objectId").equals(objectId)) {
                child.removeFromParent();
                break;
            }
        }
    }
}
