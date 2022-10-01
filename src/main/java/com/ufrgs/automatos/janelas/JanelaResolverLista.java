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

public class JanelaResolverLista extends JFrame {

	private static final long serialVersionUID = 8667039720767982981L;
	private JPanel contentPane;
	
	private JButton btnNewButton, btnVoltarAoMenu;
	
	private JTextArea textArea;
	
	private JLabel textArea1;

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
		
		textArea = new JTextArea();
		textArea1 = new JLabel("");
		
		textArea1.setVerticalAlignment(JLabel.TOP);
		
		JScrollPane scroll1 = new JScrollPane(textArea);
		JScrollPane scroll2 = new JScrollPane(textArea1);
		
		scroll1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		
		btnNewButton = new JButton("Resolver palavras");
		btnVoltarAoMenu = new JButton("Voltar ao menu");
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(32)
							.addComponent(scroll1, GroupLayout.PREFERRED_SIZE, 318, GroupLayout.PREFERRED_SIZE)
							.addGap(77)
							.addComponent(scroll2, GroupLayout.PREFERRED_SIZE, 318, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(btnVoltarAoMenu, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnNewButton))
							.addGap(308)))
					.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(77))
		);
		
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(87)
					.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(54)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(scroll1, GroupLayout.PREFERRED_SIZE, 264, GroupLayout.PREFERRED_SIZE)
						.addComponent(scroll2, GroupLayout.PREFERRED_SIZE, 264, GroupLayout.PREFERRED_SIZE))
					.addGap(39)
					.addComponent(btnNewButton)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnVoltarAoMenu)
					.addContainerGap(37, Short.MAX_VALUE))
		);
		
		contentPane.setLayout(gl_contentPane);
		
		
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
