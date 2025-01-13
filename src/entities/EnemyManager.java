package entities;

import java.awt.Graphics;

import java.awt.image.BufferedImage;

import screens.Playing;
import levels.Level;
import utilz.ImageLoader;
import static utilz.Constants.EnemyConstants.*;

public class EnemyManager {

	private Playing playing;
	private BufferedImage[][] goombaArr, koopaArr;
	private Level currentLevel;

	public EnemyManager(Playing playing) {
		this.playing = playing;
		loadEnemyImgs();
	}

	public void loadEnemies(Level level) {
		this.currentLevel = level;
		
	}

	public void update(int[][] lvlData) {
	
		for (Goomba g : currentLevel.getGoombas())
			if (g.isActive()) 
				g.update(lvlData, playing);
				
		for (Koopa k : currentLevel.getKoopas())
			if (k.isActive()) 
				k.update(lvlData, playing);
	}

	public void draw(Graphics g, int xLvlOffset) {
		drawGoombas(g, xLvlOffset);
		drawkoopas(g, xLvlOffset);
	}

    private void drawGoombas(Graphics gr, int xLvlOffset) {
		for (Goomba g : currentLevel.getGoombas())
			if (g.isActive()) {
				gr.drawImage(goombaArr[g.getState()][g.getAniIndex()], (int) g.getHitbox().x - xLvlOffset - GOOMBA_DRAWOFFSET_X + g.flipX(),
						(int) g.getHitbox().y - GOOMBA_DRAWOFFSET_Y , GOOMBA_WIDTH * g.flipW(), GOOMBA_HEIGHT, null);

				// g.drawHitbox(gr, xLvlOffset);
			}
	}

	private void drawkoopas(Graphics g, int xLvlOffset) {
		for (Koopa k : currentLevel.getKoopas())
			if (k.isActive()) {
				g.drawImage(koopaArr[k.getState()][k.getAniIndex()], (int) k.getHitbox().x - xLvlOffset - KOOPA_DRAWOFFSET_X + k.flipX(),
						(int) k.getHitbox().y - KOOPA_DRAWOFFSET_Y , KOOPA_WIDTH * k.flipW(), KOOPA_HEIGHT, null);
				// k.drawHitbox(g, xLvlOffset);
			}
	}

	

	private void loadEnemyImgs() {

		goombaArr = getImgArr(ImageLoader.loadImage(ImageLoader.GOOMBA_SPRITE), 2, 4, 16, 16);
		koopaArr = getImgArr(ImageLoader.loadImage(ImageLoader.KOOPA_SPRITE), 2, 4, 16, 24);
    }

	private BufferedImage[][] getImgArr(BufferedImage atlas, int xSize, int ySize, int spriteW, int spriteH) {
		BufferedImage[][] tempArr = new BufferedImage[ySize][xSize];
		
		tempArr[0][0] = atlas.getSubimage(0 * spriteW, 0 * spriteH, spriteW, spriteH);
		tempArr[0][1] = atlas.getSubimage(1 * spriteW, 0 * spriteH, spriteW, spriteH);
        tempArr[1][0] = atlas.getSubimage(0 * spriteW, 0 * spriteH, spriteW, spriteH);
        tempArr[1][1] = atlas.getSubimage(1 * spriteW, 0 * spriteH, spriteW, spriteH);
        tempArr[2][0] = atlas.getSubimage(2 * spriteW, 0 * spriteH, spriteW, spriteH);
        tempArr[2][1] = atlas.getSubimage(2 * spriteW, 0 * spriteH, spriteW, spriteH);
		tempArr[3][0] = atlas.getSubimage(2 * spriteW, 0 * spriteH, spriteW, spriteH);
        tempArr[3][1] = atlas.getSubimage(2 * spriteW, 0 * spriteH, spriteW, spriteH);
        
		return tempArr;
	}

	public void resetAllEnemies() {
		for (Goomba g : currentLevel.getGoombas())
			g.resetEnemy();
		for (Koopa k : currentLevel.getKoopas())
			k.resetEnemy();
	}
    
}
