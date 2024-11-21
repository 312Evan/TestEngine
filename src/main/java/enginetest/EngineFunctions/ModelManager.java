package enginetest.EngineFunctions;

import java.io.InputStream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CompoundCollisionShape;
import com.jme3.bullet.collision.shapes.MeshCollisionShape;
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
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Dome;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;
import com.jme3.util.TangentBinormalGenerator;

public class ModelManager {
    private SimpleApplication app;
    private Spatial playerModel;
    private CharacterControl player;
    private Vector3f walkDirection = new Vector3f();
    private boolean left = false, right = false, up = false, down = false;

    public ModelManager(SimpleApplication app) {
        this.app = app;
    }

    public void CreateUnshadedCube(Vector3f size, Vector3f position, Vector3f rotation, ColorRGBA color, int mass, int objectId) {
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
        addPhysics(geom, new BoxCollisionShape(size), mass);
    }

    public void CreateShadedSolidCube(Vector3f size, Vector3f position, Vector3f rotation, ColorRGBA ambientColor, ColorRGBA diffuseColor, ColorRGBA specularColor, float shininess,
            int mass, int objectId) {
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
        addPhysics(geom, new BoxCollisionShape(size), mass);
    }

    public void CreateTexturedCube(Vector3f size, Vector3f position, Vector3f rotation, String texturePath, int tileX, int tileY, int mass, int objectId) {
        createModel("Models/cube.obj", texturePath, tileX, tileY, position, size, rotation, mass, objectId);
    }

    public void CreateSphere(float radius, Vector3f position, Vector3f rotation, String texturePath, float tileX, float tileY, int mass, int objectId) {
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
        addPhysics(geom, new SphereCollisionShape(radius), mass);
    }

    public void CreatePyramid(float radius, Vector3f position, Vector3f rotation, String texturePath, float tileX, float tileY, int mass, int objectId) {
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
        addPhysics(geom, new SphereCollisionShape(radius), mass);
    }

    public void CreateCone(float radius, Vector3f position, Vector3f rotation, String texturePath, float tileX, float tileY, int mass, int objectId) {
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
        addPhysics(geom, new SphereCollisionShape(radius), mass);
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

    public void createModel(String modelPath, String texturePath, int tileX, int tileY, Vector3f position, Vector3f scale, Vector3f rotation, int mass, int objectId) {
        Spatial model = app.getAssetManager().loadModel(modelPath);
        model.setLocalTranslation(position);
        model.setShadowMode(ShadowMode.CastAndReceive);
        model.setLocalScale(scale);
        model.rotate(rotation.x, rotation.y, rotation.z);
        model.setUserData("objectId", objectId);

        Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");

        addPresetTexture(texturePath, mat, model);

        if (model instanceof Geometry) {
            ((Geometry) model).getMesh().scaleTextureCoordinates(new Vector2f(tileX, tileY));
        } else if (model instanceof Node) {
            for (Spatial child : ((Node) model).getChildren()) {
                if (child instanceof Geometry) {
                    ((Geometry) child).getMesh().scaleTextureCoordinates(new Vector2f(tileX, tileY));
                }
            }
        }

        app.getRootNode().attachChild(model);

        if (model instanceof Geometry) {
            addScaledPhysics(model, scale, mass);
        } else if (model instanceof Node) {
            for (Spatial child : ((Node) model).getChildren()) {
                if (child instanceof Geometry) {
                    addScaledPhysics(child, scale, mass);
                }
            }
        }
    }

    public void createModelWithCustomTexture(String modelPath, String texturePath, String normalPath, Vector3f position, Vector3f scale, Vector3f rotation, int mass, int objectId) {
        Spatial model = app.getAssetManager().loadModel(modelPath);
        model.setLocalTranslation(position);
        model.setShadowMode(ShadowMode.CastAndReceive);
        model.setLocalScale(scale);
        model.rotate(rotation.x, rotation.y, rotation.z);
        model.setUserData("objectId", objectId);

        Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        Texture texture = app.getAssetManager().loadTexture(texturePath);
        mat.setTexture("DiffuseMap", texture);
        mat.setColor("Specular", ColorRGBA.White);
        mat.setColor("Diffuse", ColorRGBA.White);
        mat.setFloat("Shininess", 0f);
        mat.setColor("Ambient", ColorRGBA.White);
        mat.setBoolean("UseMaterialColors", true);
        TangentBinormalGenerator.generate(model);
        mat.setTexture("NormalMap", app.getAssetManager().loadTexture(normalPath));
        model.setMaterial(mat);

        app.getRootNode().attachChild(model);

        if (model instanceof Geometry) {
            addScaledPhysics(model, scale, mass);
        } else if (model instanceof Node) {
            for (Spatial child : ((Node) model).getChildren()) {
                if (child instanceof Geometry) {
                    addScaledPhysics(child, scale, mass);
                }
            }
        }
    }

    private void addScaledPhysics(Spatial spatial, Vector3f scale, float mass) {
        MeshCollisionShape meshShape = new MeshCollisionShape(((Geometry) spatial).getMesh());

        CompoundCollisionShape compoundShape = new CompoundCollisionShape();
        compoundShape.addChildShape(meshShape, Vector3f.ZERO);
        compoundShape.setScale(scale);

        RigidBodyControl control = new RigidBodyControl(compoundShape, mass);
        spatial.addControl(control);

        BulletAppState bulletAppState = app.getStateManager().getState(BulletAppState.class);
        bulletAppState.getPhysicsSpace().add(control);
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
            Integer storedId = child.getUserData("objectId");
            if (storedId != null && storedId.equals(objectId)) {
                child.removeFromParent();
                app.getStateManager().getState(BulletAppState.class).getPhysicsSpace().remove(child.getControl(RigidBodyControl.class));
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
        playerModel = app.getAssetManager().loadModel("Models/defaultCharacter.obj");
        playerModel.setLocalTranslation(position);
        playerModel.setShadowMode(ShadowMode.CastAndReceive);
        playerModel.setLocalScale(3.5f);

        Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        Texture texture = app.getAssetManager().loadTexture("Textures/ModelTextures/defaultCharacter.png");
        mat.setTexture("DiffuseMap", texture);
        playerModel.setMaterial(mat);

        app.getRootNode().attachChild(playerModel);

        CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.5f, 3.5f, 1);
        player = new CharacterControl(capsuleShape, 0.05f);
        player.setJumpSpeed(20);
        player.setFallSpeed(30);
        player.setGravity(30);
        player.setPhysicsLocation(position);
        playerModel.addControl(player);

        BulletAppState bulletAppState = app.getStateManager().getState(BulletAppState.class);
        bulletAppState.getPhysicsSpace().add(player);

        ChaseCamera chaseCam = new ChaseCamera(app.getCamera(), playerModel, app.getInputManager());
        chaseCam.setDefaultDistance(12);

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
                int mass = addCube.get("mass").asInt();
                int objectId = addCube.get("objectId").asInt();
                int tileX = addCube.get("tileX").asInt();
                int tileY = addCube.get("tileY").asInt();
                String texturePath = addCube.get("texturePath").asText();
                CreateTexturedCube(size, position, rotation, texturePath, tileX, tileY, mass, objectId);
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
                int mass = addModel.get("mass").asInt();
                int objectId = addModel.get("objectId").asInt();

                createModel(modelPath, texturePath, 1, 1, position, size, rotation, mass, objectId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadCustomModelsFromJson(AssetManager assetManager) {
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream is = assetManager.locateAsset(new com.jme3.asset.AssetKey<>("Data/game.json")).openStream()) {
            JsonNode root = mapper.readTree(is);
            for (JsonNode modelNode : root.get("custommodels")) {
                JsonNode addModel = modelNode.get("addmodelwithcustomtexture");

                Vector3f size = new Vector3f((float) addModel.get("size").get("x").asDouble(), (float) addModel.get("size").get("y").asDouble(),
                        (float) addModel.get("size").get("z").asDouble());
                Vector3f position = new Vector3f((float) addModel.get("position").get("x").asDouble(), (float) addModel.get("position").get("y").asDouble(),
                        (float) addModel.get("position").get("z").asDouble());
                Vector3f rotation = new Vector3f((float) addModel.get("rotation").get("x").asDouble(), (float) addModel.get("rotation").get("y").asDouble(),
                        (float) addModel.get("rotation").get("z").asDouble());
                String texturePath = addModel.get("texturePath").asText();
                String normalPath = addModel.get("normalPath").asText();
                String modelPath = addModel.get("modelPath").asText();
                int mass = addModel.get("mass").asInt();
                int objectId = addModel.get("objectId").asInt();

                createModelWithCustomTexture(modelPath, texturePath, normalPath, position, size, rotation, mass, objectId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadSpheresFromJson(AssetManager assetManager) {
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream is = assetManager.locateAsset(new com.jme3.asset.AssetKey<>("Data/game.json")).openStream()) {
            JsonNode root = mapper.readTree(is);
            for (JsonNode shapeNode : root.get("spheres")) {
                JsonNode addSphere = shapeNode.get("addsphere");

                float radius = (float) addSphere.get("radius").asDouble();
                Vector3f position = new Vector3f((float) addSphere.get("position").get("x").asDouble(), (float) addSphere.get("position").get("y").asDouble(),
                        (float) addSphere.get("position").get("z").asDouble());
                Vector3f rotation = new Vector3f((float) addSphere.get("rotation").get("x").asDouble(), (float) addSphere.get("rotation").get("y").asDouble(),
                        (float) addSphere.get("rotation").get("z").asDouble());
                String texturePath = addSphere.get("texturePath").asText();
                int tileX = addSphere.get("tileX").asInt();
                int tileY = addSphere.get("tileY").asInt();
                int mass = addSphere.get("mass").asInt();
                int objectId = addSphere.get("objectId").asInt();

                CreateSphere(radius, position, rotation, texturePath, tileX, tileY, mass, objectId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadPyramidsFromJson(AssetManager assetManager) {
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream is = assetManager.locateAsset(new com.jme3.asset.AssetKey<>("Data/game.json")).openStream()) {
            JsonNode root = mapper.readTree(is);
            for (JsonNode shapeNode : root.get("pyramids")) {
                JsonNode addPyramid = shapeNode.get("addpyramid");

                float radius = (float) addPyramid.get("radius").asDouble();
                Vector3f position = new Vector3f((float) addPyramid.get("position").get("x").asDouble(), (float) addPyramid.get("position").get("y").asDouble(),
                        (float) addPyramid.get("position").get("z").asDouble());
                Vector3f rotation = new Vector3f((float) addPyramid.get("rotation").get("x").asDouble(), (float) addPyramid.get("rotation").get("y").asDouble(),
                        (float) addPyramid.get("rotation").get("z").asDouble());
                String texturePath = addPyramid.get("texturePath").asText();
                int tileX = addPyramid.get("tileX").asInt();
                int tileY = addPyramid.get("tileY").asInt();
                int mass = addPyramid.get("mass").asInt();
                int objectId = addPyramid.get("objectId").asInt();

                CreatePyramid(radius, position, rotation, texturePath, tileX, tileY, mass, objectId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadConesFromJson(AssetManager assetManager) {
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream is = assetManager.locateAsset(new com.jme3.asset.AssetKey<>("Data/game.json")).openStream()) {
            JsonNode root = mapper.readTree(is);
            for (JsonNode shapeNode : root.get("cones")) {
                JsonNode addCone = shapeNode.get("addcone");

                float radius = (float) addCone.get("radius").asDouble();
                Vector3f position = new Vector3f((float) addCone.get("position").get("x").asDouble(), (float) addCone.get("position").get("y").asDouble(),
                        (float) addCone.get("position").get("z").asDouble());
                Vector3f rotation = new Vector3f((float) addCone.get("rotation").get("x").asDouble(), (float) addCone.get("rotation").get("y").asDouble(),
                        (float) addCone.get("rotation").get("z").asDouble());
                String texturePath = addCone.get("texturePath").asText();
                int tileX = addCone.get("tileX").asInt();
                int tileY = addCone.get("tileY").asInt();
                int mass = addCone.get("mass").asInt();
                int objectId = addCone.get("objectId").asInt();

                CreateCone(radius, position, rotation, texturePath, tileX, tileY, mass, objectId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Material Presets
    public void addPresetTexture(String textureName, Material mat, Spatial model) {
        if ("rock".equals(textureName)) {
            Texture texture = app.getAssetManager().loadTexture("Textures/Textures/rock.jpg");
            texture.setWrap(Texture.WrapMode.Repeat);
            mat.setTexture("DiffuseMap", texture);
            mat.setColor("Specular", ColorRGBA.DarkGray);
            mat.setColor("Diffuse", ColorRGBA.White);
            mat.setColor("Ambient", ColorRGBA.White);
            mat.setFloat("Shininess", 32f);
            mat.setBoolean("UseMaterialColors", true);
            TangentBinormalGenerator.generate(model);
            Texture normalMap = app.getAssetManager().loadTexture("Textures/rockNormal.jpg");
            normalMap.setWrap(Texture.WrapMode.Repeat);
            mat.setTexture("NormalMap", normalMap);
        } else if ("sand".equals(textureName)) {
            Texture texture = app.getAssetManager().loadTexture("Textures/Textures/sand.jpg");
            texture.setWrap(Texture.WrapMode.Repeat);
            mat.setTexture("DiffuseMap", texture);
            mat.setColor("Specular", ColorRGBA.White);
            mat.setColor("Diffuse", ColorRGBA.White);
            mat.setColor("Ambient", ColorRGBA.White);
            mat.setFloat("Shininess", 0f);
            mat.setBoolean("UseMaterialColors", true);
            TangentBinormalGenerator.generate(model);
            Texture normalMap = app.getAssetManager().loadTexture("Textures/sandNormal.jpg");
            normalMap.setWrap(Texture.WrapMode.Repeat);
            mat.setTexture("NormalMap", normalMap);
        } else if ("grass".equals(textureName)) {
            Texture texture = app.getAssetManager().loadTexture("Textures/Textures/grass.jpg");
            texture.setWrap(Texture.WrapMode.Repeat);
            mat.setTexture("DiffuseMap", texture);
            mat.setColor("Specular", ColorRGBA.White);
            mat.setColor("Diffuse", ColorRGBA.White);
            mat.setColor("Ambient", ColorRGBA.White);
            mat.setFloat("Shininess", 1f);
            mat.setBoolean("UseMaterialColors", true);
            TangentBinormalGenerator.generate(model);
            Texture normalMap = app.getAssetManager().loadTexture("Textures/grassNormal.jpg");
            normalMap.setWrap(Texture.WrapMode.Repeat);
            mat.setTexture("NormalMap", normalMap);
        } else if ("brick".equals(textureName)) {
            Texture texture = app.getAssetManager().loadTexture("Textures/Textures/brick.jpg");
            texture.setWrap(Texture.WrapMode.Repeat);
            mat.setTexture("DiffuseMap", texture);
            mat.setColor("Specular", ColorRGBA.White);
            mat.setColor("Diffuse", ColorRGBA.White);
            mat.setColor("Ambient", ColorRGBA.White);
            mat.setFloat("Shininess", 1f);
            mat.setBoolean("UseMaterialColors", true);
            TangentBinormalGenerator.generate(model);
            Texture normalMap = app.getAssetManager().loadTexture("Textures/brickNormal.jpg");
            normalMap.setWrap(Texture.WrapMode.Repeat);
            mat.setTexture("NormalMap", normalMap);
        } else if ("dirt".equals(textureName)) {
            Texture texture = app.getAssetManager().loadTexture("Textures/Textures/dirt.jpg");
            texture.setWrap(Texture.WrapMode.Repeat);
            mat.setTexture("DiffuseMap", texture);
            mat.setColor("Specular", ColorRGBA.Brown);
            mat.setColor("Diffuse", ColorRGBA.White);
            mat.setColor("Ambient", ColorRGBA.White);
            mat.setFloat("Shininess", 15f);
            mat.setBoolean("UseMaterialColors", true);
            TangentBinormalGenerator.generate(model);
            Texture normalMap = app.getAssetManager().loadTexture("Textures/dirtNormal.jpg");
            normalMap.setWrap(Texture.WrapMode.Repeat);
            mat.setTexture("NormalMap", normalMap);
        } else if ("ice".equals(textureName)) {
            Texture texture = app.getAssetManager().loadTexture("Textures/Textures/ice.jpg");
            texture.setWrap(Texture.WrapMode.Repeat);
            mat.setTexture("DiffuseMap", texture);
            mat.setColor("Specular", ColorRGBA.White);
            mat.setColor("Diffuse", ColorRGBA.White);
            mat.setFloat("Shininess", 128f);
            mat.setColor("Ambient", ColorRGBA.White);
            mat.setBoolean("UseMaterialColors", true);
            TangentBinormalGenerator.generate(model);
            Texture normalMap = app.getAssetManager().loadTexture("Textures/iceNormal.jpg");
            normalMap.setWrap(Texture.WrapMode.Repeat);
            mat.setTexture("NormalMap", normalMap);
        } else if ("concrete".equals(textureName)) {
            Texture texture = app.getAssetManager().loadTexture("Textures/Textures/concrete.jpg");
            texture.setWrap(Texture.WrapMode.Repeat);
            mat.setTexture("DiffuseMap", texture);
            mat.setColor("Specular", ColorRGBA.White);
            mat.setColor("Diffuse", ColorRGBA.White);
            mat.setFloat("Shininess", 1f);
            mat.setColor("Ambient", ColorRGBA.White);
            mat.setBoolean("UseMaterialColors", true);
            TangentBinormalGenerator.generate(model);
            Texture normalMap = app.getAssetManager().loadTexture("Textures/concreteNormal.jpg");
            normalMap.setWrap(Texture.WrapMode.Repeat);
            mat.setTexture("NormalMap", normalMap);
        } else if ("forestground".equals(textureName)) {
            Texture texture = app.getAssetManager().loadTexture("Textures/Textures/forestground.jpg");
            texture.setWrap(Texture.WrapMode.Repeat);
            mat.setTexture("DiffuseMap", texture);
            mat.setColor("Specular", ColorRGBA.Brown);
            mat.setColor("Diffuse", ColorRGBA.White);
            mat.setFloat("Shininess", 15f);
            mat.setColor("Ambient", ColorRGBA.White);
            mat.setBoolean("UseMaterialColors", true);
            TangentBinormalGenerator.generate(model);
            Texture normalMap = app.getAssetManager().loadTexture("Textures/forestgroundNormal.jpg");
            normalMap.setWrap(Texture.WrapMode.Repeat);
            mat.setTexture("NormalMap", normalMap);
        } else if ("snow".equals(textureName)) {
            Texture texture = app.getAssetManager().loadTexture("Textures/Textures/snow.jpg");
            texture.setWrap(Texture.WrapMode.Repeat);
            mat.setTexture("DiffuseMap", texture);
            mat.setColor("Specular", ColorRGBA.fromRGBA255(171, 197, 255, 0));
            mat.setColor("Diffuse", ColorRGBA.White);
            mat.setFloat("Shininess", 128f);
            mat.setColor("Ambient", ColorRGBA.White);
            mat.setBoolean("UseMaterialColors", true);
            TangentBinormalGenerator.generate(model);
            Texture normalMap = app.getAssetManager().loadTexture("Textures/snowNormal.jpg");
            normalMap.setWrap(Texture.WrapMode.Repeat);
            mat.setTexture("NormalMap", normalMap);
        } else if ("asphalt".equals(textureName)) {
            Texture texture = app.getAssetManager().loadTexture("Textures/Textures/asphalt.jpg");
            texture.setWrap(Texture.WrapMode.Repeat);
            mat.setTexture("DiffuseMap", texture);
            mat.setColor("Specular", ColorRGBA.fromRGBA255(156, 156, 156, 0));
            mat.setColor("Diffuse", ColorRGBA.White);
            mat.setFloat("Shininess", 15f);
            mat.setColor("Ambient", ColorRGBA.White);
            mat.setBoolean("UseMaterialColors", true);
            TangentBinormalGenerator.generate(model);
            Texture normalMap = app.getAssetManager().loadTexture("Textures/asphaltNormal.jpg");
            normalMap.setWrap(Texture.WrapMode.Repeat);
            mat.setTexture("NormalMap", normalMap);
        } else if ("wood".equals(textureName)) {
            Texture texture = app.getAssetManager().loadTexture("Textures/Textures/wood.jpg");
            texture.setWrap(Texture.WrapMode.Repeat);
            mat.setTexture("DiffuseMap", texture);
            mat.setColor("Specular", ColorRGBA.Brown);
            mat.setColor("Diffuse", ColorRGBA.White);
            mat.setColor("Ambient", ColorRGBA.White);
            mat.setFloat("Shininess", 32f);
            mat.setBoolean("UseMaterialColors", true);
            TangentBinormalGenerator.generate(model);
            Texture normalMap = app.getAssetManager().loadTexture("Textures/woodNormal.jpg");
            normalMap.setWrap(Texture.WrapMode.Repeat);
            mat.setTexture("NormalMap", normalMap);
        }
        model.setMaterial(mat);
    }

}