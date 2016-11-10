package se.gafw.util;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInput implements MouseListener, MouseMotionListener{

	private int x, y;
	private boolean button1, button2;
	
	public MouseInput(){
		
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public boolean getButton(int button){
		return button == 1 ? button1 : button == 2 ? button2 : false;
	}
	
	
	public void mouseReleased(MouseEvent e) {
		button1 = e.getButton() == MouseEvent.BUTTON1 ? false : button1;
		button2 = e.getButton() == MouseEvent.BUTTON2 ? false : button2;
		x = e.getX();
		y = e.getY();
	}
	public void mousePressed(MouseEvent e) {
		button1 = e.getButton() == MouseEvent.BUTTON1 ? true : button1;
		button2 = e.getButton() == MouseEvent.BUTTON2 ? true : button2;
		x = e.getX();
		y = e.getY();
	}
	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	//används inte
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}
