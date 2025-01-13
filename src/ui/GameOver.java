package ui;

import static audio.AudioManager.GAMEOVER;
import static game.Game.GAME_HEIGHT;
import static game.Game.GAME_WIDTH;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

import screens.Playing;
import screens.ScreenState;
import utilz.FontManager;
import utilz.ImageLoader;

public class GameOver {
    private Playing playing;

    private BufferedImage gameoverImg;

    private Font marioFont;
    private AttributedString scoreTotalText;
    
    public GameOver(Playing playing) {
        this.playing = playing;
        gameoverImg = ImageLoader.loadImage(ImageLoader.GAME_OVER_SCREEN);

        marioFont = FontManager.loadExternalFont(FontManager.MARIO_FONT);
        marioFont = marioFont.deriveFont(Font.TRUETYPE_FONT, 48);
    }

    public void update() {
        String text = "SCORE " + Integer.toString(playing.getScore());
        scoreTotalText = new AttributedString(text);
        scoreTotalText.addAttribute(TextAttribute.FONT, marioFont, 0, text.length());
        scoreTotalText.addAttribute(TextAttribute.FOREGROUND, Color.WHITE);

        playing.getGame().getAudioManager().stopSong();
        playing.getGame().getAudioManager().playEffect(GAMEOVER);
        
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        playing.resetScoreAndLives();

        ScreenState.state = ScreenState.MENU;

        playing.setGameOver(false);
        playing.setPlayerDying(false);
    }

    public void draw(Graphics g) {
        FontMetrics metrics = g.getFontMetrics(marioFont);
        // Determine the X coordinate for the text
        int x = (GAME_WIDTH - metrics.stringWidth(getStr())) / 2;

        g.drawImage(gameoverImg, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
        g.drawString(scoreTotalText.getIterator(), x, GAME_HEIGHT- (int)(GAME_HEIGHT*0.3));
    }

    private String getStr(){
        AttributedCharacterIterator x = scoreTotalText.getIterator();
        String a = "";

        a+=x.current();
        while (x.getIndex() < x.getEndIndex())
            a += x.next();
        a=a.substring(0,a.length()-1);

        return a;
    }
}
