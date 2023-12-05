package core;

import java.util.ArrayList;

import core.events.GhostFollow;
import core.events.GhostJumpScare;

public class Map {
    private int mapWidth;
    private int mapHeight;

    // private double spawnX = 40.5;
    // private double spawnY = 13.5;
    private double spawnX = 2.5;
    private double spawnY = 13.5;

    private Point<Integer> exitPoint = new Point<>(42, 13);

    private Texture mapContent[][];
    private int preMapContent[][] = {
            { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 },
            { 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3 },
            { 3, 0, 0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3 },
            { 3, 0, 0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 0, 3 },
            { 3, 0, 0, 12, 0, 0, 0, 0, 0, 12, 12, 12, 12, 12, 0, 0, 0, 0, 0, 0, 0, 3 },
            { 3, 0, 0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3 },
            { 3, 0, 0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 0, 0, 3 },
            { 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3 },
            { 3, 0, 0, 0, 0, 0, 12, 0, 0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3 },
            { 3, 0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 0, 0, 0, 0, 12, 12, 12, 12, 0, 0, 3 },
            { 3, 0, 0, 0, 12, 0, 0, 0, 0, 12, 0, 0, 0, 0, 0, 12, 12, 12, 12, 0, 0, 3 },
            { 3, 0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 0, 0, 0, 0, 12, 12, 12, 12, 0, 0, 3 },
            { 3, 0, 0, 0, 0, 0, 12, 0, 0, 12, 0, 0, 0, 0, 0, 12, 12, 12, 12, 0, 0, 3 },
            { 3, 0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3 },
            { 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3 },
            { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 }
    };

    private ArrayList<Point<Integer>> spawnedLetters = new ArrayList<>();
    private ArrayList<MapEvent> mapEvents = new ArrayList<>();
    private ArrayList<Sprite> renderedSprites = new ArrayList<>();

    public Sprite josh = new SpriteWhite(new Point<Double>(5.5, 13.5));

    public Map() {
        addSprite(josh);

        parseMap();
    }

    public void parseMap() {
        mapHeight = preMapContent.length;
        mapWidth = preMapContent[0].length;

        mapContent = new Texture[mapHeight][mapWidth];
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                mapContent[i][j] = Texture.values()[preMapContent[i][j]];
            }
        }
    }

    public void addLetter(int x, int y) {
        spawnedLetters.add(new Point<Integer>(x, y));
    }

    public void addSprite(Sprite sprite) {
        renderedSprites.add(sprite);
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

    public boolean checkExit(int x, int y) {
        return exitPoint.x == x && exitPoint.y == y;
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

    public void removeSprite(Sprite sprite) {
        renderedSprites.remove(sprite);
    }

    public ArrayList<MapEvent> getMapEvents() {
        return mapEvents;
    }

    public double getSpawnX() {
        return spawnX;
    }

    public void setSpawnX(double spawnX) {
        this.spawnX = spawnX;
    }

    public double getSpawnY() {
        return spawnY;
    }

    public void setSpawnY(double spawnY) {
        this.spawnY = spawnY;
    }
}
