package core;

public class RayCast {
    private Map map;
    private Point<Double> playerPosition;
    private double direction; // In radian

    private double distance;
    private Point<Integer> mapPoint;
    private Point<Double> hitPoint;
    private HitSide hitSide;

    public enum HitSide {
        HORIZONTAL,
        VERTICAL
    }

    public RayCast() {
    }

    public RayCast(Point<Double> playerPosition, double direction) {
        this.playerPosition = playerPosition;
        this.direction = direction;
    }

    public void cast() {
        // DDA Algorithm
        double posX = playerPosition.x;
        double posY = playerPosition.y;

        Point<Double> endPoint = new Point<>(Math.cos(direction) * 100, Math.sin(direction) * 100);
        Point<Double> startPoint = new Point<>(posX, posY);

        Point<Double> rayDirection = new Point<>(0d, 0d);
        rayDirection.x = endPoint.x - startPoint.x;
        rayDirection.y = endPoint.y - startPoint.y;

        // Normalize
        double rayLength = Math.sqrt(rayDirection.x * rayDirection.x + rayDirection.y * rayDirection.y);
        rayDirection.x /= rayLength;
        rayDirection.y /= rayLength;

        Point<Double> rayUnitStepSize = new Point<>(
                Math.sqrt(1 + ((rayDirection.y / rayDirection.x) * (rayDirection.y / rayDirection.x))),
                Math.sqrt(1 + ((rayDirection.x / rayDirection.y) * (rayDirection.x / rayDirection.y))));
        Point<Integer> mapCheck = new Point<>((int) posX, (int) posY);
        // store length in x, y
        Point<Double> rayLengthCumu = new Point<>(0d, 0d);
        // store step in x, y
        Point<Double> rayStep = new Point<>(0d, 0d);

        if (rayDirection.x < 0) {
            rayStep.x = -1d;
            rayLengthCumu.x = (startPoint.x - (float) mapCheck.x) * rayUnitStepSize.x;
        } else {
            rayStep.x = 1d;
            rayLengthCumu.x = ((float) mapCheck.x + 1 - startPoint.x) * rayUnitStepSize.x;
        }

        if (rayDirection.y < 0) {
            rayStep.y = -1d;
            rayLengthCumu.y = (startPoint.y - (float) mapCheck.y) * rayUnitStepSize.y;
        } else {
            rayStep.y = 1d;
            rayLengthCumu.y = ((float) mapCheck.y + 1 - startPoint.y) * rayUnitStepSize.y;
        }

        Point<Double> beforeHit = new Point<>();

        boolean hit = false;
        double distance = 0;
        double MAX_DISTANCE = 100;
        while (!hit && distance < MAX_DISTANCE) {
            beforeHit.x = rayLengthCumu.x;
            beforeHit.y = rayLengthCumu.y;

            if (rayLengthCumu.x < rayLengthCumu.y) {
                mapCheck.x += rayStep.x.intValue();
                distance = rayLengthCumu.x;
                rayLengthCumu.x += rayUnitStepSize.x;
            } else {
                mapCheck.y += rayStep.y.intValue();
                distance = rayLengthCumu.y;
                rayLengthCumu.y += rayUnitStepSize.y;
            }

            if (mapCheck.x >= 0 && mapCheck.y >= 0 && mapCheck.x < map.getMapWidth()
                    && mapCheck.y < map.getMapHeight()) {
                if (map.getTexture(mapCheck.x, mapCheck.y) != Texture.EMPTY) {
                    hit = true;
                }
            }
        }

        Point<Double> intersecPoint = new Point<>(0d, 0d);
        intersecPoint.x = startPoint.x + rayDirection.x * distance;
        intersecPoint.y = startPoint.y + rayDirection.y * distance;

        this.mapPoint = mapCheck;
        this.distance = distance;
        this.hitPoint = intersecPoint;
        this.hitSide = beforeHit.x < beforeHit.y ? HitSide.HORIZONTAL : HitSide.VERTICAL;
        return;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void setPlayerPosition(Point<Double> playerPosition) {
        this.playerPosition = playerPosition;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public Map getMap() {
        return map;
    }

    public double getDistance() {
        return distance;
    }

    public Point<Double> getHitPoint() {
        return hitPoint;
    }

    public HitSide getHitSide() {
        return hitSide;
    }

    public Point<Integer> getMapPoint() {
        return mapPoint;
    }
}