package enginetest;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;
import enginetest.EngineFunctions.*;


public class EngineTest extends SimpleApplication {
    private BulletAppState bulletAppState;
    LoadingManager loadingManager = new LoadingManager(this);
    ModelManager modelManager = new ModelManager(this);

    public static void main(String[] args) {
        EngineTest app = new EngineTest();
        AppSettings settings = new AppSettings(true);
        settings.setVSync(false);
        settings.setResolution(1200, 700);
        settings.setResizable(true);
        settings.setFullscreen(false);
        settings.setGammaCorrection(true);
        settings.setTitle("Engine Test");
        app.setSettings(settings);
        app.setShowSettings(false);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        cam.setFrustumFar(3000);

        // Initialize physics
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        flyCam.setMoveSpeed(100);

        //Load Game From Json
        loadingManager.loadGameFromJson();

        // modelManager.createThirdPersonController(new Vector3f(0,0,0));
    }

    @Override
    public void simpleUpdate(float tpf) {
        // if (modelManager != null) {
        //     modelManager.update(tpf);
        // }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        // Render additional elements
    }
}   