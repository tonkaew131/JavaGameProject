package core;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Renderer extends JPanel {
    private BufferedImage bufferedImageA;
    private BufferedImage bufferedImageB;
    private boolean isDisplayingImageA;
    private Tick tick;
    private Player player;
    private Map map;

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
        Graphics2D g2d = (Graphics2D) g;

        // drawRainbow(g);
        if (Setting.SHOW_FPS) drawFPS(g);

        drawMap(g2d);

        // g2d.setColor(Color.WHITE);
        // for (int i = 0; i < Setting.WINDOWS_WIDTH; i++) {
        //    rayCast(player.getPosX(), player.getPosY(), player.getDirectionAlpha());
        //    g2d.drawLine(i, Setting.WINDOWS_HEIGHT / 2, i, Setting.WINDOWS_HEIGHT / 2);
        //}

        this.update(this.getGraphics());
        this.repaint();
    }

    public void rayCast(double posX, double posY, double direction) {
        // DDA Algorithm

        // distance from top-left
        double dx = posX % 1;
        double dy = posY % 1;

        // first horizontal intersect
        double x = posX + (dy / Math.tan(direction));
        double y = posY + dy;

        int targetX = (int) x;
        int targetY = (int) y;

        // horizontal step
        double xStep = 1 / Math.tan(direction);
        double yStep = 1;

        while (true) {
            if (map.getTexture(targetX, targetY) != Texture.EMPTY) {
                break;
            }
            x += xStep;
            y += yStep;
        }

        System.out.println(String.format("Hit! %d", map.getTexture(targetX, targetY)));
        System.out.println(String.format("x:%8f y:%.8f a:%.8f", x, y, direction));
        System.out.println(String.format("x:%d y:%d", targetX, targetY));
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

    public void drawMap(Graphics g) {
        int mapScale = 30;
        int posX = Setting.WINDOWS_WIDTH - (map.getMapWidth() * mapScale), posY = 0;
        for (int i = 0; i < map.getMapHeight(); i++) {
            for (int j = 0; j < map.getMapWidth(); j++) {
                if (map.getTexture(i, j) != Texture.EMPTY) {
                    g.setColor(Color.CYAN);
                } else g.setColor(Color.BLACK);

                g.fillRect(posX + (mapScale * i), posY + (j * mapScale), mapScale, mapScale);
            }
        }

        g.setColor(Color.cyan);
        g.fillOval(posX + (int) (player.getPosX() * mapScale), posY + (int) (player.getPosY() * mapScale), 5, 5);
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

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setMap(Map map) {
        this.map = map;
    }
}
