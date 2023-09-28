package core;

public class Map {
    private int mapWidth;
    private int mapHeight;

    private Texture mapContent[][];
    private int preMapContent[][] = {
            { 3, 3, 3, 3, 3, 3, 3 },
            { 3, 0, 0, 0, 0, 0, 3 },
            { 3, 0, 0, 0, 0, 0, 3 },
            { 4, 0, 0, 0, 0, 0, 3 },
            { 4, 0, 0, 0, 0, 0, 3 },
            { 3, 0, 0, 0, 0, 0, 3 },
            { 3, 3, 3, 3, 3, 3, 3 }
    };

    public Map() {
        mapHeight = preMapContent.length;
        mapWidth = preMapContent[0].length;

        mapContent = new Texture[mapHeight][mapWidth];
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                mapContent[i][j] = Texture.values()[preMapContent[i][j]];
            }
        }
    }

    public Texture getTexture(int x, int y) {
        if (x < 0 || x >= mapWidth || y < 0 || y >= mapHeight)
            return Texture.BLACK_WALL;
        return mapContent[x][y];
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }
}
