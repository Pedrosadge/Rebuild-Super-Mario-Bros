package objects;

import static game.Game.SCALE;
import static game.Game.TILES_SIZE;

import static utilz.Constants.EnemyConstants.HIT;
import static audio.AudioManager.COIN;

import java.awt.Graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import entities.Goomba;
import entities.Koopa;
import entities.Player;
import levels.Level;
import screens.Playing;
import utilz.ImageLoader;

public class ObjectManager {
    private Playing playing;
	private BufferedImage endFlagImg;
    private BufferedImage fireFlowerImg, oneUpMushroomImg, bigMushroomImg;
    private BufferedImage coinImg;
    private BufferedImage[][] fireballImgs;

	private ArrayList<FireFlower> fireFlowers = new ArrayList<>();
    private ArrayList<BigMushroom> bigMushrooms = new ArrayList<>();
	private ArrayList<Fireball> fireballs = new ArrayList<>();
    private ArrayList<OneUpMushroom> oneUpMushrooms = new ArrayList<>();
    private ArrayList<Coin> coins = new ArrayList<>();
    private EndCastle endCastleObj;
    private EndFlag endFlag;
    
    private Level currentLevel;

    private int yOffset;

    public ObjectManager(Playing playing){
        this.playing = playing;

        loadImages();
    }

    public void initEndFlagAndEndCastle(){
        int endFlagPos[] = currentLevel.getEndFlagPosition();
        endFlag = new EndFlag(endFlagPos[0] * TILES_SIZE, endFlagPos[1] * TILES_SIZE);

        int endCastlePos[] = currentLevel.getEndCastlePosition();
        endCastleObj = new EndCastle(endCastlePos[0] * TILES_SIZE, endCastlePos[1] * TILES_SIZE);
    }

    private void loadImages() {
        BufferedImage img = ImageLoader.loadImage(ImageLoader.COIN_SPRITE);
        coinImg = img.getSubimage(0, 0, 16, 16);

        img = ImageLoader.loadImage(ImageLoader.FLAG_SPRITE);
        endFlagImg = img.getSubimage(32, 0, 16, 16);

        img = ImageLoader.loadImage(ImageLoader.FlOWER_AND_MUSHROOMS);
        fireFlowerImg = img.getSubimage(0, 0, 16, 16);

        bigMushroomImg = img.getSubimage(16, 0, 16, 16);

        oneUpMushroomImg = img.getSubimage(32, 0, 16, 16);

        img = ImageLoader.loadImage(ImageLoader.FIREBALL_SPRITE);
        fireballImgs = new BufferedImage[2][4];
        for (int i = 0; i < fireballImgs.length; i++) {
            for (int j = 0; j < fireballImgs[0].length; j++) {
                if(i == 0)
                    fireballImgs[i][j] = img.getSubimage(8*j, 0, 8, 8);
                else
                    fireballImgs[i][j] = img.getSubimage(16*j, 0, 16, 16);
            }
        }

        img = ImageLoader.loadImage(ImageLoader.FLAG_SPRITE);
        endFlagImg = img.getSubimage(32, 0, 16, 16);
    }

    public void update(int[][] lvlData, Player player) {
        updateMysteryBoxes();
        updateBigMushrooms();
        updateOneUpMushrooms();
        updateFireFlowers();
        updateFireballs();
        updateCoins();
        updateEndFlag();
        updateEndCastle();
    }

    private void updateEndCastle() {
        if(checkIfPlayerTouchEndCastle()){
            playing.win();
        }
    }

   

 private void updateEndFlag() {
        if(checkIfPlayerTouchEndFlag()) {
            endFlag.update();
        }
    }

    private void updateCoins() {
        for(Coin c : coins) {
            if(c.isActive) {
                c.update();
            }
        }
    }

    private void updateFireballs() {
        for (Fireball fb : fireballs) {
            if (fb.isActive) {
                fb.update(currentLevel.getLevelData());

                for(Goomba g : currentLevel.getGoombas()) 
                    if (g.isActive()) 
                        if (fb.hitbox.intersects(g.getHitbox())) {
                            fb.changeState(false, false, false, false);
                            g.setState(HIT);
                            break;
                        }
                
                for (Koopa k : currentLevel.getKoopas()) 
                    if(k.isActive())
                        if (fb.hitbox.intersects(k.getHitbox())) {
                            fb.changeState(false, false, false, false);
                            k.setState(HIT);
                            break;
                        }            
            }
        }
    }

    private void updateMysteryBoxes(){
        for(MysteryBox mb : currentLevel.getMysteryBoxes()){
            if (mb.isActive) {
                if (checkIfHeadedByPlayer(playing.getPlayer(), mb)) {
                    spawnItem(mb);
                    turnMysteryBoxIntoBuildingBrick(mb.x, mb.y);
                    mb.changeState(false, false, false, false);
                }
                
            }
        }
    }

    private void updateBigMushrooms() {
        for (BigMushroom bm : bigMushrooms) {
            if (bm.isActive) {
                bm.update(currentLevel.getLevelData(), playing);
            }
        }
    }

    private void updateOneUpMushrooms() {
        for (OneUpMushroom oum : oneUpMushrooms) {
            if (oum.isActive) {
                oum.update(currentLevel.getLevelData(), playing);
            }
        }
    }

    private void updateFireFlowers() {
        for (FireFlower ff : fireFlowers) {
            if (ff.isActive) {
                ff.update(currentLevel.getLevelData(), playing);
            }
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawBigMushroom(g, xLvlOffset);
        drawOneUpMushroom(g, xLvlOffset);
        drawFireFlowers(g, xLvlOffset);
        drawFireballs(g, xLvlOffset);
        drawCoins(g, xLvlOffset);
        drawEndFlag(g ,xLvlOffset);
	}

    private void drawEndFlag(Graphics g, int xLvlOffset) {
        g.drawImage(endFlagImg, (int) endFlag.hitbox.x - xLvlOffset - 16, (int) endFlag.hitbox.y, (int)(TILES_SIZE*SCALE), (int)(TILES_SIZE*SCALE), null);
    }

    private void drawCoins(Graphics g, int xLvlOffset) {
        for (Coin c : coins) {
            if(c.isActive){
                // draw
                g.drawImage(coinImg, (int)c.hitbox.x - xLvlOffset, (int)c.hitbox.y, (int)(TILES_SIZE*SCALE), (int)(TILES_SIZE*SCALE), null);

            
            }
        }
    }

    private void drawFireballs(Graphics g, int xLvlOffset) {
		for (Fireball fb : fireballs)
			if (fb.isActive()) { //fb.getState()
				g.drawImage(fireballImgs[fb.getState()][fb.getAniIndex()], (int) fb.getHitbox().x - xLvlOffset + fb.flipX(),
						(int) fb.getHitbox().y , 12 * fb.flipW(), 12, null);
				// fb.drawHitbox(g, xLvlOffset);
			}
	}


    private void drawBigMushroom(Graphics g, int xLvlOffset) { 
        for(BigMushroom bm : bigMushrooms){
            if(bm.isActive) {
                g.drawImage(bigMushroomImg, (int)bm.hitbox.x - xLvlOffset, (int)bm.hitbox.y, (int)(TILES_SIZE*SCALE), (int)(TILES_SIZE*SCALE), null);
                // bm.draw(g, xLvlOffset);
            }   
        }
    }

    private void drawOneUpMushroom(Graphics g, int xLvlOffset) { 
        for(OneUpMushroom oum : oneUpMushrooms){
            if(oum.isActive) {
                g.drawImage(oneUpMushroomImg, (int)oum.hitbox.x - xLvlOffset, (int)oum.hitbox.y, (int)(TILES_SIZE*SCALE), (int)(TILES_SIZE*SCALE), null);
                // oum.draw(g, xLvlOffset);
            }   
        }
    }

    private void drawFireFlowers(Graphics g, int xLvlOffset) { 
        for(FireFlower ff : fireFlowers){
            if(ff.isActive) {
                g.drawImage(fireFlowerImg, (int)ff.hitbox.x - xLvlOffset, (int)ff.hitbox.y, (int)(TILES_SIZE*SCALE), (int)(TILES_SIZE*SCALE), null);
                // ff.draw(g, xLvlOffset);
            }   
        }
    }

    private void turnMysteryBoxIntoBuildingBrick(int x, int y){
        
        currentLevel.getLevelData()[(int)(y/TILES_SIZE)][(int)(x/TILES_SIZE)] = 1;
    }

    private boolean checkIfHeadedByPlayer(Player player, MysteryBox mb){
        if(player.getHeadbox().intersects(mb.hitbox)) {
        
            if(player.getHeadbox().y > mb.hitbox.y + mb.hitbox.height/3) {
     
                return true;
            }
        }
        return false;
    }

    private boolean checkIfPlayerTouchEndCastle() {
        if (playing.getPlayer().getHitbox().x >= endCastleObj.x) {
            return true;
        }
        return false;
    }

    private void spawnItem(MysteryBox mb) {
        int randomNum = ThreadLocalRandom.current().nextInt(1,  5);
        switch (randomNum) {
            case 1:
                bigMushrooms.add(new BigMushroom(mb.x, mb.y - yOffset));
                break;
            case 2:
                oneUpMushrooms.add(new OneUpMushroom(mb.x, mb.y - yOffset)); 
                break;
            case 3:
                fireFlowers.add(new FireFlower(mb.x, mb.y - yOffset)); 
                break;
            case 4:
                // add score to player
                playing.getGame().getAudioManager().playEffect(COIN);
                coins.add( new Coin(mb.x, mb.y) );
                playing.addScore(200);
                break;
        }
    }

    public ArrayList<Fireball> getFireballs(){
        return fireballs;
    }

    private boolean checkIfPlayerTouchEndFlag() {
        if(playing.getPlayer().getHitbox().x >= endFlag.getHitbox().x) {
            return true;
        }
        return false;
    }

    public void resetObjects(){
        fireballs.clear();
        fireFlowers.clear();
        bigMushrooms.clear();
        oneUpMushrooms.clear();
        coins.clear();
    }

    public void loadObject(Level currentLevel) {
        resetObjects();
        
        this.currentLevel = currentLevel;

        yOffset = (int)(TILES_SIZE * SCALE);

        initEndFlagAndEndCastle();

    }
    
}
