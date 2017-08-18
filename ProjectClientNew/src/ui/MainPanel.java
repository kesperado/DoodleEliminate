package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.SourceDataLine;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import logic.player.PlayerLogicService;
import po.PlayerPO;
import rmi.RMIController;
import ui.game.common.SingleGamePanel;

@SuppressWarnings("serial")
public class MainPanel extends JPanel {
	private int width = 800;
	private int height = 600;

	private int x = 0;
	private int y = 0;
	private Image back1;// 深色背景
	private Image back2;// 这是变淡的背景
	private Image back;// 这是目前的背景

	private JLabel single_label;
	private JLabel hall_label;
	private JLabel rank_label;
	private JLabel store_label;
	private JLabel info_label;

	private Icon single_icon;
	private Icon hall_icon;
	private Icon rank_icon;
	private Icon store_icon;
	private Icon info_icon;
	private Icon single_icon2;
	private Icon hall_icon2;
	private Icon rank_icon2;
	private Icon store_icon2;
	private Icon info_icon2;

	private StorePanel sp;// 商城界面
	private RankPanel rp;// 排行榜界面
	private BeginPanel bp;// 这是单机版开始前的界面
	private InfoPanel ip;// 这是信息界面
	private HallPanel hp;
	private RoomPanel rp2;
	private SingleGamePanel sp2;

	private String name;
	private PlayerPO po;
	private ArrayList<JLabel> list = new ArrayList<JLabel>();

	private Icon exit_icon;
	private Icon exit_icon2;
	private JLabel exit_label;

	private Icon lb;
	private Icon lb2;
	private JLabel lb_label;

	private MusicPlay plm;

	// 它才是真正的核心!
	MainPanel(PlayerPO po) {
		this.po = po;
		this.setBounds(x, y, width, height);
		this.setLayout(null);
		this.setVisible(true);
		this.setOpaque(false);

		single_icon = new ImageIcon("image/system/single.png");
		single_icon2 = new ImageIcon("image/system/single2.png");
		single_label = new JLabel(single_icon);
		single_label.setHorizontalAlignment(SwingConstants.RIGHT);
		single_label.addMouseListener(new MyListener());

		hall_icon = new ImageIcon("image/system/gamehall.png");
		hall_icon2 = new ImageIcon("image/system/gamehall2.png");
		hall_label = new JLabel(hall_icon);
		hall_label.setHorizontalAlignment(SwingConstants.RIGHT);
		hall_label.addMouseListener(new MyListener());

		rank_icon = new ImageIcon("image/system/rank.png");
		rank_icon2 = new ImageIcon("image/system/rank2.png");
		rank_label = new JLabel(rank_icon);
		rank_label.setHorizontalAlignment(SwingConstants.RIGHT);
		rank_label.addMouseListener(new MyListener());

		store_icon = new ImageIcon("image/system/store.png");
		store_icon2 = new ImageIcon("image/system/store2.png");
		store_label = new JLabel(store_icon);
		store_label.setHorizontalAlignment(SwingConstants.RIGHT);
		store_label.addMouseListener(new MyListener());

		info_icon = new ImageIcon("image/system/info.png");
		info_icon2 = new ImageIcon("image/system/info2.png");
		info_label = new JLabel(info_icon);
		info_label.setHorizontalAlignment(SwingConstants.RIGHT);
		info_label.addMouseListener(new MyListener());

		back1 = Toolkit.getDefaultToolkit().getImage("image/back/back5.png");
		back2 = Toolkit.getDefaultToolkit().getImage("image/back/back7.png");

		exit_icon = new ImageIcon("image/system/tuichu.png");
		exit_icon2 = new ImageIcon("image/system/tuichu2.png");

		exit_label = new JLabel(exit_icon);
		exit_label.addMouseListener(new MyListener());

		plm = new MusicPlay(MusicPlay.class.getResource("/The Phoenix.wav"));
		plm.start();

		lb = new ImageIcon("image/system/lb.png");
		lb2 = new ImageIcon("image/system/nolb.png");
		lb_label = new JLabel(lb);
		lb_label.addMouseListener(new MyListener());

		init();
	}

	void init() {
		back = back2;

		single_label.setBounds(638, 150, 150, 50);
		add(single_label);

		hall_label.setBounds(638, 210, 150, 50);
		add(hall_label);

		rank_label.setBounds(638, 280, 150, 50);
		add(rank_label);

		store_label.setBounds(638, 345, 150, 50);
		add(store_label);

		info_label.setBounds(588, 410, 200, 50);
		add(info_label);

		exit_label.setBounds(738, 22, 50, 50);
		add(exit_label);

		lb_label.setBounds(680, 22, 50, 50);
		add(lb_label);

		list.add(single_label);
		list.add(hall_label);
		list.add(rank_label);
		list.add(store_label);
		list.add(info_label);
		list.add(exit_label);

		hp = new HallPanel(this, po);

		sp = new StorePanel(this, po);

		rp = new RankPanel(this, po);

		bp = new BeginPanel(this, po);

		ip = new InfoPanel(this, po);

		// rp2 = new RoomPanel();
		// add(rp2);
	}

	void changeBeiJ() {
		if (back == back1) {
			back = back2;
		} else {
			back = back1;
		}
		repaint();
	}

	public void xiaoqu() {
		remove(exit_label);
		remove(lb_label);
		repaint();
	}

	public void chuxian() {
		add(exit_label);
		add(lb_label);
		repaint();
	}

	public void guansy() {
		plm.stop();
	}

	public void kaisy() {
		plm.start();
	}

	public void setLabelsVisible(Boolean e) {
		for (JLabel j : list) {
			j.setVisible(e);
		}
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int height = back.getHeight(this);
		int width = back.getWidth(this);
		g.drawImage(back, 0, 0, width, height, this);

	}

	public void changePanel(JPanel aim) {
		this.removeAll();
		this.add(aim);
		this.validate();
		this.repaint();
	}

	class MyListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() == single_label) {
				remove(hp);
				remove(rp);
				remove(sp);
				remove(ip);
				add(bp);
				// single的话 是 先出现一个可选道具什么的界面
			} else if (e.getSource() == hall_label) {
				remove(sp);
				remove(rp);
				remove(bp);
				remove(ip);
				add(hp);
			} else if (e.getSource() == rank_label) {
				remove(sp);
				remove(hp);
				remove(bp);
				remove(ip);
				rp.updateData();
				add(rp);
			} else if (e.getSource() == store_label) {
				remove(hp);
				remove(rp);
				remove(bp);
				remove(ip);
				add(sp);
			} else if (e.getSource() == info_label) {
				remove(hp);
				remove(rp);
				remove(bp);
				remove(sp);
				ip.getInfo(po);
				ip.getInfo2();
				add(ip);
			} else if (e.getSource() == exit_label) {
				RMIController rmiController = new RMIController();
				PlayerLogicService playerLogicService = rmiController
						.getPlayerService();
				try {
					playerLogicService.logout(po.playerID, po.password);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "网络连接中断");
				}
				System.exit(0);
			} else if (e.getSource() == lb_label) {
				if (lb_label.getIcon() == lb) {
					lb_label.setIcon(lb2);
					plm.stop();
				} else {
					lb_label.setIcon(lb);
					plm.start();
				}

			}
			repaint();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			if (e.getSource() == single_label) {
				single_label.setIcon(single_icon2);
			} else if (e.getSource() == hall_label) {
				hall_label.setIcon(hall_icon2);
			} else if (e.getSource() == rank_label) {
				rank_label.setIcon(rank_icon2);
			} else if (e.getSource() == store_label) {
				store_label.setIcon(store_icon2);
			} else if (e.getSource() == info_label) {
				info_label.setIcon(info_icon2);
			} else if (e.getSource() == exit_label) {
				exit_label.setIcon(exit_icon2);
			}
			repaint();
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if (e.getSource() == single_label) {
				single_label.setIcon(single_icon);
			} else if (e.getSource() == hall_label) {
				hall_label.setIcon(hall_icon);
			} else if (e.getSource() == rank_label) {
				rank_label.setIcon(rank_icon);
			} else if (e.getSource() == store_label) {
				store_label.setIcon(store_icon);
			} else if (e.getSource() == info_label) {
				info_label.setIcon(info_icon);
			} else if (e.getSource() == exit_label) {
				exit_label.setIcon(exit_icon);
			}
			repaint();
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}
	}
}
