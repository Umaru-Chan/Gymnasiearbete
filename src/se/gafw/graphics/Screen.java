package se.gafw.graphics;

import se.gafw.level.Tile;

public class Screen {
	
	public int[] pixels;
	private final int clearColor;
	public final int width, height;
	private int xScroll, yScroll;
	
	/**
	 * used to render to the screen
	 * @param width
	 * @param height
	 * @param clearColor
	 */
	public Screen(int width, int height, int clearColor){
		pixels = new int[width * height];
		this.clearColor = clearColor;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * clears all the pixels
	 */
	public void clear(){
		for(int i = 0; i < pixels.length; i++)
			pixels[i] = clearColor;
	}
	
	/**
	 * renders Sprite sprite at x, y.
	 * clipped is not yet implemented.
	 * 
	 * @param sprite
	 * @param x
	 * @param y
	 * @param clipped
	 */
	public void renderSprite(Sprite sprite, int x, int y, boolean clipped /*TODO*/){
		for(int v = 0; v < sprite.height; v++){
			int vp = v + y;
			if(vp < 0 || vp >= height)continue;
			for(int u = 0; u < sprite.width; u++){
				int up = u + x;
				if(up < 0 || up >= width)continue;				
				pixels[up + vp * width] = sprite.pixels[u + v * sprite.width];
			}
		}	
	}
	
	public void renderTile(Tile tile, int xp, int yp)
	{
		xp -= xScroll;
		yp -= yScroll;
		for (int y = 0; y < tile.sprite.height; y++) {
			int ya = y + yp;
			for (int x = 0; x < tile.sprite.width; x++) {
				int xa = x + xp;
				if (xa < -tile.sprite.width || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				pixels[xa + ya * width] = tile.sprite.pixels[x + y * tile.sprite.width];
			}
		}
	}
	
	public void setScroll(int x, int y){
		xScroll = x;
		yScroll = y;
	}
}
