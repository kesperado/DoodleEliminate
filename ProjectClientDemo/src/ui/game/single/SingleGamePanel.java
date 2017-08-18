package ui.game.single;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import logic.game.GameLogicService;
import logic.game.GameService;
import logic.game.Message;
import rmi.RMIController;

@SuppressWarnings("serial")
public class SingleGamePanel extends JPanel {
	private SinglePanel mp;
	private GameLogicService gls;// 获得信息的对象
	private Message message;
	private Image back;
	private String name;

	public SingleGamePanel(String name, int number) {
		this.setSize(600, 600);
		this.setLayout(null);
		this.name = name;

		back = Toolkit.getDefaultToolkit().getImage("system/right2.png");

		RMIController rmi = new RMIController();
		GameService gs = rmi.getGameService();
		int gameno = number;
		try {
			gls = gs.getSingleGame(gameno);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}

		mp = new SinglePanel(name, gls, this);
		this.add(mp);// 成功把监听限制在panel上
	}

	public void alert() {
		try {
			message = gls.getMessage(name);
			switch (message.imformation) {
			case "End":
				JOptionPane.showMessageDialog(null, "Times UP!");
				break;
			default:
				mp.getRenew();
				break;
			}

		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.WHITE);
		if (back != null) {
			int height = back.getHeight(this);
			int width = back.getWidth(this);
			if (height != -1 && height > getHeight())
				height = getHeight();
			if (width != -1 && width > getWidth())
				width = getWidth();
			int x = (int) (((double) (getWidth() - width)) / 2.0);
			int y = (int) (((double) (getHeight() - height)) / 2.0);
			g.drawImage(back, x, y, width, height, this);
		}
	}

}
