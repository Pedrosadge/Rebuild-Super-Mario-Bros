package objects;

import static game.Game.TILES_SIZE;

public class Coin extends GameObject{

    private int maxHeight;

    public Coin(int x, int y) {
        super(x, y - TILES_SIZE);
        
        initHitbox(28, 28);

        isBreakable = false;
        isMoveable = false;
        isShown = true;
        isActive = true;

        maxHeight = y - (TILES_SIZE * 3);
    }
    

    public void update() {
        if(hitbox.y+hitbox.height >= maxHeight){
            hitbox.y -= 1.5;
        }
        else {
            changeState(false, false, false, false);
        }
    }


    
}
