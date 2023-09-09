package core;

import java.awt.*;
import java.awt.event.KeyEvent;

public class KeyListener implements KeyEventDispatcher {
    private Player player;

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        if (e.getID() == KeyEvent.KEY_PRESSED) {
            switch (e.getKeyChar()) {
                case 'w':
                    this.player.forward(Setting.WALKING_STEP);
                    break;
                case 's':
                    this.player.forward(-Setting.WALKING_STEP);
                    break;
                case 'a':
                    this.player.turnRight(-Setting.TURNING_STEP);
                    break;
                case 'd':
                    this.player.turnRight(Setting.TURNING_STEP);
                    break;
            }

        }
        return false;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
