package core;

import java.util.ArrayList;

public class MapTest extends Map {
    private int mapWidth;
    private int mapHeight;

    private Texture mapContent[][];
    private int preMapContent[][] = {
            { 1, 1, 1, 1, 1, 1, 1 },
            { 1, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 1 },
            { 1, 1, 1, 1, 1, 1, 1 },
    };

    private ArrayList<Point<Integer>> spawnedLetters = new ArrayList<>();
    private ArrayList<Sprite> renderedSprites = new ArrayList<>();

    public MapTest() {
        setSpawnX(2d);
        setSpawnY(2d);

        renderedSprites.add(new SpriteWhite(new Point<Double>(3d, 3d)));
        // renderedSprites.add(new SpriteWhite(new Point<Double>(1d, 1d)));
        // renderedSprites.add(new SpriteWhite(new Point<Double>(5d, 5d)));
        // renderedSprites.add(new SpriteWhite(new Point<Double>(2d, 5d)));

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
        return mapContent[y][x];
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public boolean checkLetter(int x, int y) {
        for (Point<Integer> letter : spawnedLetters) {
            if (letter.x == x && letter.y == y) {
                return true;
            }
        }
        return false;
    }

    public void removeLetter(int x, int y) {
        for (Point<Integer> letter : spawnedLetters) {
            if (letter.x == x && letter.y == y) {
                spawnedLetters.remove(letter);
                mapContent[y][x] = Texture.getNoLetterTexture(mapContent[y][x].getTextureId());
                break;
            }
        }
    }

    public ArrayList<Sprite> getRenderedSprites() {
        return renderedSprites;
    }
}
