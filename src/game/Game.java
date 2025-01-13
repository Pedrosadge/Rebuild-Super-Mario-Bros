package game;

import java.awt.Graphics;

import audio.AudioManager;
import screens.About;
import screens.Help;
import screens.Menu;
import screens.PickLevel;
import screens.Playing;
import screens.ScreenState;




public class Game implements Runnable {

	private GameWindow gameWindow;
	private GamePanel gamePanel;
	
	private Thread gameThread;
	
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;
	
	// Screens
	private Menu menu;
	private Help help;
	private About about;
	private PickLevel pickLevel;
	private Playing playing;
	
	
	// game sizing
	public final static int TILE_DEFAULT_SIZE = 32;
	public final static float SCALE = 1.0f;
	public final static int TILES_IN_WIDTH = 36;
	public final static int TILES_IN_HEIGHT = 20;
	public final static int TILES_SIZE = (int) (TILE_DEFAULT_SIZE * SCALE);
	public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
	public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

	
	AudioManager audioManager;

	
	public Game() {
		
		initClasses();
		
		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);
		
		
		gamePanel.setFocusable(true);
		gamePanel.requestFocus();

		
		startGameLoop();
		
	}
	
	
	private void initClasses() {
		menu = new Menu(this);
		help = new Help(this);
		about = new About(this);
		pickLevel = new PickLevel(this);

		
		playing = new Playing(this);
		audioManager = new AudioManager();
	}

	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void update() {
		if (ScreenState.state != ScreenState.PLAYING) {
			if(playing.getIsSongPlaying()) {
				audioManager.stopSong();
				playing.setIsSongPlaying(false);
			}
		}

		
		switch(ScreenState.state) {
		case MENU:
			menu.update();
			break;
		case PLAYING:
			playing.update();
			break;
		case HELP:
			help.update();
			break;
		case ABOUT:
			about.update();
			break;
		case PICKLEVEL:
			pickLevel.update();
			break;
		default:
			break;
		}
	}
	
	public void render(Graphics g) {
		
		switch(ScreenState.state) {
		case MENU:
			menu.draw(g);
			break;
		case PLAYING:
			playing.draw(g);
			break;
		case HELP:
			help.draw(g);
			break;
		case ABOUT:
			about.draw(g);
			break;
		case PICKLEVEL:
			pickLevel.draw(g);
			break;
		default:
			break;
		}
	}
	
	@Override
	public void run() {
		
		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePerUpdate = 1000000000.0 / UPS_SET;
		
		
		long previousTime = System.nanoTime();
		
		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis();
		
		double deltaU = 0;
		double deltaF = 0;
		
		while(true) {
			
			long currentTime = System.nanoTime();
			
			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			
			previousTime = currentTime;
			
			if(deltaU >= 1) {
				update();
				updates++;
				deltaU--;
			}
	
			if(deltaF >= 1) {
				gamePanel.repaint();
				frames++;
				deltaF--;
			}
			

			
			if(System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS = " + frames + " | UPS: " + updates);
				frames = 0;
				updates = 0;
			}
			
			
		}
		
	}

	public void windowFocusLost() {
		if(ScreenState.state == ScreenState.PLAYING)
			playing.getPlayer().resetDirBooleans();
	}
	
	public Menu getMenu() {
		return menu;
	}
	
	public Playing getPlaying() {
		return playing;
	}
	
	public Help getHelp() {
		return help;
	}
	
	public About getAbout() {
		return about;
	}

	public PickLevel getPickLevel(){
		return pickLevel;
	}

	public AudioManager getAudioManager(){
		return audioManager;
	}

	
}
