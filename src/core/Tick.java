package core;

import java.time.Instant;

public class Tick extends Thread {
    // time since game lanuched!
    private long gameStartMillis;
    // time since last frame finish render
    private long lastTickMillis;
    // time between frame
    private long deltaTime;
    private Renderer renderer;

    public Tick(Renderer renderer) {
        this.renderer = renderer;
        this.renderer.setTick(this);
    }

    public void run() {
        long targetDeltaTime = 1000 / Setting.MAX_FPS;
        long currentTimeMillis = this.getCurrentMillis();
        gameStartMillis = currentTimeMillis;
        lastTickMillis = currentTimeMillis;

        while (true) {
            currentTimeMillis = this.getCurrentMillis();
            deltaTime = currentTimeMillis - lastTickMillis;

            // Dear myself in the future please propery implements tick system with fixed update and dynamic update so no more stupid fickering

            this.renderer.render();
            try {
                Thread.sleep(targetDeltaTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

//            if (deltaTime < targetDeltaTime) {
//                try {
//                    Thread.sleep(targetDeltaTime - deltaTime);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }

            // System.out.println("Tick updated! deltaTime: " + deltaTime + ", current time (ms): " + currentTimeMillis);
            // System.out.println("FPS: " + (deltaTime != 0 ? 1000 / deltaTime : "-"));
            this.renderer.render();

            lastTickMillis = currentTimeMillis;
        }
    }

    public long getDeltaTime() {
        return deltaTime;
    }

    long getCurrentMillis() {
        return Instant.now().toEpochMilli();
    }
}
