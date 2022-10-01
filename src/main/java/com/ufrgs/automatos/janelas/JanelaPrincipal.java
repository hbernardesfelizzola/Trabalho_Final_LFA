package com.ufrgs.automatos.janelas;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.ufrgs.automatos.JogoMain;

public class JanelaPrincipal extends JFrame {

	private static final long serialVersionUID = 7265125024432901282L;
	
	private JPanel contentPane;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaPrincipal frame = new JanelaPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public JanelaPrincipal() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setCenterBounds(800, 500);
		contentPane = new JPanel();
		setContentPane(contentPane);
		
		try {
			ImageIcon img = new ImageIcon(ImageIO.read(JogoMain.class.getClassLoader().getResourceAsStream("menu_jogo.jpg")));
			JLabel background = new JLabel("", img, JLabel.CENTER);
			
			background.setBounds(0, 0, 800, 500);
			getContentPane().add(background);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	private void setCenterBounds(int width, int height) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double widthSrc = screenSize.getWidth()/2;
		double heightSrc = screenSize.getHeight()/2;

		widthSrc -= width/2;
		heightSrc -= height/2;

		setBounds((int) widthSrc, (int) heightSrc, width, height);
	}
	
	class ImagePanel extends JComponent {
	    /**
		 * 
		 */
		private static final long serialVersionUID = -1251274837564899453L;
		
		private Image image;
		
	    public ImagePanel(Image image) {
	        this.image = image;
	    }
	    
	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        g.drawImage(image, 0, 0, this);
	    }
	}


}
