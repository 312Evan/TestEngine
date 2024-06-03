package enginetest.EngineFunctions;

import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioNode;
import com.jme3.audio.AudioData.DataType;

public class AudioManager {
    private SimpleApplication app;

    public AudioManager(SimpleApplication app) {
        this.app = app;
    }

    public void playsound(String filepath, boolean loop) {
        AudioNode sound = new AudioNode(app.getAssetManager(), filepath, DataType.Buffer);
        sound.setPositional(false);
        sound.setLooping(false);
        sound.setVolume(2);
        if (loop) {
            sound.setLooping(loop);
        }
        // sound.play();
        app.getRootNode().attachChild(sound);
    }
}
