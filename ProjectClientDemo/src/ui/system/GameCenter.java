package ui.system;

import javax.swing.JButton;
import javax.swing.JFrame;

import logic.netcore.NetcoreLogicService;
import rmi.RMIController;
import ui.game.cooperate.CooperateGameListener;
import ui.game.cooperate.CooperateGamePanel;

public class GameCenter extends JFrame
{
	
	CooperateGamePanel gamePanel;
	CooperateGameListener listener;
	JButton firstRoom;
	JButton secondRoom;
	NetcoreLogicService ns;
	
	public GameCenter()
	{
		this.setVisible(true);
		this.setBounds(0, 0, 800, 600);
		this.setTitle("Test Game Center");
		this.setLayout(null);
		
		RMIController rmi = new RMIController();
		ns =rmi.getNetworkService();
		
		firstRoom = new JButton("first");
		firstRoom.setVisible(true);
		firstRoom.setBounds(100, 100, 100, 50);

		secondRoom = new JButton("second");
		secondRoom.setVisible(true);
		secondRoom.setBounds(100, 200, 100, 50);
		
		this.add(firstRoom);
		this.add(secondRoom);
		repaint();
	}
	
	public static void main(String[] args)
	{
		new GameCenter();
	}
}
