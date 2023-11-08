package core;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {
    private Point<Double> pos;
    // Width & Height of rendered sprite (in block size)
    private double width;
    private double height;
    private Image image;
    private int imageHeight;
    private int imageWidth;

    public Sprite(Point<Double> pos, double width, double height, String imagePath) {
        this.pos = pos;
        this.width = width;
        this.height = height;

        File file = new File(imagePath);
        try {
            this.image = ImageIO.read(file);
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
        double alpha = Math.atan2(pos.y - point.x, pos.x - point.x);
        if (alpha < 0)
            alpha += Math.PI * 2;
        return alpha;
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
}
