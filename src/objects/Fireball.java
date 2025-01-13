package objects;

import static utilz.Constants.Directions.RIGHT;
import static utilz.HelperMethods.CanMoveHere;



public class Fireball extends GameObject{
    // fireball state
    public static final int FLYING = 0;
    public static final int HIT = 1;
    
    private int currentState;

    public Fireball(int x, int y, int direction) {
        super(x, y);
        
        initHitbox(16, 16);

        isBreakable = false;
        isMoveable = true;
        isShown = true;
        isActive = true;
        currentState = FLYING;
        this.direction = direction;
    }

    public void update(int levelData[][]) {
        updateAnimationTick(currentState);
        if (!CanMoveHere(hitbox.x, hitbox.y, hitbox.width, hitbox.height, levelData)) {

            currentState = HIT;
            if(aniIndex == 2) changeState(false, false, false, false);
            return;
        }
        if (direction == RIGHT) {
            hitbox.x += 2;
        } else {
            hitbox.x -= 2;
        }
        // move to left or right until explodes
    }

    public int getState() {
        return currentState;
    }

    
   

   
}
