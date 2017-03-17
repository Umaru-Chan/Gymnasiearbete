package se.gafw.graphics;

import se.gafw.level.Tile;

/**
 * 
 * The screen class is used to render Sprites and SpriteSheets to the of screen buffer.
 *
 */
public class Screen {
	
	//an array containing pixel data
	public int[] pixels;
	//the color to clear the screen with, also the screen width and height meassured in pixels (NOTE not window width/height)
	private final int clearColor;
	public final int width, height;
	//character offset
	private int xScroll, yScroll;
	
	/**
	 * used to render to the screen
	 * @param width			canvas width
	 * @param height		canvas height
	 * @param clearColor	color to clear with
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
	 * @return all the pixel data
	 */
	public int[] getCurrentBuffer()
	{
		return pixels;
	}
	
	/**
	 * renders Sprite sprite at x, y.
	 * "fixed" is not yet implemented.
	 * 
	 * @param sprite
	 * @param x
	 * @param y
	 * @param clipped
	 */
	public void renderSprite(Sprite sprite, int x, int y, boolean fixed/*TODO*/){
		for(int v = 0; v < sprite.height; v++){
			
			int vp = v + y;
			if(vp < 0 || vp >= height)continue;
			for(int u = 0; u < sprite.width; u++){

				int up = u + x;
				if(up < 0 || up >= width)continue;		
				int col = sprite.pixels[u + v * sprite.width];
				
				if(col == 0xffff00ff || col == 0xff7f007f)continue;
				pixels[up + vp * width] = col;
			}
		}	
	}
	
	/**
	 * renderes the sprite at x, y
	 * flipps the sprite if requested
	 * 
	 * @param sprite 	the sprite to be rendered
	 * @param x			the x-coordinate for the sprite
	 * @param y			the y-coordinate for the sprite
	 * @param flippedX	wether or not to flip the sprite in the x direction (around the y axis)
	 * @param flippedY	wether or not to flip the sprite in the y direction (around the x axis)
	 */
	public void renderSprite(Sprite sprite, int x, int y, boolean flippedX, boolean flippedY){
		for(int v = 0; v < sprite.height; v++){
			//om man ska flippa y ledet
			int vs = v;
			if(flippedY)vs = sprite.height - v - 1;
			
			int vp = v + y;
			if(vp < 0 || vp >= height)continue;
			
			for(int u = 0; u < sprite.width; u++){
				//om man ska flippa x ledet
				int us = u;
				if(flippedX)us = sprite.width - u - 1;
				
				int up = u + x;
				if(up < 0 || up >= width)continue;
				
				int col = sprite.pixels[us + vs * sprite.width];
				if(col == 0xffff00ff || col == 0xff7f007f)continue;
				
				pixels[up + vp * width] = col;
			}
		}	
	}
	
	/**
	 * used to render tiles
	 * 
	 * @param tile the tile to be rendered
	 * @param xp   the x position of the tile
	 * @param yp   the y position of the tile	
	 */
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
				
				int col = tile.sprite.pixels[x + y * tile.sprite.width];
				if(col == 0xffff00ff || col == 0xff7f007f)continue;
				
				pixels[xa + ya * width] = col;
			}
		}
	}
	
	/**
	 * sets the screen offset
	 */
	public void setScroll(int x, int y){
		xScroll = x;
		yScroll = y;
	}
}
