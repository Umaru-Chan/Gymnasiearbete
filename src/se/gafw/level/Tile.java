package se.gafw.level;

import se.gafw.graphics.Screen;
import se.gafw.graphics.Sprite;
import se.gafw.graphics.SpriteSheet;

public abstract class Tile {
	
	public static final Tile LMAO = new AirTile(new Sprite(SpriteSheet.TILES, 4,4,16));//TEMP
	public static final Tile VOID_TILE = new AirTile(Sprite.VOID);
	public static final int VOID_COLOR = 0xffffffff;
	public static final Tile GRASS_TILE = new CollidableTile(Sprite.GRASS);
	public static final int GRASS_COLOR = 0xff000000;
	public static final Tile DIRT_TILE = new CollidableTile(Sprite.DIRT);
	public static final int DIRT_COLOR = 0xaa88aa;
	
	
	public final Sprite sprite;
	
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
		screen.renderTile(this, x << 4, y << 4);
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
