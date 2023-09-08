

import core.Renderer;
import core.Setting;

import javax.swing.*;

public class Game {
    Renderer renderer;

    public Game() {
        JFrame frame = new JFrame();

        this.renderer = new Renderer();
        frame.add(renderer);
        frame.pack();

        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setSize(Setting.WINDOWS_WIDTH, Setting.WINDOWS_HEIGHT);
        frame.setResizable(Setting.WINDOWS_RESIZABLE);
        frame.setTitle("Very Super Duper Extremly Cool Game! by @tonkaew131");
        frame.setVisible(true);
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> new Game());
    }

    public Renderer getRenderer() {
        return renderer;
    }
}
