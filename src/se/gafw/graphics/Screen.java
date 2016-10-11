package se.gafw.graphics;

public class Screen {
	
	public int[] pixels;
	private final int clearColor, width, height;
	
	public Screen(int width, int height, int clearColor){
		pixels = new int[width * height];
		this.clearColor = clearColor;
		this.width = width;
		this.height = height;
	}
	
	public void clear(){
		for(int i = 0; i < pixels.length; i++)
			pixels[i] = clearColor;
	}
	
	public void renderSprite(Sprite sprite, int x, int y, boolean clipped /*TODO*/){
		for(int v = 0; v < sprite.height; v++){
			int vp = v + y;
			
			if(vp < 0 || vp > height)continue;
			for(int u = 0; u < sprite.width; u++){
				int up = u + x;
				if(up < 0 || up > width)continue;
				pixels[up + vp * width] = sprite.pixels[u + v * sprite.width];
			}
		}	
	}
}
