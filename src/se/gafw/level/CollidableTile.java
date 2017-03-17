package se.gafw.level;

import se.gafw.graphics.Sprite;

public class CollidableTile extends Tile{

	public CollidableTile(Sprite sprite) {
		super(sprite);
	}
	
	public boolean solid(){
		return true;
	}

}
