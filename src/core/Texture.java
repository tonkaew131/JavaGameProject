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
    WINDOW(9),
    METAL_DOOR(10),
    WOOD_DOOR(11),
    METAL_WALL(12),
    WOOD_DOOR_BLOOD(13),
    LETTER(14),
    LETTER_METAL_WALL(15),
    GET_OUT(16),
    DRY_WALL_BLOOD(17);

    public final int textureId;
    private static Dictionary<Integer, TextureLoader> dict = new Hashtable<>();

    private Texture(int textureId) {
        this.textureId = textureId;
    }

    public static void loadTexture() {
        long startTime = System.currentTimeMillis();
        System.out.println("[Texture]: Loading textures...");
        try {
            dict.put(3, new TextureLoader("resources/texture/dry_wall.png"));
            dict.put(4, new TextureLoader("resources/texture/wood.png"));
            dict.put(6, new TextureLoader("resources/texture/dry_wall_glass_door.png"));
            dict.put(7, new TextureLoader("resources/texture/dry_wall_board.png"));
            dict.put(8, new TextureLoader("resources/texture/dry_wall_help.png"));
            dict.put(9, new TextureLoader("resources/texture/dry_wall_window.png"));
            dict.put(10, new TextureLoader("resources/texture/dry_wall_metal_door.png"));
            dict.put(11, new TextureLoader("resources/texture/dry_wall_wood_door.png"));
            dict.put(12, new TextureLoader("resources/texture/metal_wall.png"));
            dict.put(13, new TextureLoader("resources/texture/dry_wall_wood_door_blood.png"));
            dict.put(14, new TextureLoader("resources/texture/letter_dry_wall.png"));
            dict.put(14, new TextureLoader("resources/texture/letter_dry_wall.png"));
            dict.put(14, new TextureLoader("resources/texture/letter_dry_wall.png"));
            dict.put(15, new TextureLoader("resources/texture/letter_metal_wall.png"));
            dict.put(16, new TextureLoader("resources/texture/dry_wall_get.png"));
            dict.put(17, new TextureLoader("resources/texture/dry_wall_blood.png"));
        } catch (IOException e) {
            System.out.println("[Texture]: Failed to load textures!");
            e.printStackTrace();
            return;
        }
        System.out.println("[Texture]: Textures loaded! (" + (System.currentTimeMillis() - startTime) + "ms)");
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

        if (dict.get(textureId) != null) {
            return dict.get(textureId).getColor(x, y);
        }

        return Color.BLACK;
    }

    public static Texture getNoLetterTexture(int textureId) {
        if (textureId == 14)
            return DRY_WALL;
        if (textureId == 15)
            return METAL_WALL;

        return DRY_WALL;
    }

    public int getTextureId() {
        return textureId;
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