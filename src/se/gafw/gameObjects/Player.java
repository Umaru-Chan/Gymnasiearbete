package se.gafw.gameObjects;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import se.gafw.Gyarb;
import se.gafw.graphics.Screen;
import se.gafw.graphics.Sprite;
import se.gafw.level.Level;
import se.gafw.util.KeyInput;

public class Player extends Entity implements MouseListener{

    private KeyInput in;
    private float dy = 0;

    public Player(Level level, float x, float y, KeyInput in) {
        super(level, x, y, Sprite.PLAYER);
        this.in = in;
    }

    //bara temporär kod för att testa kollision
    public void update() {
        if(dy < 0)dy = 0;//don't even know (jo det gör jag)
        if(dy > 0)dy-=.2;//om man har hoppat ska man falla (?)
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


	public void mousePressed(MouseEvent e) {
		int x = (e.getX() >> 4) / Gyarb.SCALE + ((int) (this.x + .5) >> 4);
		int y = (e.getY() >> 4) / Gyarb.SCALE + ((int) (this.y + .5) >> 4);
		if(e.getButton() == MouseEvent.BUTTON1)level.removeBlock(x, y);
		if(e.getButton() == MouseEvent.BUTTON3)level.addBlock(x, y);
	}
	public void mouseClicked(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) {	}
}
