package se.gafw;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Random;

import se.gafw.graphics.Screen;
import se.gafw.graphics.Sprite;
import se.gafw.graphics.SpriteSheet;
import se.gafw.graphics.Window;

public class Gyarb extends Canvas {
	public static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 600, HEIGHT = WIDTH / 16 * 9, SCALE = 2;
	public static final short VERSION_MAJOR = 0, VERSION_MINOR = 1;
	public static final String TITLE = "gyarb " + VERSION_MAJOR + "." + VERSION_MINOR;

	private boolean running;
	private Window window;
	private Screen screen;

	private BufferedImage image;
	private int[] pixels;
	
	/**
	 * 
	 */
	public Gyarb() {
		window = new Window(TITLE, WIDTH * SCALE, HEIGHT * SCALE, this, true);
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		screen = new Screen(WIDTH, HEIGHT, 0x8888ff);
		running = true;
		run();
	}

	/**
	 * The main gameloop, makes sure to tick 60 times per second (TODO remove and replace with deltatime) and render the game.
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
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
			}
			if (System.currentTimeMillis() - timer >= 1e3) {
				window.setTitle(TITLE + " fps: " + frames + " ups: " + updates);
				updates = frames = 0;
				timer += 1e3;
			}
		}
	}
		
	/**
	 * render everything here
	 */
	private void render() {
		BufferStrategy buffer = getBufferStrategy();
		Graphics g = buffer.getDrawGraphics();
		screen.clear();

		// TODO rendera saker här

		// sluta rendera här

		System.arraycopy(screen.pixels, 0, pixels, 0, pixels.length);
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);

		g.dispose();
		buffer.show();
	}

	/**
	 * updates all the movement and animations in the game.
	 * TODO deltaTime ???
	 */
	private void update() {

	}

	public static void main(String[] args) {
		//TODO läsa config?? (kanske på en ny tråd??) kanske rendera upp ngn loga medans spelet laddas??
		new Gyarb();
	}
}
