package ui.game.cooperate;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import logic.game.GameLogicService;
import logic.game.GameService;
import logic.game.Message;
import rmi.RMIController;

public class CooperateGamePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private CooperatePanel mp;
	private GameLogicService gls;// 获得信息的对象
	private Message message;
	private Image back;
	private String name;

	public CooperateGamePanel(String name, int number) {
		this.setSize(600, 600);
		this.setLayout(null);
		this.name = name;
		back = Toolkit.getDefaultToolkit().getImage("system/right2.png");

		RMIController rmi = new RMIController();
		GameService gs = rmi.getGameService();
		int gameno = number;
		try {
			gls = gs.getCoopGame(gameno);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}

		mp = new CooperatePanel(name, gls, this);
		this.add(mp);// 成功把监听限制在panel上
	}

	public void alert() {
		try {
			message = gls.getMessage(name);
			// System.out.println("Player"+name+"收到信息"+message.imformation);
			// for(Integer[][] in:message.list)
			// {
			// System.out.println("*********");
			// for(int i=0;i<9;i++)
			// {
			// for(int j=0;j<9;j++)
			// {
			// System.out.print(in[i][j]+" ");
			// }
			// System.out.println();
			// }
			// System.out.println("*********");
			// }

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

	public static void main(String args[]) {
		JFrame jf = new JFrame();
		jf.setBounds(270, 40, 800, 600);
		jf.setVisible(true);

		JFrame jf2 = new JFrame();
		jf2.setBounds(300, 60, 800, 600);
		jf2.setVisible(true);

		RMIController rmi = new RMIController();
		GameService gs = rmi.getGameService();
		GameLogicService gls;

		int gameno = 0;
		try {
			gameno = gs.startCoopGame();
			// System.out.println("游戏编号" + gameno);
			gls = gs.getCoopGame(gameno);

			CooperateGamePanel first = new CooperateGamePanel("first", gameno);
			CooperateGamePanel second = new CooperateGamePanel("second", gameno);

			CooperateGameListener fl = new CooperateGameListener(first);
			CooperateGameListener sl = new CooperateGameListener(second);

			gls.addListener(fl, "first");
			gls.addListener(sl, "second");

			first.setVisible(true);
			second.setVisible(true);

			jf.add(first);
			jf2.add(second);
			jf.repaint();
			jf2.repaint();

		} catch (RemoteException e1) {
			// System.out.println("Error!");
			e1.printStackTrace();
		}
	}

}
