package entities;

import static game.Game.SCALE;
import static utilz.Constants.EnemyConstants.HIT;
import static utilz.Constants.EnemyConstants.IDLE;
import static utilz.Constants.EnemyConstants.KOOPA;
import static utilz.Constants.EnemyConstants.KOOPA_HEIGHT;
import static utilz.Constants.EnemyConstants.KOOPA_WIDTH;
import static utilz.Constants.EnemyConstants.RUNNING;
import static utilz.HelperMethods.IsFloor;
import static audio.AudioManager.STOMP;

import screens.Playing;

public class Koopa extends Enemy{

    public Koopa(float x, float y) {
        super(x, y, KOOPA_WIDTH, KOOPA_HEIGHT, KOOPA);
        initHitbox(width - (int)(2 * SCALE), height - (int)(2 * SCALE));
    }

    public void update(int[][] lvlData, Playing playing) {
        updateBehaviour(lvlData, playing);
        updateAnimationTick();
    }

    private void updateBehaviour(int[][] lvlData, Playing playing) {
        if (firstUpdate)
			firstUpdateCheck(lvlData);

		if (inAir) 
			inAirChecks(lvlData, playing);
        else {
            switch (state) {
			case IDLE:
				if (IsFloor(hitbox, lvlData))
					newState(RUNNING);
				else 
                    inAir = true;
				break;
            case RUNNING:
                    checkIfHitPlayer(playing);
                    move(lvlData);
                break;

            case HIT:
                playing.addScore(100);
                playing.getGame().getAudioManager().playEffect(STOMP);
                // dies
            break;
            }
        }
    }

    
    
}
