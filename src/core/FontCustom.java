package core;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

public class FontCustom {
    public static Font PixelifySans;

    public static void loadFonts() {
        // GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        // ge.registerFont(PixelifySans);

        File fontFile = new File("resources/font/Pixelify_Sans/PixelifySans-VariableFont_wght.ttf");
        try {
            // Pixelify Sans
            PixelifySans = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        } catch (FontFormatException e) {
            System.out.println("[FontCustom]: Failed to load fonts!");
            e.printStackTrace();
            return;
        } catch (IOException e) {
            System.out.println("[FontCustom]: Failed to load fonts!");
            e.printStackTrace();
            return;
        }
        System.out.println("[FontCustom]: fonts loaded!");
    }
}
