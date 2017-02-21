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

public class Window {
    
    private JFrame frame;
    private Dimension dim;
    private JPanel panel;
    
    public Window(String title, int width, int height, Gyarb gyarb, boolean visible) {
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
       
        frame.addWindowListener(new WindowAdapter(){
        	public void windowClosing(WindowEvent e){
        		new Thread(() -> {        			
        			//TODO kollar varför det e så segt :(
        			int option = JOptionPane.showConfirmDialog(frame, "are you sure that you want to exit?", "confirmation", JOptionPane.YES_NO_OPTION);
        			if(option == JOptionPane.YES_OPTION)
                    {
                        gyarb.stop();
                        System.gc();
                    }
        			//om man vill stänga
        			option = JOptionPane.showConfirmDialog(frame, "Save?", "exiting", JOptionPane.YES_NO_OPTION);
        			if(option == JOptionPane.YES_OPTION)save();
        			//stäng applikationen
        			//mainGame.stop();
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
        //frame.setAutoRequestFocus(true);
        
        //Image image;
        //TODO load image
        //frame.setIconImage(image);
        frame.add((panel = new JPanel()).add(gyarb));
        
        panel.setFocusable(true);
        panel.requestFocus();
        
        frame.setVisible(visible);
    }
    
    public JPanel getPanel()
    {
    	return panel;
    }
    
    public JFrame getFrame()
    {
    	return frame;
    }
    
    private void save(){
    	FileHandler handler = new FileHandler("res/saves/", "save", "ser");
    	handler.writeObject(InGame.getCurrentLevel());
    }
    
    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
    
    public Dimension getDimension() {
        return dim;
    }
    
    public void setTitle(String title) {
        frame.setTitle(title);
    }

    public synchronized boolean shouldClose() {return frame == null;}
    
}
