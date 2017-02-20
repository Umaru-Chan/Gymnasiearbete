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

public class Pause extends GameState {

	private final String[] buttons = {"Resume", "Menu"};
	private int selected = 0;
	private Font font = new Font("Arial", 0, 70);
	private BufferedImage bg;
	//man behöver bara rendera menyn då något har hänt. annars
	//slösar man enbart energi
	private boolean shouldRender = true, shouldRenderBG = true;
	/*
	 * eftersom att klassen är en keylistener så kommer keypressed
	 * att kallas även om man inte är i "pause" staten.
	 * därför används en boolean för att hålla koll på 
	 * om man ska ta input.
	 */
	private boolean shouldProcessInput = false; 
	
	protected Pause()
	{
		loadBG("res/graphics/pauseBG.png");
	}
	
	public void loadBG(String path)
	{
		if(bg != null)return;
		try
		{
			bg = ImageIO.read(new File(path));
		}catch(Exception e){
			System.err.println("something went wrong while loading menu bg image!\n"+e.getMessage());
		}
	}
	
	public void enter() {
		shouldProcessInput = true;
		selected = 0;
	}

	public void leave() {
		shouldProcessInput = false;
		shouldRenderBG = true;
	}

	public void render(Screen screen, Graphics2D g2d) {
		if(!shouldRender)return;
	
		//om man renderar om bakgrunden många gånger så blir det mörkt
		if(shouldRenderBG)
		{
			shouldRenderBG = false;
			g2d.drawImage(bg, 0, 0, Gyarb.WIDTH * Gyarb.SCALE, Gyarb.WIDTH * Gyarb.SCALE, null);
		}
		
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		g2d.setFont(font);
		for(int i = 0; i < buttons.length; i++)
		{
			if(selected % buttons.length == i)g2d.setColor(Color.GREEN);
			else g2d.setColor(Color.WHITE);
			g2d.drawString(buttons[i], (Gyarb.WIDTH * Gyarb.SCALE) / 3 - 250, 450 + i * 75);
		}
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		
		shouldRender = false;
	}

	public void update() {

	}

	public void keyTyped(KeyEvent e) {

	}

	public void keyPressed(KeyEvent e) {
		if(!shouldProcessInput)return;
		shouldRender = true;//rendera om såfort man rör tangentbordet
		if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP)selected += buttons.length - 1;
		if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN)selected ++;
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			//gör selected saken
			if(selected % buttons.length == 0)
			{
				//resume
				GameStateManager.setCurrentState(GameStateManager.State.IN_GAME);
			}
			
			if(selected % buttons.length == 1)
			{
				//menu
				GameStateManager.setCurrentState(GameStateManager.State.MENU);
			}
		}
	}

	public void keyReleased(KeyEvent e) {

	}

}
