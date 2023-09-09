package core;

public class Map {
    private int mapWidth;
    private int mapHeight;

    private Texture mapContent[][] = {
            {Texture.WHITE_WALL, Texture.WHITE_WALL, Texture.WHITE_WALL, Texture.WHITE_WALL, Texture.WHITE_WALL},
            {Texture.WHITE_WALL, Texture.EMPTY, Texture.EMPTY, Texture.EMPTY, Texture.WHITE_WALL},
            {Texture.WHITE_WALL, Texture.EMPTY, Texture.RED_WALL, Texture.EMPTY, Texture.WHITE_WALL},
            {Texture.WHITE_WALL, Texture.EMPTY, Texture.EMPTY, Texture.EMPTY, Texture.WHITE_WALL},
            {Texture.WHITE_WALL, Texture.WHITE_WALL, Texture.WHITE_WALL, Texture.WHITE_WALL, Texture.WHITE_WALL},
    };

    public Map() {
        mapHeight = mapContent.length;
        mapWidth = mapContent[0].length;
    }

    public Texture getTexture(int x, int y) {
        return mapContent[x][y];
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }
}
