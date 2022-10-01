package com.ufrgs.automatos;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.ufrgs.automatos.controllers.PathsController;

public class JogoMain {
	
	public static void main(String args[]) {
		
		updateUI();
		
		PathsController controller = new PathsController();
		
		//System.out.println(controller.executeWord("ddde"));
		//System.out.println(controller.isLangFinite());
		
		System.out.println(controller.getEstados());
		System.out.println(controller.isLangEmpty());
		
		controller.minimazeAutomate();
		
		System.out.println(controller.getEstados());
		System.out.println(controller.isLangEmpty());
	}
	
	
	
	private static void updateUI() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
	

}
