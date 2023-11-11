package core;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {
    private Point<Double> pos;
    // Width & Height of rendered sprite (in block size)
    private double width;
    private double height;

    private Image image;
    private Color color[][];
    private int imageHeight;
    private int imageWidth;

    public Sprite(Point<Double> pos, double width, double height, String imagePath) {
        this.pos = pos;
        this.width = width;
        this.height = height;

        try {
            this.image = ImageIO.read(getClass().getResourceAsStream(imagePath));
            this.imageHeight = image.getHeight(null);
            this.imageWidth = image.getWidth(null);
        } catch (IOException e) {
            System.out.println(String.format("[Sprite]: Failed to load sprite texture! (%s)", imagePath));
            e.printStackTrace();
            return;
        }
    }

    public Image getImage() {
        return image;
    }

    public Point<Double> getPos() {
        return pos;
    }

    public double getDistance(Point<Double> point) {
        return Math.sqrt(Math.pow(pos.x - point.x, 2) + Math.pow(pos.y - point.y, 2));
    }

    public double getAbsoluteDirection(Point<Double> point) {
        double alpha = Math.atan2(point.y - pos.y, point.x - pos.x);
        return alpha;
    }

    public void setImage(String path) {
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream(path));
            this.imageHeight = image.getHeight(null);
            this.imageWidth = image.getWidth(null);
        } catch (IOException e) {
            System.out.println(String.format("[Sprite]: Failed to load sprite texture! (%s)", path));
            e.printStackTrace();
            return;
        }
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setPos(Point<Double> pos) {
        this.pos = pos;
    }

    public Color getColor(double x, double y) {
        if (color == null) {
            BufferedImage image = (BufferedImage) this.image;
            color = new Color[imageWidth][imageHeight];
            for (int i = 0; i < imageWidth; i++) {
                for (int j = 0; j < imageHeight; j++) {
                    color[i][j] = new Color(image.getRGB(i, j));
                }
            }
        }

        int posX = (int) (x * imageWidth);
        int posY = (int) (y * imageHeight);

        if (posX < 0 || posX >= imageWidth || posY < 0 || posY >= imageHeight)
            return Color.BLACK;

        return color[posX][posY];
    }
}
