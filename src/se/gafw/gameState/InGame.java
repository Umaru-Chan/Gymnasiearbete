package se.gafw.gameState;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import se.gafw.Gyarb;
import se.gafw.gameObjects.Player;
import se.gafw.graphics.Screen;
import se.gafw.level.Level;

public class InGame extends GameState{

    private final Player player;
    private static Level currentLevel;
    
    private final BufferedImage image;
	private final int[] pixels;
    
	protected InGame(Gyarb g) {
		pixels = ((DataBufferInt) 
				(image = new BufferedImage(Gyarb.WIDTH, Gyarb.HEIGHT, BufferedImage.TYPE_INT_RGB)).getRaster().getDataBuffer()).getData();
		currentLevel = Level.randomLevel(5, 150, 100);
		player = new Player(currentLevel, 75 << 4, 35 << 4, g); //multiplicerar med 16 för att få positionen i block och inte pixlar
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
        player.render(screen);
        
		System.arraycopy(screen.pixels, 0, pixels, 0, pixels.length);
		g2d.drawImage(image, 0, 0, Gyarb.WIDTH * Gyarb.SCALE, Gyarb.HEIGHT * Gyarb.SCALE, null);
	}


	public void update() {
		player.update();
		currentLevel.update();
	}
	
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
