package screens;

import static game.Game.GAME_HEIGHT;
import static game.Game.GAME_WIDTH;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import game.Game;
import utilz.ImageLoader;

public class About extends State implements StateMethods {

    private BufferedImage aboutScreen;

    public About(Game game){
        super(game);

        aboutScreen = ImageLoader.loadImage(ImageLoader.ABOUT_SCREEN);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_BACK_SPACE:
            case KeyEvent.VK_ESCAPE:
                ScreenState.state = ScreenState.MENU;
                break;
        
            default:
                break;
        }
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void draw(Graphics g) {
        
        g.drawImage(aboutScreen, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
    }
    
}
