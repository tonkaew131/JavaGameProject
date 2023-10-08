package core;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class KeyListener {
    private static final Map<Integer, Boolean> pressedKeys = new HashMap<>();
    private static Player player;

    static {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(event -> {
            // Pressed once
            if (event.getID() == KeyEvent.KEY_PRESSED) {
                if (event.getKeyCode() == KeyEvent.VK_L) {
                    Setting.TOGGLE_LIGHT = !Setting.TOGGLE_LIGHT;
                }
                if (event.getKeyCode() == KeyEvent.VK_M) {
                    Setting.TOGGLE_MAP = !Setting.TOGGLE_MAP;
                }
                if (event.getKeyCode() == KeyEvent.VK_E) {
                    player.collect();
                }
            }

            synchronized (KeyListener.class) {
                if (event.getID() == KeyEvent.KEY_PRESSED) {
                    pressedKeys.put(event.getKeyCode(), true);
                } else if (event.getID() == KeyEvent.KEY_RELEASED) {
                    pressedKeys.put(event.getKeyCode(), false);
                }

                return false;
            }
        });
    }

    public static Player getPlayer() {
        return player;
    }

    public static void setPlayer(Player player) {
        KeyListener.player = player;
    }

    public static boolean isKeyPressed(int keyCode) { // Any key code from the KeyEvent class
        return pressedKeys.getOrDefault(keyCode, false);
    }
}
