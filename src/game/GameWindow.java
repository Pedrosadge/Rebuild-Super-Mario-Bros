package game;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;
import javax.swing.JWindow;

import game.GamePanel;

public class GameWindow extends JWindow{
    private JFrame frame;

    public GameWindow(GamePanel gamePanel) {
        frame = new JFrame();

        frame.add(gamePanel);

        frame.setResizable(false);
        
		frame.setLocationRelativeTo(null);
		
		frame.pack();
		
        frame.addWindowFocusListener(new WindowFocusListener() {

            @Override
            public void windowGainedFocus(WindowEvent e) {
                
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
               gamePanel.getGame().windowFocusLost();
            }
            
        });

		frame.setVisible(true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
