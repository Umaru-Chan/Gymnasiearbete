package se.gafw.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	
	public static final SpriteSheet TILES = new SpriteSheet("res/graphics/sheet.png");
	
	
	public int[] pixels;
	public final int width, height;
	
	public SpriteSheet(String path){
		try{
			//BufferedImage image = ImageIO.read(getClass().getResourceAsStream(path));
			BufferedImage image = ImageIO.read(new File(path));
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
			/*NOTE TO SELF
			 * pixlarna blir ARGB
			 * */
			System.gc();
		}catch(IOException e){
			System.err.println("error loading spritesheet, path: "+path+"\n"+e.getMessage());
			throw new RuntimeException(":(");
		}
	}
}
