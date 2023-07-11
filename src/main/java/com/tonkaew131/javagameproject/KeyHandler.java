package com.tonkaew131.javagameproject;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class KeyHandler implements EventHandler<KeyEvent> {
    Game GameObect;

    KeyHandler(Game gameObject) {
        this.GameObect = gameObject;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        System.out.println("Key pressed: " + keyEvent.getText());
    }
}
