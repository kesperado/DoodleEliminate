package ui.game.common;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;

import logic.game.GameLogicService;
import po.GameSettingPO;
import ui.RoomPanel;

public class CoopGamePanel extends GamePanel {
	CoopGamePanel cGamePanel;
	RoomPanel front;

	public CoopGamePanel(GameSettingPO setting, String name,
			GameLogicService g, RoomPanel front) {
		super(setting, name, g);
		this.cGamePanel = this;
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
				front.back_to_room();
				cGamePanel.tuichu_guansheng();
				break;
			case "missing":
				JOptionPane.showMessageDialog(null, "有玩家下线了");
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
				cGamePanel.tuichu_guansheng();
				front.exit_while_game();
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
