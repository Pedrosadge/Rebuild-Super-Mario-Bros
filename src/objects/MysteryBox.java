package objects;

import static game.Game.SCALE;
import static game.Game.TILES_SIZE;



public class MysteryBox extends GameObject {


    public MysteryBox(int x, int y, int levelData[][]) {
        super(x, y);
        
        initHitbox((int) (TILES_SIZE * SCALE), (int) (TILES_SIZE * SCALE));

        isBreakable = true;
        isMoveable = false;
        isShown = true;
        isActive = true;
    }
    
}
