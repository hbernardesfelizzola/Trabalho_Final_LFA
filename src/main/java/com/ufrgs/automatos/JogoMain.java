package com.ufrgs.automatos;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class JogoMain {
	
	public static void main(String args[]) {
		
		updateUI();
		
		
		
	}
	
	private static void updateUI() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
	

}
