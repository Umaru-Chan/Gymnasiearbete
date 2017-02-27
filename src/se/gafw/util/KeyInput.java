package se.gafw.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener{

	private final boolean[] keys; //true om knappen [ascii] är nere
	private final int length;
	
	public KeyInput(){
		keys = new boolean[length = 2550]; //antalet knappar som ska läsas av
	}
	
	/**
	 * använd KeyEvent.VK_KEY
	 * @param key
	 * @return
	 */
	public boolean getKeyStatus(int key){
		return key >= length ? false : keys[key];
	}
	public boolean getKeyStatus(KeyEvent key){
		return key.getKeyCode() >= length ? false : keys[key.getKeyCode()];
	}

	public void keyPressed(KeyEvent e) {	
		if(e.getKeyCode() >= length)return;
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() >= length)return;
		keys[e.getKeyCode()] = false;
	}
	
	public void keyTyped(KeyEvent e) {}
}
