package com.ufrgs.automatos;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.ufrgs.automatos.controllers.PathsController;
import com.ufrgs.automatos.entity.PlayerData;
import com.ufrgs.automatos.janelas.JanelaPrincipal;

public class JogoMain {
	
	private static PathsController controller, gamePathsController;
	
	private static PlayerData playerData;
	
	
	
	public static PathsController getPathsController() {return controller;}
	
	public static PathsController getGamePathsController() {return gamePathsController;}
	
	public static PlayerData getPlayerData() {
		return getPlayerData(true);
	}
	
	public static PlayerData getPlayerData(boolean create) {
		if (create && playerData == null) {
			playerData = new PlayerData();
		}
		return playerData;
	}
	
	
	public static void setPathsController(PathsController controllerNew) {controller = controllerNew;}
	
	public static void main(String args[]) {
		
		updateUI();
		
		gamePathsController = new PathsController();
		controller = new PathsController();
		
		//System.out.println(controller.executeWord("ddde"));
		//System.out.println(controller.isLangFinite());
		
		System.out.println(controller.getEstados());
		System.out.println(controller.isLangEmpty());
		
		controller.minimazeAutomate();
		
		System.out.println(controller.getEstados());
		System.out.println(controller.isLangEmpty());
		
		new JanelaPrincipal().setVisible(true);
	}
	
	
	
	private static void updateUI() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
	

}
