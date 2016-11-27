package se.gafw.gameObjects;

import java.awt.event.KeyEvent;

import se.gafw.Gyarb;
import se.gafw.graphics.Screen;
import se.gafw.graphics.Sprite;
import se.gafw.level.Level;
import se.gafw.util.KeyInput;

public class Player extends Entity{

    private KeyInput in;
    private float dy = 0;

    public Player(Level level, float x, float y, KeyInput in) {
        super(level, x, y, Sprite.PLAYER);
        this.in = in;
    }

    public void update() {
        if(dy < 0)dy = 0;
        if(dy > 0)dy-=.2;
        move(0, 1 - dy); //ramla alltid nedåt
        if(in.getKeyStatus(KeyEvent.VK_A))move(-1,  0);
        if(in.getKeyStatus(KeyEvent.VK_D))move( 1,  0);
        if(in.getKeyStatus(KeyEvent.VK_SPACE) && dy == 0)dy = 6;
    }

    public void render(Screen screen) {
        screen.renderSprite(sprite, Gyarb.WIDTH / 2 - sprite.width / 2, Gyarb.HEIGHT / 2 - sprite.height / 2, false);
    }

    public int getX(){
        return (int)x;
    }

    public int getY(){
        return (int)y;
    }
}