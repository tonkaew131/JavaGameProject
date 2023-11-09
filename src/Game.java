
import core.*;
import core.Renderer;

import javax.swing.*;

import java.awt.Dimension;
import java.util.Timer;

public class Game {
    Renderer renderer;
    Player player;

    public Game() {
        JFrame frame = new JFrame();

        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(Setting.WINDOWS_RESIZABLE);
        frame.setSize(Setting.WINDOWS_WIDTH, Setting.WINDOWS_HEIGHT);
        frame.setPreferredSize(new Dimension(Setting.WINDOWS_WIDTH, Setting.WINDOWS_HEIGHT));
        frame.setTitle("Long white thingy 2.5D by @tonkaew131");
        frame.setVisible(true);
        frame.setFocusable(true);

        Texture.loadTexture();
        FontCustom.loadFonts();

        // Map map = new Map();
        Map map = new MapTest();

        this.renderer = new Renderer();
        this.renderer.setMap(map);
        Renderer.loadAssets();

        this.player = new Player();
        this.player.setMap(map);
        this.renderer.setPlayer(player);

        Tick tick = new Tick(this.renderer, this.player);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(tick, 0, 1000 / 60);

        KeyListener.setPlayer(player);

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
