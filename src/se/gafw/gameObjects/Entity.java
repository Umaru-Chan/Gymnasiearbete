package se.gafw.gameObjects;

import se.gafw.Gyarb;
import se.gafw.graphics.Screen;
import se.gafw.graphics.Sprite;
import se.gafw.level.Level;
import se.gafw.level.Tile;

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
		if(!collision((int)(dx), (int)(dy))){
			x += dx;
			y += dy;
		}
	}

	/**
	 * just nu s� r�knar man p� alla 4 h�rn
	 * TODO g�ra "ett till h�rn" i mitten av alla kanter och r�kna p� dom ocks� s� att spelaren som �r dubbelt s� bred
	 * TODO som blocken inte faller igenom blocken som �r i mitten
	 * @param xa
	 * @param ya
	 * @return
	 */
	private boolean collision(int xa, int ya){
		boolean solid = false;
		int rx = (int)x + Gyarb.WIDTH / 2 - sprite.width / 2;
		int ry = (int)y + Gyarb.HEIGHT / 2 - sprite.height / 2;
		for(int i = 0; i < 4; i++){
			int xt = ((int)(rx+xa) - i % 2 * 31 + 31) >> 4;
			int yt = ((int)(ry+ya) + i / 2 * 31) >> 4;
			if(level.getTile(xt,yt).solid())solid = true;
			//if(level.getTile(xt,yt).liquid())solid = true;
		}

		//if(level.getTile(((int)(rx+xa) + 16) >> 4, ((int)(ry+ya) + 32)).solid())solid = true;


		return solid;
	}
	
	public abstract void update();
	public abstract void render(Screen screen);
}
