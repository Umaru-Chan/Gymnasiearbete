package se.gafw.gameState;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import se.gafw.graphics.Screen;

public abstract class GameState {
	
	public abstract void enter();
	public abstract void leave();
	public abstract void render(Screen screen, Graphics2D g2d);
	public abstract void update();
	
	public void keyPressed(KeyEvent e){}
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}

}
