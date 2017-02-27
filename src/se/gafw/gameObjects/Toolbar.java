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
	 * @return
	 */
	public int getStackSize() {
		return stackSize;
	}
	
	/**
	 * Return the amount of stacks the toolbar can store. 
	 * @return
	 */
	public int getStackAmount() {
		return stackAmount;
	}
	
	/**
	 * Return the selected stack
	 * @return
	 */
	public int getCurrentStack() {
		return currentStack;
	}
	
	/**
	 * Set the current stack
	 * @param i
	 */
	public void setCurrentStack(int i) {
		currentStack = i;
	}
	
	/**
	 * 
	 * @return
	 */
	public Item[] getStack() {
		return stack;
	}
	
}



