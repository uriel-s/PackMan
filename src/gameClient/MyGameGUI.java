package gameClient;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Server.Game_Server;

public class MyGameGUI {

	public MyGameGUI() {
		try {
			JFrame in = new JFrame();
			String pickGame = JOptionPane.showInputDialog(in,"choose your game \n for manual enter 1 \n for automatic enter 2");
			int game_num =Integer.parseInt(pickGame); 
			if(game_num == 1) {
				ClientGame m = new ClientGame();
			}
			if(game_num == 2) {
				AutoGame g = new AutoGame();
			}
		}
		catch (Exception e) {
			e.printStackTrace();	
		}
	}
}
