package com.tonkaew131.javagameproject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class KeyHandler implements EventHandler<KeyEvent> {
    @Override
    public void handle(KeyEvent keyEvent) {
        System.out.println("Key pressed: " + keyEvent.getText());
    }
}
