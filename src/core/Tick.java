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
    private long runningTick;

    private long lastBreadthSoundMillis = 0;

    private Renderer renderer;
    private Sound sound;
    private Player player;

    public Tick(Renderer renderer, Player player) {
        this.player = player;

        this.renderer = renderer;
        this.renderer.setTick(this);

        long currentTimeMillis = getCurrentMillis();
        gameStartMillis = currentTimeMillis;
        lastTickMillis = currentTimeMillis;
    }

    @Override
    public void run() {
        long currentTimeMillis = getCurrentMillis();
        deltaTime = currentTimeMillis - lastTickMillis;

        double walkingStep = Setting.WALKING_STEP * deltaTime / 1000;
        double turningStep = Setting.TURNING_STEP * deltaTime / 1000;
        double staminaStep = Setting.STAMINA_STEP * deltaTime / 1000;

        // Stand still
        if (!KeyListener.isKeyPressed(KeyEvent.VK_W) && !KeyListener.isKeyPressed(KeyEvent.VK_S)) {
            this.player.setStamina(this.player.getStamina() + staminaStep * 0.6);
        }

        // Walk forward & Run
        if (KeyListener.isKeyPressed(KeyEvent.VK_W)) {
            runningTick += 1;
            if (KeyListener.isKeyPressed(KeyEvent.VK_SHIFT) && this.player.getStamina() > 0.01) {
                runningTick += 2;
                walkingStep *= 2;
                this.player.setStamina(this.player.getStamina() - staminaStep);
            } else {
                this.player.setStamina(this.player.getStamina() + staminaStep * 0.4);
            }

            this.player.forward(walkingStep);
        }

        // Walk backward
        if (KeyListener.isKeyPressed(KeyEvent.VK_S)) {
            this.player.backward(turningStep);
        }

        // Turn left
        if (KeyListener.isKeyPressed(KeyEvent.VK_A)) {
            this.player.turnRight(-turningStep);
        }

        // Turn right
        if (KeyListener.isKeyPressed(KeyEvent.VK_D)) {
            this.player.turnRight(turningStep);
        }

        // Force turn
        if (currentTimeMillis - this.player.getLastMillisForceTurn() < this.player.getForceTurnDuration()) {
            this.player.turnRight(this.player.getForceTurnStep());
        }

        if (Math.random() < 0.001 && currentTimeMillis - lastBreadthSoundMillis > 15000) {
            lastBreadthSoundMillis = currentTimeMillis;
            sound.playBreadthSound();
        }

        lastTickMillis = currentTimeMillis;
    }

    public long getDeltaTime() {
        return deltaTime;
    }

    public long getGameStartMillis() {
        return gameStartMillis;
    }

    public long getRunningTick() {
        return runningTick;
    }

    public static long getCurrentMillis() {
        return Instant.now().toEpochMilli();
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    public int getFPS() {
        if (deltaTime == 0)
            return 9999;
        return (int) (1000 / deltaTime);
    }
}
