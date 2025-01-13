

package entities;

import screens.Playing;
import utilz.ImageLoader;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import audio.AudioManager;
import game.Game;
import objects.Fireball;

import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.*;
import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.Directions.RIGHT;
import static game.Game.SCALE;
import static utilz.HelperMethods.*;

public class Player extends Entity {

    // level data
    int[][] levelData;

    // movement
    private boolean moving, left, right, jumping, inAir;
    private int healths;

    // Jumping / Gravity
	private float jumpSpeed = -2.75f * Game.SCALE;
	private float fallSpeedAfterCollision = 0.5f * Game.SCALE;

    // collision
    private float xDrawOffset = 2 * Game.SCALE;
	private float yDrawOffset = 4 * Game.SCALE;

    // screens object
    private Playing playing;

    // directions
    int flipX = 0;
    int flipW = 1;

	private Rectangle2D.Float headbox;
	
	private boolean isFiredUp = false;

	private BufferedImage[][] fireMarioAnimation;

	private int playerAnimationState = NORMAL;


	public Player(int x, int y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;

        this.healths = playing.getPlayerLives();
        this.walkSpeed = 1.6f * SCALE;

        state = IDLE;

        loadAnimation();
        initHitbox(28, 28);
		initHeadbox(14, 2);		
    }

     public void render(Graphics g, int lvlOffset, BufferedImage[][] animation) {
        g.drawImage(animation[state][aniIndex], (int) (hitbox.x - xDrawOffset) - lvlOffset + flipX, (int) (hitbox.y - yDrawOffset), width * flipW, height, null);
        // drawHitbox(g, lvlOffset);
		// drawHeadbox(g, lvlOffset);
    }

	protected void drawHeadbox(Graphics g, int xLevelOffset) {
        // For debugging the hitbox
		g.setColor(Color.PINK);
		g.drawRect((int) headbox.x - xLevelOffset, (int) headbox.y, (int) headbox.width, (int) headbox.height);
    }

    public void update() {
		
		if (healths <= 0) {
			if (state != DEAD) {
				state = DEAD;
				aniTick = 0;
				aniIndex = 0;
				playing.setPlayerDying(true);
				
				// Check if player died in air
				if (!IsEntityOnFloor(hitbox, levelData)) {
					inAir = true;
					airSpeed = 0;
				}
			} else if (aniIndex == GetSpriteAmount(DEAD) - 1 && aniTick >= ANI_SPEED - 1) {
				playing.setGameOver(true);
				
			} else {
				updateAnimationTick();

				// Fall if in air
				if (inAir)
					if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, levelData)) {
						hitbox.y += airSpeed;
						headbox.y = hitbox.y;
						airSpeed += GRAVITY;
					} else
						inAir = false;

			}

			return;
		}

		if (state == HIT) {
			if (aniIndex <= GetSpriteAmount(state)) {
                // dies
            }
		} else
			updatePos();

		if (moving) {
			// checkPotionTouched();
			// checkSpikesTouched();
			// checkInsideWater();
        }

		updateAnimationTick();
		setAnimation();
    }

    private void loadAnimation() {
        BufferedImage marioSprite = ImageLoader.loadImage(ImageLoader.MARIO_SPRITE);
        animation = new BufferedImage[6][3];
        
        // load idle animation 1 sprite
        animation[IDLE][0] = marioSprite.getSubimage(80, 32, 16, 16);

        // load running animation 3 sprites
        for(int i = 0; i < GetSpriteAmount(RUNNING); i++)
            animation[RUNNING][i] = marioSprite.getSubimage(96 + (i*16), 32, 16, 16);

        // load jumping / falling animation 1 sprite
        animation[JUMPING][0] = marioSprite.getSubimage(160, 32, 16, 16);
        animation[FALLING][0] = marioSprite.getSubimage(160, 32, 16, 16);


        // load hit / dead animation 1 sprite
        animation[HIT][0] = marioSprite.getSubimage(176, 32, 16, 16);
        animation[DEAD][0] = marioSprite.getSubimage(176, 32, 16, 16);

        
        // load powering up animation?
		loadFireMarioAnimation(marioSprite);
    }


	private void loadFireMarioAnimation(BufferedImage marioSprite) {
		fireMarioAnimation = new BufferedImage[6][3];

		// load idle animation 1 sprite
        fireMarioAnimation[IDLE][0] = marioSprite.getSubimage(80, 128, 16, 16);

        // load running animation 3 sprites
        for(int i = 0; i < GetSpriteAmount(RUNNING); i++)
            fireMarioAnimation[RUNNING][i] = marioSprite.getSubimage(96 + (i*16), 128, 16, 16);

        // load jumping / falling animation 1 sprite
        fireMarioAnimation[JUMPING][0] = marioSprite.getSubimage(160, 128, 16, 16);
        fireMarioAnimation[FALLING][0] = marioSprite.getSubimage(160, 128, 16, 16);

        // load hit / dead animation 1 sprite
        fireMarioAnimation[HIT][0] = marioSprite.getSubimage(176, 128, 16, 16);
        fireMarioAnimation[DEAD][0] = marioSprite.getSubimage(176, 128, 16, 16);
    }

	public void loadLevelData(int[][] levelData) {
		this.levelData = levelData;
		// if (!IsEntityOnFloor(hitbox, levelData))
		// 	inAir = true;
	}

    

    private void updatePos() {
        moving = false;

		if (jumping)
			jump();


		float xSpeed = 0;

		if (left && !right) {
			xSpeed -= walkSpeed;
			flipX = width;
			flipW = -1;
		}
		if (right && !left) {
			xSpeed += walkSpeed;
			flipX = 0;
			flipW = 1;
		}

		if (!inAir)
			if (!IsEntityOnFloor(hitbox, levelData))
				inAir = true;

		if (inAir) {
			if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, levelData)) {
				hitbox.y += airSpeed;
				headbox.y = hitbox.y;
				airSpeed += GRAVITY;
				updateXPos(xSpeed);
			} else {
				hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
				headbox.y = hitbox.y - 2;
				if (airSpeed > 0)
					resetInAir();
				else
					airSpeed = fallSpeedAfterCollision;
				updateXPos(xSpeed);
			}

		} else 
			updateXPos(xSpeed);
		
		if(left || right)
			moving = true;
    }

    private void resetInAir() {
		inAir = false;
		airSpeed = 0;
	}

    private void updateXPos(float xSpeed) {
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData)) {
			hitbox.x += xSpeed;
			headbox.x = hitbox.x + width/4;
		}
			
		else {
			float speed = GetEntityXPosNextToWall(hitbox, xSpeed);
			hitbox.x = speed;
			headbox.x = hitbox.x + (width/4);
		}
    }

    private void updateAnimationTick() {
        aniTick++;
        if(aniTick >= ANI_SPEED) {
            aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(state)) {
				aniIndex = 0;
				if (state == HIT) {
					newState(IDLE);
					airSpeed = 0f;
				}
			}
        }
    }

    private void setAnimation() {
        int startAni = state;

		if (state == HIT)
			return;

		if (moving)
			state = RUNNING;
		else
			state = IDLE;

		if (inAir) {
			if (airSpeed < 0)
				state = JUMPING;
			else
				state = FALLING;
		}
		if (startAni != state)
			resetAniTick();
    }

    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    private void jump() {
        if (inAir)
			return;
		playing.getGame().getAudioManager().playEffect(AudioManager.JUMP);
		inAir = true;
		airSpeed = jumpSpeed;
    }

	protected void initHeadbox(float width, float height) {
        headbox = new Rectangle2D.Float(x + (width/2), y, (int) (width * Game.SCALE), (int) (height * Game.SCALE));
    }  


	public BufferedImage[][] getAnimation(){
		switch (playerAnimationState) {
			case FIRE:
				return fireMarioAnimation;
			default:
				return animation;
		}
	}

    public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}

    public void resetDirBooleans(){
        left = false;
        right = false;
        jumping = false;
    }

    public void setMoving(boolean b) {
        this.moving = b;
    }
    
	public Rectangle2D.Float getHeadbox(){
        return headbox;
    }

	public boolean isFiredUp() {
		return isFiredUp;
	}

	public void setFiredUp(boolean isFiredUp) {
		this.isFiredUp = isFiredUp;
	}

	public int getPlayerAnimationState() {
		return playerAnimationState;
	}

	public void setPlayerAnimationState(int playerAnimationState) {
		this.playerAnimationState = playerAnimationState;
	}

	public void shootFireball() {
		if (isFiredUp) {
			int direction;
			if (flipW == -1) 
				direction = LEFT;
			else 
				direction = RIGHT;
			playing.getObjectManager().getFireballs().add(new Fireball((int)hitbox.x,(int) (hitbox.y + hitbox.width/4), direction));
			playing.getGame().getAudioManager().playEffect(AudioManager.FIREBALL);
		}
		
	}

    public void setSpawn(Point playerSpawn) {
		this.x = playerSpawn.x;
		this.y = playerSpawn.y;
		hitbox.x = x;
		hitbox.y = y;
    }

	public void setHealths(int healt){
		this.healths = healt;
	}

}
