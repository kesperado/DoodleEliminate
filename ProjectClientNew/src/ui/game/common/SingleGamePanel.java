package ui.game.common;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;

import logic.game.GameLogicService;
import po.GameSettingPO;
import ui.BeginPanel;

public class SingleGamePanel extends GamePanel {
	BeginPanel front;
	SingleGamePanel sg;

	public SingleGamePanel(GameSettingPO setting, String name,
			GameLogicService g, BeginPanel front) {
		super(setting, name, g);
		this.sg = this;
		this.front = front;
		this.exit_label.addMouseListener(new shutdownListener());

	}

	public void alert() {
		try {
			message = gls.getMessage(name);
			System.out.println(message.imformation);
			switch (message.imformation) {
			case "End":
				int score = message.list.get(0)[0][0];
				JOptionPane.showMessageDialog(null, "您的分数为" + score);
				front.back_to_begin();
				sg.tuichu_guansheng();
				break;
			default:
				mp.getRenew(message);
				break;
			}

		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	class shutdownListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			try {
				gls.shutdownGame(name);
				front.back_to_begin();
				sg.tuichu_guansheng();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}
	}
}
