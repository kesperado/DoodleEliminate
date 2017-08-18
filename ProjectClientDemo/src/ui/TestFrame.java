package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import logic.game.GameLogicService;
import logic.game.GameService;
import rmi.RMIController;
import ui.game.single.SingleGameListener;
import ui.game.single.SingleGamePanel;
import ui.information.TopPanel;
import ui.login.LoginPanel;
import ui.register.RegisterPanel;
import ui.store.StorePanel;

@SuppressWarnings("serial")
public class TestFrame extends JFrame {
	private int Width = 800;
	private int Height = 600;// 常见参数

	private int state = 0;// 状态参数

	private leftPanel leftPanel;// 左边的功能界面
	private rightPanel rightPanel;// 右边对应的操作界面

	private Image back1;// 左边背景
	private Image back2;// 右边背景

	private Icon login_icon;// 登陆图标
	private Icon register_icon;// 注册图标
	private Icon tryit_icon;// 试玩图标 以上三个是200*100的大小
	private Icon back_icon;// 后退图标

	private JLabel login_Label;// 登陆按钮
	private JLabel register_Label;// 注册按钮
	private JLabel tryit_Label;// 试玩按钮
	private JLabel back_Label;// 后退按钮

	private Icon single_icon;// 单人模式图标
	private Icon cooperate_icon;// 协作模式图标
	private Icon fight_icon;// 对战模式 以上 200*75图标
	private Icon store_icon;// 商店 200*150图标
	private Icon top_icon;// 排行榜 200*150大小图标

	private JLabel single_Label;// 单人按钮
	private JLabel cooperate_Label;// 协作按钮
	private JLabel fight_Label;// 对战按钮
	private JLabel store_Label;// 商店按钮
	private JLabel top_Label;// 排行榜按钮

	private LoginPanel lp;// 登陆界面
	private RegisterPanel rp;// 注册界面
	private StorePanel sp;// 商城界面
	private TopPanel tp;// 排行榜界面
	private SingleGamePanel sf;// 单人游戏界面

	private String name = "anonym";// 玩家姓名 默认是anonym 匿名者

	public TestFrame() {
		this.setTitle("天天爱消除");
		this.setBounds(270, 80, Width, Height);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

		back1 = Toolkit.getDefaultToolkit().getImage("system/left.png");
		back2 = Toolkit.getDefaultToolkit().getImage("system/right.png");

		login_icon = new ImageIcon("system/login.png");
		register_icon = new ImageIcon("system/register.png");
		tryit_icon = new ImageIcon("system/try.png");
		back_icon = new ImageIcon("system/back.png");

		single_icon = new ImageIcon("system/single.png");
		cooperate_icon = new ImageIcon("system/cooperate.png");
		fight_icon = new ImageIcon("system/combat.png");
		store_icon = new ImageIcon("system/store.png");
		top_icon = new ImageIcon("system/top.png");

		leftPanel = new leftPanel();
		rightPanel = new rightPanel();
		getContentPane().add(leftPanel);
		getContentPane().add(rightPanel);

	}

	public static void main(String args[]) {
		new TestFrame().setVisible(true);
	}

	class leftPanel extends JPanel {
		private Image back;

		public leftPanel() {
			this.setBounds(0, 0, 200, Height);
			this.back = back1;
			this.setLayout(null);
			this.setOpaque(true);
			init();
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

		// 不用看了 这是写背景的方法
		public void init() {
			login_Label = new JLabel(login_icon, JLabel.CENTER);
			login_Label.setBounds(0, 25, 200, 100);
			register_Label = new JLabel(register_icon, JLabel.CENTER);
			register_Label.setBounds(0, 300, 200, 100);
			tryit_Label = new JLabel(tryit_icon, JLabel.CENTER);
			tryit_Label.setBounds(0, 400, 200, 100);

			login_Label.addMouseListener(new MyListener());
			register_Label.addMouseListener(new MyListener());
			tryit_Label.addMouseListener(new MyListener());

			back_Label = new JLabel(back_icon, JLabel.CENTER);
			back_Label.setBounds(0, 600, 200, 100);
			back_Label.addMouseListener(new MyListener());

			single_Label = new JLabel(single_icon, JLabel.CENTER);
			single_Label.setBounds(0, -100, 200, 75);
			single_Label.addMouseListener(new MyListener());
			cooperate_Label = new JLabel(cooperate_icon, JLabel.CENTER);
			cooperate_Label.setBounds(0, -100, 200, 75);
			fight_Label = new JLabel(fight_icon, JLabel.CENTER);
			fight_Label.setBounds(0, -100, 200, 75);

			store_Label = new JLabel(store_icon, JLabel.CENTER);
			store_Label.setBounds(0, 600, 200, 100);
			store_Label.addMouseListener(new MyListener());

			top_Label = new JLabel(top_icon, JLabel.CENTER);
			top_Label.setBounds(0, 600, 200, 100);
			top_Label.addMouseListener(new MyListener());

			this.add(login_Label);
			this.add(register_Label);
			this.add(tryit_Label);
			this.add(back_Label);

			this.add(single_Label);
			this.add(cooperate_Label);
			this.add(fight_Label);
			this.add(store_Label);
			this.add(top_Label);
			repaint();
		}
	}

	class rightPanel extends JPanel {
		private Image back;

		public rightPanel() {
			this.setBounds(200, 0, 600, Height);
			this.back = back2;
			this.setOpaque(true);
			this.setLayout(null);
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
			// 不用看了 这是写背景的方法
		}
	}

	class MyListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getSource().equals(login_Label)) {
				changeZT(1);
			} else if (e.getSource().equals(register_Label)) {
				changeZT(2);
			} else if (e.getSource().equals(tryit_Label)) {
				changeZT(3);
			} else if (e.getSource().equals(back_Label)) {
				changeZT(0);
			} else if (e.getSource().equals(store_Label)) {
				changeZT(4);
			} else if (e.getSource().equals(top_Label)) {
				changeZT(5);
			} else if (e.getSource().equals(single_Label)) {
				changeZT(6);
			}
		}

		public void mouseEntered(MouseEvent e) {

		}

		public void mouseExited(MouseEvent e) {

		}

		public void mousePressed(MouseEvent arg0) {

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	}

	void yidong(final JLabel one, int dex, int dey) {
		final int nowX = one.getX();
		final int nowY = one.getY();
		final double stepX = (dex - nowX) / 30;
		final double stepY = (dey - nowY) / 30;// 五步到达
		one.setLocation((int) (one.getX() + 15 * stepX),
				(int) (one.getY() + 15 * stepY));
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				int i = 0;
				while (i < 15) {
					one.setLocation((int) (one.getX() + stepX),
							(int) (one.getY() + stepY));
					i++;
					try {
						Thread.sleep(25);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		t.start();
	}

	// 左侧功能块移动方法

	void yidong2(final JPanel one, int dex, int dey) {
		final int nowX = one.getX();
		final int nowY = one.getY();
		final double stepX = (dex - nowX) / 30;
		final double stepY = (dey - nowY) / 30;// 五步到达
		one.setLocation((int) (one.getX() + 10 * stepX),
				(int) (one.getY() + 10 * stepY));
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				int i = 0;
				while (i < 20) {
					one.setLocation((int) (one.getX() + stepX),
							(int) (one.getY() + stepY));
					i++;
					try {
						Thread.sleep(25);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		t.start();
	}

	// 右边界面移动的方法
	void changeZT(int zt) {
		// 0 退回界面 或者主界面
		// 1登陆界面
		// 2注册界面
		// 3游戏界面
		// 4商城界面
		// 5排行榜界面
		// 6单机游戏界面
		// 7协作游戏界面
		// 8协作对战游戏界面
		// state是现有状态 zt是目标状态
		switch (state) {
		case 0:
			if (zt == 1) {
				yidong(tryit_Label, 0, 600);
				yidong(register_Label, 0, 600);
				yidong(back_Label, 0, 450);

				lp = new LoginPanel();
				lp.setLocation(600, 0);
				rightPanel.add(lp);
				yidong2(lp, 0, 0);
				state = 1;
			}// 登陆界面
			else if (zt == 2) {
				yidong(tryit_Label, 0, 600);
				yidong(login_Label, 0, -100);
				yidong(register_Label, 0, 50);
				yidong(back_Label, 0, 450);

				rp = new RegisterPanel();
				rp.setLocation(600, 0);
				rightPanel.add(rp);
				yidong2(rp, 0, 0);
				state = 2;
			}// 注册界面
			else if (zt == 3) {
				yidong(tryit_Label, 0, 600);
				yidong(login_Label, 0, -100);
				yidong(register_Label, 0, 600);

				yidong(single_Label, 0, 25);
				yidong(cooperate_Label, 0, 125);
				yidong(fight_Label, 0, 225);

				yidong(store_Label, 0, 375);
				yidong(top_Label, 0, 275);

				yidong(back_Label, 0, 475);

				state = 3;
			}// 游客登陆
			break;
		case 1:
			if (zt == 0) {
				yidong(back_Label, 0, 600);
				yidong(register_Label, 0, 300);
				yidong(tryit_Label, 0, 400);

				yidong2(lp, 600, 0);
				state = 0;
			}// 从登陆退回主界面
			break;
		case 2:
			if (zt == 0) {
				yidong(login_Label, 0, 50);
				yidong(register_Label, 0, 300);
				yidong(tryit_Label, 0, 400);
				yidong(back_Label, 0, 600);

				yidong2(rp, 600, 0);
				state = 0;
			}// 从注册退回主界面
			break;
		case 3:
			if (zt == 0) {
				yidong(single_Label, 0, -100);
				yidong(cooperate_Label, 0, -100);
				yidong(fight_Label, 0, -100);

				yidong(store_Label, 0, 600);
				yidong(top_Label, 0, 600);
				yidong(back_Label, 0, 600);

				yidong(login_Label, 0, 50);
				yidong(register_Label, 0, 300);
				yidong(tryit_Label, 0, 400);
				state = 0;
			}// 从游戏主界面退回主界面
			else if (zt == 4) {
				yidong(single_Label, 0, -100);
				yidong(cooperate_Label, 0, -100);
				yidong(fight_Label, 0, -100);

				yidong(store_Label, 0, 0);
				yidong(top_Label, 0, 600);
				yidong(back_Label, 0, 475);

				sp = new StorePanel();
				sp.setLocation(600, 0);
				rightPanel.add(sp);
				yidong2(sp, 0, 0);

				state = 4;
			}// 进入商城界面
			else if (zt == 5) {
				yidong(single_Label, 0, -100);
				yidong(cooperate_Label, 0, -100);
				yidong(fight_Label, 0, -100);

				yidong(store_Label, 0, 600);
				yidong(top_Label, 0, 0);
				yidong(back_Label, 0, 475);

				tp = new TopPanel();
				tp.setLocation(600, 0);
				rightPanel.add(tp);
				yidong2(tp, 0, 0);

				state = 5;
			}// 进入排行榜界面
			else if (zt == 6) {
				yidong2(leftPanel, -250, 0);
				RMIController rmi = new RMIController();
				GameService gs = rmi.getGameService();
				try {
					int number = gs.startSingleGame();
					sf = new SingleGamePanel(name, number);
					SingleGameListener gl = new SingleGameListener(sf);
					GameLogicService gls = gs.getSingleGame(number);
					gls.addListener(gl, name);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				sf.setLocation(800, 0);
				// 这里应该有一个另外的游戏专用的panel
				rightPanel.add(sf);
				yidong2(sf, 0, 0);
				state = 6;
			}// 进入单机模式
			break;
		case 4:
			if (zt == 0) {
				yidong(tryit_Label, 0, 600);
				yidong(login_Label, 0, -100);
				yidong(register_Label, 0, 600);

				yidong(single_Label, 0, 25);
				yidong(cooperate_Label, 0, 125);
				yidong(fight_Label, 0, 225);

				yidong(store_Label, 0, 400);
				yidong(top_Label, 0, 275);

				yidong(back_Label, 0, 475);

				yidong2(sp, 600, 0);
				state = 3;
			}// 从商城界面退回主界面
			break;
		case 5:
			if (zt == 0) {
				yidong(tryit_Label, 0, 600);
				yidong(login_Label, 0, -100);
				yidong(register_Label, 0, 600);

				yidong(single_Label, 0, 25);
				yidong(cooperate_Label, 0, 125);
				yidong(fight_Label, 0, 225);

				yidong(store_Label, 0, 375);
				yidong(top_Label, 0, 300);

				yidong(back_Label, 0, 475);
				yidong2(tp, 600, 0);
				state = 3;
			}// 从排行榜界面退回
			break;
		}
	}
}
