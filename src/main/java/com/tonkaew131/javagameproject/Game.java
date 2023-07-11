package com.tonkaew131.javagameproject;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class Game extends Application {
    public Player player;

    Player getPlayer() {
        return this.player;
    }

    @Override
    public void start(Stage stage) {
        this.player = new Player();

        var label = new Label("Hello World!");

        var scene = new Scene(new StackPane(label), 640, 480);
        scene.setOnKeyPressed(new KeyHandler(this));

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}