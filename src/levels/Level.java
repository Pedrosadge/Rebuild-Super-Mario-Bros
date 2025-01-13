package levels;

import static game.Game.TILES_SIZE;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import game.Game;
import objects.MysteryBox;

import java.util.ArrayList;
import entities.Goomba;
import entities.Koopa;

public class Level {

    private BufferedImage img;
	private int[][] levelData;

    private int lvlTilesWide;
	private int maxTilesOffset;
	private int maxLvlOffsetX;

    
    private ArrayList<Goomba> goombas = new ArrayList<>();
	private ArrayList<Koopa> koopas = new ArrayList<>();

    private ArrayList<MysteryBox> mysteryBoxes = new ArrayList<>();

    private Point playerSpawn;

    private int[] endFlagPosition, endCastlePosition;

    public Level(BufferedImage img) {
        this.img = img;
        levelData = new int[img.getHeight()][img.getWidth()];
        loadLevel();
        calcLvlOffsets();
    }

    

    private void loadLevel() {
        for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				Color c = new Color(img.getRGB(x, y));
				int red = c.getRed();
				int green = c.getGreen();
				int blue = c.getBlue();

				loadLevelData(red, x, y);
				loadEntities(green, x, y);
                loadGameObjects(blue, x, y);
				
			}
        }
    }

    private void loadGameObjects(int blue, int x, int y) {
        switch (blue) {
            case 24:
                playerSpawn = new Point(x * Game.TILES_SIZE, y * Game.TILES_SIZE);
                break;
            // mystery block
            case 48:
                mysteryBoxes.add(new MysteryBox(x * TILES_SIZE, y * TILES_SIZE, levelData));
                break;
            case 99:
                endFlagPosition = new int[2];
                endFlagPosition[0] = x;
                endFlagPosition[1] = y;
                break;
            case 199:
                endCastlePosition = new int[2];
                endCastlePosition[0] = x;
                endCastlePosition[1] = y;
                
            default:
                break;
        }
    }

    private void loadEntities(int green, int x, int y) {
        switch (green) {
            case 10 -> goombas.add(new Goomba(x * Game.TILES_SIZE, y * Game.TILES_SIZE));
            case 20 -> koopas.add(new Koopa(x * Game.TILES_SIZE, y * Game.TILES_SIZE));
            
            }
    }

    private void loadLevelData(int red, int x, int y) {

        /*
        * RGB to brick
        * red 120 = Base bricks
        * red 200 = building brick
        * red 240 = breakable brick
        * red 1 = transparent brick
        * red 10 = top left pipe
        * red 20 = top right pipe
        * red 30 = bottom left pipe
        * red 40 = bottom right pipe
        * red 60 = end game flag top part 
        * red 70 = end game flag pole
        * red 80 = castle top left
        */
        
        switch (red) {
            case 120:
                levelData[y][x] = 0;
                break;
            case 200:
                levelData[y][x] = 1;
                break;
            case 240:
                levelData[y][x] = 2;
                break;
            case 255:
                levelData[y][x] = 3;
                break;
            case 1:
                levelData[y][x] = 4;
                break;
            case 10:
                levelData[y][x] = 5;
                break;
            case 20:
                levelData[y][x] = 6;
                break;
            case 30:
                levelData[y][x] = 7;
                break;
            case 40:
                levelData[y][x] = 8;
                break;
            case 60:
                levelData[y][x] = 9;
                break;
            case 70:
                levelData[y][x] = 10;
                break;
            case 80:
                levelData[y][x] = 11;
                break;
            default:
                break;
        }
    }

    public int[][] getLevelData(){
        return this.levelData;
    }

    public int getSpriteIndex(int x, int y) {
		return levelData[y][x];
	}

    private void calcLvlOffsets() {
		lvlTilesWide = img.getWidth();
		maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
		maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
	}

    public int getLvlOffset() {
        return maxLvlOffsetX;
    }

    public ArrayList<Goomba> getGoombas(){
        return goombas;
    }

    public ArrayList<Koopa> getKoopas(){
        return koopas;
    }

    public Point getPlayerSpawn() {
		return playerSpawn;
	}

    public ArrayList<MysteryBox> getMysteryBoxes() {
        return mysteryBoxes;
    }

    public int[] getEndFlagPosition(){
        return this.endFlagPosition;
    }



    public int[] getEndCastlePosition() {
        return endCastlePosition;
    }

   

    
}
