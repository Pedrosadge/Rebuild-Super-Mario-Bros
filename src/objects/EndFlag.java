package objects;

import static game.Game.SCALE;
import static game.Game.TILES_SIZE;


public class EndFlag extends GameObject{

    // the maximum amount the flag can travel downwards
    int target;

    public EndFlag(int x, int y) {
        super(x, y);

        initHitbox((int) (TILES_SIZE * SCALE), (int) (TILES_SIZE * SCALE));
        
        isBreakable = false;
        isMoveable = true;
        isShown = true;
        isActive = true;

        target = (int)hitbox.y + (TILES_SIZE*8);
    }

    public void update() {
        if(hitbox.y < target){
            hitbox.y += 2;
        }
    }

   


}
