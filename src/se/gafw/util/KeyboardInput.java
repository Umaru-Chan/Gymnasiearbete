package se.gafw.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import se.gafw.gameState.GameStateManager;

public class KeyboardInput implements KeyListener{

	public void keyTyped(KeyEvent e) {
		GameStateManager.keyTyped(e);
	}

	public void keyPressed(KeyEvent e) {
		GameStateManager.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		GameStateManager.keyReleased(e);
	}

}
