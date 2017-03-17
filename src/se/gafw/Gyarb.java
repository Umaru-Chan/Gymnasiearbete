package se.gafw;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import se.gafw.gameState.GameStateManager;
import se.gafw.graphics.Screen;
import se.gafw.graphics.Window;
import se.gafw.util.KeyboardInput;


public class Gyarb extends Canvas implements Runnable{
	public static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 450, HEIGHT = WIDTH / 16 * 9, SCALE = 3;
	public static final short VERSION_MAJOR = 0, VERSION_MINOR = 5;
	public static final String TITLE = "gyarb " + VERSION_MAJOR + "." + VERSION_MINOR;

	private boolean running;
	private Thread thread;
	
	private final Window window;
	private final Screen screen;
	/**
	 * 
	 */
	public Gyarb() {
		window = new Window(TITLE, WIDTH * SCALE, HEIGHT * SCALE, this, true);
		screen = new Screen(WIDTH, HEIGHT, 0x8888ff);
		addKeyListener(new KeyboardInput());
		GameStateManager.init(this);
		start();		
	}
	
	public synchronized void start(){
		thread = new Thread(this, "mainThread");
		running = true;
		thread.start();
	}
	
	public synchronized void stop(){
		try{
			thread.join();
			running = false;
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * huvodloopen (antar att man kan skriva det?) TODO skriva bättre kommentarer och komma överens om vilket språk vi ska skriva dom i...
	 * 日本語を分かりますか?
     * 何日迄仕上げか
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
				Thread.sleep(5); //TODO fixa 60fps inte 100 (får ca 400 på macen om man inte sleepar)
			}catch(InterruptedException e){}
			
			if (System.currentTimeMillis() - timer >= 1e3) {
				window.setTitle(TITLE + " fps: " + frames + " ups: " + updates);
				
				updates = frames = 0;
				timer += 1e3;
			}
		}
		stop();
	}
		
	/**
	 * rendera allt här, render metoden kommer att kallas så många gånger per sekund som datorn kan
	 */
	private void render() {
		if(window.shouldClose())//TODO fixa en crash
            return;
		
		BufferStrategy buffer = getBufferStrategy();
		Graphics g = buffer.getDrawGraphics();
		
		GameStateManager.render(screen, (Graphics2D) g);
		
		g.dispose();
		buffer.show();
	}
	
	/**
	 * uppdatera all rörelse och logik i spelet (update kommer alltid att kallas 60ggr per sekund oberoende av system)
	 */
	private void update() {
		GameStateManager.update();
	}

	public static void main(String[] args) {
		//TODO läsa config?? kanske rendera upp ngn loga medans spelet laddas??
		new Gyarb();
	}
}
