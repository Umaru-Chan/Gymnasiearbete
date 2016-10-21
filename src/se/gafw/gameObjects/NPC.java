package se.gafw.gameObjects;

import se.gafw.graphics.Screen;
import se.gafw.graphics.Sprite;
import se.gafw.level.Level;

public class NPC extends Entity {

	private float health;
	private float speed;
	
	public NPC(Level level, float x, float y, Sprite sprite, float health, float speed) {
		super(level, x, y, sprite);
		this.health = health;
		this.speed = speed;
	}
	
	public void tick() {
		//TODO röra på skiten??
	}
	
	//TODO metod för att attakera spelaren / andra nps:s
	
	//TODO metod för ngn special-sak, vad vet jag??

	
	public void render(Screen screen) {
		//TODO animationer??
		screen.renderSprite(sprite, (int)x, (int)y, false);
	}
}
