package se.gafw.gameState;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import se.gafw.Gyarb;
import se.gafw.graphics.Screen;

public class GameStateManager{
	
	private static List<GameState> states = new ArrayList<GameState>();
	private static State currentState = null;
	
	public enum State
	{
		MENU,
		IN_GAME,
		ABOUT,
		OPTIONS,
		PAUSE
	}
	
	private GameStateManager(){}
	
	/**
	 * skapar och lägger till alla gamestates. om något state behöver mer
	 * gjort så görs det här (t.ex. ladda bakgrundsbilden till menyn)
	 * @param g
	 */
	public static void init(Gyarb g)
	{	
		states.add(State.MENU.ordinal(), new Menu());
		
		states.add(State.IN_GAME.ordinal(), new InGame(g));
		
		states.add(State.ABOUT.ordinal(), new About());
		
		states.add(State.OPTIONS.ordinal(), null);

		states.add(State.PAUSE.ordinal(), new Pause());
		
		setCurrentState(State.MENU);
	}
	
	/*
	 *	---------------------------------------------------------------------------------------------------------------- 
	 *  buffermetoder
	 */
	public static void render(Screen screen, Graphics2D g2d)
	{
		states.get(currentState.ordinal()).render(screen, g2d);
	}
	
	public static void update()
	{
		states.get(currentState.ordinal()).update();
	}
	
	public static void keyPressed(KeyEvent e)
	{
		states.get(currentState.ordinal()).keyPressed(e);
	}

	public static void keyReleased(KeyEvent e)
	{
		states.get(currentState.ordinal()).keyReleased(e);
	}

	public static void keyTyped(KeyEvent e)
	{
		states.get(currentState.ordinal()).keyTyped(e);
	}
	
	/*
	 *	----------------------------------------------------------------------------------------------------------------
	 */
	
	public static void setCurrentState(State newState)
	{
		if(currentState == newState)return;
		if(currentState != null)states.get(currentState.ordinal()).leave();
		currentState = newState;
		states.get(currentState.ordinal()).enter();
	}
}
