package se.gafw.level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import se.gafw.gameObjects.Entity;
import se.gafw.graphics.Screen;

public class Level {

	public static final Level TEST = new Level(new Random(), 256, 256, new int[]{0xffffffff, 0xff000000});
	
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

    private Level(int[] tileColors, int width, int height)
    {
        this.tileColors = tileColors;
        this.width = width;
        this.height = height;
    }

	public static Level randomLevel(int amplitude, int width, int depth)
    {
        Random random = new Random();
        Level result;
        int[] h = new int[width];
        int[] tileColors = new int[width * depth];
        for(int i = 0; i < width; i++)
        {
            h[i] = depth + random.nextInt(amplitude);
        }

        for(int x = 0; x < width; x++)
        {
            for(int y = 0; y < depth; y++)
            {
                if(y + depth >= h[x])
                {
                    System.out.println("lmao");
                    tileColors[x + y * width] = Tile.TEST_COLOR;
                }
                else tileColors[x + y * width] = Tile.VOID_COLOR;
            }
        }

        return result = new Level(tileColors, width, depth);
    }

	/**
	 * Generera en slumpmässig uppsättning tiles.
	 * @param random
	 * @param width
	 * @param height
	 * @param tile  	en array med färgerna för alla tiles som man vill ha
	 */
	public Level(Random random, int width, int height, int[] tile){
		this.width = width;
		this.height = height;
		tileColors = new int[width * height];
		for(int i = 0; i < tileColors.length; i++)
		{
			tileColors[i] = tile[random.nextInt(tile.length)];
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
		screen.setScroll(xOffs, yOffs);
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

	public Tile getTile(int x, int y){
		if(x < 0 || x >= width || y < 0 || y >= height)return Tile.VOID_TILE;
		
		switch(tileColors[x + y * width]){
		case Tile.VOID_COLOR:return Tile.VOID_TILE;
		case Tile.TEST_COLOR:return Tile.TEST_TILE;
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