package se.gafw.level;

import se.gafw.graphics.Screen;
import se.gafw.graphics.Sprite;

public abstract class Tile {
	
	public static final Tile VOID_TILE = new CollidableTile(Sprite.VOID);
	public static final int VOID_COLOR = 0xffffffff;
	
	
	private final Sprite sprite;
	
	/**
	 * @param sprite
	 */
	public Tile(Sprite sprite){ this.sprite = sprite; }
	
	/**
	 * renders the tile at x, y
	 * @param screen
	 * @param x
	 * @param y
	 */
	public void render(Screen screen, int x, int y)
	{
		screen.renderSprite(sprite, x << 4, y << 4, false);
	}
	/**
	 * @return true if the tile is solid, else return false.
	 */
	public boolean solid(){ return false; }
	/**
	 * @return true if the tile is liquid, else return false.
	 */
	public boolean liquid(){ return false; }
}
