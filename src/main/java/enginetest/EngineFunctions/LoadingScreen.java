package enginetest.EngineFunctions;

import javax.swing.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import javax.imageio.ImageIO;

public class LoadingScreen extends JFrame {
    SimpleApplication app;
    private JProgressBar progressBar;
    private BufferedImage backgroundImage;
    String title;
    String creator;

    private String[] imageUrls = { "https://cdn.pixabay.com/photo/2022/09/21/17/02/blue-background-7470781_640.jpg" };

    public LoadingScreen(SimpleApplication app) {
        try {
            Random random = new Random();
            int randomIndex = random.nextInt(imageUrls.length);
            URL imageUrl = new URL(imageUrls[randomIndex]);

            backgroundImage = ImageIO.read(imageUrl);

            if (backgroundImage != null) {
                System.out.println("Image loaded successfully: " + backgroundImage.getWidth() + "x" + backgroundImage.getHeight());
            } else {
                System.err.println("Error: Image could not be loaded (null).");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.err.println("Error: Invalid URL.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error: Could not load image.");
        }

        setTitle("Loading...");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(800, 600));

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        backgroundPanel.setBounds(0, 0, 800, 600);

        JPanel foregroundPanel = new JPanel();
        foregroundPanel.setOpaque(false);
        foregroundPanel.setLayout(new BorderLayout());
        foregroundPanel.setBounds(0, 0, 800, 600);

        loadGameInfoFromJson(app.getAssetManager());

        JLabel titleLabel = new JLabel("Loading " + title, SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
        titleLabel.setFont(loadTrueTypeFont(48));
        titleLabel.setForeground(Color.WHITE);
        foregroundPanel.add(titleLabel, BorderLayout.NORTH);

        JLabel creatorLabel = new JLabel("Made by: " + creator, SwingConstants.CENTER);
        creatorLabel.setFont(loadTrueTypeFont(30));
        creatorLabel.setForeground(Color.WHITE);
        creatorLabel.setBorder(BorderFactory.createEmptyBorder(80, 0, 0, 0));
        foregroundPanel.add(creatorLabel, BorderLayout.CENTER);

        progressBar = new JProgressBar(0, 100);
        progressBar.setPreferredSize(new Dimension(600, 50));
        progressBar.setForeground(Color.green);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        foregroundPanel.add(progressBar, BorderLayout.SOUTH);

        layeredPane.add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(foregroundPanel, JLayeredPane.PALETTE_LAYER);

        add(layeredPane);

        pack();
        setVisible(true);
    }

    public static Font loadTrueTypeFont(float fontSize) {
        try {
            InputStream fontStream = LoadingScreen.class.getResourceAsStream("/assets/Fonts/default.ttf");
            if (fontStream == null) {
                throw new IOException("Font file not found at the specified path.");
            }

            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            return customFont.deriveFont(fontSize);

        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load the font.");
        }
        return new Font("SansSerif", Font.PLAIN, (int) fontSize);
    }

    public void setProgress(int value) {
        progressBar.setValue(value);
    }

    public void close() {
        dispose();
    }

    public void loadGameInfoFromJson(AssetManager assetManager) {
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream is = assetManager.locateAsset(new com.jme3.asset.AssetKey<>("Data/game.json")).openStream()) {
            JsonNode root = mapper.readTree(is);
            for (JsonNode gameData : root.get("gamedata")) {
                JsonNode gameInfo = gameData.get("gameinfo");

                title = gameInfo.get("title").asText();
                creator = gameInfo.get("creator").asText();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}