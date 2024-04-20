package enginetest.EngineFunctions;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.scene.shape.Dome;
import com.jme3.texture.Texture;

public class ModelManager {
    private SimpleApplication app;

    public ModelManager(SimpleApplication app) {
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
        geom.setShadowMode(ShadowMode.CastAndReceive);
        app.getRootNode().attachChild(geom);
    }

    public void CreateTexturedCube(Vector3f size, Vector3f position, Vector3f rotation, String texturePath, float tileX, float tileY, int objectId) {
        Box b = new Box(size.x, size.y, size.z);
        Geometry geom = new Geometry("Box", b);
        b.scaleTextureCoordinates(new Vector2f(tileX, tileY));
        geom.rotate(rotation.x, rotation.y, rotation.z);
        geom.setLocalTranslation(position);
        geom.setUserData("objectId", objectId);

        Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        Texture texture = app.getAssetManager().loadTexture(texturePath);
        texture.setWrap(Texture.WrapMode.Repeat);
        mat.setTexture("DiffuseMap", texture);
        geom.setMaterial(mat);
        geom.setShadowMode(ShadowMode.CastAndReceive);
        app.getRootNode().attachChild(geom);
    }
    
    public void CreateSphere(float radius, Vector3f position, Vector3f rotation, String texturePath, float tileX, float tileY, int objectId) {
        Sphere sphere = new Sphere(32, 32, radius, false, false);
        Geometry geom = new Geometry("Sphere", sphere);
        geom.setLocalTranslation(position);
        sphere.scaleTextureCoordinates(new Vector2f(tileX, tileY));
        geom.rotate(rotation.x, rotation.y, rotation.z);
        geom.setLocalTranslation(position);
        geom.setUserData("objectId", objectId);
        Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        Texture texture = app.getAssetManager().loadTexture(texturePath);
        texture.setWrap(Texture.WrapMode.Repeat);
        mat.setTexture("DiffuseMap", texture);
        geom.setMaterial(mat);
        geom.setShadowMode(ShadowMode.CastAndReceive);
        app.getRootNode().attachChild(geom);
    }
    
    public void CreatePyramid(float radius, Vector3f position, Vector3f rotation, String texturePath, float tileX, float tileY, int objectId) {
        Dome mesh = new Dome(position, 2, 4, radius, false);
        Geometry geom = new Geometry("Pyramid", mesh);
        mesh.scaleTextureCoordinates(new Vector2f(tileX, tileY));
        geom.rotate(rotation.x, rotation.y, rotation.z);
        geom.setLocalTranslation(position);
        geom.setUserData("objectId", objectId);

        Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        Texture texture = app.getAssetManager().loadTexture(texturePath);
        texture.setWrap(Texture.WrapMode.Repeat);
        mat.setTexture("DiffuseMap", texture);
        geom.setMaterial(mat);
        geom.setShadowMode(ShadowMode.CastAndReceive);
        app.getRootNode().attachChild(geom);
    }
    
    public void CreateCone(float radius, Vector3f position, Vector3f rotation, String texturePath, float tileX, float tileY, int objectId) {
        Dome mesh = new Dome(position, 2, 50, radius, false);
        Geometry geom = new Geometry("Pyramid", mesh);
        mesh.scaleTextureCoordinates(new Vector2f(tileX, tileY));
        geom.rotate(rotation.x, rotation.y, rotation.z);
        geom.setLocalTranslation(position);
        geom.setUserData("objectId", objectId);

        Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        Texture texture = app.getAssetManager().loadTexture(texturePath);
        texture.setWrap(Texture.WrapMode.Repeat);
        mat.setTexture("DiffuseMap", texture);
        geom.setMaterial(mat);
        geom.setShadowMode(ShadowMode.CastAndReceive);
        app.getRootNode().attachChild(geom);
    }
    
    public void CreateDome(int planes, int radialSamples, float radius, Vector3f position, Vector3f rotation, String texturePath, float tileX, float tileY, int objectId) {
        Dome mesh = new Dome(position, planes, radialSamples, radius, false);
        Geometry geom = new Geometry("Pyramid", mesh);
        mesh.scaleTextureCoordinates(new Vector2f(tileX, tileY));
        geom.rotate(rotation.x, rotation.y, rotation.z);
        geom.setLocalTranslation(position);
        geom.setUserData("objectId", objectId);

        Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        Texture texture = app.getAssetManager().loadTexture(texturePath);
        texture.setWrap(Texture.WrapMode.Repeat);
        mat.setTexture("DiffuseMap", texture);
        geom.setMaterial(mat);
        geom.setShadowMode(ShadowMode.CastAndReceive);
        app.getRootNode().attachChild(geom);
    }
    
    public void createModel(String modelPath, String texturePath, Vector3f position, Vector3f scale, Vector3f rotation, int objectId) {
        Spatial model = app.getAssetManager().loadModel(modelPath);

        model.setShadowMode(ShadowMode.CastAndReceive);
        model.setLocalScale(scale);
        model.rotate(rotation.x, rotation.y, rotation.z);
        model.setUserData("objectId", objectId);

        Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        Texture texture = app.getAssetManager().loadTexture(texturePath); 
        mat.setTexture("DiffuseMap", texture);
        model.setMaterial(mat);
        app.getRootNode().attachChild(model);
    }

    public void RemoveShape(int objectId) {
        for (int i = 0; i < app.getRootNode().getQuantity(); i++) {
            Geometry geom = (Geometry) app.getRootNode().getChild(i);
            Integer storedId = geom.getUserData("objectId");
            if (storedId != null && storedId.equals(objectId)) {
                geom.removeFromParent();
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