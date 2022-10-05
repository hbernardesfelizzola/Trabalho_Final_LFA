package com.ufrgs.automatos.janelas.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ButtonsUtils {
	
	public static final boolean DEBUG_BORDER = false;
	
	
	public static JLabel createJLabel(int x, int y, int width, int height) {
		JLabel labelCurrentWord = new JLabel();
		
		labelCurrentWord.setHorizontalTextPosition(SwingConstants.CENTER);
		labelCurrentWord.setHorizontalAlignment(SwingConstants.CENTER);
		labelCurrentWord.setSize(width, height);
		labelCurrentWord.setPreferredSize(new Dimension(500, 100));
		labelCurrentWord.setLocation(x, y);
		
		if (ButtonsUtils.DEBUG_BORDER) {
			labelCurrentWord.setOpaque(true);
			labelCurrentWord.setBackground(Color.YELLOW);
		}
		
		return labelCurrentWord;
	}

	public static void createTransparentButton(int x, int y, int width, int height, JLabel label, ActionListener listener) {
		createTransparentButton("", x, y, width, height, label, listener);
	}

	public static void createTransparentButton(String text, int x, int y, int width, int height, JLabel label, ActionListener listener) {
		createTransparentButton(text, x, y, width, height, 16, label, listener);
	}

	public static void createTransparentButton(String text, int x, int y, int width, int height, int foutSize, JLabel label, ActionListener listener) {
		JButton button = new JButton(text);
		
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setSize(width, height);
		button.setPreferredSize(new Dimension(width, height));
		button.setLocation(x, y);
		
		if (!DEBUG_BORDER) {
			button.setOpaque(false);
			button.setContentAreaFilled(false);
			button.setBorderPainted(false);
			button.setBorder(BorderFactory.createEmptyBorder());
		}
		
		button.setFont(new Font("Verdana", Font.PLAIN, foutSize));
		button.setForeground(Color.WHITE);
		
		button.addActionListener(listener);
		
		label.add(button);
	}
	
	
	
}
