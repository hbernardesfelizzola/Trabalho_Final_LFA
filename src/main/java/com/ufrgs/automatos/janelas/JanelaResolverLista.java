package com.ufrgs.automatos.janelas;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.ufrgs.automatos.JogoMain;
import com.ufrgs.automatos.controllers.PathsController;
import com.ufrgs.automatos.controllers.ResponseWord;
import java.awt.Font;

public class JanelaResolverLista extends JFrame {

	private static final long serialVersionUID = 8667039720767982981L;
	private JPanel contentPane;
	
	private JButton btnNewButton, btnVoltarAoMenu;
	
	private JTextArea textArea;
	
	private JLabel textArea1, infoLangLabel;
	private JTextField fileName;

	/**
	 * Create the panel.
	 */
	public JanelaResolverLista() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setCenterBounds(800, 500);

		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(760, 92, 0, 364);
		
		textArea = new JTextArea();
		textArea1 = new JLabel("");
		
		textArea1.setVerticalAlignment(JLabel.TOP);
		
		JScrollPane scroll1 = new JScrollPane(textArea);
		scroll1.setBounds(37, 59, 318, 264);
		JScrollPane scroll2 = new JScrollPane(textArea1);
		scroll2.setBounds(432, 59, 318, 264);
		
		scroll1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		
		btnNewButton = new JButton("Resolver palavras");
		btnNewButton.setBounds(333, 362, 119, 23);
		btnVoltarAoMenu = new JButton("Voltar ao menu");
		btnVoltarAoMenu.setBounds(333, 396, 119, 23);
		contentPane.setLayout(null);
		contentPane.add(scroll1);
		contentPane.add(scroll2);
		contentPane.add(btnVoltarAoMenu);
		contentPane.add(btnNewButton);
		contentPane.add(lblNewLabel);
		
		fileName = new JTextField();
		fileName.setBounds(65, 363, 189, 20);
		contentPane.add(fileName);
		fileName.setColumns(10);
		
		JButton loadAfdButton = new JButton("Carregar AFD");
		loadAfdButton.setBounds(37, 396, 119, 23);
		contentPane.add(loadAfdButton);
		
		JButton minimizeAfdButton = new JButton("Minimalizar AFD");
		minimizeAfdButton.setBounds(166, 396, 119, 23);
		contentPane.add(minimizeAfdButton);
		
		JLabel lblNewLabel_1 = new JLabel("Nome do arquivo");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(103, 338, 119, 14);
		contentPane.add(lblNewLabel_1);
		
		infoLangLabel = new JLabel("New label");
		infoLangLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		infoLangLabel.setHorizontalAlignment(SwingConstants.CENTER);
		infoLangLabel.setBounds(432, 25, 318, 23);
		contentPane.add(infoLangLabel);
		
		btnVoltarAoMenu.addActionListener(new BackMenuListener());
		btnNewButton.addActionListener(new WordSolver());
		loadAfdButton.addActionListener(new LoadAfdListener());
		minimizeAfdButton.addActionListener(new MinimizeAfdListener());
		
		updateInfoLabel();
	}
	
	private void setCenterBounds(int width, int height) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double widthSrc = screenSize.getWidth()/2;
		double heightSrc = screenSize.getHeight()/2;

		widthSrc -= width/2;
		heightSrc -= height/2;

		setBounds((int) widthSrc, (int) heightSrc, width, height);
	}
	
	private void updateInfoLabel() {
		PathsController controller = JogoMain.getPathsController();
		
		String info = "<html>";
		
		info = info + "<font color = 'black'>Vazia:</font> " + 
				getStringReponse(controller.isLangEmpty()) + 
				" | <font color = 'black'>Finita</font>: " + 
				getStringReponse(controller.isLangFinite());
		
		info = info + "</html>";
		
		infoLangLabel.setText(info);
	}
	
	private String getStringReponse(boolean bool) {
		return bool ? "<font color = 'green'>Sim</font>" : "<font color = 'red'>Não</font>";
	}
	
	
	class BackMenuListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
		}
	}
	
	class MinimizeAfdListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			System.out.println(JogoMain.getPathsController().getEstados());
			JogoMain.getPathsController().minimazeAutomate();
			System.out.println(JogoMain.getPathsController().getEstados());
			
			textArea1.setText(JogoMain.getPathsController().getPathsAsString());
			updateInfoLabel();
		}
	}
	
	class LoadAfdListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			File f = new File(fileName.getText());
		
			if (!f.exists()) {
				fileName.setText("Arquivo inexistente");
			} else {
				try {
					PathsController controller = new PathsController(new File(fileName.getText()));
					
					JogoMain.setPathsController(controller);
					textArea1.setText(JogoMain.getPathsController().getPathsAsString());
					fileName.setText("AFD atualizado");
					updateInfoLabel();
				} catch (Exception exp) {
					fileName.setText("Formatação incorreta");
					exp.printStackTrace();
				}
			}
		}
	}
	
	class WordSolver implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			TreeMap<String, ResponseWord> responses = JogoMain.getPathsController().executeWord(convertStringToList());
			
			String text = "<html>";
			
			for (String s : responses.keySet()) {
				text = text + getWordResponse(s, responses.get(s)) + "<br>";
			}
			
			text = text + "</html>";
			
			textArea1.setText(text);
		}
		
		private String getWordResponse(String s, ResponseWord response) {
			switch (response) {
				case ACCEPTED:
					return "<font color = 'green'>" + s + " -> " + response.toString() + "</font>";
					
				case INDEFINITION:
					return "<font color = 'red'>" + s + " -> " + response.toString() + "</font>";
					
				case REJECTED:
					return "<font color = 'red'>" + s + " -> " + response.toString() + "</font>";
			}
			
			return "";
		}
		
		private ArrayList<String> convertStringToList() {
			ArrayList<String> list = new ArrayList<>();
			
			for (String s : textArea.getText().split("\n")) {
				list.add(s);
			}
			
			return list;
		}
	}
}
