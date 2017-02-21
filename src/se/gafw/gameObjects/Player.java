package se.gafw.gameObjects;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import se.gafw.Gyarb;
import se.gafw.graphics.Screen;
import se.gafw.graphics.Sprite;
import se.gafw.level.Level;

public class Player extends Entity implements MouseListener{

    private float dy = 0;
    private Direction dir = null;
    private boolean jumping = false, showInventory = false;
    private boolean[] keys = new boolean[2550];
    
    private Inventory inventory = new Inventory(Sprite.INVENTORY);
    private Toolbar toolbar = new Toolbar(Sprite.TOOLBAR);
    private Item item;
	
    enum Direction{
    	RIGHT,
    	LEFT
    }

    public Player(Level level, float x, float y) {
        super(level, x, y, Sprite.PLAYER);

        //se till att man spawnar på marken när man skapar en spelare
    	while(!collision(0, 1))
    		move(0, 1);        
    }

    //bara temporÃ¤r kod fÃ¶r att testa kollision
    public void update() {
        
    	if(!collision(0, 1))
    	{
    		//om man inte står på solid grund
    		//tyngdaccelerationen blir 12 enheter/sekund
    		//metoden kallas som sagt 60 gånger per sekund (värt att ha koll på)
    		dy += 0.15;
    		jumping = true;
    	}else if(getKeyStatus(KeyEvent.VK_SPACE) && !jumping && !showInventory)dy = -4;
    	
    	move(0, (float)dy);
    	
    	if(collision(0, 1))
		{
    		jumping = false;
    		dy = 0;
		}
    	
  
        if(getKeyStatus(KeyEvent.VK_A) && !showInventory){
        	dir = Direction.LEFT;
        	move(-1,  0);//flippa även karaktären
        }
        if(getKeyStatus(KeyEvent.VK_D) && !showInventory){
        	dir = Direction.RIGHT;
        	move( 1,  0);
        }
        
        if(getKeyStatus(KeyEvent.VK_ESCAPE)){
        	GameStateManager.setCurrentState(GameStateManager.State.PAUSE);
        	keys[KeyEvent.VK_ESCAPE] = false;
        }
    }

    public void render(Screen screen) {
        screen.renderSprite(sprite, Gyarb.WIDTH / 2 - sprite.width / 2, Gyarb.HEIGHT / 2 - sprite.height / 2,
        		/*om man går åt vänster så ska spelaren renderas flippad i x led*/dir == Direction.LEFT, false);
        
	    if(showInventory)
        	inventory.render(screen);
	    
	    toolbar.render(screen);
    }

    public int getX(){
        return (int)x;
    }

    public int getY(){
        return (int)y;
    }


	public void mousePressed(MouseEvent e) {
		//Remove and add blocks near the player
    	int minX = Gyarb.SCALE * (Gyarb.WIDTH / 2 - 100);
    	int maxX = Gyarb.SCALE * (Gyarb.WIDTH / 2 + 100);
    	int minY = Gyarb.SCALE * (Gyarb.HEIGHT / 2 - 100);
    	int maxY = Gyarb.SCALE * (Gyarb.HEIGHT / 2 + 100);
    	
    	if(e.getX() > minX && e.getX() < maxX && e.getY() > minY && e.getY() < maxY) {
	        int x = (int)(e.getX() / Gyarb.SCALE + this.x) >> 4;
	        int y = (int)(e.getY() / Gyarb.SCALE + this.y) >> 4;
	        
	        //Remove block
	        if(e.getButton() == MouseEvent.BUTTON1 && !showInventory) {
	        	//Remove dirt or grass tile
	        	if(level.getTile(x, y) == Tile.DIRT_TILE || level.getTile(x, y) == Tile.GRASS_TILE)
	        		item = Item.DIRT;
	        	//Remove stone tile
	        	else if(level.getTile(x, y) == Tile.STONE_TILE)
	        		item = Item.STONE;
	        	//Remove ruby tile
	        	else if(level.getTile(x, y) == Tile.RUBY_TILE)
	        		item = Item.RUBY;
	        	else item = null;
	            
	        	level.removeBlock(x, y);
	         
	        	if(item != null)
	        		inventory.addItem(item);
	        }
	        //Add block
	        if(e.getButton() == MouseEvent.BUTTON3 && !showInventory) {
	        	if(toolbar.stack[toolbar.currentStack * inventory.stack] != null) 
	        		//Add dirt block
	        		if(toolbar.stack[toolbar.currentStack * inventory.stack] == Item.DIRT) 
	        			level.addBlock(x, y, Tile.DIRT_COLOR);
	        		//Add stone block
	        		else if(toolbar.stack[toolbar.currentStack * inventory.stack] == Item.STONE)
	        			level.addBlock(x, y, Tile.STONE_COLOR);
	        		//Add ruby block
	        		else if(toolbar.stack[toolbar.currentStack * inventory.stack] == Item.RUBY)
	        			level.addBlock(x, y, Tile.RUBY_COLOR);
	        	
	        	for(int i = toolbar.currentStack * inventory.stack + inventory.stack - 1; i >= toolbar.currentStack * inventory.stack; i--) {
	        		if(toolbar.stack[i] != null) {
	        			toolbar.removeItem(i);
	        			break;
	        		}
	        	}
	        }
    	}
    	
        //Inventory
        if(showInventory) {
            int invX = Gyarb.SCALE * (inventory.getX() + 4);	 //X-coordinate for inventory
            int invY = Gyarb.SCALE * (inventory.getY() + 4); 	 //Y-coordinate for inventory
            int invCol = (e.getX() - invX) / (20 * Gyarb.SCALE); //Column
            int invRow = (e.getY() - invY) / (20 * Gyarb.SCALE); //Row

          //Move stack from inventory to toolbar
            if(invCol < inventory.column && e.getX() >= invX && invRow < inventory.row && e.getY() >= invY) {
                int index = inventory.stack * (invRow * inventory.column + invCol); 
               	for(int i = index; i < index + inventory.stack; i++) { //Add item to toolbar
               		if(inventory.slots[i] != null) toolbar.addItem(inventory.slots[i]);
               	}
               	for(int i = index + inventory.stack - 1; i >= index; i--) { //Remove item in inventory
               		inventory.removeItem(i);
               	}
            }    
        }
        
        //Toolbar
        int toolbarX = Gyarb.SCALE * (toolbar.getX() + 4); //X-coordinate for toolbar
        int toolbarY = Gyarb.SCALE * (toolbar.getY() + 4); //Y-coordinate for toolbar
        int toolbarCol = (e.getX() - toolbarX) / (20 * Gyarb.SCALE); //Column
        
        //Move stack from toolbar to inventory
        if(toolbarCol < toolbar.stacks && e.getX() >= toolbarX && e.getY() >= toolbarY) {
        	int index = toolbarCol * inventory.stack;
        	for(int i = index; i < index + inventory.stack; i++) { //Add item to inventory
        		if(toolbar.stack[i] != null) inventory.addItem(toolbar.stack[i]);
        	}
        	for(int i = index + inventory.stack - 1; i >= index; i--) { //Remove item in toolbar
        		toolbar.removeItem(i);
        	}
        }
	}
	public void mouseClicked(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) {	}
	
	private boolean getKeyStatus(int key) {
		return keys[key];
	}
	
	public void keyTyped(KeyEvent e) {
		if(Character.toLowerCase(e.getKeyChar()) == 'q')showInventory = !showInventory;
		
		if(Character.toLowerCase(e.getKeyChar()) == '1')toolbar.currentStack = 0;
		if(Character.toLowerCase(e.getKeyChar()) == '2')toolbar.currentStack = 1;
		if(Character.toLowerCase(e.getKeyChar()) == '3')toolbar.currentStack = 2;
		if(Character.toLowerCase(e.getKeyChar()) == '4')toolbar.currentStack = 3;
		if(Character.toLowerCase(e.getKeyChar()) == '5')toolbar.currentStack = 4;
		if(Character.toLowerCase(e.getKeyChar()) == '6')toolbar.currentStack = 5;
		if(Character.toLowerCase(e.getKeyChar()) == '7')toolbar.currentStack = 6;
		if(Character.toLowerCase(e.getKeyChar()) == '8')toolbar.currentStack = 7;
		if(Character.toLowerCase(e.getKeyChar()) == '9')toolbar.currentStack = 8;
	}
	
	public void resetKeys()
	{
		for(int i = 0; i < keys.length; i++)keys[i] = false;
	}

	public void keyPressed(KeyEvent e) 
	{
		keys[e.getKeyCode()] = true;
	}
	
	public void keyReleased(KeyEvent e)
	{
		keys[e.getKeyCode()] = false;
	}
}
