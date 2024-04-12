package enginetest.EngineFunctions;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

public class CubeManager {
    private SimpleApplication app;

    public CubeManager(SimpleApplication app) {
        this.app = app;
    }

    public void CreateUnshadedCube(Vector3f size, Vector3f position, Vector3f rotation, ColorRGBA color, int objectId) {
        Box b = new Box(size.x, size.y, size.z);
        Geometry geom = new Geometry("Box", b);
        geom.rotate(rotation.x, rotation.y, rotation.z);
        geom.setLocalTranslation(position);
        geom.setUserData("objectId", objectId);

        Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        geom.setMaterial(mat);

        app.getRootNode().attachChild(geom);
    }

    public void CreateShadedSolidCube(Vector3f size, Vector3f position, Vector3f rotation, ColorRGBA ambientColor, ColorRGBA diffuseColor, ColorRGBA specularColor, float shininess, int objectId) {
        Box b = new Box(size.x, size.y, size.z);
        Geometry geom = new Geometry("Box", b);
        geom.rotate(rotation.x, rotation.y, rotation.z);
        geom.setLocalTranslation(position);
        geom.setUserData("objectId", objectId);

        Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        mat.setColor("Ambient", ambientColor);
        mat.setColor("Diffuse", diffuseColor);
        mat.setColor("Specular", specularColor);
        mat.setFloat("Shininess", shininess);
        geom.setMaterial(mat);

        app.getRootNode().attachChild(geom);
    }

    public void CreateTexturedCube(Vector3f size, Vector3f position, Vector3f rotation, String texturePath, int objectId) {
        Box b = new Box(size.x, size.y, size.z);
        Geometry geom = new Geometry("Box", b);
        geom.rotate(rotation.x, rotation.y, rotation.z);
        geom.setLocalTranslation(position);
        geom.setUserData("objectId", objectId);

        Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        Texture texture = app.getAssetManager().loadTexture(texturePath);
        mat.setTexture("DiffuseMap", texture);
        geom.setMaterial(mat);

        app.getRootNode().attachChild(geom);
    }

    public void RemoveCube(int objectId) {
        for (int i = 0; i < app.getRootNode().getQuantity(); i++) {
            Geometry geom = (Geometry) app.getRootNode().getChild(i);
            Integer storedId = geom.getUserData("objectId");
            if (storedId != null && storedId.equals(objectId)) {
                geom.removeFromParent();
                break;
            }
        }
    }
}
