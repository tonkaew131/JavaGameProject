package core;

public class Player {
    // Player position
    private double posX = 1.5, posY = 1.5;

    // Player direction (in PI)
    // Right is 0, top is PI / 2, left is PI, bottom is 3PI / 2
    private double directionAlpha = 3 * Math.PI / 2;

    private double health = 100;

    public void forward(double amount) {
        posX += amount;

        // System.out.println(this);
    }

    // amount in radius
    public void turnRight(double amount) {
        directionAlpha -= amount;

        // directionAlpha = directionAlpha % (2 * Math.PI);
        // System.out.println(this);
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public double getDirectionAlpha() {
        return directionAlpha;
    }

    @Override
    public String toString() {
        return String.format("X: %.2f, Y: %.2f, a: %.2f", posX, posY, directionAlpha);
    }
}