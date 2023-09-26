
import core.*;
import core.Renderer;

import javax.swing.*;
import java.awt.*;

public class Game {
    Renderer renderer;

    public Game() {
        JFrame frame = new JFrame();

        this.renderer = new Renderer();
        frame.add(renderer);
        frame.pack();

        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(Setting.WINDOWS_RESIZABLE);
        frame.setTitle("Long white thingy 2.5D by @tonkaew131");
        frame.setVisible(true);

        Texture.loadTexture();

        Player player = new Player();
        KeyListener keyListener = new KeyListener();
        keyListener.setPlayer(player);

        frame.setFocusable(true);
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addKeyEventDispatcher(keyListener);

        Tick tick = new Tick(renderer);
        tick.start();

        Map map = new Map();

        this.renderer.setMap(map);
        this.renderer.setPlayer(player);
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> new Game());
    }

    public Renderer getRenderer() {
        return renderer;
    }
}
