package se.gafw.graphics;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import se.gafw.Gyarb;
import se.gafw.util.FileHandler;

public class Window {
    
    private JFrame frame;
    private Dimension dim;
    private Gyarb mainGame;
    
    public Window(String title, int width, int height, Gyarb gyarb, boolean visible) {
    	mainGame = gyarb;
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter(){
        	@Override
        	public void windowClosing(WindowEvent e){
        		new Thread(() -> {        			
        			//TODO kollar varför det e så segt :(
        			int option = JOptionPane.showConfirmDialog(frame, "are you sure that you want to exit?", "confirmation", JOptionPane.YES_NO_OPTION);
        			if(option == JOptionPane.YES_OPTION)return;
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
        frame.setMaximumSize(dim = new Dimension(width, height));
        frame.setMinimumSize(dim = new Dimension(width, height));
        frame.setLocationRelativeTo(null);
        frame.add(gyarb);
        frame.setVisible(visible);
        gyarb.start();
    }
    
    
    private void save(){
    	FileHandler handler = new FileHandler("res/saves/", "save", "ser");
    	handler.writeObject(mainGame.getCurrentLevel());
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
    
}