package se.gafw.gyarb;

import java.awt.Dimension;
import javax.swing.JFrame;

public class Window {
    
    private JFrame frame;
    private Dimension dim;
    private String title;
    
    public Window(String title, int width, int height, boolean visible) {
        this.title = title;
        frame = new JFrame(title);
        frame.setPreferredSize(dim = new Dimension(width, height));
        frame.setMaximumSize(dim = new Dimension(width, height));
        frame.setMinimumSize(dim = new Dimension(width, height));
        frame.setLocationRelativeTo(null);
        frame.setVisible(visible);
    }
    
    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
    
    public Dimension getDimension() {
        return dim;
    }
    
    public void setTitle(String title) {
        this.title = title;
        frame.setTitle(title);
    }
    
}
