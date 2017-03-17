package se.gafw.gameState;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import se.gafw.Gyarb;
import se.gafw.graphics.Screen;

/**
 * 
 * Used to manage the current gamestate, makes sure that only the things that needs
 * to be rendered and updated is rendered and updated.
 *
 */
public class GameStateManager{
	
	// a list containing all instances of the gamestates
	private static List<GameState> states = new ArrayList<GameState>();
	// to keep track of wich state the game is in at any time
	private static State currentState = null;
	
	/**
	 * The ordinal number of each enum attribute is 
	 * used to differentiate between the different states in the state list.
	 */
	public enum State
	{
		MENU,
		IN_GAME,
		ABOUT,
		OPTIONS,
		PAUSE
	}
	
	// the class is meant to be used statically
	private GameStateManager(){}
	
	/**
	 * Creates instances of all gamestates and adds them to the list
	 * @param g
	 */
	public static void init(Gyarb g)
	{	
		states.add(State.MENU.ordinal(), new Menu());
		
		states.add(State.IN_GAME.ordinal(), new InGame(g));
		
		states.add(State.ABOUT.ordinal(), new About());
		
		states.add(State.OPTIONS.ordinal(), null);

		states.add(State.PAUSE.ordinal(), new Pause());
		
		//the default state should always be the menu state (for when you first start the game)
		setCurrentState(State.MENU);
	}
	
	/*
	 *	---------------------------------------------------------------------------------------------------------------- 
	 *  buffer methods
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
	
	/**
	 * sets the current state to the newState
	 * @param newState the gamestate to jump into
	 */
	public static void setCurrentState(State newState)
	{
		// no need to do anything if you already are in the desired state
		if(currentState == newState)return;
		// alert the currentstate that you are leaving
		if(currentState != null)states.get(currentState.ordinal()).leave();
		currentState = newState; //set the new state
		// alert the new state that you are entering
		states.get(currentState.ordinal()).enter();
	}
}
