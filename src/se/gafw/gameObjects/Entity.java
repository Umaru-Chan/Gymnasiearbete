package se.gafw.gameObjects;

import se.gafw.Gyarb;
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
	
	private int specialAbs(float val)
	{
		return val < 0 ? -1 : val > 0 ? 1 : 0;
	}
	
	protected void move(float dx, float dy){
		//om det är 5 pixlar kvar till ett mål och man rör sig med 6 pixlar så kommer kollisionen kollas 6 pixlar frammåt, 
		//fungerar bättre att bara röra sig 1 pixel i taget
		if(dx > 1 || dy > 1)
		{
			if(dx > 0)
			{
				for(int  i = 0; i < dx; dx--)
				{
					move(specialAbs(dx), 0);
				}
			}
			if(dy > 0)
			{
				for(int  i = 0; i < dy; dy--)
				{
					move(0, specialAbs(dy));
				}
			}			
		}
		if(!collision(dx, 0))//kolla båda leden var för sig
			x += dx;
		if(!collision(0, dy))
			y += dy;
	}

	/**
	 * just nu så räknar man på alla 4 hörn
	 * TODO göra "ett till hörn" i mitten av alla kanter och räkna på dom också så att spelaren som är dubbelt så bred
	 * TODO som blocken inte faller igenom blocken som är i mitten
	 * @param xa
	 * @param ya
	 * @return
	 */
	protected boolean collision(float xa, float ya){
		if(x + xa + 32 > (level.getWidth() - 20/*lite marginal*/) * 16 || x + xa < 0)return true;

		boolean solid = false;
		int rx = (int)x + Gyarb.WIDTH / 2 - sprite.width / 2;
		int ry = (int)y + Gyarb.HEIGHT / 2 - sprite.height / 2;

		for(int i = 0; i < 4; i++){
			int xt = ((int)(rx+xa) - i % 2 * 31 + 31) >> 4;
			int yt = ((int)(ry+ya) + i / 2 * 31) >> 4;
			if(level.getTile(xt,yt).solid())solid = true;
		}

		
		{
			int xt = ((int)(rx+xa) + 15) >> 4;
			int yt = ((int)(ry+ya) + 31) >> 4;
			if(level.getTile(xt,yt).solid())return true;
		}
		{
			int xt = ((int)(rx+xa) + 15) >> 4;
			int yt = ((int)(ry+ya)) >> 4;
			if(level.getTile(xt,yt).solid())return true;
		}
		
		return solid;
	}
	
	public abstract void update();
	public abstract void render(Screen screen);
}
