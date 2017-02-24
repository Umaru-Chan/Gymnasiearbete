package se.gafw.graphics;

public class Sprite {
	
	public static final Sprite VOID = new Sprite(SpriteSheet.TILES, 0, 0, 16);
	public static final Sprite GRASS = new Sprite(SpriteSheet.TILES, 1, 0, 16);
	public static final Sprite DIRT = new Sprite(SpriteSheet.TILES, 2, 0, 16);
	public static final Sprite PLAYER = new Sprite(SpriteSheet.TILES, 3, 7, 32);
	public static final Sprite INVENTORY = new Sprite(SpriteSheet.INVENTORY, 0, 0, 184, 64);
	public static final Sprite TOOLBAR = new Sprite(SpriteSheet.INVENTORY, 0, 0, 184, 24);
	public static final Sprite TOOLBAR_STACK = new Sprite(SpriteSheet.TILES, 0, 16, 24, 2);

	/**
	 * 
	 */
	public static final Sprite STONE = new Sprite(16, 16, 0xaaaaaa);
	public static final Sprite RUBY = new Sprite(16, 16, 0xaa0000);
	/**
	 * 
	 */
	
	public int[] pixels;
	public int width, height;
	
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
	
		public Sprite(int width, int height, int color)
	{
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		
		for(int i = 0; i < pixels.length; i++) pixels[i] = color;
	}

}
