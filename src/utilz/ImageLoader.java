package utilz;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * ImageLoader
 */
public class ImageLoader {

	// screens
    public static final String START_SCREEN = "screens/start-screen.png";
    public static final String HELP_SCREEN = "screens/help-screen.png";
	public static final String ABOUT_SCREEN = "screens/about-screen.png";
	public static final String PICK_LEVEL_SCREEN = "screens/pick-level-screen.png";

	public static final String SELECT_ICON = "screens/select-icon.png";

	public static final String GAME_OVER_SCREEN = "screens/game-over.png"; 

	// characters
	public static final String MARIO_SPRITE = "character/mario-forms.png"; 

	//level background
	public static final String LEVEL_BACKGROUND = "levels/background.png"; 

	// levels data
	public static final String MAP_ONE = "levels/Map 1.png"; 
	public static final String MAP_TWO = "levels/Map 2.png"; 
	public static final String MAP_THREE = "levels/Map 3.png"; 

	// bricks 
	public static final String BRICKS_SPRITE = "levels/bricks-sprite.png"; 
	public static final String MYSTERY_BRICKS_SPRITE = "levels/mystery-bricks.png"; 
	public static final String TRANSPARENT_BRICKS_SPRITE = "levels/transparent-brick.png"; 
	public static final String PIPE_SPRITE = "levels/pipe-sprite.png"; 
	
	// enemies 
	public static final String GOOMBA_SPRITE = "enemies/goomba-sprite.png"; 
	public static final String KOOPA_SPRITE = "enemies/koopa-sprite.png";


	// end game stuff
	public static final String FLAG_SPRITE = "levels/flag-sprite.png";
	public static final String CASTLE = "levels/end-castle.png";

	// game objects
	public static final String FlOWER_AND_MUSHROOMS = "levels/flower-and-mushrooms-sprite.png"; 
	public static final String FIREBALL_SPRITE = "levels/fireball-sprite.png"; 
	public static final String COIN_SPRITE = "levels/coin-sprite.png";
    
	// ui
	public static final String HEART_SPRITE = "levels/heart-icon.png"; 

    public static BufferedImage loadImage(String fileName){
        InputStream is = ImageLoader.class.getResourceAsStream("/" + fileName);
       
		BufferedImage img = null;
		
		try {
			img = ImageIO.read(is);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return img;
    }
    
}