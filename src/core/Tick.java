package core;

import java.time.Instant;

public class Tick {
    // time since game lanuched!
    long gameStartMillis;
    // time since last frame finish render
    long lastTickMillis;
    // time between frame
    long deltaTime;
    Renderer renderer;

    public Tick(Renderer renderer) {
        this.renderer = renderer;
    }

    public void runGame() {
        gameStartMillis = this.getCurrentMillis();
        lastTickMillis = this.getCurrentMillis();

        while (true) {
            deltaTime = this.getCurrentMillis() - lastTickMillis;

            for (int i = 0; i < 1000000000; i++) ;
            System.out.println("Tick updated! deltaTime: " + deltaTime);
            this.renderer.render();

            lastTickMillis = this.getCurrentMillis();
        }
    }

    long getCurrentMillis() {
        return Instant.now().getEpochSecond() / 1000;
    }
}
