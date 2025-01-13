package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import game.Game;

public abstract class Entity {

    // animation
    protected int aniTick, aniIndex;
    protected BufferedImage[][] animation;
    protected int state;

    protected float x, y;
    protected int width, height;
    protected Rectangle2D.Float hitbox;

    protected float airSpeed;
    protected float walkSpeed;
    
    protected float pushDrawOffset = 0;

    protected Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected void initHitbox(float width, float height) {
        hitbox = new Rectangle2D.Float(x, y, (int) (width * Game.SCALE), (int) (height * Game.SCALE));
    }  

    protected void drawHitbox(Graphics g, int xLevelOffset) {
        // For debugging the hitbox
		g.setColor(Color.PINK);
		g.drawRect((int) hitbox.x - xLevelOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    protected void newState(int state){
        this.state = state;
        aniIndex = 0;
        aniTick = 0;
    }

    public int getState() {
		return state;
	}

    public int getAniIndex() {
		return aniIndex;
	}

    public Rectangle2D.Float getHitbox(){
        return hitbox;
    }

    

   
}
