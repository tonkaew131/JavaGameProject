package core;

public class Player {
    // Player position
    private double posX, posY;

    // Player direction (in PI)
    // Right is 0, top is PI / 2, left is PI, bottom is 3PI / 2
    private double directionAlpha = 0;

    private double health = 100;

    public void forward(double amount) {
        posX += amount;
    }
}