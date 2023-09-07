package core;

public enum Texture {
    EMPTY(0),
    WHITE_WALL(1);

    public final int textureId;

    private Texture(int textureId) {
        this.textureId = textureId;
    }
}
