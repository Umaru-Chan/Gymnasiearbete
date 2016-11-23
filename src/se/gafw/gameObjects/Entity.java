package se.gafw.gameObjects;

import se.gafw.graphics.Screen;
import se.gafw.graphics.Sprite;
import se.gafw.level.Level;

public abstract class Entity {

	protected Level level;
	protected float x, y, width, height;
	protected Sprite sprite;
	
	public Entity(Level level, float x, float y, Sprite sprite){
		this.x = x;
		this.y = y;
		this.level = level;
		this.sprite = sprite;
		width = sprite.width;
		height =  sprite.height;
	}
	
	protected void move(float dx, float dy){
		if(!collision(x+dx, y+dy)){
			x += dx;
			y += dy;
		}
	}
	
	protected boolean collision(float x, float y){
		//TODO kolla om x,y | x+width,y | x,y+height | x+width,y+height kolliderar med en Tile
		return false;
	}
	
	public abstract void update();
	public abstract void render(Screen screen);
}
