package entities;

import static utilz.Constants.EnemyConstants.*;
import static utilz.HelperMethods.*;

import java.awt.geom.Rectangle2D;

import screens.Playing;

import static utilz.Constants.Directions.*;
import static utilz.Constants.*;

import game.Game;

public abstract class Enemy extends Entity {
	protected int enemyType;
	protected boolean firstUpdate = true;
	protected int walkDir = LEFT;
	protected int tileY;
	protected boolean active = true;

    protected boolean inAir;

	public Enemy(float x, float y, int width, int height, int enemyType) {
		super(x, y, width, height);
		this.enemyType = enemyType;

		walkSpeed = Game.SCALE * 0.35f;
	}


	protected void firstUpdateCheck(int[][] lvlData) {
		if (!IsEntityOnFloor(hitbox, lvlData))
			inAir = true;
		firstUpdate = false;
	}

	protected void inAirChecks(int[][] lvlData, Playing playing) {
		if (state != HIT && state != DEAD) 
			updateInAir(lvlData);
		
	}

	protected void updateInAir(int[][] lvlData) {
		
		// falling
		if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
			hitbox.y += airSpeed;
			airSpeed += GRAVITY;	
			
		} else {
			// in the floor
			inAir = false;

			// bermasalah di sini
			/* when koopa hit da floor
			 * his hitbox.y is changed to higher than his actual current height
			 * he basically teleports above his last position
			 * 
			 * solution: koopa height is now 32 px lol
			 */
			hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
			
			tileY = (int) (hitbox.y / Game.TILES_SIZE);
		}
	}

	

	protected void move(int[][] lvlData) {
		float xSpeed = 0;

		if (walkDir == LEFT)
			xSpeed = -walkSpeed;
		else
			xSpeed = walkSpeed;

		if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData))
			if (IsFloor(hitbox, xSpeed, lvlData)) {
				hitbox.x += xSpeed;
				return;
			}

		changeWalkDir();
	}


	public void hurt() {
		newState(DEAD);
	}


	protected void updateAnimationTick() {
		aniTick++;
		if (aniTick >= ANI_SPEED) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(enemyType, state)) {
				if (enemyType == GOOMBA || enemyType == KOOPA) {
					aniIndex = 0;

					switch (state) {
					case HIT -> state = DEAD;
					case DEAD -> active = false;
					}
				}
			}
		}
	}

	protected void checkIfHitPlayer(Playing playing) {
		boolean isTouchingPlayer = playing.getPlayer().getHitbox().intersects(this.hitbox);
		Rectangle2D.Float playerHitbox = playing.getPlayer().getHitbox();
        if (isTouchingPlayer) {
			if (playerHitbox.y + playerHitbox.height <= hitbox.y + hitbox.height/3) {
				newState(HIT);	
			}
			else  {
				// mario dies
				playing.playerDie();
			}
		}
		
	}

	protected void changeWalkDir() {
		if (walkDir == LEFT)
			walkDir = RIGHT;
		else
			walkDir = LEFT;
	}

	public void resetEnemy() {
		hitbox.x = x;
		hitbox.y = y;
		firstUpdate = true;
		newState(IDLE);
		active = true;
		airSpeed = 0;
	}

	public int flipX() {
		if (walkDir == RIGHT)
			return width;
		else
			return 0;
	}

	public int flipW() {
		if (walkDir == RIGHT)
			return -1;
		else
			return 1;
	}

	public boolean isActive() {
		return active;
	}

	public void setState(int state){
		this.state = state;
	}

}