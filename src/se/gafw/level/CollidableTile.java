package se.gafw.level;

import se.gafw.graphics.Sprite;

/**
 * 
 * Used when creating Tile objects that will return true when calling solid()
 *
 */
public class CollidableTile extends Tile{

	public CollidableTile(Sprite sprite) {
		super(sprite);
	}
	
	public boolean solid(){
		return true;
	}

}
