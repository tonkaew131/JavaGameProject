package com.tonkaew131.javagameproject;

import java.time.Instant;

public class Tick extends Thread {
    long gameStartMillis;
    long timePassed;
    long deltaTime;
    Game gameObject;

    Tick(Game gameObject) {
        this.gameObject = gameObject;
    }

    public void run() {
        gameStartMillis = this.getCurrentMillis();
        timePassed = 0;

        while (true) {
            // Dynamic Update
            update();

            // Render
            gameObject.getRenderer().render();

            deltaTime = getCurrentMillis() - deltaTime;
            timePassed += deltaTime;
        }
    }

    // The game dynamic update, same as frame rate
    public void update() {

    }

    // Fixed update
    public void fixedUpdate() {

    }

    long getCurrentMillis() {
        return Instant.now().getEpochSecond() / 1000;
    }
}
