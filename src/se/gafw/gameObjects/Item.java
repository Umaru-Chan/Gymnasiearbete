package se.gafw.gameObjects;

import se.gafw.graphics.Screen;
import se.gafw.graphics.Sprite;

/**
 * Item class represents items that the player can pick up and store in a Inventory.
 */
public class Item {
	
	private Sprite sprite; //The sprite for the item.
	private String name; //The name of the item.
	private int x, y;
	
	public static final Item DIRT = new Item(Sprite.DIRT, "Dirt"); 		//Dirt item
	public static final Item GRASS = new Item(Sprite.GRASS, "Grass");	//Grass item
	public static final Item STONE = new Item(Sprite.STONE, "Stone"); 	//Stone item
	public static final Item DIAMOND = new Item(Sprite.DIAMOND, "Ruby");//Diamond item
	
	/**
	 * Class constructor. Creates a new item with a sprite and a name.
	 * @param sprite is the sprite for the item
	 * @param name is the name of the item
	 */
	public Item(Sprite sprite, String name) {
		this.sprite = sprite;
		this.name = name;
	}
	
	/**
	 * Renders the items on a specified x and y coordinate.
	 * @param screen for rendering the items.
	 * @param x is the x coordinate for the item.
	 * @param y is the y coordinate for the item.
	 */
	public void render(Screen screen, int x, int y) {
		this.x = x;
		this.y = y;
		screen.renderSprite(sprite, x, y, false);
	}
		
	/**
	 * Return the name of the item.
	 * @return name, the name of the item.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Return the sprite of the item.
	 * @return sprite, the sprite the item has.
	 */
	public Sprite getSprite() {
		return sprite;
	}
	
	/**
	 * Return horizontal x-coordinate for the item.
	 * @return x, x-coordinate for the item
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Return vertical y-coordinate for the item.
	 * @return y, y-coordinate for the item.
	 */
	public int getY() {
		return y;
	}
	
}
