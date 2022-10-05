package com.ufrgs.automatos.janelas;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.ufrgs.automatos.janelas.utils.SceneUtils;

public class JanelaPrincipal extends JFrame {

	private static final long serialVersionUID = 7265125024432901282L;
	
	
	private static JanelaPrincipal instance;

	public static JanelaPrincipal getInstance() {return instance;}
	
	
	private JPanel contentPane;
	
	
	public JanelaPrincipal() {
		instance = this;
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setCenterBounds(800, 500);
		contentPane = new JPanel();
		setContentPane(contentPane);
		
		SceneUtils.updateToMainMenu(this);
	}
	
	
	private void setCenterBounds(int width, int height) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double widthSrc = screenSize.getWidth()/2;
		double heightSrc = screenSize.getHeight()/2;

		widthSrc -= width/2;
		heightSrc -= height/2;

		setBounds((int) widthSrc, (int) heightSrc, width, height);
	}
	
}
