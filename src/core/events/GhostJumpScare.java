package core.events;

import core.MapEvent;
import core.Point;
import core.Sprite;
import core.SpriteWhite;

public class GhostJumpScare extends MapEvent {
    public GhostJumpScare(int x, int y) {
        super(x, y);
    }

    @Override
    public void trigger(long currentMillis) {
        System.out.println("[MapEvent]: Ghost jump scare!");
        Sprite ghost = new SpriteWhite(new Point<Double>(6.5, 22.5));
        getMap().addSprite(ghost);
        getPlayer().forceTurnTo(ghost.getAbsoluteDirection(getPlayer().getPosition()) + Math.PI, currentMillis, 250);
        getSound().playJumpScareSound();

        // remove ghost
        new java.util.Timer().schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                getMap().removeSprite(ghost);
            }
        }, 500);
    }
}
