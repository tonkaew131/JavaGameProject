import core.Renderer;
import core.Setting;

import javax.swing.*;
import java.awt.*;

public class Game {
    public Game() {
        JFrame frame = new JFrame();

        frame.add(new Renderer());
        frame.pack();

        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setSize(Setting.WINDOWS_WIDTH, Setting.WINDOWS_HEIGHT);
        frame.setResizable(Setting.WINDOWS_RESIZABLE);
        frame.setTitle("Veru super Cool Game! by @tonkaew131");
        frame.setVisible(true);
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> new Game());
    }
}
