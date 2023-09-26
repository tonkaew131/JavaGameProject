package core;

public class Map {
    private int mapWidth;
    private int mapHeight;

    private Texture mapContent[][];
    private int preMapContent[][] = {
            { 1, 1, 1, 2, 1, 1, 1 },
            { 1, 0, 0, 2, 0, 0, 1 },
            { 1, 0, 2, 2, 0, 0, 1 },
            { 2, 0, 0, 2, 2, 0, 2 },
            { 1, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 1 },
            { 1, 1, 1, 2, 1, 1, 1 }
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
        return mapContent[x][y];
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }
}
