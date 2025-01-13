package ui;

import static audio.AudioManager.GAMEOVER;
import static game.Game.GAME_HEIGHT;
import static game.Game.GAME_WIDTH;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.font.TextAttribute;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

import screens.Playing;
import screens.ScreenState;
import utilz.FontManager;


public class GameCompletedOverlay {
    private Playing playing;
    
    private Font marioFont;
    private AttributedString winningText;

    public GameCompletedOverlay(Playing playing) {
        this.playing = playing;

        marioFont = FontManager.loadExternalFont(FontManager.MARIO_FONT);
        marioFont = marioFont.deriveFont(Font.TRUETYPE_FONT, 48);
    }

    public void update() {
        String text = "YOU WON";
        winningText = new AttributedString(text);
        winningText.addAttribute(TextAttribute.FONT, marioFont, 0, text.length());
        winningText.addAttribute(TextAttribute.FOREGROUND, Color.WHITE);
        
        playing.getGame().getAudioManager().stopSong();
        playing.getGame().getAudioManager().playEffect(GAMEOVER);
        
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        
        playing.resetScoreAndLives();

        ScreenState.state = ScreenState.MENU;

        playing.setIsGameCompleted(false);
    }

    public void draw(Graphics g) {
        FontMetrics metrics = g.getFontMetrics(marioFont);
        // Determine the X coordinate for the text
        int x = (GAME_WIDTH - metrics.stringWidth(getStr())) / 2;

       
        g.drawString(winningText.getIterator(), x, (int)(GAME_HEIGHT/2));
    }

    private String getStr(){
        AttributedCharacterIterator x = winningText.getIterator();
        String a = "";

        a+=x.current();
        while (x.getIndex() < x.getEndIndex())
            a += x.next();
        a=a.substring(0,a.length()-1);

        return a;
    }
}
