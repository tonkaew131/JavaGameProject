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
                bufferedImageA = new BufferedImage(Setting.WINDOWS_WIDTH, Setting.WINDOWS_HEIGHT,
                        BufferedImage.TYPE_INT_RGB);
            return bufferedImageA;
        }

        if (bufferedImageB == null)
            bufferedImageB = new BufferedImage(Setting.WINDOWS_WIDTH, Setting.WINDOWS_HEIGHT,
                    BufferedImage.TYPE_INT_RGB);
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

        if (Setting.SHOW_FPS)
            drawFPS(g);

        // drawMap(g2d);

        drawFloors(g2d);
        double scanStep = (double) Setting.FOV / Setting.WINDOWS_WIDTH * Math.PI / 180;
        double scanStart = player.getDirectionAlpha() + ((double) Setting.FOV / 2 * Math.PI / 180);
        for (int i = 0; i < Setting.WINDOWS_WIDTH; i++) {
            rayCast(player.getPosX(), player.getPosY(), scanStart, i, Setting.WINDOWS_HEIGHT / 2, g2d);
            scanStart -= scanStep;
        }

        this.update(this.getGraphics());
        this.repaint();
    }

    public void drawFloors(Graphics2D g2d) {
        // Floor
        g2d.setColor(Color.GRAY);
        g2d.fillRect(0, Setting.WINDOWS_HEIGHT / 2, Setting.WINDOWS_WIDTH, Setting.WINDOWS_HEIGHT / 2);

        // Ceiling
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(0, 0, Setting.WINDOWS_WIDTH, Setting.WINDOWS_HEIGHT / 2);
    }

    public void rayCast(double posX, double posY, double direction, int pixelX, int pixelY, Graphics2D g2d) {
        // DDA Algorithm

        Point endPoint = new Point(Math.cos(direction) * 100, Math.sin(direction) * 100);
        Point startPoint = new Point(posX, posY);

        Point rayDirection = new Point();
        rayDirection.x = endPoint.x - startPoint.x;
        rayDirection.y = endPoint.y - startPoint.y;

        // Normalize
        double rayLength = Math.sqrt(rayDirection.x * rayDirection.x + rayDirection.y * rayDirection.y);
        rayDirection.x /= rayLength;
        rayDirection.y /= rayLength;

        Point rayUnitStepSize = new Point(
                Math.sqrt(1 + ((rayDirection.y / rayDirection.x) * (rayDirection.y / rayDirection.x))),
                Math.sqrt(1 + ((rayDirection.x / rayDirection.y) * (rayDirection.x / rayDirection.y))));
        PointInt mapCheck = new PointInt(posX, posY);
        // store length in x, y
        Point rayLengthCumu = new Point();
        // store step in x, y
        Point rayStep = new Point();

        if (rayDirection.x < 0) {
            rayStep.x = -1;
            rayLengthCumu.x = (startPoint.x - (float) mapCheck.x) * rayUnitStepSize.x;
        } else {
            rayStep.x = 1;
            rayLengthCumu.x = ((float) mapCheck.x + 1 - startPoint.x) * rayUnitStepSize.x;
        }

        if (rayDirection.y < 0) {
            rayStep.y = -1;
            rayLengthCumu.y = (startPoint.y - (float) mapCheck.y) * rayUnitStepSize.y;
        } else {
            rayStep.y = 1;
            rayLengthCumu.y = ((float) mapCheck.y + 1 - startPoint.y) * rayUnitStepSize.y;
        }

        Point beforeHit = new Point();

        boolean hit = false;
        double distance = 0;
        double MAX_DISTANCE = 100;
        while (!hit && distance < MAX_DISTANCE) {
            beforeHit.x = rayLengthCumu.x;
            beforeHit.y = rayLengthCumu.y;

            if (rayLengthCumu.x < rayLengthCumu.y) {
                mapCheck.x += rayStep.x;
                distance = rayLengthCumu.x;
                rayLengthCumu.x += rayUnitStepSize.x;
            } else {
                mapCheck.y += rayStep.y;
                distance = rayLengthCumu.y;
                rayLengthCumu.y += rayUnitStepSize.y;
            }

            if (mapCheck.x >= 0 && mapCheck.y >= 0 && mapCheck.x < map.getMapWidth()
                    && mapCheck.y < map.getMapHeight()) {
                if (map.getTexture(mapCheck.x, mapCheck.y) != Texture.EMPTY) {
                    hit = true;
                }
            }
        }

        Point intersecPoint = new Point();
        intersecPoint.x = startPoint.x + rayDirection.x * distance;
        intersecPoint.y = startPoint.y + rayDirection.y * distance;

        Color color = map.getTexture(mapCheck.x, mapCheck.y).getColor();
        // if hit from side
        if (beforeHit.x < beforeHit.y) {
            color = color.darker();
        }

        // Removed distortion
        distance *= Math.cos(player.getDirectionAlpha() - direction);
        int lineHeight = (int) ((Setting.WINDOWS_HEIGHT / 2) / distance * 1);

        g2d.setColor(color);
        g2d.drawLine(pixelX, pixelY - (lineHeight / 2), pixelX, pixelY + (lineHeight / 2));
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
                if (map.getTexture(i, j) == Texture.EMPTY)
                    g.setColor(Color.BLACK);
                else if (map.getTexture(i, j) == Texture.WHITE_WALL)
                    g.setColor(Color.WHITE);
                else if (map.getTexture(i, j) == Texture.RED_WALL)
                    g.setColor(Color.RED);
                else
                    g.setColor(Color.CYAN);

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

class PointInt {
    public int x;
    public int y;

    public PointInt(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public PointInt(double x, double y) {
        this.x = (int) Math.floor(x);
        this.y = (int) Math.floor(y);
    }

    public PointInt() {
        this.x = 0;
        this.y = 0;
    }

    public String toString() {
        return String.format("(%d, %d)", this.x, this.y);
    }
}

class Point {
    public double x;
    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point() {
        this.x = 0;
        this.y = 0;
    }

    public double distance(Point point) {
        return Math.sqrt(Math.pow(this.x - point.x, 2) + Math.pow(this.y - point.y, 2));
    }

    public String toString() {
        return String.format("(%f, %f)", this.x, this.y);
    }
}