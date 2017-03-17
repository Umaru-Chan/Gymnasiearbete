package se.gafw.gameState;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import se.gafw.Gyarb;
import se.gafw.gameObjects.Player;
import se.gafw.graphics.Screen;
import se.gafw.level.Level;

/**
 * 
 * This class is used when the gamestate is in-game
 *
 */
public class InGame extends GameState{

	// player object
    private final Player player;
    // the current level
    private static Level currentLevel;
    
    // used for rendering
    private final BufferedImage image;
	private final int[] pixels;
    
	/**
	 * @param g an instance of the main class
	 */
	protected InGame(Gyarb g) {
		pixels = ((DataBufferInt) 
				(image = new BufferedImage(Gyarb.WIDTH, Gyarb.HEIGHT, BufferedImage.TYPE_INT_RGB)).getRaster().getDataBuffer()).getData();
		currentLevel = Level.randomLevel(7, 150, 100);
		player = new Player(currentLevel, 75 << 4, 35 << 4); //multiplicerar med 16 för att få positionen i block och inte pixlar
		g.addMouseListener(player);
	}
	
	public void enter()
	{
	}
	
	public void leave()
	{
		player.resetKeys();
	}
	
	public void render(Screen screen, Graphics2D g2d) {
		screen.clear();

		currentLevel.render(screen, player.getX(), player.getY());
        player.render(screen, g2d);
        
		System.arraycopy(screen.pixels, 0, pixels, 0, pixels.length);
		g2d.drawImage(image, 0, 0, Gyarb.WIDTH * Gyarb.SCALE, Gyarb.HEIGHT * Gyarb.SCALE, null);
	}


	public void update() {
		player.update();
		currentLevel.update();
	}
	
	/** 
	 * @return the current level
	 */
	public static Level getCurrentLevel()
	{
		return currentLevel;
	}
	
	public void keyPressed(KeyEvent e)
	{
		player.keyPressed(e);
	}
	
	public void keyReleased(KeyEvent e)
	{
		player.keyReleased(e);
	}
	
	public void keyTyped(KeyEvent e)
	{
		player.keyTyped(e);
	}
}
