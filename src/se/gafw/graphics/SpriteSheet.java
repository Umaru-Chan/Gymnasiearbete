package se.gafw.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	
	public int[] pixels;
	public final int width, height;
	
	public SpriteSheet(String path){
		try{
			BufferedImage image = ImageIO.read(getClass().getResourceAsStream(path));
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
			/*NOTE TO SELF
			 * pixlarna blir ARGB
			 * */
		}catch(IOException e){
			System.err.println("error loading spritesheet, path: "+path+"\n"+e.getMessage());
			throw new RuntimeException(":(");
		}
	}
}
