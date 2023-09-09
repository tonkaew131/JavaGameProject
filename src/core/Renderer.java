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

        // clear image
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, Setting.WINDOWS_WIDTH, Setting.WINDOWS_HEIGHT);

        if (Setting.SHOW_FPS) drawFPS(g);

        drawMap(g2d);

        double scanStep = (double) Setting.FOV / Setting.WINDOWS_WIDTH * Math.PI / 180;
        double scanStart = player.getDirectionAlpha() + ((double) Setting.FOV / 2 * Math.PI / 180);
        for (int i = 0; i < Setting.WINDOWS_WIDTH; i++) {
            rayCast(player.getPosX(), player.getPosY(), scanStart, i, Setting.WINDOWS_HEIGHT / 2, g2d);
            scanStart -= scanStep;
        }

        this.update(this.getGraphics());
        this.repaint();
    }

    public void rayCast(double posX, double posY, double direction, int pixelX, int pixelY, Graphics2D g2d) {
        // DDA Algorithm

        // distance from top-left
        double dx = posX % 1;
        double dy = posY % 1;

        // first horizontal intersect
        double hx = posX + (dy / Math.tan(direction));
        double hy = posY + dy;

        int targetX = (int) hx;
        int targetY = (int) hy;

        // horizontal step
        double hxStep = 1 / Math.tan(direction);
        double hyStep = direction < Math.PI ? 1 : -1;

        while (true) {
            // Point out of map
            if (targetX >= map.getMapWidth() || targetY >= map.getMapHeight() || targetX < 0 || targetY < 0) {
                break;
            }

            // Horizontal
            if (map.getTexture(targetX, targetY) != Texture.EMPTY) {
                break;
            }
            hx += hxStep;
            hy += hyStep;
            targetX = (int) hx;
            targetY = (int) hy;
        }

        // first vertical hit
        double vx = 1 - dx + posX;
        double vy = (Math.tan(direction) * (1 - dx)) + posY;
        double vxStep = (direction < Math.PI / 2 || direction > 3 * Math.PI / 2) ? 1 : -1;
        double vyStep = direction < Math.PI ? -1 : 1;
        targetX = (int) vx;
        targetY = (int) vy;

        while (true) {
            // Point out of map
            if (targetX >= map.getMapWidth() || targetY >= map.getMapHeight() || targetX < 0 || targetY < 0) {
                break;
            }

            // Horizontal
            if (map.getTexture(targetX, targetY) != Texture.EMPTY) {
                break;
            }
            vx += vxStep;
            vy += vyStep;
            targetX = (int) vx;
            targetY = (int) vy;
        }

        double distanceH = Math.sqrt(Math.pow(posX - hx, 2) + Math.pow(posY - hy, 2));
        double distanceV = Math.sqrt(Math.pow(posX - vx, 2) + Math.pow(posY - vy, 2));

        double distance = distanceV;
        if (distanceH < distanceV) {
            distance = distanceH;
            targetX = (int) hx;
            targetY = (int) hy;
        }

        distance *= Math.cos(Player.radian(direction - this.player.getDirectionAlpha()));
        // double distance = Math.sqrt(Math.pow(posX - hx, 2) + Math.pow(posY - hy, 2)) * Math.cos(direction - this.player.getDirectionAlpha());
        int lineHeight = (int) ((Setting.WINDOWS_HEIGHT / 2) / distance * 0.75);

        if (targetX >= map.getMapWidth() || targetY >= map.getMapHeight() || targetX < 0 || targetY < 0) {
            return;
        }

        g2d.setColor(map.getTexture(targetX, targetY).getColor());
        g2d.drawLine(pixelX, pixelY - (lineHeight / 2), pixelX, pixelY + (lineHeight / 2));
        // System.out.println("Hit! " + map.getTexture(targetX, targetY));
        // System.out.println(String.format("x:%8f y:%.8f a:%.8f", x, y, direction));
        // System.out.println(String.format("x:%d y:%d", targetX, targetY));
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
        int mapScale = 15;
        int posX = Setting.WINDOWS_WIDTH - (map.getMapWidth() * mapScale) - 1, posY = 1;
        for (int i = 0; i < map.getMapHeight(); i++) {
            for (int j = 0; j < map.getMapWidth(); j++) {
                if (map.getTexture(i, j) == Texture.EMPTY) g.setColor(Color.BLACK);
                else if (map.getTexture(i, j) == Texture.WHITE_WALL) g.setColor(Color.WHITE);
                else if (map.getTexture(i, j) == Texture.RED_WALL) g.setColor(Color.RED);
                else g.setColor(Color.CYAN);

                g.fillRect(posX + (mapScale * i), posY + (j * mapScale), mapScale, mapScale);
            }
        }

        g.setColor(Color.cyan);
        g.fillOval(posX + (int) (player.getPosX() * mapScale), posY + (int) (player.getPosY() * mapScale), 3, 3);
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
