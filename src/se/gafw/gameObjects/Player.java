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

public class Player extends Entity implements MouseListener{

    private float dy = 0;
    private Direction dir = null;
    private boolean jumping = false, showInventory = false, walking = false;
    private boolean[] keys = new boolean[2550];
    
    private Inventory inventory = new Inventory(Sprite.INVENTORY);
    private Toolbar toolbar = new Toolbar(Sprite.TOOLBAR);
    private Item item;
    
    private Sprite[] walkingSprites = new Sprite[2];
    private int timer = 0, currentSprite = 0;//för att animera karaktären
	
    enum Direction{
    	RIGHT,
    	LEFT
    }

    public Player(Level level, float x, float y) {
        super(level, x, y, Sprite.PLAYER);

        //se till att man spawnar pÃ¥ marken nÃ¤r man skapar en spelare
    	while(!collision(0, 1))
    		move(0, 1);        
    	
    	walkingSprites[0] = Sprite.PLAYER_WALKING_1;
    	walkingSprites[1] = Sprite.PLAYER_WALKING_2;
    }

    //bara temporÃƒÂ¤r kod fÃƒÂ¶r att testa kollision
    public void update() {
        //anta alltid att spelaren inte rör sig
    	walking = false;
        timer ++;
        
    	if(!collision(0, 1))
    	{
    		//om man inte stÃ¥r pÃ¥ solid grund
    		//tyngdaccelerationen blir 12 enheter/sekund
    		//metoden kallas som sagt 60 gÃ¥nger per sekund (vÃ¤rt att ha koll pÃ¥)
    		dy += 0.15;
    		jumping = true;
    		sprite = Sprite.PLAYER_JUMPING;
    	}else if(getKeyStatus(KeyEvent.VK_SPACE) && !jumping && !showInventory)dy = -4;
    	
    	move(0, (float)dy);
    	
    	if(collision(0, 1))
		{
    		sprite = Sprite.PLAYER;
    		jumping = false;
    		dy = 0;
		}
    	
  
        if(getKeyStatus(KeyEvent.VK_A) && !showInventory){
        	dir = Direction.LEFT;
        	move(-1,  0);//flippa Ã¤ven karaktÃ¤ren
        	walking = true;
        }
        if(getKeyStatus(KeyEvent.VK_D) && !showInventory){
        	dir = Direction.RIGHT;
        	move( 1,  0);
        	walking = true;
        }
        
        if(getKeyStatus(KeyEvent.VK_ESCAPE)){
        	GameStateManager.setCurrentState(GameStateManager.State.PAUSE);
        	keys[KeyEvent.VK_ESCAPE] = false;
        }
        
        if(walking && !jumping)
        {
        	if(timer % 20 == 0)currentSprite++;//TODO teak timing
        	sprite = walkingSprites[currentSprite % walkingSprites.length];
        	//för att förhindra att spelet krashar om timer blir mer än Integer.MAX_VALUE 
        	//(detta skulle kräva att spelet kördes ett par år non stop så ganska onödig kod)
        	if(timer > 2.14*1e9)timer = 0;
        }
    }

    public void render(Screen screen, Graphics2D g2d) {
        screen.renderSprite(sprite, Gyarb.WIDTH / 2 - sprite.width / 2, Gyarb.HEIGHT / 2 - sprite.height / 2,
        		/*om man gÃ¥r Ã¥t vÃ¤nster sÃ¥ ska spelaren renderas flippad i x led*/dir == Direction.LEFT, false);
        
	    if(showInventory)
        	inventory.render(screen);
	    
	    toolbar.render(screen);
    }
    
    public void render(Screen screen){}

    public int getX(){
        return (int)x;
    }

    public int getY(){
        return (int)y;
    }


	public void mousePressed(MouseEvent e) {
		//REMOVE AND ADD BLOCKS NEAR THE PLAYER
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
	        if(e.getButton() == MouseEvent.BUTTON3 && !showInventory && !level.getTile(x, y).solid()) {
	        	if(toolbar.getStack()[toolbar.getCurrentStack() * toolbar.getStackSize()] != null) 
	        		//Add dirt block
	        		if(toolbar.getStack()[toolbar.getCurrentStack() * toolbar.getStackSize()] == Item.DIRT) 
	        			level.addBlock(x, y, Tile.DIRT_COLOR);
	        		//Add stone block
	        		else if(toolbar.getStack()[toolbar.getCurrentStack() * toolbar.getStackSize()] == Item.STONE)
	        			level.addBlock(x, y, Tile.STONE_COLOR);
	        		//Add ruby block
	        		else if(toolbar.getStack()[toolbar.getCurrentStack() * toolbar.getStackSize()] == Item.RUBY)
	        			level.addBlock(x, y, Tile.RUBY_COLOR);
	        	
	        	for(int i = toolbar.getCurrentStack() * toolbar.getStackSize() + toolbar.getStackSize() - 1; 
	        			i >= toolbar.getCurrentStack() * toolbar.getStackSize(); i--) {
	        		if(toolbar.getStack()[i] != null) {
	        			toolbar.removeItem(i);
	        			break;
	        		}
	        	}
	        }
    	}
    	
        //INVENTORY
        if(showInventory) {
            int invX = Gyarb.SCALE * (inventory.getX() + 4);	 //X-coordinate for inventory
            int invY = Gyarb.SCALE * (inventory.getY() + 4); 	 //Y-coordinate for inventory
            int invCol = (e.getX() - invX) / (20 * Gyarb.SCALE); //Column
            int invRow = (e.getY() - invY) / (20 * Gyarb.SCALE); //Row

            //Move stack from inventory to toolbar
            if(invCol < inventory.getColumn() && e.getX() >= invX && invRow < inventory.getRow() && e.getY() >= invY) {
                int index = inventory.getStackSize() * (invRow * inventory.getColumn() + invCol); 
               	for(int i = index; i < index + inventory.getStackSize(); i++) { //Add item to toolbar
               		if(inventory.getSlots()[i] != null) toolbar.addItem(inventory.getSlots()[i]);
               	}
               	for(int i = index + inventory.getStackSize() - 1; i >= index; i--) { //Remove item in inventory
               		inventory.removeItem(i);
               	}
            }    
        }
        
        //TOOLBAR
        int toolbarX = Gyarb.SCALE * (toolbar.getX() + 4); //X-coordinate for toolbar
        int toolbarY = Gyarb.SCALE * (toolbar.getY() + 4); //Y-coordinate for toolbar
        int toolbarCol = (e.getX() - toolbarX) / (20 * Gyarb.SCALE); //Column
        
        //Move stack from toolbar to inventory
        if(toolbarCol < toolbar.getStackAmount() && e.getX() >= toolbarX && e.getY() >= toolbarY) {
        	int index = toolbarCol * toolbar.getStackSize();
        	for(int i = index; i < index + toolbar.getStackSize(); i++) { //Add item to inventory
        		if(toolbar.getStack()[i] != null) inventory.addItem(toolbar.getStack()[i]);
        	}
        	for(int i = index + toolbar.getStackSize() - 1; i >= index; i--) { //Remove item in toolbar
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
		
		//lite mindre lmao
		if(Character.isDigit(e.getKeyChar()) && e.getKeyChar() != '0')
			toolbar.setCurrentStack(Integer.parseInt("" + e.getKeyChar()) - 1);
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
