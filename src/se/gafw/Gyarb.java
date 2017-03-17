package se.gafw;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import se.gafw.gameState.GameStateManager;
import se.gafw.graphics.Screen;
import se.gafw.graphics.Window;
import se.gafw.util.KeyboardInput;

/**
 * 
 * The main class that contains some key objects and the main game loop.
 *
 */
public class Gyarb extends Canvas implements Runnable{
	public static final long serialVersionUID = 1L;
	
	/**
	 * some static final variables that are supposed to be accessable from alover the project.
	 */
	public static final int WIDTH = 450, HEIGHT = WIDTH / 16 * 9, SCALE = 3;
	public static final short VERSION_MAJOR = 0, VERSION_MINOR = 5;
	public static final String TITLE = "gyarb " + VERSION_MAJOR + "." + VERSION_MINOR;
		
	private boolean running; // if false, join the threads and close the application
	private Thread thread;	 // run the project on a new thread
	
	private final Window window; // the application window
	private final Screen screen; // the screen object used to render everyting to a of-screen buffer
	
	/**
	 * default constructor
	 */
	public Gyarb() {
		window = new Window(TITLE, WIDTH * SCALE, HEIGHT * SCALE, this, true);
		screen = new Screen(WIDTH, HEIGHT, 0x8888ff);
		
		addKeyListener(new KeyboardInput());
		GameStateManager.init(this);
		start();		
	}
	
	/**
	 * Starts the main-game thread and sets running to true
	 */
	public synchronized void start(){
		thread = new Thread(this, "mainThread");
		running = true;
		thread.start();
	}
	
	/**
	 * Joins the main-game thread and sets running to false
	 */
	public synchronized void stop(){
		try{
			thread.join();
			running = false;
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * The run method is called from the new thread when it is started, the method contains 
	 * the main game loop. This loop makes sure to call update() 60 times per second and 
	 * render as many times per second that the system allows for. This loop prioritates updates
	 * because entities are moved in the update method, if the system does not call the method at a set ammount
	 * of times per second entities may move slower or faster on different systems.
	 * 
	 */
	public void run() {
		int frames = 0, updates = 0;
		double delta = 0.0D, ns = 1e9 / 60.0D;
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		
		this.requestFocus();
		super.createBufferStrategy(3);
		
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			
			render();
			frames++;
			
			try{
				Thread.sleep(5); // Free up cpu time for other processes and make sure not to use too many system resources
			}catch(InterruptedException e){}
			
			//once every second
			if (System.currentTimeMillis() - timer >= 1e3) {
				window.setTitle(TITLE + " fps: " + frames + " ups: " + updates);
				
				updates = frames = 0;
				timer += 1e3;
			}
		}
		stop();
	}
		
	/**
	 * Renders everything in the game
	 */
	private void render() {
		if(window.shouldClose())//TODO
            return;
		BufferStrategy buffer = getBufferStrategy();
		Graphics g = buffer.getDrawGraphics();
		
		GameStateManager.render(screen, (Graphics2D) g);
		
		g.dispose();
		buffer.show();
	}
	
	/**
	 * Updates everything in the game, such as movement and physics
	 */
	private void update() {
		GameStateManager.update();
	}

	public static void main(String[] args) {
		//TODO read config file
		new Gyarb();
	}
}
