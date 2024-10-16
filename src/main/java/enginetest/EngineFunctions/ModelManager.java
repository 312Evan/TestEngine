package enginetest.EngineFunctions;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.ChaseCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Dome;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;

public class ModelManager {
    private SimpleApplication app;
    private Spatial playerModel;
    private CharacterControl player;
    private Vector3f walkDirection = new Vector3f();
    private boolean left = false, right = false, up = false, down = false;

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
        geom.setShadowMode(ShadowMode.CastAndReceive);

        app.getRootNode().attachChild(geom);
        addPhysics(geom, new BoxCollisionShape(size), 0);
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
        addPhysics(geom, new BoxCollisionShape(size), 0);
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
        addPhysics(geom, new BoxCollisionShape(size), 0);
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
        addPhysics(geom, new SphereCollisionShape(radius), 0);
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
        addPhysics(geom, new SphereCollisionShape(radius), 0);
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
        addPhysics(geom, new SphereCollisionShape(radius), 0);
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
        addPhysics(geom, new SphereCollisionShape(radius), 0);
    }

    public void createModel(String modelPath, String texturePath, Vector3f position, Vector3f scale, Vector3f rotation, int objectId) {
        Spatial model = app.getAssetManager().loadModel(modelPath);
        model.setLocalTranslation(position);
        model.setShadowMode(ShadowMode.CastAndReceive);
        model.setLocalScale(scale);
        model.rotate(rotation.x, rotation.y, rotation.z);
        model.setUserData("objectId", objectId);

        Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        Texture texture = app.getAssetManager().loadTexture(texturePath);
        mat.setTexture("DiffuseMap", texture);
        model.setMaterial(mat);

        app.getRootNode().attachChild(model);
        addPhysics(model, new BoxCollisionShape(scale), 0);
    }

    public void RemoveShape(int objectId) {
        for (int i = 0; i < app.getRootNode().getQuantity(); i++) {
            Geometry geom = (Geometry) app.getRootNode().getChild(i);
            Integer storedId = geom.getUserData("objectId");
            if (storedId != null && storedId.equals(objectId)) {
                geom.removeFromParent();
                app.getStateManager().getState(BulletAppState.class).getPhysicsSpace().remove(geom);
                break;
            }
        }
    }

    public void removeModel(int objectId) {
        for (Spatial child : app.getRootNode().getChildren()) {
            if (child.getUserData("objectId") != null && child.getUserData("objectId").equals(objectId)) {
                child.removeFromParent();
                app.getStateManager().getState(BulletAppState.class).getPhysicsSpace().remove(child);
                break;
            }
        }
    }

    private void addPhysics(Spatial spatial, com.jme3.bullet.collision.shapes.CollisionShape shape, float mass) {
        RigidBodyControl control = new RigidBodyControl(shape, mass);
        spatial.addControl(control);

        BulletAppState bulletAppState = app.getStateManager().getState(BulletAppState.class);
        bulletAppState.getPhysicsSpace().add(control);
    }

    public void createThirdPersonController(Vector3f position) {
        // Load the player model
        playerModel = app.getAssetManager().loadModel("Models/defaultCharacter.obj");
        playerModel.setLocalTranslation(position);
        playerModel.setShadowMode(ShadowMode.CastAndReceive);
        playerModel.setLocalScale(3.5f);

        Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        Texture texture = app.getAssetManager().loadTexture("Textures/defaultCharacter.png");
        mat.setTexture("DiffuseMap", texture);
        playerModel.setMaterial(mat);

        // Add the player model to the root node
        app.getRootNode().attachChild(playerModel);

        // Create a collision shape for the player
        CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.5f, 3.5f, 1);
        player = new CharacterControl(capsuleShape, 0.05f);
        player.setJumpSpeed(20);
        player.setFallSpeed(30);
        player.setGravity(30);
        player.setPhysicsLocation(position);
        playerModel.addControl(player);

        // Get the BulletAppState from the app (you need to have it initialized somewhere)
        BulletAppState bulletAppState = app.getStateManager().getState(BulletAppState.class);
        bulletAppState.getPhysicsSpace().add(player);

        // Setup the camera
        ChaseCamera chaseCam = new ChaseCamera(app.getCamera(), playerModel, app.getInputManager());
        chaseCam.setDefaultDistance(12);

        // Setup action key mappings
        app.getInputManager().addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        app.getInputManager().addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        app.getInputManager().addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
        app.getInputManager().addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
        app.getInputManager().addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));

        app.getInputManager().addListener(actionListener, "Left", "Right", "Up", "Down", "Jump");
    }

    private ActionListener actionListener = new ActionListener() {
        public void onAction(String binding, boolean value, float tpf) {
            if (binding.equals("Left")) {
                left = value;
            } else if (binding.equals("Right")) {
                right = value;
            } else if (binding.equals("Up")) {
                up = value;
            } else if (binding.equals("Down")) {
                down = value;
            } else if (binding.equals("Jump")) {
                player.jump();
            }
        }
    };

    public void update(float tpf) {
        Vector3f camDir = app.getCamera().getDirection().clone().multLocal(0.2f);
        Vector3f camLeft = app.getCamera().getLeft().clone().multLocal(0.2f);
        walkDirection.set(0, 0, 0);

        if (left) {
            walkDirection.addLocal(camLeft);
        }
        if (right) {
            walkDirection.addLocal(camLeft.negate());
        }
        if (up) {
            walkDirection.addLocal(camDir);
        }
        if (down) {
            walkDirection.addLocal(camDir.negate());
        }
        player.setWalkDirection(walkDirection);
    }
}