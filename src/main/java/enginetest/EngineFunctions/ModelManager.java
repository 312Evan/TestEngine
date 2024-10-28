package enginetest.EngineFunctions;

import java.io.InputStream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
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

    public void CreateShadedSolidCube(Vector3f size, Vector3f position, Vector3f rotation, ColorRGBA ambientColor, ColorRGBA diffuseColor, ColorRGBA specularColor, float shininess,
            int objectId) {
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

    public void CreateTexturedCubeWithNormal(Vector3f size, Vector3f position, Vector3f rotation, String texturePath, String normalMapPath, float tileX, float tileY,
            int objectId) {
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

        // Load and set the normal map
        Texture normalMap = app.getAssetManager().loadTexture(normalMapPath);
        normalMap.setWrap(Texture.WrapMode.Repeat);
        mat.setTexture("NormalMap", normalMap);

        geom.setMaterial(mat);
        geom.setShadowMode(ShadowMode.CastAndReceive);

        app.getRootNode().attachChild(geom);
        addPhysics(geom, new BoxCollisionShape(size), 0);
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

        // Get the BulletAppState from the app (you need to have it initialized
        // somewhere)
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

    public void loadCubesFromJson(AssetManager assetManager) {
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream is = assetManager.locateAsset(new com.jme3.asset.AssetKey<>("Data/game.json")).openStream()) {
            JsonNode root = mapper.readTree(is);
            for (JsonNode cubeNode : root.get("cubes")) {
                JsonNode addCube = cubeNode.get("addcube");

                Vector3f size = new Vector3f((float) addCube.get("size").get("x").asDouble(), (float) addCube.get("size").get("y").asDouble(),
                        (float) addCube.get("size").get("z").asDouble());
                Vector3f position = new Vector3f((float) addCube.get("position").get("x").asDouble(), (float) addCube.get("position").get("y").asDouble(),
                        (float) addCube.get("position").get("z").asDouble());
                Vector3f rotation = new Vector3f((float) addCube.get("rotation").get("x").asDouble(), (float) addCube.get("rotation").get("y").asDouble(),
                        (float) addCube.get("rotation").get("z").asDouble());
                int objectId = addCube.get("objectId").asInt();

                if (addCube.has("color")) {
                    ColorRGBA color = new ColorRGBA((float) addCube.get("color").get("r").asDouble(), (float) addCube.get("color").get("g").asDouble(),
                            (float) addCube.get("color").get("b").asDouble(), (float) addCube.get("color").get("a").asDouble());
                    CreateUnshadedCube(size, position, rotation, color, objectId);
                } else if (addCube.has("ambientColor") && addCube.has("diffuseColor") && addCube.has("specularColor")) {
                    ColorRGBA ambientColor = new ColorRGBA((float) addCube.get("ambientColor").get("r").asDouble(), (float) addCube.get("ambientColor").get("g").asDouble(),
                            (float) addCube.get("ambientColor").get("b").asDouble(), (float) addCube.get("ambientColor").get("a").asDouble());
                    ColorRGBA diffuseColor = new ColorRGBA((float) addCube.get("diffuseColor").get("r").asDouble(), (float) addCube.get("diffuseColor").get("g").asDouble(),
                            (float) addCube.get("diffuseColor").get("b").asDouble(), (float) addCube.get("diffuseColor").get("a").asDouble());
                    ColorRGBA specularColor = new ColorRGBA((float) addCube.get("specularColor").get("r").asDouble(), (float) addCube.get("specularColor").get("g").asDouble(),
                            (float) addCube.get("specularColor").get("b").asDouble(), (float) addCube.get("specularColor").get("a").asDouble());
                    float shininess = (float) addCube.get("shininess").asDouble();
                    CreateShadedSolidCube(size, position, rotation, ambientColor, diffuseColor, specularColor, shininess, objectId);
                } else if (addCube.has("texturePath") && addCube.has("normalMapPath")) {
                    String texturePath = addCube.get("texturePath").asText();
                    String normalMapPath = addCube.get("normalMapPath").asText();
                    float tileX = (float) addCube.get("tileX").asDouble();
                    float tileY = (float) addCube.get("tileY").asDouble();
                    CreateTexturedCubeWithNormal(size, position, rotation, texturePath, normalMapPath, tileX, tileY, objectId);
                } else if (addCube.has("texturePath")) {
                    String texturePath = addCube.get("texturePath").asText();
                    float tileX = (float) addCube.get("tileX").asDouble();
                    float tileY = (float) addCube.get("tileY").asDouble();
                    CreateTexturedCube(size, position, rotation, texturePath, tileX, tileY, objectId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadModelsFromJson(AssetManager assetManager) {
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream is = assetManager.locateAsset(new com.jme3.asset.AssetKey<>("Data/game.json")).openStream()) {
            JsonNode root = mapper.readTree(is);
            for (JsonNode modelNode : root.get("models")) {
                JsonNode addModel = modelNode.get("addmodel");

                Vector3f size = new Vector3f((float) addModel.get("size").get("x").asDouble(), (float) addModel.get("size").get("y").asDouble(),
                        (float) addModel.get("size").get("z").asDouble());
                Vector3f position = new Vector3f((float) addModel.get("position").get("x").asDouble(), (float) addModel.get("position").get("y").asDouble(),
                        (float) addModel.get("position").get("z").asDouble());
                Vector3f rotation = new Vector3f((float) addModel.get("rotation").get("x").asDouble(), (float) addModel.get("rotation").get("y").asDouble(),
                        (float) addModel.get("rotation").get("z").asDouble());
                String texturePath = addModel.get("texturePath").asText();
                String modelPath = addModel.get("modelPath").asText();
                int objectId = addModel.get("objectId").asInt();

                createModel(modelPath, texturePath, position, size, rotation, objectId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadSpheresFromJson(AssetManager assetManager) {
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream is = assetManager.locateAsset(new com.jme3.asset.AssetKey<>("Data/game.json")).openStream()) {
            JsonNode root = mapper.readTree(is);
            for (JsonNode shapeNode : root.get("shapes")) {
                JsonNode addSphere = shapeNode.get("addsphere");

                float radius = (float) addSphere.get("radius").asDouble();
                Vector3f position = new Vector3f((float) addSphere.get("position").get("x").asDouble(), (float) addSphere.get("position").get("y").asDouble(),
                        (float) addSphere.get("position").get("z").asDouble());
                Vector3f rotation = new Vector3f((float) addSphere.get("rotation").get("x").asDouble(), (float) addSphere.get("rotation").get("y").asDouble(),
                        (float) addSphere.get("rotation").get("z").asDouble());
                String texturePath = addSphere.get("texturePath").asText();
                int tileX = addSphere.get("tileX").asInt();
                int tileY = addSphere.get("tileY").asInt();
                int objectId = addSphere.get("objectId").asInt();

                CreateSphere(radius, position, rotation, texturePath, tileX, tileY, objectId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void loadPyramidsFromJson(AssetManager assetManager) {
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream is = assetManager.locateAsset(new com.jme3.asset.AssetKey<>("Data/game.json")).openStream()) {
            JsonNode root = mapper.readTree(is);
            for (JsonNode shapeNode : root.get("shapes")) {
                JsonNode addPyramid = shapeNode.get("addpyramid");

                float radius = (float) addPyramid.get("radius").asDouble();
                Vector3f position = new Vector3f((float) addPyramid.get("position").get("x").asDouble(), (float) addPyramid.get("position").get("y").asDouble(),
                        (float) addPyramid.get("position").get("z").asDouble());
                Vector3f rotation = new Vector3f((float) addPyramid.get("rotation").get("x").asDouble(), (float) addPyramid.get("rotation").get("y").asDouble(),
                        (float) addPyramid.get("rotation").get("z").asDouble());
                String texturePath = addPyramid.get("texturePath").asText();
                int tileX = addPyramid.get("tileX").asInt();
                int tileY = addPyramid.get("tileY").asInt();
                int objectId = addPyramid.get("objectId").asInt();

                CreatePyramid(radius, position, rotation, texturePath, tileX, tileY, objectId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}