package se.gafw.level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import se.gafw.gameObjects.Entity;
import se.gafw.graphics.Screen;

/**
 * 
 * Level is a class used to describe the viritual enviroment that the player moves around in and changes. The level contains data
 * for all the tiles that will be rendered and the player will collide with, remove and add. The Level class also contain all the 
 * entities for instances of level, all entities will be rendered and updated when the level is rendered and updated.
 *
 */

public class Level {

	// Stores the level width and height in ammount of tiles.
	private int width, height;
	// Stores all the tiles of the level, the tiles are represented in hexadecimal color (RGB)
	private int[] tileColors;
	
	// Stores all the entities in the level
	private ArrayList<Entity> entities = new ArrayList<>();

	/**
	 * loads a level from a .PNG (other formats work but they are not recommended because 
	 * 		of compression) file.
	 * @param path a file path to the level file (.png or other). Represented as eighter an absolute path or 
	 * a path relative to the runnable .jar file. 
	 */
	public Level(String path){
		try{
			// load the level from disk
			BufferedImage image = ImageIO.read(getClass().getResourceAsStream(path));
			this.width = image.getWidth();
			this.height = image.getHeight();
			image.getRGB(0,0,width, height, tileColors, 0, width);
		}catch(IOException e){
			System.err.println("failed loading level!!");
			e.printStackTrace();
		}
	}

	/**
	 * Used when generating a random level. Creates a level from predefined parameters.
	 * 
	 * @param tileColors The level data
	 * @param width		 The level width
	 * @param height	 The level height
	 */
    private Level(int[] tileColors, int width, int height)
    {
        this.tileColors = tileColors;
        this.width = width;
        this.height = height;
    }

    /**
     * Generats and returns a random level.
     * @param amplitude The maximal difference between the tallest mountain and deepest valley, meassured in ammount of Tiles.
     * @param width The width of the generated level, meassured in amount of Tiles.
     * @param depth The depth of the generated level, meassured in amount of Tiles.
     * @return The random level object
     */
   public static Level randomLevel(int amplitude, int width, int depth)
    {
	   /*
	    * The level is generated in columns, every column have a dirt surface level and a stone level underneath it.
	    */
	   
	    // The random object provides more utility than the simple (minimum + Math.random() * (maximum - minimum)) does. 
        Random random = new Random();
        
        // used to store where the dirt surface and the stone layer top is.
        int[] dirtTop = new int[width], stoneTop = new int[width];
        // used to store where minerals will be placed
        int[] specialStones = new int[width * depth];
        
        // this array will be filled and then used when returning the final level object.
        int[] tileColors = new int[width * depth];
        
        // generate some random numbers for the top layers
        for(int i = 0; i < width; i++)
        {
            dirtTop[i] = depth / 2 - random.nextInt(amplitude);
            stoneTop[i] = depth / 2 - random.nextInt(amplitude);
        }

        // generate some random numbers that represent locations for special stones
        for(int x = 0; x < width; x++)
        	for(int y = 0; y < depth; y++)
        		specialStones[x + y * width] = (random.nextInt(100 - y / 5) == 0) ? 1 : 0;
        
        // fill the random world with dirt and stone (and void over the dirt level)
        for(int x = 1; x < width - 1; x++)
        {
            for(int y = depth / 3; y < depth; y++)
            {
            	/*
            	 * average out the height of each column with the two columns next to it so that the world does not
            	 * look too artificial. 
            	 */
            	int avgDirt = (dirtTop[x] + dirtTop[x-1] + dirtTop[x+1]) / 3;
            	int avgStone = (stoneTop[x] + stoneTop[x-1] + stoneTop[x+1]) / 3 + 5;
            	
            	// check if we are under the dirt surface
                if(y >= avgDirt)
                {      	
                	// if we are under the dirt surface and the first layer of stone, place a stone Tile, otherwise place dirt
                	if(y >= avgStone)
            		{
                		tileColors[x + y * width] = Tile.STONE_COLOR;
            		}
                	else tileColors[x + y * width] = Tile.DIRT_COLOR;
                }
                // if we are not under the dirt surface place a void block
                else tileColors[x + y * width] = Tile.VOID_COLOR;
            }
        }
        
        /*
         * place all the minerals (special tiles, the only one in the game at the moment is ruby).
         */
        for(int x = 1; x < width - 1; x++)
        {
        	for(int y = depth / 3; y < depth - 1; y++)
        	{
        		// same calculations as earlier to get the same stone top layer
        		int avgStone = (stoneTop[x] + stoneTop[x-1] + stoneTop[x+1]) / 3 + 8;
        		
        		// if we are under the stone top layer, it's time to check wether or not to place a special Tile
        		if(y >= avgStone)
        		{
        			// if the random array for special tiles generated a '1' in the current location then place a Tile or a bunch of Tiles
        			if(specialStones[x + y * width] == 1)
    				{
        				// The middle tile will always be a ruby
        				tileColors[x + y * width] = Tile.RUBY_COLOR;
        				// if the player is lucky, more tiles will be added next to the ruby center
        				if(random.nextBoolean()) tileColors[x + (y - 1) * width] = Tile.RUBY_COLOR;
        				if(random.nextBoolean()) tileColors[x + (y + 1) * width] = Tile.RUBY_COLOR;
        				if(random.nextBoolean()) tileColors[(x - 1) + y * width] = Tile.RUBY_COLOR;
        				if(random.nextBoolean()) tileColors[(x + 1) + y * width] = Tile.RUBY_COLOR;
        				// if the player is really lucky add even more ruby blocks diagonally from the center
        				if(random.nextBoolean() && random.nextBoolean()) tileColors[(x - 1) + (y - 1) * width] = Tile.RUBY_COLOR;
        				if(random.nextBoolean() && random.nextBoolean()) tileColors[(x - 1) + (y + 1) * width] = Tile.RUBY_COLOR;
        				if(random.nextBoolean() && random.nextBoolean()) tileColors[(x + 1) + (y - 1) * width] = Tile.RUBY_COLOR;
        				if(random.nextBoolean() && random.nextBoolean()) tileColors[(x + 1) + (y + 1) * width] = Tile.RUBY_COLOR;
    				}
        		}
        	}
        }
        
        // checkinf for errors, if there is a block that is somehow (should not be possible) not occupied, just add a void tile.
        for(int i = 0; i < tileColors.length; i++)
        	if(tileColors[i] == 0)tileColors[i] = Tile.VOID_COLOR;
        
        // return the new level
        return new Level(tileColors, width, depth);
    }
   
   	/**
   	 * old constructor, not used anymore.
   	 * @param width level width in Tiles
   	 * @param height level height in Tiles
   	 */
	@Deprecated
	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		tileColors = new int[width * height];
	}
	
	/**
	 * render all the enteties in the level and all the tiles in view of the player. An entity will be rendered even if it is not in view
	 * of the player. A system to check wether or not the entity is in range of the player will, in the games current state, only be 
	 * detremental because there is no entities. Calling render(xxx) 0 times is better than 
	 * having a lot of code for checking range(would be pythagoras). 
	 * @param screen the screen to render the level to
	 * @param xOffs  the player x-position (meassured in pixels), so that we only render the Tiles visible to the player.
	 * @param yOffs  the player y-position (meassured in pixels), so that we only render the Tiles visible to the player.
	 */
	public void render(Screen screen, int xOffs, int yOffs) {
		screen.setScroll(xOffs, yOffs);
		/*
		*  when dividing or multiplying an integer with any power of 2 it is more efficient to use bit operations.
		*  these numbers work as the bounds for the screen, ignore all Tiles that have an x-coordinate lower than x0 or higher than x1.
		*  the same goes for y-coordinates.
		*/
		int x0 = xOffs >> 4;
		int x1 = (xOffs + screen.width + 16) >> 4;
		int y0 = yOffs >> 4;
		int y1 = (yOffs + screen.height + 16) >> 4;
		
		// this allows us to only call getTile once.
		Tile temp = Tile.VOID_TILE;
		// loop through the tiles at the positions that we want to render
		for(int y = y0; y < y1; y++){
			for(int x = x0; x < x1; x++){
				temp = getTile(x, y);
				// don't render void!!
				if(!temp.equals(Tile.VOID_TILE))
					temp.render(screen, x, y);
			}
		}
		
		// finish the rendering by rendering all the entities, this, ofcourse, will happen after (over) the rendering of the tiles.
		renderEntities(screen);
	}

	/**
	 * 
	 * @param x the x-coordinate of the tile, not meassured in pixels but in tile coordinates
	 * @param y the y-coordinate of the tile, not meassured in pixels but in tile coordinates
	 * @return the tile at x and y
	 */
	public Tile getTile(int x, int y){
		
		// there are no tiles outside of the map, but let's just pretend that they are void tiles.
		if(outOfBounds(x, y))return Tile.VOID_TILE;
		
		switch(tileColors[x + y * width]){
			case Tile.VOID_COLOR:	return Tile.VOID_TILE;
			case Tile.GRASS_COLOR:	return Tile.GRASS_TILE;
			case Tile.DIRT_COLOR:	return Tile.DIRT_TILE;
			case Tile.STONE_COLOR:	return Tile.STONE_TILE;
			case Tile.RUBY_COLOR: 	return Tile.RUBY_TILE;
		}
		
		// this should never be called but java complains if there is no return after the switch.
		return Tile.VOID_TILE;
	}
	
	/**
	 * 
	 * @param x the x-coordinate of the tile, not meassured in pixels but in tile coordinates
	 * @param y the y-coordinate of the tile, not meassured in pixels but in tile coordinates
	 * @return true if the x and y -coordinates point to a block that is outside the bounds of the map.
	 */
	private boolean outOfBounds(int x, int y){
		if(x < 0 || x >= width || y < 0 || y >= height)return true;
		return false;
	}
	
	/**
	 * removes the tile at (x, y)
	 * @param x the x-coordinate of the tile, not meassured in pixels but in tile coordinates
	 * @param y the y-coordinate of the tile, not meassured in pixels but in tile coordinates
	 */
	public void removeBlock(int x, int y)
	{
		// don't remove a void tile, that would be a waste of time since removing a tile means replacing it with a void tile
		if(getTile(x, y).equals(Tile.VOID_TILE))return;
		
		tileColors[x + y * width] = Tile.VOID_COLOR;
		//there is a chance that the player removed a tile ontop of a dirt block, then update it so that the dirt block becomes new grass
		updateGrass();
	}
	
	/**
	 * adds a tile at (x, y), the type of tile that's added is determined by the tileColor parameter
	 * @param x the x-coordinate of the tile, not meassured in pixels but in tile coordinates
	 * @param y the y-coordinate of the tile, not meassured in pixels but in tile coordinates
	 * @param tileColor the color representing the tile that you want to add
	 */
	public void addBlock(int x, int y, int tileColor)
	{
		if(!getTile(x, y).equals(Tile.VOID_TILE) || outOfBounds(x, y))return;//bygg inte pÃ¥ annat Ã¤n void
		tileColors[x + y * width] = tileColor;
		updateGrass();
	}
	
	/**
	 * loop through all the enteties and render them.
	 * @param screen the screen for rendering entities
	 */
	private void renderEntities(Screen screen){
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(screen);
		}
	}
	
	/**
	 * updates all the entities
	 */
	public void update() {
		for (int i = 0; i < entities.size(); i++)
			entities.get(i).update();
		//updateGrass();
	}
	
	/**
	 * check 2 cases
	 * 
	 * 1) if there is a dirt tile with void above it, replace that dirt with grass.
	 * 2) if there is a grass tile with something other than void above it, replace it with dirt
	 */
	private void updateGrass()
	{
		for(int x = 0; x < width; x++)
		{
			for(int y = 0; y < height - 1; y++)
			{
				// check to see if there is a tile (not void) above grass.
				if(tileColors[x + y * width] != Tile.VOID_COLOR
						&& tileColors[x + (y + 1) * width] == Tile.GRASS_COLOR)
					tileColors[x + (y + 1) * width] = Tile.DIRT_COLOR;
				
				// check if there is void over dirt
				if(tileColors[x + y * width] == Tile.VOID_COLOR
						&& tileColors[x + (y + 1) * width] == Tile.DIRT_COLOR)
					tileColors[x + (y + 1) * width] = Tile.GRASS_COLOR;	
			}
		}
	}
	
	/**
	 * adds Entity entity to the list of entities
	 * @param entity the entitie to be added
	 */
	public void addEntity(Entity entity) {
		entities.add(entity);
	}
	/**
	 * removes Entity entity to the list of entities
	 * @param entity the entitie to be removed
	 */	
	public void killEntity(Entity entity) {
		entities.remove(entity);
	}
	
	/**
	 * @return the level width in blocks
	 */
	public int getWidth()
	{
		return width;
	}
}