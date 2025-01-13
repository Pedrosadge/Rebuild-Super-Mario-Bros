package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import game.Game;
import utilz.ImageLoader;

public class LevelManager {

    private Game game;
	private BufferedImage[] levelSprite;
    private ArrayList <Level> levels = new ArrayList<>();
	private Level level;
	

   


    public LevelManager(Game game) {
		this.game = game;
		importBricksSprites();
		buildAllLevel();
	}

    private void buildAllLevel() {
        levels.add(new Level(ImageLoader.loadImage(ImageLoader.MAP_ONE)));
        levels.add(new Level(ImageLoader.loadImage(ImageLoader.MAP_TWO)));
        levels.add(new Level(ImageLoader.loadImage(ImageLoader.MAP_THREE)));
    }

    public void chooseLevel(int lvlIndex){
        level = levels.get(lvlIndex);   
    }

    public void reBuildLevel(int lvlIndex) {
        switch (lvlIndex) {
            case 0:
                levels.set(0, new Level(ImageLoader.loadImage(ImageLoader.MAP_ONE)));
                break;
            case 1:
                levels.set(1, new Level(ImageLoader.loadImage(ImageLoader.MAP_TWO)));
                break;
            case 2:
                levels.set(2, new Level(ImageLoader.loadImage(ImageLoader.MAP_THREE)));
            default:
                break;
        }
    }


    private void importBricksSprites() {
    
        BufferedImage img = ImageLoader.loadImage(ImageLoader.BRICKS_SPRITE);
        levelSprite = new BufferedImage[13];

        // base bricks
        levelSprite[0] = img.getSubimage(0, 0, 16, 16);
        // building brick
        levelSprite[1] = img.getSubimage(0, 17, 16, 16);
        // breakable brick
        levelSprite[2] = img.getSubimage(17, 0, 16, 16);
        // mystery brick
        levelSprite[3] = ImageLoader.loadImage(ImageLoader.MYSTERY_BRICKS_SPRITE);
        // transparent brick
        levelSprite[4] = ImageLoader.loadImage(ImageLoader.TRANSPARENT_BRICKS_SPRITE);
        
        // loading pipe block
        img = ImageLoader.loadImage(ImageLoader.PIPE_SPRITE);
        // top left pipe
        levelSprite[5] = img.getSubimage(0, 0, 16, 16);
        // top right pipe
        levelSprite[6] = img.getSubimage(16, 0, 16, 16);
        // bottom left pipe
        levelSprite[7] = img.getSubimage(0, 16, 16, 16);
        // bottom right pipe
        levelSprite[8] = img.getSubimage(16, 16, 16, 16);

        // loading end game stuff
        img = ImageLoader.loadImage(ImageLoader.FLAG_SPRITE);
        // flag top part
        levelSprite[9] = img.getSubimage(16, 0, 16, 16);
        // flag pole
        levelSprite[10] = img.getSubimage(0, 0, 16, 16);
        
        // castle top left
        img = ImageLoader.loadImage(ImageLoader.CASTLE);
        levelSprite[11] = img;
    }

    public void update() {
        
    }

    public void draw(Graphics g, int levelOffset) {
        for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) 
			for (int i = 0; i < level.getLevelData()[0].length; i++) {
				int index = level.getSpriteIndex(i, j);
				int x = Game.TILES_SIZE * i - levelOffset;
				int y = Game.TILES_SIZE * j;
                
                // drawing castle cause it is special lol
                if(index == 11) {
                    g.drawImage(levelSprite[index], x, y, levelSprite[index].getWidth() * 2, levelSprite[index].getHeight() * 2, null);
                } else {
                    // drawing other bricks and stuff
			        g.drawImage(levelSprite[index], x, y, Game.TILES_SIZE, Game.TILES_SIZE, null);
                }                
			}

    }

    public Level getCurrentLevel() {
        return level;
    }

    public void clearLevel(){
        level = null;
    }
    
}


