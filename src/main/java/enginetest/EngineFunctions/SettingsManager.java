//THIS IS NOT USABLE YET

package enginetest.EngineFunctions;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;

public class SettingsManager {
    
    private SimpleApplication app;
    public AppSettings settings = new AppSettings(true);
    

    public SettingsManager(SimpleApplication app) {
        this.app = app;
    }

    public final void setResolution(int width, int height) {
        settings.setResolution(width, height);
        app.setSettings(settings);
    }
}


        // AppSettings settings = new AppSettings(true);
        // // settings.setFrameRate(100);
        // settings.setVSync(false);
        // settings.setResolution(1600, 900);
        // settings.setResizable(true);
        // settings.setFullscreen(true);
        // app.setSettings(settings);