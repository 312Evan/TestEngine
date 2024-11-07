package enginetest.EngineFunctions;

import java.io.InputStream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;

public class UiManager {

    private SimpleApplication app;

    public UiManager(SimpleApplication app) {
        this.app = app;
    }

    public void addText(String text, int fontSize, int positionX, int positionY) {
                BitmapFont font = app.getAssetManager().loadFont("Interface/Fonts/Default.fnt");

        BitmapText textLabel = new BitmapText(font);
        textLabel.setSize(font.getCharSet().getRenderedSize() * fontSize);
        textLabel.setText(text);
        textLabel.setColor(ColorRGBA.White);
        textLabel.setLocalTranslation(300, textLabel.getLineHeight() + 300, 0);

        app.getGuiNode().attachChild(textLabel);
    }

    public void loadTextFromJson(AssetManager assetManager) {
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream is = assetManager.locateAsset(new com.jme3.asset.AssetKey<>("Data/game.json")).openStream()) {
            JsonNode root = mapper.readTree(is);
            for (JsonNode textNode : root.get("text")) {
                JsonNode addText = textNode.get("addtext");

                String text = addText.get("text").asText();
                int fontSize = addText.get("fontSize").asInt();
                int positionX = addText.get("positionX").asInt();
                int positionY = addText.get("positionY").asInt();

                addText(text, fontSize, positionX, positionY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
