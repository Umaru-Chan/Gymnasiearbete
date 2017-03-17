package se.gafw.gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import se.gafw.Gyarb;
import se.gafw.graphics.Screen;

/**
 * 
 * used when the gamestate is in the main menu
 *
 */
public class Menu extends GameState{

	private int selected = 0;
	private final String[] buttons = {"Start", "Options", "About", "Exit"};
	
	private BufferedImage bg;
	private Font font = new Font(/*"Purisa"*/"Georgia", 0, 70);
	
	protected Menu() {
		loadBG("res/graphics/menuBG.png");
	}
	
	/**
	 * loads the menu background
	 * @param path the path to the image file
	 */
	private void loadBG(String path)
	{
		if(bg != null)return;
		try
		{
			bg = ImageIO.read(new File(path));
		}catch(Exception e){
			System.err.println("something went wrong while loading menu bg image!\n"+e.getMessage());
		}
	}
	
	public void enter()
	{
		selected = 0;
	}
	
	public void leave()
	{
	
	}
	
	public void render(Screen screen, Graphics2D g2d) {
		//render the background and the buttons
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setFont(font);
		g2d.drawImage(bg, 0, 0, Gyarb.WIDTH * Gyarb.SCALE, Gyarb.WIDTH * Gyarb.SCALE, null);
		for(int i = 0; i < buttons.length; i++)
		{
			if(selected % buttons.length == i)g2d.setColor(Color.GREEN);
			else g2d.setColor(Color.WHITE);
		
			g2d.drawString(buttons[i], (Gyarb.WIDTH * Gyarb.SCALE) / 3 - 250, 450 + i * 75);
		}	
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
	}

	public void update() {}

	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}

	/**
	 * check for key-events
	 */
	public void keyPressed(KeyEvent e)
	{
		
		if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP)selected += buttons.length - 1;
		if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN)selected ++;
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			//check what option is selected
			if(selected % buttons.length == 0)
			{
				//start
				GameStateManager.setCurrentState(GameStateManager.State.IN_GAME);
			}
			
			if(selected % buttons.length == 1)
			{
				//options
				//TODO implement ^^
			}
			
			if(selected % buttons.length == 2)
			{
				GameStateManager.setCurrentState(GameStateManager.State.ABOUT);
				//about
			}
			
			if(selected % buttons.length == 3)
			{
				//exit
				System.exit(0);
			}
		}
	}
}
