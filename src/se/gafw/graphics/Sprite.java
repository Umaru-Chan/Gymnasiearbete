package se.gafw.graphics;

public class Sprite {
	
	public static final Sprite VOID = new Sprite(SpriteSheet.TILES, 0, 0, 16);
	
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

}
