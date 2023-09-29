package core;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

public class Renderer extends JPanel implements ActionListener {
    private BufferedImage bufferedImageA;
    private BufferedImage bufferedImageB;
    private boolean isDisplayingImageA;

    private RayCast rayCaster;
    private Map map;
    private Tick tick;
    private Timer timer = new Timer((int) (1000.0 / Setting.MAX_FPS), this);
    private Player player;

    private static Dictionary<String, Image> assets = new Hashtable<>();

    public Renderer() {
        rayCaster = new RayCast();

        super.setSize(Setting.WINDOWS_WIDTH, Setting.WINDOWS_HEIGHT);
        super.setPreferredSize(new Dimension(Setting.WINDOWS_WIDTH, Setting.WINDOWS_HEIGHT));

        this.setBackground(Color.BLACK);
        timer.start();
    }

    public static void loadAssets() {
        try {
            assets.put("overlay", ImageIO.read(new File("src/texture/overlay.png")));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Assets loaded!");
        }
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

    public void render() {
        BufferedImage img = this.getBufferedImage();
        Graphics g = img.getGraphics();
        Graphics2D g2d = (Graphics2D) g;

        // clear image
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, Setting.WINDOWS_WIDTH, Setting.WINDOWS_HEIGHT);

        // drawFloors(g2d);

        double scanStep = (double) Setting.FOV / Setting.WINDOWS_WIDTH * Math.PI / 180;
        double scanDirection = player.getDirectionAlpha() + ((double) Setting.FOV / 2 * Math.PI / 180);

        // predefined variable
        int darkness, shadow;
        int lineHeight;
        double distance;
        double wallYPercentage, wallXPercentage;

        rayCaster.setPlayerPosition(player.getPosition());
        for (int i = 0; i < Setting.WINDOWS_WIDTH; i++) {
            rayCaster.setDirection(scanDirection);
            rayCaster.cast();

            wallXPercentage = Math.max(rayCaster.getHitPoint().x % 1, rayCaster.getHitPoint().y % 1);

            // Removed distortion
            distance = rayCaster.getDistance();
            distance *= Math.cos(player.getDirectionAlpha() - scanDirection);

            lineHeight = (int) (Setting.WINDOWS_HEIGHT / distance);

            Point<Integer> mapCheck = rayCaster.getMapPoint();
            Color color;

            lineHeight = Math.min(lineHeight, Setting.WINDOWS_HEIGHT);
            int pixelY = Setting.WINDOWS_HEIGHT / 2 - lineHeight / 2;
            for (int j = 0; j < lineHeight; j++) {
                wallYPercentage = (double) j / lineHeight;

                color = map.getTexture(mapCheck.x, mapCheck.y).getColor(wallXPercentage, wallYPercentage);
                darkness = Setting.TOGGLE_LIGHT ? (int) (distance * 50) : 0;
                shadow = rayCaster.getHitSide() == RayCast.HitSide.VERTICAL ? 10 : 0;
                color = new Color(Math.max(0, color.getRed() - darkness - shadow),
                        Math.max(0, color.getGreen() - darkness - shadow),
                        Math.max(0, color.getBlue() - darkness - shadow));

                img.setRGB(i, pixelY + j, color.getRGB());
            }

            scanDirection -= scanStep;
        }

        g2d.drawImage(img, 0, 0, this);

        // drawMap(g2d);

        drawOverlay(g2d);

        // if (Setting.SHOW_FPS)
        // drawFPS(g);
    }

    public void drawFloors(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, Setting.WINDOWS_WIDTH, Setting.WINDOWS_HEIGHT);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

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

    public void drawOverlay(Graphics2D g) {
        g.drawImage(assets.get("overlay"), 0, 0, Setting.WINDOWS_WIDTH, Setting.WINDOWS_HEIGHT, this);

        g.setColor(Color.WHITE);
        int staminaBar = (int) (player.getStamina() * 200);
        g.fillRoundRect(Setting.WINDOWS_WIDTH / 2 - staminaBar / 2, 25, staminaBar, 4, 4, 4);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        render();
        repaint();
    }

    public void setTick(Tick tick) {
        this.tick = tick;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setMap(Map map) {
        this.map = map;
        this.rayCaster.setMap(map);
    }
}