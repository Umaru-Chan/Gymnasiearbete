package se.gafw.level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import se.gafw.gameObjects.Entity;
import se.gafw.graphics.Screen;
import se.gafw.graphics.Sprite;
import se.gafw.graphics.SpriteSheet;

public class Level {

	public static final Level TEST = new Level(new Random(), 256, 256, new int[]{0xffffffff, 0xff000000});
	
	private int width, height;
	//private Tile[] tiles;
	private int[] tileColors;
	private int xOffs, yOffs;
	private long updates; //för att hålla koll på hur många uppdateringar som har skett
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
        
        //för höjden på jordnivån och stennivån
        int[] dirtTop = new int[width], stoneTop = new int[width];
        int[] specialStones = new int[width * depth];
        
        int[] tileColors = new int[width * depth];
        
        for(int i = 0; i < width; i++)
        {
            dirtTop[i] = depth / 2 - random.nextInt(amplitude);
            stoneTop[i] = depth / 2 - random.nextInt(amplitude);
        }

        //generera ngra random värden för speciella stenar
        for(int x = 0; x < width; x++)
        	for(int y = 0; y < depth; y++)
        		specialStones[x + y * width] = (random.nextInt(40) == 0) ? 1 : 0;
        
        for(int x = 1; x < width - 1; x++)
        {
            for(int y = depth / 3; y < depth; y++)
            {
            	//för att världen inte ska se ut som en spikmatta
            	int avgDirt = (dirtTop[x] + dirtTop[x-1] + dirtTop[x+1]) / 3;
            	int avgStone = (stoneTop[x] + stoneTop[x-1] + stoneTop[x+1]) / 3 + 5;
            	
                if(y /*+ depth*/ >= avgDirt)
                {      	
                	if(y >= avgStone)
            		{
                		tileColors[x + y * width] = Tile.STONE_COLOR;
                		//under stenlagret, om randomsaken, lägg till ett coolt block
                		if(specialStones[x + y * width] == 1)tileColors[x + y * width] = Tile.RUBY_COLOR;
            		
            		}
                	else tileColors[x + y * width] = Tile.DIRT_COLOR;
                }
                else tileColors[x + y * width] = Tile.VOID_COLOR;
            }
        }
        
        //temp
        for(int i = 0; i < tileColors.length; i++)
        	if(tileColors[i] == 0)tileColors[i] = Tile.VOID_COLOR;
        
        return new Level(tileColors, width, depth);
    }

	/**
	 * Generera en slumpmÃ¤ssig uppsÃ¤ttning tiles.
	 * @param random
	 * @param width
	 * @param height
	 * @param tile  	en array med fÃ¤rgerna fÃ¶r alla tiles som man vill ha
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
	
	//anvÃ¤nd inte atm
	@Deprecated
	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		//tiles = new Tile[width * height];
		tileColors = new int[width * height];
	}
	
	public void render(Screen screen, int xOffs, int yOffs) {
		screen.setScroll(xOffs, yOffs);
		this.xOffs = xOffs;
		this.yOffs = yOffs;
		int x0 = xOffs >> 4;// >> 4 = / 16
		int x1 = (xOffs + screen.width + 16) >> 4;
		int y0 = yOffs >> 4;
		int y1 = (yOffs + screen.height + 16) >> 4;
		//TODO matte
		
		//if(xp < 0 || xp >= width)
		
		Tile temp = Tile.VOID_TILE;
		for(int y = y0; y < y1; y++){
			for(int x = x0; x < x1; x++){
				temp = getTile(x, y);
				if(!temp.equals(Tile.VOID_TILE))
					temp.render(screen, x, y);
			}
		}
		
		//rendera allt Ã¶ver alla tiles
		renderEntities(screen);
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @return tilen pÃ¥ x och y (notera att x Ã¥ y inte kan vara stÃ¶rre Ã¤n bredden och hÃ¶jden av nivÃ¥n)
	 */
	public Tile getTile(int x, int y){
		if(outOfBounds(x, y))return Tile.VOID_TILE;
		
		switch(tileColors[x + y * width]){
		case Tile.VOID_COLOR:	return Tile.VOID_TILE;
		case Tile.GRASS_COLOR:	return Tile.GRASS_TILE;
		case Tile.DIRT_COLOR:	return Tile.DIRT_TILE;
		}
		
		return Tile.VOID_TILE;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return true om x och y Ã¤r utanfÃ¶r nivÃ¥n
	 */
	private boolean outOfBounds(int x, int y){
		if(x < 0 || x >= width || y < 0 || y >= height)return true;
		return false;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public void removeBlock(int x, int y)
	{
		if(getTile(x, y).equals(Tile.VOID_TILE))return;//ta inte bort void
		tileColors[x + y * width] = Tile.VOID_COLOR;
		updateGrass();
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public void addBlock(int x, int y, int tileColor)
	{
		if(!getTile(x, y).equals(Tile.VOID_TILE) || outOfBounds(x, y))return;//bygg inte pÃ¥ annat Ã¤n void
		tileColors[x + y * width] = tileColor;
		updateGrass();
	}
	
	private void renderEntities(Screen screen){
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(screen);
		}
	}
	
	public void update() {
		for (int i = 0; i < entities.size(); i++)
			entities.get(i).update();
		updateGrass();
	}
	
	private void updateGrass()
	{
		//kan uppenbarligen inte ha ngt över jord över det översta lagret av nivån, därför börjar man scanna
		//på width + 1
		for(int x = 0; x < width; x++)
		{
			for(int y = 0; y < height - 1; y++)
			{
				//kolla om det finns ett block (inte void) över gräs
				if(tileColors[x + y * width] != Tile.VOID_COLOR
						&& tileColors[x + (y + 1) * width] == Tile.GRASS_COLOR)
					tileColors[x + (y + 1) * width] = Tile.DIRT_COLOR;
				
				//kolla om det finns void över dirt
				if(tileColors[x + y * width] == Tile.VOID_COLOR
						&& tileColors[x + (y + 1) * width] == Tile.DIRT_COLOR)
					tileColors[x + (y + 1) * width] = Tile.GRASS_COLOR;
				//TODO göra så att det måste vara void hela vägen upp innan ett dirt blir gräs
				
			}
		}
	}
	
	
	public void addEntity(Entity entity) {
		entities.add(entity);
	}
	
	public void killEntity(Entity entity) {
		entities.remove(entity);
	}
}
