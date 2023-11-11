package core;

import java.util.ArrayList;

import core.events.GhostFollow;
import core.events.GhostJumpScare;

public class Map {
    private int mapWidth;
    private int mapHeight;

    // private double spawnX = 40.5;
    // private double spawnY = 13.5;
    private double spawnX = 1.5;
    private double spawnY = 26.5;

    private Point<Integer> exitPoint = new Point<>(42, 13);

    private Texture mapContent[][];
    private int preMapContent[][] = {
        { 3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,12,15,3,8,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3 },
        { 3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,0,3,0,0,0,12,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3 },
        { 3,3,3,3,3,3,3,3,3,3,3,3,3,13,3,11,3,9,0,0,0,0,0,0,12,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3 },
        { 3,3,3,3,3,3,3,3,3,3,3,3,0,0,0,0,0,3,0,0,0,0,0,0,12,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3 },
        { 3,3,3,3,3,3,3,3,3,3,3,11,0,0,0,0,0,9,0,0,3,3,0,3,3,3,16,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3 },
        { 3,3,3,3,3,3,3,3,3,3,3,3,0,0,12,0,0,3,0,0,3,0,0,0,0,0,0,0,7,3,3,3,3,3,3,3,3,3,3,3,3,3,3 },
        { 3,3,3,3,3,3,3,3,3,3,3,7,0,0,0,0,0,9,0,0,3,3,3,8,3,3,3,0,3,3,8,3,3,3,3,3,3,3,3,3,3,3,3 },
        { 3,3,3,3,3,3,3,3,3,3,3,3,0,0,12,0,0,3,0,0,3,3,3,3,3,15,0,0,0,0,0,0,3,0,3,3,3,3,3,3,3,3,3 },
        { 3,3,3,3,3,3,3,3,3,3,3,11,0,0,0,0,0,9,0,0,8,3,3,3,3,12,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,3,3 },
        { 3,3,3,3,3,3,3,3,3,3,3,3,0,0,0,0,0,3,0,0,3,3,3,3,3,3,3,3,3,3,3,3,0,0,3,3,3,3,3,3,3,3,3 },
        { 3,3,3,3,3,3,3,3,3,3,3,3,3,8,3,0,3,9,0,0,3,3,3,3,3,3,0,0,0,0,0,0,0,0,16,3,3,3,3,3,3,3,3 },
        { 3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,0,0,3,0,0,3,3,3,3,3,8,0,0,0,0,0,0,3,0,3,3,3,3,3,3,3,3,3 },
        { 3,3,3,3,6,3,3,3,3,3,3,3,3,11,0,0,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,3,0,3,3,3,12,16,12,12,12,3 },
        { 3,3,3,0,0,0,3,3,3,3,3,3,3,11,0,0,0,0,0,0,3,3,3,3,3,17,0,0,0,0,0,0,0,0,3,3,12,0,0,0,0,0,10 },
        { 3,3,11,0,0,0,13,3,3,3,3,3,3,3,0,0,0,3,0,0,16,3,3,3,3,3,0,3,3,3,3,3,3,8,3,3,13,0,0,0,12,12,3 },
        { 3,3,3,0,0,0,3,3,3,3,3,3,3,3,3,0,3,3,0,0,3,3,3,3,3,3,0,0,0,0,0,0,3,3,3,3,16,0,0,0,8,3,3 },
        { 3,3,11,0,0,0,11,3,3,3,3,3,3,3,0,0,0,3,0,0,0,0,3,3,3,3,0,12,0,0,12,0,14,3,3,3,12,0,0,0,12,3,3 },
        { 3,3,3,0,0,0,3,7,3,11,3,11,3,13,0,0,0,0,0,0,0,0,13,3,3,16,0,12,0,0,12,0,3,3,3,3,3,3,0,3,3,3,3 },
        { 3,3,11,0,0,0,0,0,0,0,0,0,0,11,0,0,0,0,0,0,0,0,3,3,3,3,0,0,0,0,0,0,3,17,17,16,3,0,0,0,3,3,3 },
        { 3,3,3,0,0,0,3,0,0,0,0,0,0,3,0,0,0,3,0,0,0,0,11,3,8,3,3,3,3,0,0,3,17,0,0,0,0,0,0,0,8,3,3 },
        { 3,3,13,0,0,0,3,3,3,16,3,0,0,3,3,0,3,3,0,0,0,0,3,0,0,0,0,0,0,0,0,0,17,0,0,0,3,0,0,0,3,3,3 },
        { 3,3,3,0,0,0,3,12,12,12,3,0,0,3,0,0,0,3,0,0,0,0,11,3,0,3,3,3,3,3,3,3,3,17,17,17,3,0,3,3,3,3,3 },
        { 3,3,11,0,0,0,0,0,0,15,6,0,0,0,0,0,0,3,0,0,0,0,3,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,3,3,3,3 },
        { 3,3,3,0,0,0,3,12,12,12,3,0,0,3,0,0,0,3,0,0,3,11,3,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,11,3,3,3 },
        { 3,3,3,3,0,3,3,11,3,13,3,12,12,3,3,7,16,3,14,13,3,3,3,3,0,3,3,0,3,3,3,3,3,0,12,12,0,14,3,3,3,3,3 },
        { 3,3,7,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,8,0,3,0,0,0,0,0,0,0,0,0,0,0,3,3,3,3,3,3 },
        { 6,0,0,0,0,14,0,0,3,0,0,10,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,11,3,13,3,11,16,11,3,11,3,3,3,3,3,3,3 },
        { 3,3,3,0,0,0,0,0,0,0,0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3 },
        { 3,3,3,3,8,3,9,9,9,9,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3 },
    };

    private ArrayList<Point<Integer>> spawnedLetters = new ArrayList<>();
    private ArrayList<MapEvent> mapEvents = new ArrayList<>();
    private ArrayList<Sprite> renderedSprites = new ArrayList<>();

    public Map() {
        addLetter(5, 26);
        addLetter(9, 22);
        addLetter(19, 0);
        addLetter(25, 7);
        addLetter(37, 24);
        addLetter(32, 16);
        addLetter(18, 24);

        MapEvent e1 = new GhostJumpScare(4, 23);
        ((GhostJumpScare) e1).setGhostX(6.5);
        ((GhostJumpScare) e1).setGhostY(22.5);
        mapEvents.add(e1);
        MapEvent e2 = new GhostJumpScare(7, 18);
        ((GhostJumpScare) e2).setGhostX(7.5);
        ((GhostJumpScare) e2).setGhostY(19.5);
        mapEvents.add(e2);
        MapEvent e3 = new GhostJumpScare(15, 9);
        ((GhostJumpScare) e3).setGhostX(12.5);
        ((GhostJumpScare) e3).setGhostY(7.5);
        mapEvents.add(e3);
        MapEvent e4 = new GhostJumpScare(22, 5);
        ((GhostJumpScare) e4).setGhostX(24.5);
        ((GhostJumpScare) e4).setGhostY(5.5);
        mapEvents.add(e4);
        MapEvent e5 = new GhostJumpScare(35, 19);
        ((GhostJumpScare) e5).setTurning(false);
        ((GhostJumpScare) e5).setGhostX(34.5);        
        ((GhostJumpScare) e5).setGhostY(20.5);
        mapEvents.add(e5);
        MapEvent e6 = new GhostJumpScare(35, 19);
        ((GhostJumpScare) e6).setTurning(false);
        ((GhostJumpScare) e6).setGhostX(33.5);        
        ((GhostJumpScare) e6).setGhostY(19.5);
        mapEvents.add(e6);
        MapEvent e7 = new GhostJumpScare(35, 19);
        ((GhostJumpScare) e7).setTurning(false);
        ((GhostJumpScare) e7).setGhostX(33.5);        
        ((GhostJumpScare) e7).setGhostY(20.5);
        mapEvents.add(e7);
        
        MapEvent e8 = new GhostFollow(33, 13);
        ((GhostFollow) e8).setGhostX(28.5);
        ((GhostFollow) e8).setGhostY(15.5);
        mapEvents.add(e8);

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
