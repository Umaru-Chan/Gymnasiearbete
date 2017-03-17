package se.gafw.gameObjects;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import se.gafw.Gyarb;
import se.gafw.gameState.GameStateManager;
import se.gafw.graphics.Screen;
import se.gafw.graphics.Sprite;
import se.gafw.level.Level;
import se.gafw.level.Tile;

/**
 * The class Player creates a player that the user of the game controls with W-A-D.
 * 
 * The player can move left, right and jump with animations. When jumping it accelerates 
 * downwards with 12 unites per second. 
 * 
 * If the user press the Q button a inventory is rendering. The inventory can store 
 * items that can be used later. When the inventory is rendering the player can not
 * walk, jump, remove or place blocks.
 * 
 * The player can remove and place blocks. When a blocked is removed the player
 * gets it as an item in the inventory that can be used later.
 *
 * BUG: the player can place tiles on himself
 */
public class Player extends Entity implements MouseListener{

    private float dy = 0;
    private Direction dir = null; 
    private boolean jumping = false, showInventory = false, walking = false;
    private boolean[] keys = new boolean[2550];
    
    private Inventory inventory = new Inventory(Sprite.INVENTORY); //Inventory
    private Toolbar toolbar = new Toolbar(Sprite.TOOLBAR); //Toolbar
    private Item item;
    
    private Sprite[] walkingSprites = new Sprite[2]; //Array of sprites for animations
    private int timer = 0, currentSprite = 0;//Timer and integer for the animations
	
    /**
     * Enum for the direction the player is moving. The sprite 
     * switches direction if the player is going to left or right.
     */
    enum Direction{
    	RIGHT,
    	LEFT
    }

    /**
     * Class constructor. Creates a new player.
     * @param level
     * @param x, x-coordinate.
     * @param y, y-coordinate.
     */
    public Player(Level level, float x, float y) {
        super(level, x, y, Sprite.PLAYER);

    	//Checks for collision so the player spawns on the ground.
    	while(!collision(0, 1)) 
    		move(0, 1);        
    	
    	//Sprites for animations
    	walkingSprites[0] = Sprite.PLAYER_WALKING_1;
    	walkingSprites[1] = Sprite.PLAYER_WALKING_2;
    }

    /**
     * Update change variables, moves the player and changes the
     * players sprite for animations. The method is called 60 times
     * per second.
     */
    public void update() {
    	walking = false;
        timer++;
        
        //If the player is not on solid ground than it accelerates downwards with 12 units/seconds.
    	if(!collision(0, 1)) {
    		dy += 0.15;
    		jumping = true; 
    		sprite = Sprite.PLAYER_JUMPING; //Sprite that renders when the player jumps
    	}

    	//Jump if space is pressed.
    	else if(getKeyStatus(KeyEvent.VK_SPACE) && !jumping && !showInventory)dy = -4;
    	
    	move(0, (float)dy);
    	
    	if(collision(0, 1)) { 
    		sprite = Sprite.PLAYER; //The sprite that renders when the player is on the ground.
    		jumping = false; 
    		dy = 0; //No acceleration downwards.
		}
    	
    	//Move left if A is pressed
    	if(getKeyStatus(KeyEvent.VK_A) && !showInventory){
        	dir = Direction.LEFT; 
        	move(-1,  0);
        	walking = true;
        }

    	//Move right if D is pressed
    	if(getKeyStatus(KeyEvent.VK_D) && !showInventory){
        	dir = Direction.RIGHT;
        	move( 1,  0);
        	walking = true;
        }
        
        //Pause the game with escape button 
        if(getKeyStatus(KeyEvent.VK_ESCAPE)){
        	GameStateManager.setCurrentState(GameStateManager.State.PAUSE); //Sets the state to PAUSE.
        	keys[KeyEvent.VK_ESCAPE] = false;
        }
        
        //Animations for walking
        if(walking && !jumping){
        	if(timer % 20 == 0)currentSprite++;
        	sprite = walkingSprites[currentSprite % walkingSprites.length];
        	if(timer > 2.14*1e9)timer = 0; //If timer reaches it´s maximum value.
        }
    }

    /**
     * Renders the player on the middle of the window and the toolbar is rendered at the button of the window. 
     * If boolean showInventory equals true the inventory is rendering on the middle of the screen.
     * @param screen
     * @param g2d
     */
    public void render(Screen screen, Graphics2D g2d) {
        screen.renderSprite(sprite, Gyarb.WIDTH / 2 - sprite.width / 2, Gyarb.HEIGHT / 2 - sprite.height / 2,
        		/*om man gÃ¥r Ã¥t vÃ¤nster sÃ¥ ska spelaren renderas flippad i x led*/dir == Direction.LEFT, false);
        
	    if(showInventory) //Render inventory if showInventory equals true.
        	inventory.render(screen);
	    
	    toolbar.render(screen);
    }
    
    public void render(Screen screen){}

    /**
     * Return the x-coordinate of the player. 
     * @return x, x-coordinate
     */
    public int getX(){
        return (int)x;
    }

    /**
     * Return the y-coordinate of the player.
     * @return y, y-coordinate
     */
    public int getY(){
        return (int)y;
    }


    /**
     * MousePressed is called if the user clicks the mouse. 
     * 
     * The user can remove and place blocks near the player and
     * move items from the inventory to the toolbar and the other way around.
     */
	public void mousePressed(MouseEvent e) {
		//Integers to only remove and place blocks near the player
    	int minX = Gyarb.SCALE * (Gyarb.WIDTH / 2 - 100);  //Minimum x-coordinate
    	int maxX = Gyarb.SCALE * (Gyarb.WIDTH / 2 + 100);  //Maximum x-coordinate
    	int minY = Gyarb.SCALE * (Gyarb.HEIGHT / 2 - 100); //Minimum y-coordinate
    	int maxY = Gyarb.SCALE * (Gyarb.HEIGHT / 2 + 100); //Maximum y-coordinate
    	
    	if(e.getX() > minX && e.getX() < maxX && e.getY() > minY && e.getY() < maxY) {
	        int x = (int)(e.getX() / Gyarb.SCALE + this.x) >> 4; //x coordinate for blocks
	        int y = (int)(e.getY() / Gyarb.SCALE + this.y) >> 4; //y coordinate for blocks
	        
	        //Remove block
	        if(e.getButton() == MouseEvent.BUTTON1 && !showInventory) {
	        	//Remove dirt or grass tile
	        	if(level.getTile(x, y) == Tile.DIRT_TILE || level.getTile(x, y) == Tile.GRASS_TILE)
	        		item = Item.DIRT; //The player gets a dirt item.
	        	//Remove stone tile
	        	else if(level.getTile(x, y) == Tile.STONE_TILE)
	        		item = Item.STONE; //The player gets a stone item.
	        	//Remove diamond tile
	        	else if(level.getTile(x, y) == Tile.DIAMOND_TILE)
	        		item = Item.DIAMOND; //The player gets a diamond item.
	        	else item = null;
	            
	        	level.removeBlock(x, y); //Removes the block at the x and y coordinate for the block
	         
	        	if(item != null) //Add the item to the inventory
	        		inventory.addItem(item);
	        }
	        //Place block 
	        if(e.getButton() == MouseEvent.BUTTON3 && !showInventory && !level.getTile(x, y).solid()) {
	        	if(toolbar.getStack()[toolbar.getCurrentStack() * toolbar.getStackSize()] != null) 
	        		//Place dirt block
	        		if(toolbar.getStack()[toolbar.getCurrentStack() * toolbar.getStackSize()] == Item.DIRT) 
	        			level.addBlock(x, y, Tile.DIRT_COLOR); //Places a dirt block
	        		//Place stone block
	        		else if(toolbar.getStack()[toolbar.getCurrentStack() * toolbar.getStackSize()] == Item.STONE)
	        			level.addBlock(x, y, Tile.STONE_COLOR); //Places a stone block
	        		//Place diamond block
	        		else if(toolbar.getStack()[toolbar.getCurrentStack() * toolbar.getStackSize()] == Item.DIAMOND)
	        			level.addBlock(x, y, Tile.DIAMOND_COLOR); //Places a diamond block
	        	
	        	//Removes a item from the toolbar when a block is placed.
	        	for(int i = toolbar.getCurrentStack() * toolbar.getStackSize() + toolbar.getStackSize() - 1; 
	        			i >= toolbar.getCurrentStack() * toolbar.getStackSize(); i--) {
	        		if(toolbar.getStack()[i] != null) { 
	        			toolbar.removeItem(i);
	        			break;
	        		}
	        	}
	        }
    	}
    	
        //Move a stack from the inventory to the toolbar
        if(showInventory) {
            int invX = Gyarb.SCALE * (inventory.getX() + 4);	 //X-coordinate for inventory
            int invY = Gyarb.SCALE * (inventory.getY() + 4); 	 //Y-coordinate for inventory
            int invCol = (e.getX() - invX) / (20 * Gyarb.SCALE); //Column that is pressed
            int invRow = (e.getY() - invY) / (20 * Gyarb.SCALE); //Row that is pressed

            //Move stack from inventory to toolbar
            if(invCol < inventory.getColumn() && e.getX() >= invX && invRow < inventory.getRow() && e.getY() >= invY) {
            	//The index of the first item in the stack that is going to be moved to the toolbar.
                int index = inventory.getStackSize() * (invRow * inventory.getColumn() + invCol); 
                
               	for(int i = index; i < index + inventory.getStackSize(); i++) { //Add items to toolbar
               		if(inventory.getSlots()[i] != null) toolbar.addItem(inventory.getSlots()[i]);
               	}
               	for(int i = index + inventory.getStackSize() - 1; i >= index; i--) { //Remove items in inventory
               		inventory.removeItem(i);
               	}
            }    
        }
        
        //Toolbar
        int toolbarX = Gyarb.SCALE * (toolbar.getX() + 4); //X-coordinate for toolbar
        int toolbarY = Gyarb.SCALE * (toolbar.getY() + 4); //Y-coordinate for toolbar
        int toolbarCol = (e.getX() - toolbarX) / (20 * Gyarb.SCALE); //Column that is pressed.
        
        //Move stack from toolbar to inventory
        if(toolbarCol < toolbar.getStackAmount() && e.getX() >= toolbarX && e.getY() >= toolbarY) {
        	//The index of the first item of the stack that is going to be moved to the inventory.
        	int index = toolbarCol * toolbar.getStackSize();
        	
        	for(int i = index; i < index + toolbar.getStackSize(); i++) { //Add items to inventory
        		if(toolbar.getStack()[i] != null) inventory.addItem(toolbar.getStack()[i]);
        	}
        	for(int i = index + toolbar.getStackSize() - 1; i >= index; i--) { //Remove items in toolbar
        		toolbar.removeItem(i);
        	}
        }
	}
	
	//Implemented methods from MouseListener that is never used. 
	public void mouseClicked(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) {	}
	
	/**
	 * Get the key status.
	 * @param key, integer for the key.
	 * @return
	 */
	private boolean getKeyStatus(int key) {
		return keys[key];
	}
	
	/**
	 * The method invokes when a key has been typed.
	 * @param e
	 */
	public void keyTyped(KeyEvent e) {
		//Render inventory
		if(Character.toLowerCase(e.getKeyChar()) == 'q')showInventory = !showInventory;
		
		//Sets the current stack in toolbar with the number keys.
		if(Character.isDigit(e.getKeyChar()) && e.getKeyChar() != '0')
			toolbar.setCurrentStack(Integer.parseInt("" + e.getKeyChar()) - 1);
	}
	
	/**
	 * Reset the key array. 
	 */
	public void resetKeys(){
		for(int i = 0; i < keys.length; i++)keys[i] = false;
	}

	/**
	 * The method invokes when a key is pressed.
	 * @param e, KeyEvent
	 */
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}
	
	/**
	 * The method invokes when a key is released.
	 * @param e, KeyEvent
	 */
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

}
