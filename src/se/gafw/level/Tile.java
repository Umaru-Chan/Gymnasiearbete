package se.gafw.level;

import se.gafw.graphics.Screen;
import se.gafw.graphics.Sprite;

public abstract class Tile {
	
	private final Sprite sprite;
	
	public Tile(Sprite sprite){ this.sprite = sprite; }
	
	public void render(Screen screen, int x, int y)
	{
		screen.renderSprite(sprite, x, y, false);
	}
	
	public boolean solid(){ return false; }
	public boolean liquid(){ return false; }
}
