package core;

import java.awt.event.KeyEvent;
import java.time.Instant;
import java.util.TimerTask;

public class Tick extends TimerTask {
    // time since game lanuched!
    private long gameStartMillis;
    // time since last frame finish render
    private long lastTickMillis;
    // time between frame
    private long deltaTime;

    private Renderer renderer;
    private Player player;

    public Tick(Renderer renderer, Player player) {
        this.player = player;

        this.renderer = renderer;
        this.renderer.setTick(this);

        long currentTimeMillis = this.getCurrentMillis();
        gameStartMillis = currentTimeMillis;
        lastTickMillis = currentTimeMillis;
    }

    @Override
    public void run() {
        long currentTimeMillis = this.getCurrentMillis();
        deltaTime = currentTimeMillis - lastTickMillis;

        double walkingStep = Setting.WALKING_STEP * deltaTime / 1000;
        double turningStep = Setting.TURNING_STEP * deltaTime / 1000;

        if (KeyListener.isKeyPressed(KeyEvent.VK_SHIFT))
            walkingStep *= 2;

        if (KeyListener.isKeyPressed(KeyEvent.VK_W)) {
            this.player.forward(walkingStep);
        }
        if (KeyListener.isKeyPressed(KeyEvent.VK_S)) {
            this.player.backward(turningStep);
        }
        if (KeyListener.isKeyPressed(KeyEvent.VK_A)) {
            this.player.turnRight(-turningStep);
        }
        if (KeyListener.isKeyPressed(KeyEvent.VK_D)) {
            this.player.turnRight(turningStep);
        }

        lastTickMillis = currentTimeMillis;
    }

    public long getDeltaTime() {
        return deltaTime;
    }

    long getCurrentMillis() {
        return Instant.now().toEpochMilli();
    }

}
