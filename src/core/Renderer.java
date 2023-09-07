package core;

import javax.swing.*;
import java.awt.*;

public class Renderer extends JPanel {
    public Renderer() {
        this.setBackground(new Color(167, 199, 231));
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Setting.WINDOWS_WIDTH, Setting.WINDOWS_HEIGHT);
    }

    @Override
    public void paintComponent(Graphics g) {
        // super.paintComponent(g);

        // Graphics2D g2d = (Graphics2D) g;

        // g2d.setStroke(new BasicStroke(2));
        // g2d.setColor(Color.CYAN);
        // g2d.drawLine(0, 0, Setting.WINDOWS_WIDTH, Setting.WINDOWS_HEIGHT);

        drawRainbow(g);
    }

    public void drawRainbow(Graphics g) {
        super.paintComponent(g);

        Color[] rainbows = {
                new Color(148, 0, 211),
                new Color(75, 0, 130),
                new Color(0, 0, 255),
                new Color(0, 255, 0),
                new Color(255, 255, 0),
                new Color(255, 127, 0),
                new Color(255, 0, 0),
        };

        Graphics2D g2d = (Graphics2D) g;

        int rainbowSize = 20;
        g2d.setStroke(new BasicStroke(rainbowSize));

        int h = Setting.WINDOWS_HEIGHT;
        int w = Setting.WINDOWS_WIDTH;
        for (int y = 0; y < h; y += rainbowSize) {
            for (int x = 0; x < w; x += rainbowSize) {
                g2d.setColor(rainbows[(x + y) % rainbows.length]);
                g2d.drawLine(x, y, x, y);
            }
        }
    }
}
