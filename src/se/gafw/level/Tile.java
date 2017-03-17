package se.gafw.level;

import se.gafw.graphics.Screen;
import se.gafw.graphics.Sprite;

/**
 * A Tile is a abstract description of an area in the game and that areas properties. Tiles are accessed statically as they have 
 * no need for individual properties.
 *	
 */
public abstract class Tile {
	
	/**
	 * Static Tile objects used all across the game, no need to create new instances of Tiles.
	 * The tile color is used in the level class when creating levels, the initial idea was that you can
	 * create level files and save them as .PNG where every pixel represents a Tile.
	 */
	public static final Tile VOID_TILE = new AirTile(Sprite.VOID);
	public static final int VOID_COLOR = 0xffffffff;
	public static final Tile GRASS_TILE = new CollidableTile(Sprite.GRASS);
	public static final int GRASS_COLOR = 0xff000000;
	public static final Tile DIRT_TILE = new CollidableTile(Sprite.DIRT);
	public static final int DIRT_COLOR = 0xaa88aa;
	public static final Tile STONE_TILE = new CollidableTile(Sprite.STONE);
	public static final int STONE_COLOR = 0xaaaaaa;
	public static final Tile RUBY_TILE = new CollidableTile(Sprite.RUBY);
	public static final int RUBY_COLOR = 0xff0000;

	
	/**
	 * The sprite used for rendering the tile.
	 */
	public final Sprite sprite;
	
	/**
	 * @param sprite the sprite used to render the tile.
	 */
	public Tile(Sprite sprite){ this.sprite = sprite; }
	
	/**
	 * renders the tile in screen at x, y. x and y params are not pixel coordinates but tile coordinates. 
	 * If you have a tile at (x1, y1) and wanna render a tile next to it you render it at (x1 + 1, y1) and
	 * NOT at (x1 + sprite.width, y1)
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