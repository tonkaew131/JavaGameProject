package com.tonkaew131.javagameproject;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class Game extends Application {
    public Player player;
    public Renderer renderer;
    public GraphicsContext graphicsContext;
    int width;
    int height;
    public boolean isTickRunning;

    Player getPlayer() {
        return this.player;
    }

    Renderer getRenderer() {
        return this.renderer;
    }

    @Override
    public void start(Stage stage) {
        this.player = new Player();
        this.renderer = new Renderer(this);
        this.width = 640;
        this.height = 480;
        this.isTickRunning = true;

        final Canvas canvas = new Canvas(this.width, this.height);
        this.graphicsContext = canvas.getGraphicsContext2D();
        this.graphicsContext.setFill(Color.GREEN);
        this.graphicsContext.fillRect(75, 75, 100, 100);

        Group root = new Group();
        root.getChildren().add(canvas);

        var scene = new Scene(root, this.width, this.height, Color.BLACK);
        scene.setOnKeyPressed(new KeyHandler(this));

        stage.setScene(scene);
        stage.show();

        // Added Thread handler
        Tick gameTicks = new Tick(this);
        gameTicks.start();
    }

    public static void main(String[] args) {
        launch();
    }

}