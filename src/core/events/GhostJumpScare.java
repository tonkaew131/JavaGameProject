package core.events;

import core.MapEvent;
import core.Point;
import core.Sprite;
import core.SpriteWhite;

public class GhostJumpScare extends MapEvent {
    private double ghostX;
    private double ghostY;
    private boolean turning = true;

    public GhostJumpScare(int x, int y) {
        super(x, y);
    }

    @Override
    public void trigger(long currentMillis) {
        System.out.println("[MapEvent]: Ghost jump scare!");
        Sprite ghost = new SpriteWhite(new Point<Double>(ghostX, ghostY));
        getMap().addSprite(ghost);
        if (turning)
            getPlayer().forceTurnTo(ghost.getAbsoluteDirection(getPlayer().getPosition()) + Math.PI, currentMillis,
                    250);
        getSound().playJumpScareSound();

        // remove ghost
        new java.util.Timer().schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                getMap().removeSprite(ghost);
            }
        }, 500);
    }

    public void setGhostX(double ghostX) {
        this.ghostX = ghostX;
    }

    public void setGhostY(double ghostY) {
        this.ghostY = ghostY;
    }

    public void setTurning(boolean turning) {
        this.turning = turning;
    }
}
