package enginetest.EngineFunctions;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;

public class LoadingScreen extends JFrame {
    private JProgressBar progressBar;
    private BufferedImage backgroundImage;
    Font defaultfont = new Font("Apple Casual", Font.PLAIN, 30);

    public LoadingScreen() {
        try {
            URL imageUrl = new URL("https://cdn.discordapp.com/attachments/1291853060620161187/1296591163532251208/Screenshot_2024-10-17_172517.png?ex=6712d81e&is=6711869e&hm=bc206ee10465d34581850eccbbedf6c56d49a341cf3d8544005064f013b28913&"); // Replace with actual URL
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

        JLabel label = new JLabel("Your Game Is Loading", SwingConstants.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
        label.setFont(defaultfont);
        label.setForeground(Color.WHITE);
        foregroundPanel.add(label, BorderLayout.NORTH);
        
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

    public void setProgress(int value) {
        progressBar.setValue(value);
    }
    
    public void close() {
        dispose();
    }
}
