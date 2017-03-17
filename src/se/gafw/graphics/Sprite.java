package se.gafw.graphics;

/**
 * 
 * A sprite is essentially an array of integers representing the individual pixels of an image.
 * To create a Sprite you must first load the file into a SpriteSheet and then 
 * select wich pixels you want.
 *
 */
public class Sprite {
	
	public static final Sprite VOID = new Sprite(SpriteSheet.TILES, 0, 0, 16);
	public static final Sprite GRASS = new Sprite(SpriteSheet.TILES, 1, 0, 16);
	public static final Sprite DIRT = new Sprite(SpriteSheet.TILES, 2, 0, 16);
	public static final Sprite INVENTORY = new Sprite(SpriteSheet.INVENTORY, 0, 0, 184, 64);
	public static final Sprite TOOLBAR = new Sprite(SpriteSheet.INVENTORY, 0, 0, 184, 24);
	public static final Sprite TOOLBAR_STACK = new Sprite(SpriteSheet.TILES, 0, 16, 24, 2);
	/**
	 * 
	 */
	public static final Sprite STONE = new Sprite(SpriteSheet.TILES, 3, 0, 16);
	public static final Sprite RUBY = new Sprite(SpriteSheet.TILES, 6, 0, 16);
	/**
	 * 
	 */
	public static final Sprite PLAYER = new Sprite(SpriteSheet.TILES, 2, 7, 32);
	public static final Sprite PLAYER_WALKING_1 = new Sprite(SpriteSheet.TILES, 1, 7, 32);
	public static final Sprite PLAYER_WALKING_2 = new Sprite(SpriteSheet.TILES, 3, 7, 32);
	public static final Sprite PLAYER_JUMPING = new Sprite(SpriteSheet.TILES, 4, 7, 32);
	
	/**
	 * 
	 */
	
	// an array containing the pixel data
	public int[] pixels;
	// the width and height of the sprite meassured in pixels
	public final int width, height;
	
	/**
	 * 
	 * @param sheet the peer spritesheet
	 * @param x		the rop left x-coordinate of the square that you want to use as a sprite (meassured in pixels)
	 * @param y		the top left y-coordinate of the square that you want to use as a sprite (meassured in pixels)
	 * @param size  the width and height of the square (meassured in pixels)
	 */
	public Sprite(SpriteSheet sheet, int x, int y, int size){
		this.width = size;
		this.height = size;
		pixels = new int[width * height];
		
		for(int v = 0; v < height; v++){
			int vp = v + y * height;
			if(vp < 0 || vp > sheet.height)continue;
			for(int u = 0; u < width; u++){
				int up = u + x * width;
				if(up < 0 || up > sheet.width)continue;
				pixels[u + v * width] = sheet.pixels[up + vp * sheet.width];
			}
		}
	}

	/**
	 * 
	 * @param sheet  the peer spritesheet
	 * @param x		 the rop left x-coordinate of the rectangle that you want to use as a sprite (meassured in pixels)
	 * @param y		 the top left y-coordinate of the rectangle that you want to use as a sprite (meassured in pixels)
	 * @param width  the width of the rectangle (meassured in pixels)
	 * @param height the height of the rectangle (meassured in pixels)
	 */
	public Sprite(SpriteSheet sheet, int x, int y, int width, int height){
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		
		for(int v = 0; v < height; v++){
			int vp = v + y * height;
			if(vp < 0 || vp > sheet.height)continue;
			for(int u = 0; u < width; u++){
				int up = u + x * width;
				if(up < 0 || up > sheet.width)continue;
				pixels[u + v * width] = sheet.pixels[up + vp * sheet.width];
			}
		}
	}
	
	/**
	 * creates a sprite without a spritesheet, the sprite is a rectangle with only one solid color.
	 * @param width		width  (pixels)
	 * @param height	height (pixels)
	 * @param color		color
	 */
	public Sprite(int width, int height, int color)
	{
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		
		for(int i = 0; i < pixels.length; i++) pixels[i] = color;
	}
		
	/**
	 * @return the width of the sprite
	 */
	public int getWidth()
	{
		return width;
	}

	/**
	 * @return the height of the sprite
	 */
	public int getHeight()
	{
		return height;
	}
}
