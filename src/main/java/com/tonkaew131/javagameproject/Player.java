package com.tonkaew131.javagameproject;

public class Player {
    float posX;
    float posY;
    float health;

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
