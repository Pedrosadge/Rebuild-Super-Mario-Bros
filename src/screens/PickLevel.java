package screens;

import static game.Game.GAME_HEIGHT;
import static game.Game.GAME_WIDTH;
import static game.Game.SCALE;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import game.Game;
import utilz.ImageLoader;



public class PickLevel extends State implements StateMethods {

    private BufferedImage pickLevelScreen, selectIcon;
    int mapChoice = 0; // 0, 1, 2 (map 1, 2, 3)

    public PickLevel(Game game) {
        super(game);
        
        pickLevelScreen = ImageLoader.loadImage(ImageLoader.PICK_LEVEL_SCREEN);
        selectIcon = ImageLoader.loadImage(ImageLoader.SELECT_ICON);
    }

    @Override
    public void keyPressed(KeyEvent e) {
		

        switch(e.getKeyCode()) {
			case KeyEvent.VK_BACK_SPACE:
            case KeyEvent.VK_ESCAPE:
                ScreenState.state = ScreenState.MENU;
                break;

			case KeyEvent.VK_W:
				if(mapChoice > 0)
					mapChoice--;
				break;
			case KeyEvent.VK_S:
				if(mapChoice < 2)
					mapChoice++;
				break;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            getGame().getPlaying().loadLevel(getMapChoice());

            getGame().getPlaying().setPlayerLives(3);

            ScreenState.state = ScreenState.PLAYING;
		}
    }

    @Override
    public void update() {
        
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(pickLevelScreen, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);

        moveSelectIcon(g);
    }

    private void moveSelectIcon(Graphics g) {
		switch(mapChoice) {
			case 0:
				g.drawImage(selectIcon, (int)(0.38*GAME_WIDTH), (int)(0.38*GAME_HEIGHT), (int)(48*SCALE),(int) (48*SCALE), null);
                mapChoice = 0;
				break;
			case 1:
				g.drawImage(selectIcon, (int)(0.38*GAME_WIDTH), (int) (0.46*GAME_HEIGHT) , (int)(48*SCALE),(int) (48*SCALE), null);
				mapChoice = 1;
                break;
			case 2:
				g.drawImage(selectIcon, (int)(0.38*GAME_WIDTH), (int) (0.54*GAME_HEIGHT) , (int)(48*SCALE),(int) (48*SCALE), null);
				mapChoice = 2;
                break;
			
		}
	}

    public int getMapChoice(){
        return mapChoice;
    }
    
}
