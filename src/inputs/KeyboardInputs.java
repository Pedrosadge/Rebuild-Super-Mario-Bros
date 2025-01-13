package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import game.GamePanel;
import screens.ScreenState;

public class KeyboardInputs implements KeyListener {
    
    private GamePanel gamePanel;

    public KeyboardInputs(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (ScreenState.state) {
            case MENU:
                gamePanel.getGame().getMenu().keyPressed(e);
                break;
            case HELP:
                gamePanel.getGame().getHelp().keyPressed(e);
                break;
            case ABOUT:
                gamePanel.getGame().getAbout().keyPressed(e);
                break;
            case PICKLEVEL:
                gamePanel.getGame().getPickLevel().keyPressed(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().keyPressed(e);
                break;
        
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (ScreenState.state) {
            case PLAYING:
                gamePanel.getGame().getPlaying().keyReleased(e);
                break;
        
            default:
                break;
        }
        
    }
}
