package ui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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

import logic.player.PlayerLogicService;
import po.PlayerPO;
import rmi.RMIController;

@SuppressWarnings("serial")
public class RegisterPanel extends JPanel {
	private RMIController rmiController = new RMIController();
	private PlayerLogicService playerLogicService = rmiController
			.getPlayerService();
	private int width = 800;
	private int height = 600;

	private int x = 0;
	private int y = 0;

	private String name;
	private String password;

	private Image back;
	private Image back1;
	private Image back2;

	private JTextField id_field;
	private JPasswordField pas_field;

	private JLabel id_label;
	private Icon id_icon;
	private JLabel pas_label;
	private Icon pas_icon;

	private JLabel register_label;
	private Icon register_icon;
	private Icon register_icon2;

	private Icon begin_icon2;
	private JLabel begin_label;
	private Icon begin_icon;

	private JLabel welcome_label;
	private Icon welcome_icon;

	private Icon check_false;
	private Icon check_true;
	private JLabel check_label;

	private Icon back_icon;
	private Icon back_icon2;
	private JLabel back_label;

	private MainFrame mf;
	private boolean isDone = false;

	RegisterPanel(MainFrame mf) {
		this.mf = mf;
		this.setBounds(x, y, width, height);
		this.setLayout(null);
		this.setOpaque(false);

		check_false = new ImageIcon("image/info/check_false.png");
		check_true = new ImageIcon("image/info/check_true.png");
		check_label = new JLabel();

		welcome_icon = new ImageIcon("image/system/wel.png");
		welcome_label = new JLabel(welcome_icon);

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
		pas_field.setBorder(BorderFactory.createEmptyBorder());
		pas_field.addFocusListener(new MyListener2());
		pas_field.addKeyListener(new MyListener3());

		register_icon = new ImageIcon("image/system/register.png");
		register_icon2 = new ImageIcon("image/system/register2.png");
		register_label = new JLabel(register_icon);
		register_label.addMouseListener(new MyListener());

		begin_icon2 = new ImageIcon("image/system/begin2.png");
		begin_icon = new ImageIcon("image/system/begin.png");
		begin_label = new JLabel(begin_icon);
		begin_label.addMouseListener(new MyListener());

		back_icon = new ImageIcon("image/system/back.png");
		back_icon2 = new ImageIcon("image/system/back2.png");
		back_label = new JLabel(back_icon);
		back_label.addMouseListener(new MyListener());

		this.setVisible(true);

		back1 = Toolkit.getDefaultToolkit().getImage("image/back/back3.png");
		back2 = Toolkit.getDefaultToolkit().getImage("image/back/back5.png");

		init();
	}

	void init() {
		back = back1;
		id_label.setBounds(400, 142, 100, 50);
		add(id_label);

		id_field.setBounds(564, 152, 161, 29);
		add(id_field);
		id_field.setColumns(10);

		pas_label.setBounds(400, 221, 150, 50);
		add(pas_label);

		pas_field.setBounds(567, 224, 171, 29);
		add(pas_field);

		register_label.setBounds(541, 378, 150, 50);
		add(register_label);

		begin_label.setBounds(541, 477, 150, 50);

		welcome_label.setBounds(432, 25, 300, 70);
		add(welcome_label);

		// 这个是验证用户名是否为已注册
		check_label.setBounds(735, 142, 40, 40);
		add(check_label);

		back_label.setBounds(73, 510, 80, 80);
		add(back_label);

		remove(begin_label);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int height = back.getHeight(this);
		int width = back.getWidth(this);
		g.drawImage(back, 0, 0, width, height, this);
	}

	class MyListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() == register_label) {
				name = id_field.getText();
				password = pas_field.getText();
				if (name.length() == 0) {
					JOptionPane.showMessageDialog(null, "NO User Name");
				} else if (password.length() == 0) {
					JOptionPane.showMessageDialog(null, "NO password");
				} else {
					pas_field.setText("");
					id_field.setText("");
					// 这里试图注册
					// 可能会失败
					boolean registrysucceed = false;
					try {
						registrysucceed = playerLogicService.registry(name,
								password);
						System.out.println(name);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if (registrysucceed) {
						add(begin_label);
						remove(register_label);
						remove(id_label);
						remove(pas_label);
						remove(id_field);
						remove(pas_label);
						remove(check_label);
						back = back2;
						isDone = true;
						repaint();
					} else {
						JOptionPane.showMessageDialog(null,
								"Your Name has been registryed!!");
					}
				}
				// 成功以后会有paly标签出现 直接进入mainpanel

			} else if (e.getSource() == back_label) {
				mf.back_regis();
				// 退回注册界面

			} else if (e.getSource() == begin_label) {
				// 这里就进入mainpanel的部分了
				PlayerPO currentPlayer = null;
				try {
					System.out.println(name);
					currentPlayer = playerLogicService.login(name, password);
					System.out.println(password);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (currentPlayer == null) {
					JOptionPane.showMessageDialog(null, "Login Error");
				} else {
					mf.login(currentPlayer);
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			if (e.getSource() == register_label) {
				register_label.setIcon(register_icon2);
			} else if (e.getSource() == back_label) {
				back_label.setIcon(back_icon2);
			} else if (e.getSource() == begin_label) {
				begin_label.setIcon(begin_icon2);
			}
			repaint();
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if (e.getSource() == register_label) {
				register_label.setIcon(register_icon);
			} else if (e.getSource() == back_label) {
				back_label.setIcon(back_icon);
			} else if (e.getSource() == begin_label) {
				begin_label.setIcon(begin_icon);
			}
			repaint();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	class MyListener2 implements FocusListener {

		@Override
		public void focusGained(FocusEvent arg0) {
			// TODO Auto-generated method stub
			// 即是获得了密码框的焦点 这个时候进行用户名使用情况检验
			boolean hasbeenregis = false;
			name = id_field.getText();
			PlayerPO playerPO = null;
			try {
				playerPO = playerLogicService.getPlayer(name);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "网络连接中断");
			}
			if (playerPO != null) {
				hasbeenregis = true;
			}

			if (hasbeenregis) {
				check_label.setIcon(check_false);
			} else {
				check_label.setIcon(check_true);
			}
			// check_label.setIcon(check_false);

			repaint();
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			// TODO Auto-generated method stub

		}

	}

	class MyListener3 implements KeyListener {

		@Override
		public void keyPressed(KeyEvent arg0) {
			if (arg0.getKeyChar() == '\n' && !isDone) {
				name = id_field.getText();
				password = pas_field.getText();
				if (name.length() == 0) {
					JOptionPane.showMessageDialog(null, "NO User Name");
				} else if (password.length() == 0) {
					JOptionPane.showMessageDialog(null, "NO password");
				} else {
					pas_field.setText("");
					id_field.setText("");
					// 这里试图注册
					// 可能会失败
					boolean registrysucceed = false;
					try {
						registrysucceed = playerLogicService.registry(name,
								password);
						System.out.println(name);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if (registrysucceed) {
						add(begin_label);
						remove(register_label);
						remove(id_label);
						remove(pas_label);
						remove(id_field);
						remove(pas_label);
						remove(check_label);
						back = back2;
						isDone = true;
						repaint();
					} else {
						JOptionPane.showMessageDialog(null,
								"Your Name has been registryed!!");
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
