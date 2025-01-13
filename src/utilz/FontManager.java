package utilz;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

public class FontManager {
    public static String MARIO_FONT = "font/mario-font.ttf";


    public static Font loadExternalFont(String filename) {
        Font font = null;
        try {
            
            font = Font.createFont(Font.TRUETYPE_FONT, new File("res/" + filename));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        font = font.deriveFont(Font.PLAIN, 32);
        

        return font;
    }
}
