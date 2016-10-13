package se.gafw.graphics;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window {
    
    private JFrame frame;
    private Dimension dim;
    
    /**
     * Constructor. Creates a window
     * 
     * @param title The windows title
     * @param width The windows width
     * @param height The windows height
     * @param canvas The game draws everything on the canvas 
     * @param visible If the window is visible or not
     */
    public Window(String title, int width, int height, Canvas canvas, boolean visible) {
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setPreferredSize(dim = new Dimension(width, height));
        frame.setMaximumSize(dim = new Dimension(width, height));
        frame.setMinimumSize(dim = new Dimension(width, height));
        frame.setLocationRelativeTo(null);
        frame.add(canvas);
        frame.setVisible(visible);
    }
    
    /**
     * Decides if the window is visible or not.
     * 
     * @param visible
     */
    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
    
    /**
     * Returns the dimension of the window
     * 
     * @return
     */
    public Dimension getDimension() {
        return dim;
    }
    
    /**
     * Changes the title of the game
     * 
     * @param title 
     */
    public void setTitle(String title) {
        frame.setTitle(title);
    }
    
}
