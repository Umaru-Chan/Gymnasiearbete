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
package se.gafw.gameObjects;

import se.gafw.Gyarb;
import se.gafw.graphics.Screen;
import se.gafw.graphics.Sprite;

/**
 * The Inventory class creates a inventory that the player can store items in. 
 * The Inventory has 27 slots and each slot can store 64 items. Every item is 
 * stored in an array of type Item. 
 */
public class Inventory {
	
	private Sprite sprite; //The sprite for the item.
	
	private int stackSize = 64; //Each stack can have 64 items
	private int row = 3; 
	private int column = 9;
	private int size = row * column;
	private int x, y;
	
	private Item[] slots = new Item[size * stackSize]; //Array that stores Items
		
	/**
	 * Class constructor. Creates a new inventory with a sprite.
	 * @param sprite is the sprite for the inventory
	 */
	public Inventory(Sprite sprite) {
		this.sprite = sprite;
	}
	
	/**
	 * Adds items to the array slots[]. The index for the item is
	 * returned from the method findSlots();
	 * @param item
	 */
	public void addItem(Item item) {
		int emptySlot = findSlot(item); //The index the item gets 
		if(emptySlot >= 0) {
			slots[emptySlot] = item;
		}
		else if(emptySlot < 0)
			System.out.println("Inventory is full");
	}
	
	/**
	 * Remove a item on a specified index in slots[] by setting that index equal to null.
	 * @param index the index that is going to be null in slots[].
	 */
	public void removeItem(int index) {
		slots[index] = null;
	}
	
	/**
	 * Renders the inventory on the center of the window. The inventory has 27 slots that 
	 * each can have 64 items, the first item of each stack is rendered over the slots on
	 * the inventory. The items that renders has index 0, 64, 128 and so on in the array
	 * slots[].
	 * @param screen
	 */
	public void render(Screen screen) {
		x = Gyarb.WIDTH / 2 - sprite.getWidth() / 2;   //x-coordinate for inventory
		y = Gyarb.HEIGHT / 2 - sprite.getHeight() / 2; //y-coordinate for inventory
		
		//Render inventory
		screen.renderSprite(sprite, x, y, false);
		
		//Render items
		for(int i = 0; i < size; i++) {
			if(slots[i * stackSize] != null) { //Only renders the first item of each stack
				slots[i * stackSize].render(screen, x + 4 + ((i % column) * 20), y + 4 + (i / column) * 20);
			}
		}	
	}
	
	/**
	 * Finds a index in the array slots[] for a specified item. Returns a index if there is a empty place
	 * for the item or returns -1 if there is no place for it.
	 * @param item is the item that is going to be placed inside slots[].
	 * @return a integer for the index. 
	 */
	private int findSlot(Item item) {
		for(int i = 0; i < slots.length; i++) {
			//If the index equals null return i
			if(slots[i] == null) 
				return i;
			//If the index contains the same item sprite and if there is less than 64 of the item continue. 
			else if(slots[i].getSprite() == item.getSprite() && slots[i + stackSize - i -1] == null) 
				continue;
			//If index is not equal to null and do not contain the same item sprite continue to the next stack
			else if(slots[i] != null && slots[i].getSprite() != item.getSprite())
				i = i + stackSize - 1;
		}
		return -1; //Return -1 if there is no space for the item
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
	 * Return the amount of rows in the inventory.
	 * @return row, an integer for the amount of rows.
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Return the amount of columns in the inventory.
	 * @return column, an integer for the amount of columns.
	 */
	public int getColumn() {
		return column;
	}
	
	/**
	 * Return the size of a stack, the amount of same items that can be stacked.
	 * @return stackSize, the size of a stack.
	 */
	public int getStackSize() {
		return stackSize;
	}
	
	/**
	 * Return the array that contains all the items.
	 * @return slots, array that stores Items.
	 */
	public Item[] getSlots() {
		return slots;
	}
}



