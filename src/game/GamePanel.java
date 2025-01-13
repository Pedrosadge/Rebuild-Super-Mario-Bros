package game;

import java.awt.Graphics;
import java.awt.Dimension;


import javax.swing.JPanel;

import inputs.KeyboardInputs;

import static game.Game.GAME_HEIGHT;
import static game.Game.GAME_WIDTH;

public class GamePanel extends JPanel {

	private Game game;
	
	
	public GamePanel(Game game) {
		this.game = game;
		
	
		setPanelSize();
		addKeyListener(new KeyboardInputs(this));
		
	}
	
	public void setPanelSize() {
		Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
		setPreferredSize(size);
		
		System.out.println("size: " + GAME_WIDTH + " : " + GAME_HEIGHT);
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		game.render(g);
	}

	public Game getGame() {
		return game;
	}
	
}
