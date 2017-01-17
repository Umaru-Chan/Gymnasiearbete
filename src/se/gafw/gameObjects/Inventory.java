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
		
	public Inventory(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public void addItem(Item item) {
		int emptySlot = findSlot(item);
		if(emptySlot >= 0) {
			slots[emptySlot] = item;
		}
		else if(emptySlot < 0)
			System.out.println("Inventory is full");
	}
	
	public void removeItem(int index) {
		for(int i = index; i < index + stack; i++)
			slots[i] = null;
	}
	
	public void render(Screen screen) {
		int x = Gyarb.WIDTH / 2 - sprite.width / 2;   //x-coordinate for inventory
		int y = Gyarb.HEIGHT / 2 - sprite.height / 2; //y-coordinate for inventory
		
		//Render inventory
		screen.renderSprite(sprite, x, y, false);
		
		//Render items
		for(int i = 0; i < size; i++) {
			if(slots[i*stack] != null) {
				slots[i * stack].render(screen, x + 4 + ((i % column) * 20), y + 4 + (i / column) * 20);
			}
		}	
	}
	
	private int findSlot(Item item) {
		for(int i = 0; i < slots.length; i++) {
			if(slots[i] == null)
				return i;
			else if(slots[i].getSprite() == item.getSprite() && slots[i + stack - 1] == null)
				continue;
			else if(slots[i] != null && slots[i].getSprite() != item.getSprite())
				i = i + stack - 1;
		}
		return -1;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
}
