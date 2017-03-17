package se.gafw.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 * A sheet containing a lot of sprites, the main reason for using spritesheets and then 
 * splitting it with code is so that you don't have to mess with alot of files. One spritesheet
 * can easely contains hundreds of sprites.
 *
 */
public class SpriteSheet {
	
	public static final SpriteSheet TILES = new SpriteSheet("res/graphics/sheet.png");
	public static final SpriteSheet INVENTORY = new SpriteSheet("res/graphics/inventory.png");
	public static final SpriteSheet TOOLBAR = new SpriteSheet("res/graphics/Toolbar.png");
	
	// an array containing pixel data
	public int[] pixels;
	// the width and height of the spritesheet meassured in pixels
	public final int width, height;
	
	public SpriteSheet(String path){
		try{
			//BufferedImage image = ImageIO.read(getClass().getResourceAsStream(path));
			BufferedImage image = ImageIO.read(new File(path));
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
			/*
			 * NOTE TO SELF
			 * the pixels are saved in ARGB
			 * */
			System.gc();
		}catch(IOException e){
			System.err.println("error loading spritesheet, path: "+path+"\n"+e.getMessage());
			throw new RuntimeException(":(");
		}
	}
}
