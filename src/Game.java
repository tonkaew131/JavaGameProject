
import core.*;
import core.Renderer;

import javax.swing.*;
import java.awt.*;

public class Game {
    Renderer renderer;

    public Game() {
        JFrame frame = new JFrame();

        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(Setting.WINDOWS_RESIZABLE);
        frame.setSize(Setting.WINDOWS_WIDTH, Setting.WINDOWS_HEIGHT);
        frame.setTitle("Long white thingy 2.5D by @tonkaew131");
        frame.setVisible(true);
        frame.setFocusable(true);

        Texture.loadTexture();

        // Tick tick = new Tick(renderer);
        // tick.start();

        Map map = new Map();

        this.renderer = new Renderer();
        this.renderer.setMap(map);

        Player player = new Player();
        this.renderer.setPlayer(player);

        KeyListener keyListener = new KeyListener();
        keyListener.setPlayer(player);
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyListener);

        frame.add(renderer);
        // frame.pack();
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> new Game());
    }

    public Renderer getRenderer() {
        return renderer;
    }
}
