package com.ufrgs.automatos.janelas;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import com.ufrgs.automatos.JogoMain;
import com.ufrgs.automatos.controllers.ResponseWord;
import javax.swing.JTextField;

public class JanelaResolverLista extends JFrame {

	private static final long serialVersionUID = 8667039720767982981L;
	private JPanel contentPane;
	
	private JButton btnNewButton, btnVoltarAoMenu;
	
	private JTextArea textArea;
	
	private JLabel textArea1;
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
		fileName.setBounds(103, 363, 119, 20);
		contentPane.add(fileName);
		fileName.setColumns(10);
		
		JButton loadAfdButton = new JButton("Carregar AFD");
		loadAfdButton.setBounds(37, 396, 119, 23);
		contentPane.add(loadAfdButton);
		
		JButton minimizeAfdButton = new JButton("Minimalizar AFD");
		minimizeAfdButton.setBounds(166, 396, 119, 23);
		contentPane.add(minimizeAfdButton);
		
		
		btnVoltarAoMenu.addActionListener(new BackMenuListener());
		btnNewButton.addActionListener(new WordSolver());
	}
	
	private void setCenterBounds(int width, int height) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double widthSrc = screenSize.getWidth()/2;
		double heightSrc = screenSize.getHeight()/2;

		widthSrc -= width/2;
		heightSrc -= height/2;

		setBounds((int) widthSrc, (int) heightSrc, width, height);
	}
	
	
	class BackMenuListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
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
