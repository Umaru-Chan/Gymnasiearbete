package se.gafw.gameObjects;

import se.gafw.graphics.Screen;
import se.gafw.graphics.Sprite;
import se.gafw.level.Level;

/**
 * The NPC class is the no player creatures, the creatures that the player do
 * not have control over. 
 */
public class NPC extends Entity {

	@SuppressWarnings("unused")
	private float health, speed; //Health and speed for the creature.
	
	/**
	 * Class constructor. Creates a NPC.
	 * @param level
	 * @param x, the x-coordinate for the NPC.
	 * @param y, the y-coordinate for the NPC.
	 * @param sprite, the sprite for the NPC.
	 * @param health, how much health the NPC has.
	 * @param speed, how fast the NPC move.
	 */
	public NPC(Level level, float x, float y, Sprite sprite, float health, float speed) {
		super(level, x, y, sprite); //Calls the super constructor
		this.health = health;
		this.speed = speed;
	}
	
	/**
	 * 
	 */
	public void update() {
		
	}	

	/**
	 * Renders the NPC on the window.
	 * @param screen 
	 */
	public void render(Screen screen) {
		//TODO animations
		screen.renderSprite(sprite, (int)x, (int)y, false);
	}
}
