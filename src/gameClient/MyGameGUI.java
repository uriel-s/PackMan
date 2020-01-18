package gameClient;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Server.Game_Server;

public class MyGameGUI {
/**
 * this class let the user to choose between start a manual or automatic game
 * 
 */
	public MyGameGUI() {
		try {
			JFrame in = new JFrame();
			String pickGame = JOptionPane.showInputDialog(in,"choose your game \n for manual enter 1 \n for automatic enter 2");
			int game_num =Integer.parseInt(pickGame); 
			if(game_num == 1) {
				// option 1 - manual game
				ClientGame m = new ClientGame(); 
			}
			if(game_num == 2) {
				// option 2 - auto game
				AutoGame g = new AutoGame();
			}
			else throw new RuntimeException("input isnt good ");	
		}
		catch (Exception e) {
			e.printStackTrace();	
		}
	}
}