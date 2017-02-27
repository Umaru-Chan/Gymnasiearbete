package se.gafw.gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import se.gafw.Gyarb;
import se.gafw.graphics.Screen;

public class Pause extends GameState {

	private final String[] buttons = {"Resume", "Menu"};
	private int selected = 0;
	private Font font = new Font("Arial", 0, 70);
	//
	private int[] lastBuffer = null;	//pixel array
	private BufferedImage image;		//image to draw from pixels
	
	protected Pause()
	{
	}
	
	public void enter() {
		selected = 0;
	}

	public void leave() {
		lastBuffer = null;
	}
	
	public void render(Screen screen, Graphics2D g2d) {
		//om man renderar om bakgrunden många gånger så blir det mörkt
	
		if(lastBuffer == null)
		{
			image = new BufferedImage(Gyarb.WIDTH, Gyarb.HEIGHT, BufferedImage.TYPE_INT_RGB);
			int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
			lastBuffer = screen.getCurrentBuffer();
			//TODO gray
			for(int i = 0; i < lastBuffer.length; i++)
			{
				lastBuffer[i] *= .2;
				if(lastBuffer[i] < 0)lastBuffer[i] = 0;
			}

			System.arraycopy(lastBuffer, 0, pixels, 0, pixels.length);
		}
		
		g2d.drawImage(image, 0, 0, Gyarb.WIDTH * Gyarb.SCALE, Gyarb.HEIGHT * Gyarb.SCALE, null);
		
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		g2d.setFont(font);
		for(int i = 0; i < buttons.length; i++)
		{
			if(selected % buttons.length == i)g2d.setColor(Color.GREEN);
			else g2d.setColor(Color.WHITE);
			g2d.drawString(buttons[i], (Gyarb.WIDTH * Gyarb.SCALE) / 3 - 250, 450 + i * 75);
		}
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
	}

	public void update() {

	}

	public void keyTyped(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)GameStateManager.setCurrentState(GameStateManager.State.IN_GAME);
	}

	public void keyPressed(KeyEvent e) {
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
