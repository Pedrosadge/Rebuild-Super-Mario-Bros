package objects;

import static game.Game.SCALE;
import static game.Game.TILES_SIZE;
import static utilz.Constants.ANI_SPEED;
import static utilz.Constants.GRAVITY;
import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.Directions.RIGHT;
import static utilz.Constants.PlayerConstants.FIRE;
import static utilz.Constants.PlayerConstants.HIT;
import static utilz.HelperMethods.CanMoveHere;
import static utilz.HelperMethods.GetEntityYPosUnderRoofOrAboveFloor;
import static utilz.HelperMethods.IsEntityOnFloor;

import static audio.AudioManager.BIGMUSHROOM;
import static audio.AudioManager.ONEUP;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import game.Game;
import screens.Playing;

public class GameObject {
    protected int x, y;
	protected Rectangle2D.Float hitbox;
	protected boolean isBreakable, isMoveable, isShown, isActive;
	protected int aniTick, aniIndex;
	protected int xDrawOffset, yDrawOffset;

    protected int direction = RIGHT;
    protected float walkSpeed, airSpeed;
    protected boolean inAir;
    protected int tileY;

	protected boolean firstUpdate = true;

    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
        walkSpeed = 0.40f * SCALE;
    }

    protected void initHitbox(int width, int height) {
		hitbox = new Rectangle2D.Float(x, y, (int) (width * Game.SCALE), (int) (height * Game.SCALE));
	}

    protected boolean isActive() {
        return isActive;
    }

    protected void drawHitbox(Graphics g, int xLevelOffset) {
        // For debugging the hitbox
		g.setColor(Color.PINK);
		g.drawRect((int) hitbox.x - xLevelOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    protected void changeState(boolean isBreakable, boolean isMoveable, boolean isShown, boolean isActive){
        this.isBreakable = isBreakable;
        this.isMoveable = isMoveable;
        this.isShown = isShown;
        this.isActive = isActive;
    }

    protected void move(int[][] lvlData) {
		float xSpeed = 0;

		if (direction == LEFT)
			xSpeed = -walkSpeed;
		else
			xSpeed = walkSpeed;

		// if left or right is free, move
		if(canMoveRightOrLeft(lvlData)) {
			hitbox.x += xSpeed;
			return;
		}
		
		
		changeDirection();
        
	}

    private boolean canMoveRightOrLeft(int[][] lvlData) {
		int xPos = (int) hitbox.x / TILES_SIZE;
		int yPos = (int) hitbox.y / TILES_SIZE;
		if (direction == RIGHT && lvlData[yPos][xPos + 1] == 4) {
			return true;
		} else if (direction == LEFT && lvlData[yPos][xPos - 1] == 4) {
			return true;
		}

		return false;
	}

	protected void changeDirection() {
		if (direction == LEFT)
			direction = RIGHT;
		else
			direction = LEFT;
	}

    protected void falling(int[][] lvlData) {
		if(IsEntityOnFloor(hitbox, lvlData)){
			inAir = false;
			airSpeed = 0;
		}
		airSpeed += GRAVITY;
		hitbox.y += airSpeed;
	}

    protected void resetBooleans() {
        isBreakable = false;
        isMoveable = false;
        isShown = false;
        isActive = false;
        inAir = false;
    }

	protected void firstUpdateCheck(int[][] lvlData) {
		if (!IsEntityOnFloor(hitbox, lvlData))
			inAir = true;
	}

	protected void updateInAir(int[][] lvlData) {
		
		// falling
		if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
			hitbox.y += airSpeed;
			airSpeed += GRAVITY;	
			
		} else {
			
			// in the floor
			inAir = false;

			hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
			
			tileY = (int) (hitbox.y / Game.TILES_SIZE);

		}
	}

	protected void checkIfHitPlayer(Playing playing, String type) {
		boolean isTouchingPlayer = playing.getPlayer().getHitbox().intersects(this.hitbox);
		
        if (isTouchingPlayer) {
			// mario power up
			switch (type) {
				case "bigMushroom":
					playing.getGame().getAudioManager().playEffect(BIGMUSHROOM);
					playing.addScore(1000);
					// increase score by 1000
					break;
				case "oneUpMushroom":
					playing.getGame().getAudioManager().playEffect(ONEUP);
					// increase live by 1
					playing.addLive(1);
					// increse score by 100
					playing.addScore(100);
					break;
				case "fireFlower":
					playing.getGame().getAudioManager().playEffect(BIGMUSHROOM);
					playing.getPlayer().setPlayerAnimationState(FIRE);
					playing.getPlayer().setFiredUp(true);
					playing.addScore(100);
					break;
			}

			// mushroom/flower gone
			changeState(false, false, false, false);
		}
	}

	public int getAniIndex() {
		return aniIndex;
	}

	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}

	public int flipX() {
		if (direction == RIGHT)
			return (int) hitbox.width;
		else
			return 0;
	}

	public int flipW() {
		if (direction == RIGHT)
			return -1;
		else
			return 1;
	}

	public static int getSpriteAmount(int state){
		switch (state) {
		 case HIT:
			 return 3;
		
		 default:
			 return 4;
		}
	 }

	protected void updateAnimationTick(int state) {
		aniTick++;
		if (aniTick >= ANI_SPEED) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= getSpriteAmount(state)) {
				aniIndex = 0;

				switch (state) {
					case HIT:
						
						break;
					default:
						break;
				}
			}
		}
	}
}
