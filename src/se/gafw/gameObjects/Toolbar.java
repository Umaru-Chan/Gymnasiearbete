package se.gafw.gameObjects;

import se.gafw.Gyarb;
import se.gafw.graphics.Screen;
import se.gafw.graphics.Sprite;

public class Toolbar {

	private Sprite sprite;
	private Sprite selectedStack = Sprite.TOOLBAR_STACK;

	private int stackSize = 64;
	private int x, y;

	public int stacks = 9;
	public int currentStack = 0;
	
	public Item[] stack = new Item[stacks * stackSize];
	
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
		x = Gyarb.WIDTH / 2 - sprite.width / 2;   
		y = Gyarb.HEIGHT - sprite.height - 10;
		
		screen.renderSprite(sprite, x, y, false);
		
		for(int i = 0; i < 9; i++) {
			if(stack[i * stackSize] != null) {
				stack[i * stackSize].render(screen, x + 4 + ((i % 9) * 20), y + 4);
			}
		}
		
		screen.renderSprite(selectedStack, x + (currentStack * 20), y + 20, false);
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
}
