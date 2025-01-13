package screens;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import ui.UIManager;

import audio.AudioManager;
import entities.EnemyManager;
import entities.Player;
import game.Game;
import levels.LevelManager;
import objects.ObjectManager;
import ui.GameCompletedOverlay;
import ui.GameOver;
import ui.PauseOverlay;
import utilz.ImageLoader;

public class Playing extends State implements StateMethods {

    private BufferedImage background;

    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private ObjectManager objectManager;

    private GameCompletedOverlay gameCompleted;
    private PauseOverlay pauseOverlay;
    private GameOver gameOver;
    private UIManager uiManager;

    private boolean isSongPlaying = false;
    private boolean isGameCompleted = false, 
                    isGameOver = false, 
                    isGamePaused = false, 
                    isPlayerDied = false;


    
    private int xLvlOffset;
	private int leftBorder = (int) (0.4 * Game.GAME_WIDTH);
	private int rightBorder = (int) (0.6 * Game.GAME_WIDTH);
	private int maxLvlOffsetX;

    private int score = 0, playerLives = 3;

    public Playing(Game game) {
        super(game);
        initClasses();

        background = ImageLoader.loadImage(ImageLoader.LEVEL_BACKGROUND);
        
    }

    private void initClasses() {
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);
        objectManager = new ObjectManager(this);

        pauseOverlay = new PauseOverlay(this);
        gameOver = new GameOver(this);
        gameCompleted = new GameCompletedOverlay(this);
        uiManager = new UIManager(this);
    }

    public void keyReleased(KeyEvent e) {
        if (!isGameOver && !isGameCompleted && !isGamePaused)
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(false);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(false);
                    break;
                case KeyEvent.VK_W:
                    player.setJumping(false);
                    break;
                case KeyEvent.VK_SPACE:
                    player.shootFireball();
                default:
                    player.setMoving(false);
                    break;
            }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!isGameOver && !isGameCompleted)
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(true);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(true);
                    break;
                case KeyEvent.VK_W:
                    player.setJumping(true);
                    break;
                case KeyEvent.VK_BACK_SPACE:
                    isGamePaused = !isGamePaused;
                    player.resetDirBooleans();
                    break;
                case KeyEvent.VK_ESCAPE:
                    
                    unpauseGame();
                    // reset score and lives
                    resetScoreAndLives();
                    resetLevel();
   
                    ScreenState.state = ScreenState.MENU; 
                    break;
                default:
                    break;
        } 
    }

    public void playerDie(){
        playerLives--;

        long currentTime = getGame().getAudioManager().pauseSong();
        getGame().getAudioManager().playEffect(AudioManager.MARIODIES);
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        resetLevel();
        loadLevel(getGame().getPickLevel().getMapChoice()); 
        
        getGame().getAudioManager().playSongAt(currentTime);
    }

    private void resetLevel(){
        player.resetDirBooleans();
        // rebuild it again
        levelManager.reBuildLevel(getGame().getPickLevel().getMapChoice());
        // delete current level
        levelManager.clearLevel();
    }

    public void resetScoreAndLives() {
        score = 0;
        playerLives = 3;
        player.setHealths(3);
    }

    private void unpauseGame() {
        isGamePaused = false;
    }

    @Override
    public void update() {
        
        if(isGamePaused)
        pauseOverlay.update();
        else if (isGameCompleted) 
            gameCompleted.update();
        else if (isGameOver)
            gameOver.update();
        else if (isPlayerDied)
            player.update();
        else {
            player.update();
            levelManager.update();
            enemyManager.update(levelManager.getCurrentLevel().getLevelData());
            objectManager.update(levelManager.getCurrentLevel().getLevelData(), player);
            uiManager.update();

            if(!isSongPlaying && !isGamePaused) {
                
                getGame().getAudioManager().playSong(AudioManager.BACKGROUND);
                isSongPlaying = true;
        }
        checkCloseToBorder();
        }        
    }

    @Override
    public void draw(Graphics g) {
        
        g.drawImage(background, 0 - xLvlOffset, 0, null);
        levelManager.draw(g, xLvlOffset);
        uiManager.draw(g);
        enemyManager.draw(g, xLvlOffset);
        objectManager.draw(g, xLvlOffset);
        player.render(g, xLvlOffset, player.getAnimation());

        if (isGamePaused) {
			g.setColor(new Color(0, 0, 0, 150));
			g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
			pauseOverlay.draw(g);
		} else if (isGameOver)
			gameOver.draw(g);
		else if (isGameCompleted)
			gameCompleted.draw(g);
        
        
    }
    

    private void checkCloseToBorder() {
		int playerX = (int) player.getHitbox().x;
		int diff = playerX - xLvlOffset;

		if (diff > rightBorder)
			xLvlOffset += diff - rightBorder;
		else if (diff < leftBorder)
			xLvlOffset += diff - leftBorder;

		xLvlOffset = Math.max(Math.min(xLvlOffset, maxLvlOffsetX), 0);
	}

    public Player getPlayer() {
        return player;
    }

    public void setGameOver(boolean gameOver) {
        this.isGameOver = gameOver;
    }

    public void setPlayerDying(boolean playerDying) {
        this.isPlayerDied = playerDying;
    }
    
    public void setMaxLvlOffset(int lvlOffset) {
		this.maxLvlOffsetX = lvlOffset;
	}

    private void calcLvlOffset() {
		maxLvlOffsetX = levelManager.getCurrentLevel().getLvlOffset();
	}

    public boolean getIsSongPlaying() {
        return isSongPlaying;
    }
    public void setIsSongPlaying(boolean b) {
        isSongPlaying =  b;   
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public ObjectManager getObjectManager(){
        return objectManager;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public void addLive(int live) {
        playerLives += live;
    }

    public void win(){
        isGameCompleted = true;
    }

    public void loadLevel(int levelIndex) {
        levelManager.chooseLevel(levelIndex);
        
        calcLvlOffset();
        

        player = new Player(40, 40, (int) (32  * Game.SCALE), (int) (32  * Game.SCALE), this);
        player.loadLevelData(levelManager.getCurrentLevel().getLevelData());
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());

        
        enemyManager.loadEnemies(levelManager.getCurrentLevel());
        objectManager.loadObject(levelManager.getCurrentLevel());
    }

    public int getScore(){
        return score;
    }

    public int getPlayerLives(){
        return playerLives;
    }

    public void setPlayerLives(int lives) {
		playerLives = lives;
    }

    public void setIsGameCompleted(boolean b) {
        isGameCompleted = b;
    }


}
