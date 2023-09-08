package core;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Renderer extends JPanel {
    private BufferedImage bufferedImageA;
    private BufferedImage bufferedImageB;
    private boolean isDisplayingImageA;
    private Tick tick;

    public Renderer() {
        this.setBackground(Color.BLACK);
    }

    public BufferedImage getBufferedImage() {
        if (isDisplayingImageA) {
            if (bufferedImageA == null)
                bufferedImageA = new BufferedImage(Setting.WINDOWS_WIDTH, Setting.WINDOWS_HEIGHT, BufferedImage.TYPE_INT_RGB);
            return bufferedImageA;
        }

        if (bufferedImageB == null)
            bufferedImageB = new BufferedImage(Setting.WINDOWS_WIDTH, Setting.WINDOWS_HEIGHT, BufferedImage.TYPE_INT_RGB);
        return bufferedImageB;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Setting.WINDOWS_WIDTH, Setting.WINDOWS_HEIGHT);
    }

    public void render() {
        Graphics g = this.getBufferedImage().getGraphics();

        // drawRainbow(g);
        if (Setting.SHOW_FPS) drawFPS(g);

        this.update(this.getGraphics());
        this.repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (isDisplayingImageA) {
            g.drawImage(bufferedImageA, 0, 0, this);
        } else {
            g.drawImage(bufferedImageB, 0, 0, this);
        }
        isDisplayingImageA = !isDisplayingImageA;
    }

    public void drawFPS(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Font font = new Font("Serif", Font.BOLD, 16);

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, 30, 16);

        g2d.setFont(font);
        g2d.setColor(Color.BLACK);
        g2d.drawString(String.format("%d", (tick.getDeltaTime() != 0 ? (1000 / tick.getDeltaTime()) : 9999)), 3, 13);
    }

    public void drawRainbow(Graphics g) {
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

        int rainbowSize = 5;
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

    public void setTick(Tick tick) {
        this.tick = tick;
    }
}
