package se.gafw.graphics;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import se.gafw.Gyarb;
import se.gafw.gameState.InGame;
import se.gafw.util.FileHandler;

/**
 * 
 * This class is used to create a JFrame, add a canvas and then support modifying the jframe later on.
 *
 */
public class Window {
    
    private final JFrame frame;
    private Dimension dim;
    private JPanel panel;
    
    /**
     * creates a window object and automatically open a new JFrame
     * 
     * @param title initial frame title
     * @param width frame width
     * @param height frame height
     * @param gyarb gyarb extends canvas and is supposed to be placed into the JPanel panel wich 
     * 																is then placed into the frame
     * @param visible if true, the frame opens visible else it opens invisible
     */
    public Window(String title, int width, int height, Gyarb gyarb, boolean visible) {
        frame = new JFrame(title);
        
        //initialize the frame

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter(){
        	public void windowClosing(WindowEvent e){
        		new Thread(() -> {        			
        			//TODO fix performance
        			int option = JOptionPane.showConfirmDialog(frame, "are you sure that you want to exit?", "confirmation", JOptionPane.YES_NO_OPTION);
        			if(option == JOptionPane.YES_OPTION)
        			{
        				gyarb.stop();
        				System.gc();
        			}
        			// ask if the user are sure on closing the application
        			option = JOptionPane.showConfirmDialog(frame, "Save?", "exiting", JOptionPane.YES_NO_OPTION);
        			if(option == JOptionPane.YES_OPTION)save();
        			// exit the application
        			System.gc();
        			System.exit(0);
        		}).start();
        	}
        });
        frame.setResizable(false);
        frame.setPreferredSize(dim = new Dimension(width, height));
        frame.setMaximumSize(dim);
        frame.setMinimumSize(dim);
        frame.setLocationRelativeTo(null);
        frame.setFocusable(true);
     
        frame.add((panel = new JPanel()).add(gyarb));
        
        panel.setFocusable(true);
        panel.requestFocus();
        
        frame.setVisible(visible);
    }
    
    /**
     * @return the JPanel wich contains the game canvas
     */
    public JPanel getPanel()
    {
    	return panel;
    }
    
    /**
     * @return the JFrame object
     */
    public JFrame getFrame()
    {
    	return frame;
    }
    
    /**
     * saves the current level
     */
    private void save(){
    	FileHandler handler = new FileHandler("res/saves/", "save", "ser");
    	handler.writeObject(InGame.getCurrentLevel());
    }
    
    /**
     * Sets the title of the frame to the string in the parameter
     * @param title the new title of the window
     */
    public void setTitle(String title) {
        frame.setTitle(title);
    }

    /**
     * @return frame == null
     */
    public synchronized boolean shouldClose() {return frame == null;}
}
