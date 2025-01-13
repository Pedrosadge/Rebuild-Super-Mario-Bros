package objects;

import static game.Game.SCALE;
import static game.Game.TILES_SIZE;
import static utilz.HelperMethods.IsFloor;

import java.awt.Graphics;

import screens.Playing;

public class BigMushroom extends GameObject{
    public BigMushroom(int x, int y) {
        super(x, y);
        
        initHitbox((int) (TILES_SIZE * SCALE) - 2, (int) (TILES_SIZE * SCALE) - 2);

        isBreakable = false;
        isMoveable = true;
        isShown = true;
        isActive = true;
        inAir = true;
    }

    public void update(int levelData[][], Playing playing) {
        
        if (firstUpdate)
			firstUpdateCheck(levelData);

		if (inAir) 
			updateInAir(levelData); 
        else {
            if (!IsFloor(hitbox, levelData)) {
                inAir = true; 
            }
            else {
                move(levelData);
                checkIfHitPlayer(playing, "bigMushroom");
            }
                     
        }        
    }

    public void draw(Graphics g, int xLvlOffset) {
        
        drawHitbox(g, xLvlOffset);
    }
}
