package se.gafw.gameObjects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import se.gafw.Gyarb;
import se.gafw.graphics.Screen;
import se.gafw.graphics.Sprite;

public class Toolbar {

	private Sprite sprite;
	private Sprite selectedStack = Sprite.TOOLBAR_STACK;

	private int stackSize = 64;
	private int stackAmount = 9;
	private int currentStack = 0;
	private int x, y;

	private Item[] stack = new Item[stackAmount * stackSize];
	
	/**
	 * 
	 * @param sprite
	 */
	public Toolbar(Sprite sprite) {
		this.sprite = sprite;
	}
	
	/**
	 * 
	 * @param item
	 */
	public void addItem(Item item) {
		int findSlot = findStack(item);
		if(findSlot >= 0)
			stack[findSlot] = item;
		else if (findSlot < 0)
			System.out.println("Out of space in toolbar");
	}
	
	/**
	 * 
	 * @param index
	 */
	public void removeItem(int index) {
		stack[index] = null;
	}
	
	/**
	 * 
	 * @param screen
	 */
	public void render(Screen screen) {
		x = Gyarb.WIDTH / 2 - sprite.getWidth() / 2;   
		y = Gyarb.HEIGHT - sprite.getHeight() - 10;
		
		screen.renderSprite(sprite, x, y, false);
		
		for(int i = 0; i < 9; i++) {
			if(stack[i * stackSize] != null) {
				
				stack[i * stackSize].render(screen, x + 4 + ((i % 9) * 20), y + 4);
			}
		}
		screen.renderSprite(selectedStack, x + (currentStack * 20), y + 22, false);
	}
	
	/**
	 * 
	 * @param item
	 * @return
	 */
	private int findStack(Item item) {
		for(int i = 0; i < stack.length; i++) {
			if(stack[i] == null)
				return i;
			else if(stack[i].getSprite() == item.getSprite() && stack[i + stackSize - i - 1] == null)
				continue;
			else if(stack[i] != null && stack[i].getSprite() != item.getSprite())
				i = i + stackSize - 1;
		}
		return -1;
	}
	
	/**
package se.gafw.gameObjects;

import se.gafw.Gyarb;
import se.gafw.graphics.Screen;
import se.gafw.graphics.Sprite;

/**
 * Toolbar class creates a toolbar where the player can store and use items. 
 * The items that is stored in the toolbar comes from the inventory. If the player 
 * left clicks on a stack in the inventory, the stack is moved to the toolbar from the 
 * inventory so the player can use the items.
 */
public class Toolbar {

	private Sprite sprite; //Sprite for the toolbar
	private Sprite selectedStack = Sprite.TOOLBAR_STACK; //Sprite to show which stack that is selected

	private int stackSize = 64; //Size of each stack
	private int stackAmount = 9; //Amount of stack the toolbar have
	private int currentStack = 0; //Integer for the current stack
	private int x, y;

	private Item[] stack = new Item[stackAmount * stackSize]; //Array to story Items
	
	/**
	 * Class constructor. Creates a Toolbar with a sprite
	 * @param sprite, the toolbars sprite
	 */
	public Toolbar(Sprite sprite) {
		this.sprite = sprite;
	}
	
	/**
	 * Adds items to the array stack. The index for the item is
	 * returned from the method findSlots();
	 * @param item
	 */
	public void addItem(Item item) {
		int findSlot = findSlot(item); //The index for the item
		if(findSlot >= 0)
			stack[findSlot] = item;
		else if (findSlot < 0)
			System.out.println("Out of space in toolbar");
	}
	
	/**
	 * Remove a item on a specified index in stack[] by setting that index equal to null.
	 * @param index, the index that is going to be null in stack[].
	 */
	public void removeItem(int index) {
		stack[index] = null;
	}
	
	/**
	 * Renders the toolbar at the bottom of the screen. The toolbar can have 9 stacks of items
	 * and each stack can store 64 of the same item. The first item of each stack is rendered over the
	 * toolbar. The index for the first item of each stack is 0, 64, 128 and so on. 
	 * @param screen
	 */
	public void render(Screen screen) {
		x = Gyarb.WIDTH / 2 - sprite.getWidth() / 2; //x-coordinate for the toolbar
		y = Gyarb.HEIGHT - sprite.getHeight() - 10;  //y-coordinate for the toolbar
		
		screen.renderSprite(sprite, x, y, false); //Render the toolbar 
		
		//Renders the items
		for(int i = 0; i < 9; i++) {
			if(stack[i * stackSize] != null) { //Only the first item of each stack should render
				stack[i * stackSize].render(screen, x + 4 + ((i % 9) * 20), y + 4);
			}
		}
		screen.renderSprite(selectedStack, x + (currentStack * 20), y + 22, false);
	}
	
	/**
	 * Finds a index in the array stack for a specified item. Returns a index if there is a empty place
	 * for the item or returns -1 if there is no place for it.
	 * @param item, the item that is going to be placed inside slots[].
	 * @return a integer for the index. 
	 */
	private int findSlot(Item item) {
		for(int i = 0; i < stack.length; i++) {
			//If index i equals null, return i.
			if(stack[i] == null)
				return i;
			//If index i contains the same item sprite and the end of the stack is empty, continue.
			else if(stack[i].getSprite() == item.getSprite() && stack[i + stackSize - i - 1] == null)
				continue;
			//If index i is not null and it do not contain the same item sprite, continue to the next stack.
			else if(stack[i] != null && stack[i].getSprite() != item.getSprite())
				i = i + stackSize - 1;
		}
		return -1; //Return -1 if there is no place for the item.
	}
	
	/**
	 * Return horizontal x-coordinate for the toolbar.
	 * @return x, an integer for the horizontal position.
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Return vertical y-coordinate for the toolbar.
	 * @return y, an integer for the vertical position.
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Return the size of a stack.
	 * @return stackSize, the size of each stack.
	 */
	public int getStackSize() {
		return stackSize;
	}
	
	/**
	 * Return the amount of stacks the toolbar can store. 
	 * @return stackAmount, the amount of stacks the toolbar can store.
	 */
	public int getStackAmount() {
		return stackAmount;
	}
	
	/**
	 * Return the selected stack.
	 * @return cuurentStack, an integer for the selected stack.
	 */
	public int getCurrentStack() {
		return currentStack;
	}
	
	/**
	 * Set the current stack.
	 * @param i, an integer for the selected stack.
	 */
	public void setCurrentStack(int i) {
		currentStack = i;
	}
	
	/**
	 * Return the array stack that stores items in the toolbar.
	 * @return stack, an array that stores Item.
	 */
	public Item[] getStack() {
		return stack;
	}
	
}
