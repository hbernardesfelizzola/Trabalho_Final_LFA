package com.ufrgs.automatos.janelas.utils;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.ufrgs.automatos.JogoMain;
import com.ufrgs.automatos.entity.PlayerData;
import com.ufrgs.automatos.entity.ResponseWord;
import com.ufrgs.automatos.entity.Terminal;
import com.ufrgs.automatos.janelas.utils.ListenersUtils.BackToMenuListener;
import com.ufrgs.automatos.janelas.utils.ListenersUtils.ExecuteWordListener;
import com.ufrgs.automatos.janelas.utils.ListenersUtils.InsertTerminalListener;
import com.ufrgs.automatos.janelas.utils.ListenersUtils.LucioExplainsListener;
import com.ufrgs.automatos.janelas.utils.ListenersUtils.NewGameListener;
import com.ufrgs.automatos.janelas.utils.ListenersUtils.NextTerminalListener;
import com.ufrgs.automatos.janelas.utils.ListenersUtils.RemoveTerminalListener;
import com.ufrgs.automatos.janelas.utils.ListenersUtils.ShowMapListener;

public class SceneUtils {
	
	
	
	public static void updateToMainMenu(JFrame jframe) {
		createScene(SceneMainMenu.class).executeUpdate(jframe);
	}

	public static void updateToWordInsertion(JFrame jframe) {
		createScene(SceneWordInsertion.class).executeUpdate(jframe);
	}
	
	public static void updateToShowHistory(JFrame jframe) {
		createScene(SceneReadHistory.class).executeUpdate(jframe);
	}
	
	
	private static <T> SceneUpdater createScene(Class<? extends SceneUpdater> clazz) {
		try {
			Constructor<? extends SceneUpdater> contructor = (Constructor<? extends SceneUpdater>) clazz.getConstructor();
			
			return contructor.newInstance();
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return null;
	}
	
	public static abstract class SceneUpdater {
		
		public SceneUpdater() {}
		
		public final void executeUpdate(JFrame jframe) {
			resetScreen(jframe);
			executeUpdateIntern(jframe);
			
			jframe.repaint();
		}
		
		protected abstract void executeUpdateIntern(JFrame jframe);

		private void resetScreen(JFrame jframe) {
			jframe.getContentPane().removeAll();
		}
	}
	
	public static class SceneMainMenu extends SceneUpdater {
		
		public SceneMainMenu() {}

		@Override
		protected void executeUpdateIntern(JFrame jframe) {

			try {
				ImageIcon img = getImageIcon("menu_jogo.jpg");
				JLabel background = new JLabel("", img, JLabel.CENTER);
				
				background.setBounds(0, 0, 800, 500);
				jframe.getContentPane().add(background);
				
				String buttonNewGame = JogoMain.getPlayerData(false) == null ? "Novo jogo" : "Continuar jogo";
				
				ButtonsUtils.createTransparentButton(buttonNewGame, 295, 257, 218, 25, background, new NewGameListener(jframe));
				ButtonsUtils.createTransparentButton("Definições tecnicas", 295, 324, 218, 25, background, new LucioExplainsListener());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static class SceneReadHistory extends SceneUpdater {

		@Override
		protected void executeUpdateIntern(JFrame jframe) {
			try {
				PlayerData data = JogoMain.getPlayerData();
				ImageIcon img = getImageIcon("show_text_menu.png");
				JLabel background = new JLabel("", img, JLabel.CENTER);
				
				background.setBounds(0, 0, 800, 500);
				background.setLocation(-7, -17);
				
				jframe.getContentPane().add(background);
				
				updateCache();
				
				String text = data.isLastIndex() ? "Tentar novamente" : "Continuar";
				String currentNode = JogoMain.getGamePathsController().getNodeFromWord(data.getCurrentIndexWord());
				
				System.out.println(currentNode);
				
				JLabel labelTitle = ButtonsUtils.createJLabel(217, 100, 353, 22);
				JLabel labelText = ButtonsUtils.createJLabel(227, 161, 330, 135);

				updateText(labelText, "<html><center>" + JogoMain.getGamePathsController().getNodeMessage(currentNode) + "</center></html>", "Segoe Print", 12, Font.PLAIN);
				updateText(labelTitle, JogoMain.getGamePathsController().getNodeName(currentNode), "Verdana", 16, Font.BOLD);

				ButtonsUtils.createTransparentButton(text, 307, 321, 170, 36, background, new NextTerminalListener(jframe));
				
				background.add(labelTitle);
				background.add(labelText);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		private void updateCache() {
			PlayerData data = JogoMain.getPlayerData();
			
			if (data.getCurrentIndex() > 0) {
				char c = data.getWord().charAt(data.getCurrentIndex()-1);
				ResponseWord rw = JogoMain.getGamePathsController().executeWord(data.getCurrentIndexWord());
				
				System.out.println(data.getCurrentIndexWord().substring(0, data.getCurrentIndexWord().length()-1) + " " + c + " " + rw);
				
				data.addToCache(data.getCurrentIndexWord().substring(0, data.getCurrentIndexWord().length()-1), Terminal.toTerminal(c), rw);
			}
		}
		
		private void updateText(JLabel label, String text, String font, int fontSize, int fontType) {
			label.setFont(new Font(font, fontType, fontSize));
			label.setForeground(Color.WHITE);
			
			label.setText(text);
		}
	}
	
	public static class SceneWordInsertion extends SceneUpdater {

		@Override
		protected void executeUpdateIntern(JFrame jframe) {
			try {
				ImageIcon img = getImageIcon("interacao_jogo.jpg");
				JLabel background = new JLabel("", img, JLabel.CENTER);
				
				background.setBounds(0, 0, 800, 500);
				background.setLocation(-7, -17);
				
				jframe.getContentPane().add(background);
				
				
				ButtonsUtils.createTransparentButton(615, 105, 36, 36, background, new BackToMenuListener(jframe));
				ButtonsUtils.createTransparentButton(138, 103, 36, 36, background, new ShowMapListener());

				ButtonsUtils.createTransparentButton(200, 230, 36, 36, background, new RemoveTerminalListener(jframe));
				ButtonsUtils.createTransparentButton(575, 230, 36, 36, background, new ExecuteWordListener(jframe));
				
				ButtonsUtils.createTransparentButton("W", 237, 333, 36, 36, background, new InsertTerminalListener(jframe, Terminal.TERMINAL_W));
				ButtonsUtils.createTransparentButton("S", 310, 333, 36, 36, background, new InsertTerminalListener(jframe, Terminal.TERMINAL_S));
				ButtonsUtils.createTransparentButton("D", 383, 333, 36, 36, background, new InsertTerminalListener(jframe, Terminal.TERMINAL_D));
				ButtonsUtils.createTransparentButton("E", 455, 333, 36, 36, background, new InsertTerminalListener(jframe, Terminal.TERMINAL_E));
				ButtonsUtils.createTransparentButton("B", 525, 333, 36, 36, background, new InsertTerminalListener(jframe, Terminal.TERMINAL_B));

				updateCurrentWordLabel(background);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		private void updateCurrentWordLabel(JLabel background) {
			JLabel labelCurrentWord = ButtonsUtils.createJLabel(242, 238, 318, 22);
			
			updateButtonsColors(background);
			
			String text = getPlayerDataAsText();
			
			labelCurrentWord.setText(text);
			
			background.add(labelCurrentWord);
		}
		
		private String getPlayerDataAsText() {
			PlayerData pd = JogoMain.getPlayerData();
			String already = "";
			String text = "<html>";
			
			for (int i = 0; i < pd.getWord().length(); i++) {
				char c = pd.getWord().charAt(i);
				String display = (c + "").toUpperCase();
				ResponseWord rw = pd.getCache(already, Terminal.toTerminal(c));
				
				if (rw == null) {
					text = text + "<font color = 'white'>" + display + "</font>";
				} else {
					switch (rw) {
						case REJECTED:
						case ACCEPTED:
							text = text + "<font color = 'green'>" + display + "</font>";
							break;
							
						case INDEFINITION:
							text = text + "<font color = 'red'>" + display + "</font>";
							break;
					}
				}
				
				already = already + c;
			}
			
			return text + "</html>";
		}
		
		private void updateButtonsColors(JLabel main) {
			PlayerData pd = JogoMain.getPlayerData();
			
			if (pd.getCurrentCache() != null) {
				for (Terminal t : pd.getCurrentCache().keySet()) {
					ResponseWord rw = pd.getCurrentCache().get(t);
					String image = rw.equals(ResponseWord.ACCEPTED) || rw.equals(ResponseWord.REJECTED) ? "accepted_button.png" : "rejected_button.png";
					
					try {
						ImageIcon img = getImageIcon(image);
						JLabel background = new JLabel("", img, JLabel.CENTER);
						
						background.setBounds(t.getX(), t.getZ(), 62, 62);
						background.setLocation(t.getX(), t.getZ());
						
						main.add(background);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
			}
		}
	}
	
	
	public static ImageIcon getImageIcon(String url) throws IOException {
		return new ImageIcon(ImageIO.read(JogoMain.class.getClassLoader().getResourceAsStream(url)));
	}
}
