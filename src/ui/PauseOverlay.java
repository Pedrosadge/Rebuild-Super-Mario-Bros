package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.TextAttribute;
import java.text.AttributedString;

import screens.Playing;
import utilz.FontManager;

import static game.Game.GAME_HEIGHT;
import static game.Game.GAME_WIDTH;

public class PauseOverlay {

    private Playing playing;
    private Font marioFont;
    private AttributedString pauseScreenText;
    
    
    
    public PauseOverlay(Playing playing) {
        this.playing = playing;
        marioFont = FontManager.loadExternalFont(FontManager.MARIO_FONT);
        String text = "Game Paused";
        pauseScreenText = new AttributedString(text);
        pauseScreenText.addAttribute(TextAttribute.FONT, marioFont, 0, text.length());
        pauseScreenText.addAttribute(TextAttribute.FOREGROUND, Color.WHITE);
    }

    public void update() {

    }

    public void draw(Graphics g) {
        g.drawString(pauseScreenText.getIterator(), (GAME_WIDTH/2)-(int)(GAME_WIDTH*0.15), GAME_HEIGHT/2);
    }

    
}
