package objects;

import static game.Game.TILES_SIZE;
import static utilz.HelperMethods.IsFloor;

import java.awt.Graphics;

import screens.Playing;

import static game.Game.SCALE;

public class OneUpMushroom extends GameObject{
    public OneUpMushroom(int x, int y) {
        super(x, y);
        initHitbox((int) (TILES_SIZE * SCALE) - 2, (int) (TILES_SIZE * SCALE) - 2);

        isBreakable = false;
        isMoveable = true;
        isShown = true;
        isActive = true;
    }

    public void update(int levelData[][], Playing playing) {
        if (firstUpdate)
			firstUpdateCheck(levelData);

		if (inAir) 
			updateInAir(levelData); 
        else {
            if (IsFloor(hitbox, levelData)) {
                move(levelData);
                checkIfHitPlayer(playing, "oneUpMushroom");
            }
            else
                inAir = true;      
        }        
    }

    public void draw(Graphics g, int xLvlOffset) {
        
        drawHitbox(g, xLvlOffset);
    }

   
}
