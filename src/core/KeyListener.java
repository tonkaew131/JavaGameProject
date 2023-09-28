package core;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class KeyListener {
    private static final Map<Integer, Boolean> pressedKeys = new HashMap<>();

    static {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(event -> {
            if (event.getID() == KeyEvent.KEY_PRESSED) {
                if (event.getKeyCode() == KeyEvent.VK_L) {
                    Setting.TOGGLE_LIGHT = !Setting.TOGGLE_LIGHT;
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

    public static boolean isKeyPressed(int keyCode) { // Any key code from the KeyEvent class
        return pressedKeys.getOrDefault(keyCode, false);
    }
}
