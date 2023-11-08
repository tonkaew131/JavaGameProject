package core;

public class Player {
    // Player position
    private double posX = 1, posY = 1;

    // Player direction (in PI)
    // Right is 0, top is PI / 2, left is PI, bottom is 3PI / 2
    private double directionAlpha = 0;
    private double stamina = 1;
    private boolean disableMoving = false;
    private int letterCount = 0;

    private Map map;
    private RayCast rayCast = new RayCast();

    public void forward(double amount) {
        if (disableMoving)
            return;

        rayCast.setPlayerPosition(getPosition());
        rayCast.setDirection(directionAlpha);
        rayCast.cast();
        if (rayCast.getDistance() < 0.1)
            return;

        posX += Math.cos(directionAlpha) * amount;
        posY += Math.sin(directionAlpha) * amount;
    }

    public void backward(double amount) {
        if (disableMoving)
            return;

        double directionAlpha = this.directionAlpha + Math.PI;

        rayCast.setPlayerPosition(getPosition());
        rayCast.setDirection(radian(directionAlpha));
        rayCast.cast();

        if (rayCast.getDistance() < 0.1)
            return;

        posX += Math.cos(directionAlpha) * amount;
        posY += Math.sin(directionAlpha) * amount;
    }

    // amount in radius
    public void turnRight(double amount) {
        directionAlpha -= amount;

        directionAlpha = radian(directionAlpha);
    }

    public static double radian(double amount) {
        amount %= Math.PI * 2;
        if (amount < 0)
            amount += Math.PI * 2;
        return amount;
    }

    public void collect() {
        rayCast.setDirection(directionAlpha);
        rayCast.setPlayerPosition(getPosition());
        rayCast.cast();

        if (rayCast.getDistance() > Setting.LETTER_REACH_DISTANCE)
            return;

        Point<Integer> mapCheck = rayCast.getMapPoint();
        if (map.checkLetter(mapCheck.x, mapCheck.y)) {
            map.removeLetter(mapCheck.x, mapCheck.y);
            letterCount++;
        }
        return;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public Point<Double> getPosition() {
        return new Point<Double>(posX, posY);
    }

    public double getDirectionAlpha() {
        return directionAlpha;
    }

    public void setMap(Map map) {
        this.map = map;
        this.rayCast.setMap(map);
        posX = map.getSpawnX();
        posY = map.getSpawnY();
    }

    public double getStamina() {
        return stamina;
    }

    public void setStamina(double stamina) {
        this.stamina = Math.max(0, Math.min(1, stamina));
    }

    public void addLetter() {
        letterCount++;
    }

    public int getLetterCount() {
        return letterCount;
    }

    @Override
    public String toString() {
        return String.format("X: %.2f, Y: %.2f, a: %.2f", posX, posY, directionAlpha);
    }
}