package core;

import java.awt.event.KeyEvent;
import java.time.Instant;
import java.util.TimerTask;

import core.path.PathFind;

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
    protected Sound sound;
    protected Player player;

    public Tick(Renderer renderer, Player player) {
        this.player = player;

        this.renderer = renderer;

        long currentTimeMillis = getCurrentMillis();
        gameStartMillis = currentTimeMillis;
        lastTickMillis = currentTimeMillis;
    }

    @Override
    public void run() {
        long currentTimeMillis = getCurrentMillis();
        this.deltaTime = currentTimeMillis - lastTickMillis;

        // System.out.println(this);
        // System.out.println(currentTimeMillis + " " + deltaTime + " " +
        // lastTickMillis);

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
        if (this.player.getForceTurnDuration() > 0) {
            this.player.setDisableTurning(false);
            this.player.setDisableMoving(false);

            this.player
                    .setDirectionAlpha(this.player.getDirectionAlpha() + (this.player.getForceTurnStep() / deltaTime));
            this.player.setForceTurnDuration(this.player.getForceTurnDuration() - deltaTime);
            if (this.player.getForceTurnDuration() <= 0) {
                this.player.setForceTurnDuration(0);
                this.player.setDirectionAlpha(this.player.getForceTurnTo());
            }
        }
        this.player.setDisableTurning(false);
        this.player.setDisableMoving(false);

        // if (Math.random() < 0.001 && currentTimeMillis - lastBreadthSoundMillis >
        // 15000) {
        // lastBreadthSoundMillis = currentTimeMillis;
        // sound.playBreadthSound();
        // }

        if (Math.random() < 0.5) {
            Point<Double> joshPos = this.renderer.getMap().josh.getPos();
            Point<Double> playerPos = this.player.getPosition();
            double distanceFromJosh = Math
                    .sqrt(Math.pow(joshPos.x - playerPos.x, 2) + Math.pow(joshPos.y - playerPos.y, 2));

            int soundLvl = (int) (500 / distanceFromJosh + 10);
            Sound.setVolume(sound.getBackgroundMusic(), soundLvl);
        }

        // Josh always follow player using path finder
        if (Math.random() < 0.1) {
            Point<Double> joshPos = this.renderer.getMap().josh.getPos();
            Point<Double> playerPos = this.player.getPosition();

            int startCol = joshPos.x.intValue();
            int startRow = joshPos.y.intValue();

            PathFind pf = new PathFind(this.renderer.getMap());
            pf.setNode(startCol, startRow, playerPos.x.intValue(), playerPos.y.intValue());

            if (pf.search() == true) {
                this.renderer.getMap().josh.setDirection(
                        Math.atan2(pf.getPathList().get(0).row - startRow, pf.getPathList().get(0).col - startCol));
                this.renderer.getMap().josh.forward(walkingStep * 5);
            }
        }

        lastTickMillis = currentTimeMillis;
    }

    public long getDeltaTime() {
        return this.deltaTime;
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

    public Renderer getRenderer() {
        return renderer;
    }

    public int getFPS() {
        // System.out.println(this);
        // System.out.println(deltaTime);

        if (this.deltaTime == 0)
            return 9999;
        return (int) (1000 / this.deltaTime);
    }
}
