package objects;



import static game.Game.SCALE;
import static game.Game.TILES_SIZE;

public class EndCastle extends GameObject{
    public EndCastle(int x, int y) {
        super(x, y);
        

        initHitbox((int) (TILES_SIZE * SCALE), (int) (TILES_SIZE * SCALE));


        isBreakable = false;
        isMoveable = false;
        isShown = false;
        isActive = true;
    }

   
}
