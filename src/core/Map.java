package core;

public class Map {
    private int mapWidth;
    private int mapHeight;

    private Texture mapContent[][] = {
            {Texture.WHITE_WALL, Texture.WHITE_WALL, Texture.WHITE_WALL, Texture.WHITE_WALL, Texture.WHITE_WALL},
            {Texture.WHITE_WALL, Texture.EMPTY, Texture.EMPTY, Texture.EMPTY, Texture.WHITE_WALL},
            {Texture.WHITE_WALL, Texture.EMPTY, Texture.WHITE_WALL, Texture.EMPTY, Texture.WHITE_WALL},
            {Texture.WHITE_WALL, Texture.EMPTY, Texture.EMPTY, Texture.EMPTY, Texture.WHITE_WALL},
            {Texture.WHITE_WALL, Texture.WHITE_WALL, Texture.WHITE_WALL, Texture.WHITE_WALL, Texture.WHITE_WALL},
    };

    Map() {
        mapHeight = mapContent.length;
        mapWidth = mapContent[0].length;
    }
}
