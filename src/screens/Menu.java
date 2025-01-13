package screens;

import static game.Game.GAME_HEIGHT;
import static game.Game.GAME_WIDTH;
import static game.Game.SCALE;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import game.Game;
import utilz.ImageLoader;

public class Menu extends State implements StateMethods{
    
    private ScreenState[] screenState = {ScreenState.PICKLEVEL, ScreenState.HELP, ScreenState.ABOUT};
	private int screenStateIndex = 0;
    
    BufferedImage menuScreen, selectIcon;

    public Menu(Game game){
        super(game);
        menuScreen = ImageLoader.loadImage(ImageLoader.START_SCREEN);
        selectIcon = ImageLoader.loadImage(ImageLoader.SELECT_ICON);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(menuScreen, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);

        moveSelectIcon(g);
    }

    @Override
    public void update() {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
			case KeyEvent.VK_W:
				if(screenStateIndex > 0)
					screenStateIndex--;
				break;
			case KeyEvent.VK_S:
				if(screenStateIndex < 2)
					screenStateIndex++;
				break;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			ScreenState.state = screenState[screenStateIndex];
		}
    }

    private void moveSelectIcon(Graphics g) {
		switch(screenStateIndex) {
			case 0:
				g.drawImage(selectIcon, (int)(0.28*GAME_WIDTH), (int)(0.62*GAME_HEIGHT), (int)(48*SCALE),(int) (48*SCALE), null);
				break;
			case 1:
				g.drawImage(selectIcon, (int)(0.37*GAME_WIDTH), (int) (0.70*GAME_HEIGHT) , (int)(48*SCALE),(int) (48*SCALE), null);
				break;
			case 2:
				g.drawImage(selectIcon, (int)(0.35*GAME_WIDTH), (int) (0.79*GAME_HEIGHT - 5 * SCALE) , (int)(48*SCALE),(int) (48*SCALE), null);
				break;
			
		}
	}

}

