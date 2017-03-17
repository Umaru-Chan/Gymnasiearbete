package se.gafw.gameState;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import se.gafw.graphics.Screen;

/**
 * 
 * This class is used as an abstraction for a game-state.
 *
 */
public abstract class GameState {
	
	/**
	 * This method is called when the game enters the state
	 */
	public abstract void enter();
	/**
	 * This method is called when the game leaves the state
	 */
	public abstract void leave();
	/**
	 * The render method
	 * 
	 * @param screen the screen to render to
	 * @param g2d    an java rendering object used for font rendering and 
	 * 				 putting the of-screen buffer from screen on the actual screen.
	 */
	public abstract void render(Screen screen, Graphics2D g2d);
	/**
	 * updates the game-state.
	 */
	public abstract void update();
	
	/**
	 * handeling Key events.
	 */
	
	public void keyPressed(KeyEvent e){}
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}

}
