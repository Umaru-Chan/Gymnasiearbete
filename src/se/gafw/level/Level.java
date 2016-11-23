package se.gafw.level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import se.gafw.gameObjects.Entity;
import se.gafw.graphics.Screen;

public class Level {

	public static final Level TEST = new Level(256, 256);
	
	private int width, height;
	//private Tile[] tiles;
	private int[] tileColors;
	private ArrayList<Entity> entities = new ArrayList<>();
	
	public Level(String path){
		try{
			//ladda leveln...
			BufferedImage image = ImageIO.read(getClass().getResourceAsStream(path));
			this.width = image.getWidth();
			this.height = image.getHeight();
			image.getRGB(0,0,width, height, tileColors, 0, width);
			//tiles = new Tile[tileColors.length];
		}catch(IOException e){
			System.err.println(" failed loading level!!");
			e.printStackTrace();
		}
	}
	
	//använd inte atm
	@Deprecated
	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		//tiles = new Tile[width * height];
		tileColors = new int[width * height];
	}
	
	public void render(Screen screen, int xOffs, int yOffs) {
		int x0 = xOffs >> 4;// >> 4 = / 16
		int x1 = (xOffs + screen.width + 16) >> 4;
		int y0 = yOffs >> 4;
		int y1 = (yOffs + screen.height + 16) >> 4;
		//TODO matte
		//if(xp < 0 || xp >= width)
		
		for(int y = y0; y < y1; y++){
			for(int x = x0; x < x1; x++){
				getTile(x, y).render(screen, x, y);
			}
		}
		
		//rendera allt över alla tiles
		renderEntities(screen);
	}
	
	private Tile getTile(int x, int y){
		if(x < 0 || x >= width || y < 0 || y >= height)return Tile.VOID_TILE;
		
		switch(tileColors[x + y * width]){
		case Tile.VOID_COLOR:return Tile.VOID_TILE;
		}
		
		return Tile.VOID_TILE;
	}
	
	private void renderEntities(Screen screen){
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(screen);
		}
	}
	
	public void update() {
		for (int i = 0; i < entities.size(); i++)
			entities.get(i).update();
	}
	
	public void addEntity(Entity entity) {
		entities.add(entity);
	}
	
	public void killEntity(Entity entity) {
		entities.remove(entity);
	}
	
}