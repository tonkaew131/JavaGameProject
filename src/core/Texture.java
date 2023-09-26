package core;

import java.awt.*;

public enum Texture {
    EMPTY(0),
    WHITE_WALL(1),
    RED_WALL(2);

    public final int textureId;

    private Texture(int textureId) {
        this.textureId = textureId;
    }

    public Color getColor(double x, double y) {
        if (textureId == 0)
            return Color.BLACK;
        if (textureId == 1)
            return Color.WHITE;
        if (textureId == 2)
            return Color.RED;

        return Color.CYAN;
    }
}
