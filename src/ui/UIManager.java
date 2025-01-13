package ui;

import static game.Game.GAME_HEIGHT;
import static game.Game.GAME_WIDTH;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.text.AttributedString;

import screens.Playing;
import utilz.FontManager;
import utilz.ImageLoader;

public class UIManager {

    private Playing playing;

    BufferedImage heartImg;
    private Font marioFont;
    private AttributedString remainingLivesText, scoreTotalText;
    

    public UIManager(Playing playing) {
        this.playing = playing;
        marioFont = FontManager.loadExternalFont(FontManager.MARIO_FONT);
        
        marioFont = marioFont.deriveFont(Font.TRUETYPE_FONT, 16);

        
    }

    public void update(){
        heartImg = ImageLoader.loadImage(ImageLoader.HEART_SPRITE);
        String text = Integer.toString(playing.getPlayerLives());
        remainingLivesText = new AttributedString(text);
        remainingLivesText.addAttribute(TextAttribute.FONT, marioFont, 0, text.length());
        remainingLivesText.addAttribute(TextAttribute.FOREGROUND, Color.WHITE);

        text = "SCORE " + Integer.toString(playing.getScore());
        scoreTotalText = new AttributedString(text);
        scoreTotalText.addAttribute(TextAttribute.FONT, marioFont, 0, text.length());
        scoreTotalText.addAttribute(TextAttribute.FOREGROUND, Color.WHITE);
    }

    public void draw(Graphics g){
        // draw hearts
        g.drawImage(heartImg, GAME_WIDTH/15, 8, 32, 32, null);

        // draw string remaning lives
        g.drawString(remainingLivesText.getIterator(), GAME_WIDTH/10, (int) GAME_HEIGHT/20);

        // draw total score
        g.drawString(scoreTotalText.getIterator(), GAME_WIDTH - (int)(GAME_WIDTH*0.2), (int) GAME_HEIGHT/20);
    }




}
