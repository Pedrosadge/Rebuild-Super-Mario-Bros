package screens;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

public interface StateMethods {
    public void keyPressed(KeyEvent e);
    public void update();
    public void draw(Graphics g);
}

