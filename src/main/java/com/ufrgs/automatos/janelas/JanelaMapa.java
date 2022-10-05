package com.ufrgs.automatos.janelas;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.ufrgs.automatos.JogoMain;

public class JanelaMapa extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4898677378881465295L;
	
	private static JanelaMapa instance;
	
	public static JanelaMapa getInstance() {
		if (instance == null) {
			instance = new JanelaMapa();
		}
		
		return instance;
	}
	
	
	
	private JPanel contentPane;

	
	public JanelaMapa() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setCenterBounds(1250, 980);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		try {
			ImageIcon img = new ImageIcon(ImageIO.read(JogoMain.class.getClassLoader().getResourceAsStream("mapa_jogo.png")));
			img = resizeImage(img);
			JLabel background = new JLabel("", img, JLabel.CENTER);
			
			background.setBounds(0, 0, 1310, 983);
			this.getContentPane().add(background);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private ImageIcon resizeImage(ImageIcon imageIcon) {
		final double percent = 0.95;
		Image image = imageIcon.getImage(); 
		Image newimg = image.getScaledInstance((int) (imageIcon.getIconWidth()*percent), (int) (imageIcon.getIconHeight()*percent), java.awt.Image.SCALE_SMOOTH); 
		return new ImageIcon(newimg);
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
