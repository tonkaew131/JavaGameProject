package core;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.Hashtable;

public class Renderer extends JPanel implements ActionListener {
    private BufferedImage bufferedImageA;
    private BufferedImage bufferedImageB;
    private boolean isDisplayingImageA;
    private double[] zBuffer;

    private RayCast rayCaster;
    private Map map;
    private Tick tick;
    private Timer timer = new Timer((int) (1000.0 / Setting.MAX_FPS), this);
    private Player player;

    private static Dictionary<String, Image> assets = new Hashtable<>();

    JLabel titleLabel = new JLabel("", SwingConstants.CENTER);
    JLabel subtitleLabel = new JLabel("Collect 7 Letter to get out...", SwingConstants.CENTER);

    public Renderer() {
        rayCaster = new RayCast();

        super.setSize(Setting.WINDOWS_WIDTH, Setting.WINDOWS_HEIGHT);
        super.setPreferredSize(new Dimension(Setting.WINDOWS_WIDTH, Setting.WINDOWS_HEIGHT));

        this.setLayout(new BorderLayout());

        titleLabel.setFont(FontCustom.PixelifySans.deriveFont(Font.BOLD, 64));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(new EmptyBorder(100, 0, 0, 0)); // top, left, bottom, right
        add(titleLabel, BorderLayout.CENTER);

        subtitleLabel.setFont(FontCustom.PixelifySans.deriveFont(Font.PLAIN, 22));
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setBorder(new EmptyBorder(0, 0, 100, 0)); // top, left, bottom, right
        add(subtitleLabel, BorderLayout.SOUTH);

        zBuffer = new double[Setting.WINDOWS_WIDTH];

        this.setBackground(Color.BLACK);
        timer.start();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Setting.WINDOWS_WIDTH, Setting.WINDOWS_HEIGHT);
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

        // radian per pixel
        double fovRadian = Math.toRadians(Setting.FOV);
        double scanStep = fovRadian / Setting.WINDOWS_WIDTH;
        double scanDirection = player.getDirectionAlpha() + (fovRadian / 2);

        // predefined variable
        int darkness, shadow;
        int lineHeight;
        double distance;
        double wallYPercentage, wallXPercentage;
        int pixelY;
        int clipLineHeight;
        int halfScreenHeight = Setting.WINDOWS_HEIGHT / 2;
        int offsetHeight;

        BufferedImage gameplay = new BufferedImage(Setting.WINDOWS_WIDTH, Setting.WINDOWS_HEIGHT,
                BufferedImage.TYPE_INT_RGB);

        rayCaster.setPlayerPosition(player.getPosition());
        for (int i = 0; i < Setting.WINDOWS_WIDTH; i++) {
            rayCaster.setDirection(scanDirection);
            rayCaster.cast();

            wallXPercentage = rayCaster.getHitSide() == RayCast.HitSide.VERTICAL ? (rayCaster.getHitPoint().x % 1)
                    : (rayCaster.getHitPoint().y % 1);

            // Removed distortion
            distance = rayCaster.getDistance();
            distance *= Math.cos(player.getDirectionAlpha() - scanDirection);

            zBuffer[i] = distance;

            lineHeight = (int) (Setting.WINDOWS_HEIGHT / distance);

            Point<Integer> mapCheck = rayCaster.getMapPoint();
            Color color;

            pixelY = Math.max(halfScreenHeight - lineHeight / 2, 0);
            clipLineHeight = Math.min(lineHeight, Setting.WINDOWS_HEIGHT);
            offsetHeight = clipLineHeight != lineHeight ? (lineHeight - clipLineHeight) / 2 : 0;
            for (int j = 0; j < clipLineHeight; j++) {
                wallYPercentage = ((double) j + offsetHeight) / lineHeight;

                color = map.getTexture(mapCheck.x, mapCheck.y).getColor(wallXPercentage, wallYPercentage);
                darkness = Setting.TOGGLE_LIGHT ? (int) (distance * 50) : 0;
                shadow = rayCaster.getHitSide() == RayCast.HitSide.VERTICAL ? 20 : 0;
                color = new Color(Math.max(0, color.getRed() - darkness - shadow),
                        Math.max(0, color.getGreen() - darkness - shadow),
                        Math.max(0, color.getBlue() - darkness - shadow));

                gameplay.setRGB(i, pixelY + j, color.getRGB());
            }

            scanDirection -= scanStep;
        }

        // view bobbing
        int bobX = (int) 0;
        int bobY = (int) (Math.abs(7.5 * Math.sin(tick.getRunningTick() * 1.5)));
        if (Setting.VIEW_BOBBING)
            g2d.drawImage(gameplay, bobX, bobY, this);
        else
            g2d.drawImage(gameplay, 0, 0, this);

        if (Setting.TOGGLE_MAP)
            drawMap(g2d);

        drawOverlay(g2d);

        drawSprite(g2d);
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

    public void drawMap(Graphics g) {
        int mapScale = 10;
        int width = map.getMapWidth();

        int posX = Setting.WINDOWS_WIDTH - (width * mapScale) - 1;
        int posY = 1;
        Texture texture;
        for (int y = 0; y < map.getMapHeight(); y++) {
            for (int x = 0; x < map.getMapWidth(); x++) {
                texture = map.getTexture(x, y);
                if (texture == Texture.EMPTY)
                    g.setColor(Color.BLACK);
                else if (texture == Texture.WHITE_WALL)
                    g.setColor(Color.WHITE);
                else if (texture == Texture.RED_WALL)
                    g.setColor(Color.RED);
                else if (texture == Texture.DRY_WALL)
                    g.setColor(Color.YELLOW);
                else
                    g.setColor(Color.WHITE);

                g.fillRect(posX + (mapScale * x), posY + (y * mapScale), mapScale, mapScale);
            }
        }

        g.setColor(Color.RED);
        g.fillOval(
                posX + (int) Math.round(player.getPosX() * mapScale) - 2,
                posY + (int) Math.round(player.getPosY() * mapScale) - 2,
                4, 4);

        // draw sprite
        g.setColor(Color.BLUE);
        ArrayList<Sprite> sprites = map.getRenderedSprites();
        for (int i = 0; i < sprites.size(); i++) {
            g.fillOval(
                    posX + (int) Math.round(sprites.get(i).getPos().x * mapScale) - 2,
                    posY + (int) Math.round(sprites.get(i).getPos().y * mapScale) - 2,
                    4, 4);
        }

        rayCaster.setDirection(player.getDirectionAlpha());
        rayCaster.cast();
        g.setColor(Color.GREEN);
        g.drawLine(
                posX + (int) Math.round(player.getPosX() * mapScale),
                posY + (int) Math.round(player.getPosY() * mapScale),
                posX + (int) Math.round(rayCaster.getHitPoint().x * mapScale),
                posY + (int) Math.round(rayCaster.getHitPoint().y * mapScale));
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
        if (Setting.TOGGLE_LIGHT) {
            g.drawImage(assets.get("overlay"), 0, 0, Setting.WINDOWS_WIDTH,
                    Setting.WINDOWS_HEIGHT, this);
        }

        // g.drawString(String.format("X: %.2f, Y: %.2f, d: %.2f",
        // player.getPosX(), player.getPosY(),
        // Math.toDegrees(player.getDirectionAlpha())), 10, 10);

        // Draw Stamina bar
        g.setColor(Color.WHITE);
        int staminaBar = (int) (player.getStamina() * 200);
        g.fillRoundRect(Setting.WINDOWS_WIDTH / 2 - staminaBar / 2, 25, staminaBar, 4, 4, 4);

        if (Setting.SHOW_FPS)
            g.drawString("FPS: " + tick.getFPS(), 10, 25);

        // Draw Letter tooltip
        rayCaster.setDirection(player.getDirectionAlpha());
        rayCaster.cast();
        Point<Integer> mapCheck = rayCaster.getMapPoint();
        if (map.checkLetter(mapCheck.x, mapCheck.y) && rayCaster.getDistance() <= Setting.LETTER_REACH_DISTANCE) {
            g.drawString("Pless E to collect", Setting.WINDOWS_WIDTH / 2 - 50, Setting.WINDOWS_HEIGHT / 2);
        }

        // Draw Letter count
        g.drawString(String.format("Letter: %d/7", player.getLetterCount()), Setting.WINDOWS_WIDTH - 100, 25);
    }

    public void drawSprite(Graphics2D g) {
        ArrayList<Sprite> sprites = map.getRenderedSprites();

        Collections.sort(sprites, new Comparator<Sprite>() {
            @Override
            public int compare(Sprite s1, Sprite s2) {
                return Double.compare(s1.getDistance(player.getPosition()), s2.getDistance(player.getPosition()));
            }
        });

        // Near to far
        for (int i = 0; i < sprites.size(); i++) {
            double distance = sprites.get(i).getDistance(player.getPosition());

            double scale = 2 / distance;
            int width = (int) (sprites.get(i).getImageWidth() * scale);
            int height = (int) (sprites.get(i).getImageHeight() * scale);

            int y = (int) ((Setting.WINDOWS_HEIGHT / 2) - (height / 2));

            double direction = sprites.get(i).getAbsoluteDirection(player.getPosition());
            direction -= player.getDirectionAlpha();
            direction = Math.toRadians(Math.toDegrees(direction));

            double fovRadian = Math.toRadians(Setting.FOV);

            // check if sprite is in fov
            if (Math.abs(direction) > fovRadian / 2)
                continue;

            int x = (int) ((Setting.WINDOWS_WIDTH / 2) - (direction / fovRadian * Setting.WINDOWS_WIDTH) - width / 2);

            g.drawImage(sprites.get(i).getImage(), x, y, width, height, this);
        }
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