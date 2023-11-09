package core;

public class Player {
    // Player position
    private double posX = 1, posY = 1;

    // Player direction (in PI)
    // Right is 0, top is PI / 2, left is PI, bottom is 3PI / 2
    private double directionAlpha = 0;
    private double stamina = 1;
    private int letterCount = 0;

    private double lastMillisForceTurn = 0;
    private double forceTurnDuration = 0;
    private double forceTurnTo = 0;
    private double forceTurnStep = 0;
    private boolean disableMoving = false;

    private Map map;
    private Sound sound;
    private RayCast rayCast = new RayCast();

    private boolean isWinning = false;

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

    // duration is millis sec
    public void forceTurnTo(double toDirection, long currentMillis, int duration) {
        lastMillisForceTurn = currentMillis;
        forceTurnTo = toDirection;
        forceTurnDuration = duration;

        double diff = radian(toDirection - directionAlpha);
        forceTurnStep = diff / duration;
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

            sound.playLetterPickupSound();
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

    public void setSound(Sound sound) {
        this.sound = sound;
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

    public double getForceTurnDuration() {
        return forceTurnDuration;
    }

    public double getForceTurnTo() {
        return forceTurnTo;
    }

    public double getForceTurnStep() {
        return forceTurnStep;
    }

    public double getLastMillisForceTurn() {
        return lastMillisForceTurn;
    }

    public void setDirectionAlpha(double directionAlpha) {
        this.directionAlpha = directionAlpha;
    }

    public void setForceTurnDuration(double forceTurnDuration) {
        this.forceTurnDuration = forceTurnDuration;
    }

    public boolean isWinning() {
        return this.isWinning;
    }

    public void setLetterCount(int letterCount) {
        this.letterCount = letterCount;
    }

    public void setWinning(boolean isWinning) {
        this.isWinning = isWinning;
    }

    public void checkWinning() {
        rayCast.setDirection(directionAlpha);
        rayCast.setPlayerPosition(getPosition());
        rayCast.cast();

        if (rayCast.getDistance() > Setting.LETTER_REACH_DISTANCE)
            return;

        Point<Integer> mapCheck = rayCast.getMapPoint();
        if (map.checkExit(mapCheck.x, mapCheck.y) && letterCount == 7) {
            isWinning = true;
        }
    }

    @Override
    public String toString() {
        return String.format("X: %.2f, Y: %.2f, a: %.2f", posX, posY, directionAlpha);
    }
}