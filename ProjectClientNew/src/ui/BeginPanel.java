package ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import logic.game.GameLogicService;
import logic.game.GameService;
import po.GameSettingPO;
import po.PlayerPO;
import rmi.RMIController;
import ui.game.common.GameListener;
import ui.game.common.SingleGamePanel;

@SuppressWarnings("serial")
public class BeginPanel extends JPanel {

	private int width = 600;
	private int height = 600;

	private int x = 0;
	private int y = 0;

	private Icon p1;
	private Icon p2;
	private Icon p3;
	private Icon g1;
	private Icon p4;
	private Icon p5;

	private JLabel prop1;
	private JLabel prop2;
	private JLabel prop3;
	private JLabel gra;

	private JLabel play;

	private Icon us;// 不使用
	private Icon se;// 使用
	private JLabel chos1;
	private JLabel chos2;
	private JLabel chos3;
	private JLabel chos4;

	private MainPanel mp;
	private PlayerPO po;

	// ADD
	SingleGamePanel game = null;

	BeginPanel(MainPanel mp, PlayerPO po) {
		this.po = po;
		this.mp = mp;
		this.setLayout(null);
		this.setBounds(x, y, width, height);
		this.setVisible(true);
		this.setOpaque(false);

		init();
	}

	void init() {
		p1 = new ImageIcon("image/info/prop_1.png");
		prop1 = new JLabel(p1);

		p2 = new ImageIcon("image/info/prop_2.png");
		prop2 = new JLabel(p2);

		p3 = new ImageIcon("image/info/prop_3.png");
		prop3 = new JLabel(p3);

		g1 = new ImageIcon("image/info/gravity.png");
		gra = new JLabel(g1);

		p4 = new ImageIcon("image/system/play.png");
		p5 = new ImageIcon("image/system/play2.png");
		play = new JLabel(p4);
		play.addMouseListener(new MyListener());

		us = new ImageIcon("image/info/unselected.png");
		se = new ImageIcon("image/info/selected.png");

		chos1 = new JLabel(us);
		chos1.addMouseListener(new MyListener());
		chos2 = new JLabel(us);
		chos2.addMouseListener(new MyListener());
		chos3 = new JLabel(us);
		chos3.addMouseListener(new MyListener());
		chos4 = new JLabel(us);
		chos4.addMouseListener(new MyListener());

		prop1.setBounds(180, 95, 80, 80);
		add(prop1);
		prop2.setBounds(180, 195, 80, 80);
		add(prop2);
		prop3.setBounds(180, 295, 80, 80);
		add(prop3);
		gra.setBounds(380, 212, 80, 40);
		add(gra);
		play.setBounds(410, 325, 100, 50);
		add(play);
		chos1.setBounds(280, 145, 30, 30);
		add(chos1);
		chos2.setBounds(280, 245, 30, 30);
		add(chos2);
		chos3.setBounds(280, 345, 30, 30);
		add(chos3);
		chos4.setBounds(496, 212, 30, 30);
		add(chos4);
	}

	void play() {
		boolean p3 = false;
		boolean p4 = false;
		boolean p5 = false;
		int g = 0;
		if (chos1.getIcon().equals(se)) {
			p3 = true;
		}
		if (chos2.getIcon().equals(se)) {
			p4 = true;
		}
		if (chos3.getIcon().equals(se)) {
			p5 = true;
		}
		if (chos4.getIcon().equals(se)) {
			g = 1;
		}
		GameSettingPO gp = new GameSettingPO(p3, p4, p5, g);
		RMIController rmi = new RMIController();
		GameService gs = rmi.getGameService();

		// 设置对象构造

		// 这里是写出singleGamePanel的地方
		try {
			GameListener gl = new GameListener();
			GameLogicService temple = gs.startSingleGame(gp);
			temple.addListener(gl, po.playerID);
			game = new SingleGamePanel(gp, po.playerID, temple, this);
			gl.setAim(game);
			temple.startGame();
			enter_game();
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(null, "网络连接中断");
		}
	}

	// 用于游戏返回
	public void back_to_begin() {
		setSize(600, 600);
		removeAll();
		game = null;
		mp.setLabelsVisible(true);
		mp.chuxian();
		mp.kaisy();
		init();

		repaint();
	}

	// 进入游戏
	public void enter_game() {
		setSize(800, 600);
		mp.setLabelsVisible(false);
		removeAll();
		add(game);
		repaint();
	}

	class MyListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			if (arg0.getSource() == chos1 || arg0.getSource() == chos2
					|| arg0.getSource() == chos3 || arg0.getSource() == chos4) {
				if (((JLabel) arg0.getSource()).getIcon() == us) {
					((JLabel) arg0.getSource()).setIcon(se);
				} else {
					((JLabel) arg0.getSource()).setIcon(us);
				}
			} else if (arg0.getSource() == play) {
				mp.xiaoqu();
				mp.guansy();
				play();
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			if (arg0.getSource() == play) {
				play.setIcon(p5);
			}
			repaint();
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			if (arg0.getSource() == play) {
				play.setIcon(p4);
			}
			repaint();
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
