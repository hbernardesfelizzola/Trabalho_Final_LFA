package com.ufrgs.automatos.janelas.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import com.ufrgs.automatos.JogoMain;
import com.ufrgs.automatos.entity.PlayerData;
import com.ufrgs.automatos.entity.ResponseWord;
import com.ufrgs.automatos.entity.Terminal;
import com.ufrgs.automatos.janelas.JanelaMapa;
import com.ufrgs.automatos.janelas.JanelaPrincipal;
import com.ufrgs.automatos.janelas.JanelaResolverLista;

public class ListenersUtils {

	public static abstract class ActionListerWithJFrame implements ActionListener {
		
		public ActionListerWithJFrame(JFrame frame) {
			this.jframe = frame;
		}
		
		private JFrame jframe;
		
		public JFrame getJFrame() {return this.jframe;}
	}
	

	public static class NewGameListener extends ActionListerWithJFrame {
		
		public NewGameListener(JFrame frame) {
			super(frame);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			SceneUtils.updateToWordInsertion(this.getJFrame());
		}
	}
	
	public static class LucioExplainsListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JanelaPrincipal.getInstance().setVisible(false);
			JanelaResolverLista.getInstance().setVisible(true);
		}
	}
	
	public static class BackToMenuListener extends ActionListerWithJFrame {
		
		public BackToMenuListener(JFrame frame) {
			super(frame);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			SceneUtils.updateToMainMenu(this.getJFrame());
		}
	}
	
	public static class ShowMapListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JanelaMapa.getInstance().setVisible(true);
		}
	}
	

	public static class InsertTerminalListener extends ActionListerWithJFrame {

		public InsertTerminalListener(JFrame frame, Terminal terminal) {
			super(frame);
			
			this.terminal = terminal;
		}
		
		private Terminal terminal;

		@Override
		public void actionPerformed(ActionEvent e) {
			JogoMain.getPlayerData().addChar(terminal.getLetter());
			
			SceneUtils.updateToWordInsertion(getJFrame());
		}
	}
	
	public static class RemoveTerminalListener extends ActionListerWithJFrame {

		public RemoveTerminalListener(JFrame frame) {
			super(frame);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JogoMain.getPlayerData().removeLastChar();
			
			SceneUtils.updateToWordInsertion(getJFrame());
		}
	}
	
	public static class ExecuteWordListener extends ActionListerWithJFrame {

		public ExecuteWordListener(JFrame frame) {
			super(frame);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			SceneUtils.updateToShowHistory(getJFrame());
		}
	}
	
	public static class NextTerminalListener extends ActionListerWithJFrame {

		public NextTerminalListener(JFrame frame) {
			super(frame);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (JogoMain.getPlayerData().isLastIndex()) {
				JogoMain.getPlayerData().resetCurrentIndex();
				JogoMain.getPlayerData().resetWord();
				
				SceneUtils.updateToWordInsertion(getJFrame());
			} else {
				JogoMain.getPlayerData().increaseCurrentIndex();
				
				SceneUtils.updateToShowHistory(getJFrame());
			}
		}
	}
}
