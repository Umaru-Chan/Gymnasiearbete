package se.gafw.gameObjects;

import java.awt.event.KeyEvent;

import se.gafw.Gyarb;
import se.gafw.graphics.Screen;
import se.gafw.graphics.Sprite;
import se.gafw.level.Level;
import se.gafw.util.KeyInput;

public class Player extends Entity{

	private KeyInput in;
	private int timer;
	
	public Player(Level level, float x, float y, KeyInput in) {
		super(level, x, y, Sprite.PLAYER);
		this.in = in;
	}

	public void update() {
		timer ++;
		
		if(in.getKeyStatus(KeyEvent.VK_W))y-=1;
		if(in.getKeyStatus(KeyEvent.VK_S))y+=1;
		if(in.getKeyStatus(KeyEvent.VK_A))x-=1;
		if(in.getKeyStatus(KeyEvent.VK_D))x+=1;
	}

	public void render(Screen screen) {
		screen.renderSprite(sprite, Gyarb.WIDTH / 2 - sprite.width / 2, Gyarb.HEIGHT / 2 - sprite.height / 2, false);
	}

}
