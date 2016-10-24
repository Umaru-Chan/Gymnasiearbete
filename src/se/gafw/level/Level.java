package se.gafw.level;

import java.util.ArrayList;

import se.gafw.gameObjects.Entity;
import se.gafw.graphics.Screen;

public class Level {

	private int width, height;
	private Tile[] tiles;
	private ArrayList<Entity> entities = new ArrayList<>();
	
	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		tiles = new Tile[width * height];
	}
	
	public void render(Screen screen, int x, int y) {
		for (int i = 0; x < entities.size(); i++) {
			entities.get(i).render(screen);
		}
	}
	
	public void update() {
		
	}
	
	public void addEntity(Entity entity) {
		entities.add(entity);
	}
	
	public void killEntity(Entity entity) {
		entities.remove(entity);
	}
	
}
