package se.gafw.gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;

import se.gafw.Gyarb;
import se.gafw.graphics.Screen;
import se.gafw.util.FileHandler;

public class About extends GameState{
	
	private int offset = 0;
	private String[] text;
	private Font font = new Font("Arial", 0, 20);
	
	protected About(){
		loadText("res/about");
	}
	
	public void loadText(String path)
	{
		text = FileHandler.readText(path).split("\n");
	}

	public void enter() {
	}

	public void leave() {

	}

	public void render(Screen screen, Graphics2D g2d) {
		
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, Gyarb.WIDTH * Gyarb.SCALE, Gyarb.HEIGHT * Gyarb.SCALE);
		
		g2d.setFont(font);
		g2d.setColor(Color.WHITE);
		for(int i = 0; i < text.length; i++)
		{
			g2d.drawString(text[i], 150, 150 - offset + i * 22);
		}
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		
	}

	public void update() {

	}

	public void keyTyped(KeyEvent e) {
		
	}

	public void keyPressed(KeyEvent e) {
		if((e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP)
			&& offset - 25 >= 0)
			offset -=25;
		
		if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN)
			offset +=25;
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			GameStateManager.setCurrentState(GameStateManager.State.MENU);
	}

	public void keyReleased(KeyEvent e) {
		
	}
}
