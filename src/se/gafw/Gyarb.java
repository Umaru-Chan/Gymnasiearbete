package se.gafw;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;

import se.gafw.graphics.Screen;
import se.gafw.graphics.Window;
import se.gafw.level.Level;
import se.gafw.util.KeyInput;
import se.gafw.util.MouseInput;

public class Gyarb extends Canvas implements Runnable{
	public static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 400, HEIGHT = WIDTH / 16 * 9, SCALE = 3;
	public static final short VERSION_MAJOR = 0, VERSION_MINOR = 1;
	public static final String TITLE = "gyarb " + VERSION_MAJOR + "." + VERSION_MINOR;

	private boolean running;
	private Thread thread;
	
	private final Window window;
	private final Screen screen;
	private final KeyInput key;
	private final MouseInput mouse;
	private Level currentLevel;

	private final BufferedImage image;
	private final int[] pixels;
	
	/**
	 * 
	 */
	public Gyarb() {
		pixels = ((DataBufferInt) 
				(image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB)).getRaster().getDataBuffer()).getData();
		screen = new Screen(WIDTH, HEIGHT, 0x8888ff);
		addKeyListener(key = new KeyInput());
		addMouseListener(mouse = new MouseInput());
		addMouseMotionListener(mouse);
		currentLevel = Level.TEST;
		running = true;
		window = new Window(TITLE, WIDTH * SCALE, HEIGHT * SCALE, this, true);
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
	 * 日本語はわかりますか?
	 */
	public void run() {
		int frames = 0, updates = 0;
		double delta = 0.0D, ns = 1e9 / 60.0D;
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		super.createBufferStrategy(3);
		this.requestFocus();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				//TODO uppdatera med deltatid
				update();
				updates++;
				delta--;
			}
			render();
			frames++;
			try{
				Thread.sleep(1);
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
		BufferStrategy buffer = getBufferStrategy();
		Graphics g = buffer.getDrawGraphics();
		screen.clear();

		// TODO rendera saker här
		currentLevel.render(screen, 0, 0);

		// sluta rendera här

		System.arraycopy(screen.pixels, 0, pixels, 0, pixels.length);
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);

		g.dispose();
		buffer.show();
	}

	/**
	 * uppdatera all rörelse och logik i spelet (update kommer alltid att kallas 60ggr per sekund oberoende av system)
	 * TODO deltaTime ???
	 */
	private void update() {
		
	}
	
	public Level getCurrentLevel(){
		return currentLevel;
	}

	public static void main(String[] args) {
		//TODO läsa config?? kanske rendera upp ngn loga medans spelet laddas??
		new Gyarb();
	}
}
