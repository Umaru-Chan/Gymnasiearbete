package se.gafw.gameObjects;


import se.gafw.Gyarb;
import se.gafw.graphics.Screen;
import se.gafw.graphics.Sprite;

public class Inventory {
	
	public int row = 3;
	public int column = 9;
	private int size = row * column;
	public int stack = 64;
	
	public Item[] slots = new Item[size * stack]; 
	private Sprite sprite;
	
	private int x, y;
				
	/**
	 * Class constructor.
	 * @param sprite
	 */
	public Inventory(Sprite sprite) {
		this.sprite = sprite;
	}
	
	/**
	 * 
	 * @param item
	 */
	public void addItem(Item item) {
		int emptySlot = findSlot(item);
		if(emptySlot >= 0) {
			slots[emptySlot] = item;
		}
		else if(emptySlot < 0)
			System.out.println("Inventory is full");
	}
	
	/**
	 * 
	 * @param index
	 */
	public void removeItem(int index) {
		slots[index] = null;
	}
	
	/**
	 * 
	 * @param screen
	 */
	public void render(Screen screen) {
		x = Gyarb.WIDTH / 2 - sprite.width / 2;   //x-coordinate for inventory
		y = Gyarb.HEIGHT / 2 - sprite.height / 2; //y-coordinate for inventory
		
		//Render inventory
		screen.renderSprite(sprite, x, y, false);
		
		//Render items
		for(int i = 0; i < size; i++) {
			if(slots[i*stack] != null) {
				slots[i * stack].render(screen, x + 4 + ((i % column) * 20), y + 4 + (i / column) * 20);
			}
		}	
	}
	
	/**
	 * 
	 * @param item
	 * @return
	 */
	private int findSlot(Item item) {
		for(int i = 0; i < slots.length; i++) {
			if(slots[i] == null)
				return i;
			else if(slots[i].getSprite() == item.getSprite() && slots[i + stack - i -1] == null)
				continue;
			else if(slots[i] != null && slots[i].getSprite() != item.getSprite())
				i = i + stack - 1;
		}
		return -1;
	}
	
	/**
	 * Return horizontal x-coordinate for the inventory.
	 * @return x, an integer for the horizontal position.
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Return vertical y-coordinate for the inventory.
	 * @return y, an integer for the vertical position.
	 */
	public int getY() {
		return y;
	}
}

