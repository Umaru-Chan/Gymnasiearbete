package se.gafw.gameObjects;

import se.gafw.Gyarb;
import se.gafw.graphics.Screen;
import se.gafw.graphics.Sprite;

public class Inventory {
	
	private Sprite sprite;
	
	private int stackSize = 64;
	private int row = 3;
	private int column = 9;
	private int size = row * column;
	private int x, y;
	
	private Item[] slots = new Item[size * stackSize]; 
	
				
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
		x = Gyarb.WIDTH / 2 - sprite.getWidth() / 2;   //x-coordinate for inventory
		y = Gyarb.HEIGHT / 2 - sprite.getHeight() / 2; //y-coordinate for inventory
		
		//Render inventory
		screen.renderSprite(sprite, x, y, false);
		
		//Render items
		for(int i = 0; i < size; i++) {
			if(slots[i * stackSize] != null) {
				slots[i * stackSize].render(screen, x + 4 + ((i % column) * 20), y + 4 + (i / column) * 20);
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
			else if(slots[i].getSprite() == item.getSprite() && slots[i + stackSize - i -1] == null)
				continue;
			else if(slots[i] != null && slots[i].getSprite() != item.getSprite())
				i = i + stackSize - 1;
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
	
	/**
	 * 
	 * @return
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getColumn() {
		return column;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getStackSize() {
		return stackSize;
	}
	
	/**
	 * 
	 * @return
	 */
	public Item[] getSlots() {
		return slots;
	}
}


