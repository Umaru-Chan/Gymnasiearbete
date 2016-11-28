package se.gafw.gameObjects;

import se.gafw.graphics.Screen;
import se.gafw.graphics.Sprite;

public class Item {
	
	private Sprite sprite;
	private final String name; 
		
	public Item(Sprite sprite, String name) {
		this.sprite = sprite;
		this.name = name;
	}
	
	public void render(Screen screen, int x, int y) {
		screen.renderSprite(sprite, x, y, false);
	}
	
	public void update() {
		
	}
	
	public String getName() {
		return name;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
}
