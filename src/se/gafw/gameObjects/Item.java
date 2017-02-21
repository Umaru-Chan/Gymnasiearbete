package se.gafw.gameObjects;

import se.gafw.graphics.Screen;
import se.gafw.graphics.Sprite;
import se.gafw.graphics.SpriteSheet;

public class Item {
	
	private Sprite sprite;
	private String name;
	int x, y;
	
	public static final Item DIRT = new Item(Sprite.DIRT, "Dirt");
	public static final Item GRASS = new Item(Sprite.GRASS, "Grass");
	public static final Item STONE = new Item(Sprite.STONE, "Stone");
	public static final Item RUBY = new Item(Sprite.RUBY, "Ruby");
	
	/**
	 * 
	 * @param sprite
	 * @param name
	 */
	public Item(Sprite sprite, String name) {
		this.sprite = sprite;
		this.name = name;
	}
	
	/**
	 * 
	 * @param screen
	 * @param x
	 * @param y
	 */
	public void render(Screen screen, int x, int y) {
		this.x = x;
		this.y = y;
		screen.renderSprite(sprite, x, y, false);
	}
		
	/**
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @return
	 */
	public Sprite getSprite() {
		return sprite;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getY() {
		return y;
	}
	
}
