package core;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.imageio.ImageIO;

public enum Texture {
    EMPTY(0),
    WHITE_WALL(1),
    RED_WALL(2),
    DRY_WALL(3),
    WOOD(4),
    BLACK_WALL(5),
    GLASS_DOOR(6),
    BOARD(7),
    HELP_ME(8),
    WINDOW(9);

    public final int textureId;
    private static Dictionary<Integer, TextureLoader> dict = new Hashtable<>();

    private Texture(int textureId) {
        this.textureId = textureId;
    }

    public static void loadTexture() {
        try {
            dict.put(3, new TextureLoader("src/texture/dry_wall.png"));
            dict.put(4, new TextureLoader("src/texture/wood.png"));
            dict.put(6, new TextureLoader("src/texture/dry_wall_glass_door.png"));
            dict.put(7, new TextureLoader("src/texture/dry_wall_board.png"));
            dict.put(8, new TextureLoader("src/texture/dry_wall_help.png"));
            dict.put(9, new TextureLoader("src/texture/dry_wall_window.png"));
        } catch (IOException e) {
            System.out.println("Failed to load texture!");
            e.printStackTrace();
            return;
        }
        System.out.println("Texture loaded!");
    }

    public Color getColor(double x, double y) {
        if (textureId == 0)
            return Color.BLACK;
        if (textureId == 1)
            return Color.WHITE;
        if (textureId == 2)
            return Color.RED;
        if (textureId == 5)
            return Color.BLACK;

        if (textureId == 3)
            return dict.get(3).getColor(x, y);
        if (textureId == 4)
            return dict.get(4).getColor(x, y);
        if (textureId == 6)
            return dict.get(6).getColor(x, y);
        if (textureId == 7)
            return dict.get(7).getColor(x, y);
        if (textureId == 8)
            return dict.get(8).getColor(x, y);
        if (textureId == 9)
            return dict.get(9).getColor(x, y);

        return Color.BLACK;
    }
}

class TextureLoader {
    private BufferedImage image;
    private int imageWidth;
    private int imageHeight;

    private Color color[][];

    public TextureLoader(String path) throws IOException {
        File file = new File(path);
        image = ImageIO.read(file);

        imageWidth = image.getWidth();
        imageHeight = image.getHeight();

        color = new Color[imageWidth][imageHeight];
        for (int i = 0; i < imageWidth; i++) {
            for (int j = 0; j < imageHeight; j++) {
                color[i][j] = new Color(image.getRGB(i, j));
            }
        }
    }

    // as a percentage
    public Color getColor(double x, double y) {
        if (x < 0 || x >= 1 || y < 0 || y >= 1)
            return Color.BLACK;

        int i = (int) Math.min(Math.round(x * imageWidth), imageWidth - 1);
        int j = (int) Math.min(Math.round(y * imageHeight), imageHeight - 1);
        return color[i][j];
    }
}