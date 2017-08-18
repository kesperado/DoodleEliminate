package ui.game.common;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;

import logic.game.GameLogicService;
import po.GameSettingPO;
import ui.LoginPanel;

public class TryGamePanel extends GamePanel
{
	LoginPanel front;
	public TryGamePanel(GameSettingPO setting,String name, GameLogicService g, 
			LoginPanel front)
	{
		super(setting,name,g);
		
		this.front=front;
		this.exit_label.addMouseListener(new shutdownListener());

	}
	
	public void alert()
	{
		try {
			message = gls.getMessage(name);
			//System.out.println(message.imformation);
			switch (message.imformation) {
			case "End":
				JOptionPane.showMessageDialog(null, "Times UP!");
				front.back_to_login();
				break;
			default:
				mp.getRenew(message);
				break;
			}

		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	class shutdownListener implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent arg0)
		{
			try
			{
				gls.shutdownGame(name);
				front.back_to_login();
			} catch (RemoteException e)
			{
				e.printStackTrace();
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0)
		{
			// TODO Auto-generated method stub
			
		}
	}
}
