package ui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import logic.game.GameLogicService;
import logic.game.GameService;
import logic.player.PlayerLogicService;
import po.GameSettingPO;
import po.PlayerPO;
import rmi.RMIController;
import ui.game.common.GameListener;
import ui.game.common.TryGamePanel;

@SuppressWarnings("serial")
public class LoginPanel extends JPanel {

	private RMIController rmiController = new RMIController();
	private PlayerLogicService playerLogicService = rmiController
			.getPlayerService();
	private int width = 800;
	private int height = 600;

	private int x = 0;
	private int y = 0;
	private MainFrame mf;

	private String name;
	private String password;

	private Image back;
	// 正常态是 150*50 中的 130*30 聚焦后变为150*50满的
	private JTextField id_field;
	private JPasswordField pas_field;

	private JLabel id_label;
	private Icon id_icon;
	private JLabel pas_label;
	private Icon pas_icon;

	private JLabel login_label;
	private Icon login_icon;
	private Icon login_icon2;
	private JLabel register_label;
	private Icon register_icon;
	private Icon register_icon2;
	private JLabel try_label;
	private Icon try_icon;
	private Icon try_icon2;

	private Icon exit_icon;
	private Icon exit_icon2;
	private JLabel exit_label;

	// ADD
	TryGamePanel game = null;

	LoginPanel(MainFrame mf) {
		this.mf = mf;
		this.setBounds(x, y, width, height);
		this.setLayout(null);
		this.setVisible(true);
		this.setOpaque(false);

		init();
	}

	void init() {

		id_icon = new ImageIcon("image/system/id.png");
		id_label = new JLabel(id_icon);

		pas_icon = new ImageIcon("image/system/pass.png");
		pas_label = new JLabel(pas_icon);

		Font font = new Font("微软雅黑", Font.PLAIN, 16);
		id_field = new JTextField();
		id_field.setFont(font);
		id_field.setOpaque(false);
		id_field.setBorder(BorderFactory.createEmptyBorder());

		pas_field = new JPasswordField();
		pas_field.setFont(font);
		pas_field.setOpaque(false);
		pas_field.setEchoChar('*');
		pas_field.setBorder(BorderFactory.createEmptyBorder());

		login_icon = new ImageIcon("image/system/in.png");
		login_icon2 = new ImageIcon("image/system/in2.png");
		login_label = new JLabel(login_icon);

		register_icon = new ImageIcon("image/system/register.png");
		register_icon2 = new ImageIcon("image/system/register2.png");
		register_label = new JLabel(register_icon);

		try_icon = new ImageIcon("image/system/try.png");
		try_icon2 = new ImageIcon("image/system/try2.png");
		try_label = new JLabel(try_icon);

		exit_icon = new ImageIcon("image/system/exit.png");
		exit_icon2 = new ImageIcon("image/system/exit2.png");
		exit_label = new JLabel(exit_icon);

		back = Toolkit.getDefaultToolkit().getImage("image/back/back3.png");

		id_label.setBounds(425, 125, 100, 50);
		add(id_label);

		pas_label.setBounds(399, 206, 150, 50);
		add(pas_label);

		id_field.setBounds(570, 152, 175, 25);
		add(id_field);

		pas_field.setBounds(570, 229, 175, 25);
		pas_field.addKeyListener(new MyListener2());
		add(pas_field);

		login_label.setBounds(538, 330, 150, 50);
		login_label.addMouseListener(new MyListener());
		add(login_label);

		register_label.setBounds(612, 435, 150, 50);
		register_label.addMouseListener(new MyListener());
		add(register_label);

		try_label.setBounds(612, 528, 150, 50);
		try_label.addMouseListener(new MyListener());
		add(try_label);

		exit_label.setBounds(53, 498, 80, 80);
		exit_label.addMouseListener(new MyListener());
		add(exit_label);
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int height = back.getHeight(this);
		int width = back.getWidth(this);
		g.drawImage(back, 0, 0, width, height, this);
	}

	// new functions
	public void login(PlayerPO po) {
		MainPanel mp = new MainPanel(po);
		mf.changePanel(mp);
		repaint();
	}

	public void register_play() {
		RegisterPanel rp = new RegisterPanel(mf);
		rp.init();
		mf.changePanel(rp);
		repaint();
	}

	public void passer_login() {

		GameSettingPO setting = new GameSettingPO();
		setting.third = false;
		setting.fourth = false;
		setting.fifth = false;
		setting.direction = 1;
		// 监视者构造

		try {
			RMIController rmi = new RMIController();
			GameListener gl = new GameListener();
			GameService gs = rmi.getGameService();
			GameLogicService gls = gs.startSingleGame(setting);
			gls.addListener(gl, "Visitor");
			game = new TryGamePanel(setting, "Visitor", gls, this);
			gl.setAim(game);
			gls.startGame();
			enter_game();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "网络连接中断");
		}
		repaint();
	}

	// 用于游戏返回
	public void back_to_login() {
		removeAll();
		game = null;
		init();

		repaint();
	}

	// 进入游戏
	public void enter_game() {
		removeAll();
		game.setSize(800, 600);
		add(game);
		repaint();
	}

	class MyListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() == login_label) {
				// 这里就是登陆的部分
				name = id_field.getText();
				password = pas_field.getText();

				if (name.length() == 0) {
					JOptionPane.showMessageDialog(null, "NO User Name");
				} else if (password.length() == 0) {
					JOptionPane.showMessageDialog(null, "NO password");
				} else {
					PlayerPO currentPlayer = null;
					try {
						currentPlayer = playerLogicService
								.login(name, password);
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
					if (currentPlayer == null) {
						JOptionPane.showMessageDialog(null, "LoginFail");
					} else {

						mf.login(currentPlayer);
					}
				}
			} else if (e.getSource() == register_label) {
				// 这里进入注册部分
				id_field.setText("");
				pas_field.setText("");
				mf.register_play();
			} else if (e.getSource() == try_label) {
				// 进入游客登陆
				id_field.setText("");
				pas_field.setText("");
				passer_login();
			} else if (e.getSource() == exit_label) {
				System.exit(0);
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			if (e.getSource() == login_label) {
				login_label.setIcon(login_icon2);
				repaint();
			} else if (e.getSource() == register_label) {
				register_label.setIcon(register_icon2);
				repaint();
			} else if (e.getSource() == try_label) {
				try_label.setIcon(try_icon2);
				repaint();
			} else if (e.getSource() == exit_label) {
				exit_label.setIcon(exit_icon2);
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if (e.getSource() == login_label) {
				login_label.setIcon(login_icon);
				repaint();
			} else if (e.getSource() == register_label) {
				register_label.setIcon(register_icon);
				repaint();
			} else if (e.getSource() == try_label) {
				try_label.setIcon(try_icon);
				repaint();
			} else if (e.getSource() == exit_label) {
				exit_label.setIcon(exit_icon);
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}
	}

	class MyListener2 implements KeyListener {

		@Override
		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
			if (arg0.getKeyChar() == '\n') {
				name = id_field.getText();
				password = pas_field.getText();

				if (name.length() == 0) {
					JOptionPane.showMessageDialog(null, "NO User Name");
				} else if (password.length() == 0) {
					JOptionPane.showMessageDialog(null, "NO password");
				} else {
					PlayerPO currentPlayer = null;
					try {
						currentPlayer = playerLogicService
								.login(name, password);
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
					if (currentPlayer == null) {
						JOptionPane.showMessageDialog(null, "Login Fail");
					} else {

						mf.login(currentPlayer);
					}
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

	}

}
