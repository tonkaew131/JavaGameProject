package core;

import javax.swing.*;
import java.awt.*;

public class Renderer extends JPanel {
    public Renderer() {
        this.setBackground(Color.black);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Setting.WINDOWS_WIDTH, Setting.WINDOWS_HEIGHT);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.CYAN);
        g2d.drawLine(0, 0, Setting.WINDOWS_WIDTH, Setting.WINDOWS_HEIGHT);
    }
}
