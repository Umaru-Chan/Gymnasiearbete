package se.gafw.gameObjects;

import se.gafw.Gyarb;
import se.gafw.graphics.Screen;
import se.gafw.graphics.Sprite;

public class Inventory {
	
	private final int size = 27; //9*3
	private final int stack = 64;
	
	private Item[] slots = new Item[size * stack]; 
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
	
	public void removeItem() {
		//TODO remove items
		
	}
	
	public void render(Screen screen) {
		screen.renderSprite(sprite, Gyarb.WIDTH / 2 - sprite.width / 2, Gyarb.HEIGHT / 2 - sprite.height / 2, false);
		
		for(int i = 0; i < size; i++) {
			if(slots[i*stack] != null) {
				slots[i * stack].render(screen, Gyarb.WIDTH / 2 - sprite.width / 2 + 4 + ((i%9) * 20), 
						Gyarb.HEIGHT / 2 - sprite.height / 2 + 4 + (i / 9) * 20);
			}
		}
		
		//TODO show how many items every slot has
	}

	private int findSlot(Item item) {
		for(int i = 0; i < slots.length; i++) {
			if(slots[i] == null)
				return i;
			else if(slots[i].getSprite() == item.getSprite())
				continue;
			else if(slots[i] != null && slots[i].getSprite() != item.getSprite())
				i = i - 1 + stack;
		}
		return -1;
	}
	
}
