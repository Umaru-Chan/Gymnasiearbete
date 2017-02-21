package se.gafw.gameObjects;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import se.gafw.Gyarb;
import se.gafw.graphics.Screen;
import se.gafw.graphics.Sprite;
import se.gafw.level.Level;
import se.gafw.util.KeyInput;

public class Player extends Entity implements MouseListener{

    private float dy = 0;
    private Direction dir = null;
    private boolean jumping = false, showInventory = false;
    private boolean[] keys = new boolean[2550];
    
    private Inventory inventory = new Inventory(Sprite.INVENTORY);
	
    enum Direction{
    	RIGHT,
    	LEFT
    }

    public Player(Level level, float x, float y) {
        super(level, x, y, Sprite.PLAYER);

        //se till att man spawnar på marken när man skapar en spelare
    	while(!collision(0, 1))
    		move(0, 1);
        
	this.in = in;
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
    	}else if(getKeyStatus(KeyEvent.VK_SPACE) && !jumping)dy = -4;
    	
    	move(0, (float)dy);
    	
    	if(collision(0, 1))
		{
    		jumping = false;
    		dy = 0;
		}
    	
  
        if(getKeyStatus(KeyEvent.VK_A)){
        	dir = Direction.LEFT;
        	move(-1,  0);//flippa även karaktären
        }
        if(getKeyStatus(KeyEvent.VK_D)){
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
        
	    if(in.getKeyStatus(KeyEvent.VK_Q))
        	inventory.render(screen);
    }

    public int getX(){
        return (int)x;
    }

    public int getY(){
        return (int)y;
    }


	public void mousePressed(MouseEvent e) {
		//int x = (e.getX() >> 4) / Gyarb.SCALE + ((int) (this.x + .5) >> 4);
		//int y = (e.getY() >> 4) / Gyarb.SCALE + ((int) (this.y + .5) >> 4);
		int x = (int)(e.getX() / Gyarb.SCALE + this.x) >> 4;
		int y = (int)(e.getY() / Gyarb.SCALE + this.y) >> 4;
		if(e.getButton() == MouseEvent.BUTTON1)level.removeBlock(x, y);
		if(e.getButton() == MouseEvent.BUTTON3)level.addBlock(x, y, Tile.GRASS_COLOR);
	}
	public void mouseClicked(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) {	}
	
		private boolean getKeyStatus(int key)
	{
		return keys[key];
	}
	
	public void keyTyped(KeyEvent e) {
		if(Character.toLowerCase(e.getKeyChar()) == 'q')showInventory = !showInventory;
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
