package core;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {
    private Point<Integer> pos;
    private double width;
    private double height;
    private Image image;

    public Sprite(Point<Integer> pos, int width, int height, String imagePath) {
        this.pos = pos;
        this.width = width;
        this.height = height;

        File file = new File(imagePath);
        try {
            this.image = ImageIO.read(file);
        } catch (IOException e) {
            System.out.println(String.format("[Sprite]: Failed to load sprite texture! (%s)", imagePath));
            e.printStackTrace();
            return;
        }
    }
}
