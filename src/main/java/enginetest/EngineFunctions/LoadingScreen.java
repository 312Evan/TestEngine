package enginetest.EngineFunctions;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import javax.imageio.ImageIO;

public class LoadingScreen extends JFrame {
    private JProgressBar progressBar;
    private BufferedImage backgroundImage;
    Font defaultfont = new Font("Apple Casual", Font.PLAIN, 30);

    // Array of image URLs
    private String[] imageUrls = {
        "https://cdn.discordapp.com/attachments/1291853060620161187/1296871197925183751/Screenshot_2024-10-18_at_12.06.01_PM.png?ex=6713dcec&is=67128b6c&hm=da904d07f5e52ccf4ed3e7c4917a5080d4c93009a1e3932c3a93250cb5f891aa",
        "https://cdn.discordapp.com/attachments/1291853060620161187/1296591163532251208/Screenshot_2024-10-17_172517.png?ex=671380de&is=67122f5e&hm=f5ede2230e269bfea25fe12da6526886034496e95e072b9faaab080b4ec8dc75&"
    };

    public LoadingScreen() {
        try {
            // Randomly select an image URL from the array
            Random random = new Random();
            int randomIndex = random.nextInt(imageUrls.length);
            URL imageUrl = new URL(imageUrls[randomIndex]);
            
            // Load the selected image
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