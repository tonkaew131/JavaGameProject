package com.tonkaew131.javagameproject;

public class Player {
    // Top left: origin
    float posX;
    float posY;
    float health;
    // Right is origin, going left <-
    float angle;

    Player() {
        this.posX = 0;
        this.posY = 0;
        this.health = 100;
    }

    public void setPosition(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
    }
}
