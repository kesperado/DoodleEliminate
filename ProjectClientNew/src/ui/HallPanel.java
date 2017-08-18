package ui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import logic.netcore.NetcoreLogicService;
import po.PlayerPO;
import po.RoomPO;
import rmi.RMIController;
import rmi.netRoomListener;

@SuppressWarnings("serial")
public class HallPanel extends JPanel {
	private int width = 600;
	private int height = 600;

	private int x = 0;
	private int y = 0;

	// 一页共六个房间
	private JLabel room1;
	private JLabel room2;
	private JLabel room3;
	private JLabel room4;
	private JLabel room5;
	private JLabel room6;
	private Image circle;

	private Icon enter;
	private Icon enter2;
	private JLabel join1;
	private JLabel join2;
	private JLabel join3;
	private JLabel join4;
	private JLabel join5;
	private JLabel join6;

	private Icon create_icon;
	private Icon create_icon2;
	private JLabel create_label;

	private JLabel page;// 这是页数
	private Icon q1;
	private Icon q2;
	private Icon t1;
	private Icon t2;
	private JLabel qian;// 前
	private JLabel tui;// 退

	// 以下为新增加变量
	private NetcoreLogicService nls = null;
	private ArrayList<RoomPO> list = new ArrayList<RoomPO>();
	private ArrayList<JLabel> guide = new ArrayList<JLabel>();
	private MainPanel mp;
	private HallPanel hall = this;
	private int pagenumber;// 目前页码
	private PlayerPO player;// 这个理论上是应该一级一级往下传的，传入null是个随机生成姓名的玩意
	private boolean alive;// 用于轮训线程 的终止
	private RoomPanel room = null;

	HallPanel() {
		this.setLayout(null);
		this.setBounds(x, y, width, height);
		this.setVisible(true);
		this.setOpaque(false);

		init();
	}

	void init() {
		String gap1 = "      ";
		String gap2 = "   ";

		enter = new ImageIcon("image/system/enter.png");
		enter2 = new ImageIcon("image/system/enter2.png");
		q1 = new ImageIcon("image/system/qian.png");
		q2 = new ImageIcon("image/system/qian2.png");
		t1 = new ImageIcon("image/system/tui.png");
		t2 = new ImageIcon("image/system/tui2.png");
		create_icon = new ImageIcon("image/system/room.png");
		create_icon2 = new ImageIcon("image/system/room2.png");

		create_label = new JLabel(create_icon);
		create_label.addMouseListener(new registryListener(this));

		qian = new JLabel(q1);
		tui = new JLabel(t1);

		join1 = new JLabel(enter);
		join2 = new JLabel(enter);
		join3 = new JLabel(enter);
		join4 = new JLabel(enter);
		join5 = new JLabel(enter);
		join6 = new JLabel(enter);

		Font font = new Font("微软雅黑", Font.PLAIN, 18);
		room1 = new JLabel("No." + "1" + gap2 + "host:   " + "LLc" + gap1
				+ "3/4");
		room1.setFont(font);

		room2 = new JLabel("No." + "2" + gap2 + "host:   " + "LLc" + gap1
				+ "3/4");
		room2.setFont(font);

		room3 = new JLabel("No." + "3" + gap2 + "host:   " + "LLc" + gap1
				+ "3/4");
		room3.setFont(font);

		room4 = new JLabel("No." + "4" + gap2 + "host:   " + "LLc" + gap1
				+ "3/4");
		room4.setFont(font);

		room5 = new JLabel("No." + "5" + gap2 + "host:   " + "LLc" + gap1
				+ "3/4");
		room5.setFont(font);

		room6 = new JLabel("No." + "6" + gap2 + "host:   " + "LLc" + gap1
				+ "3/4");
		room6.setFont(font);

		Font font2 = new Font("微软雅黑", Font.PLAIN, 24);
		page = new JLabel("Page 1");
		page.setFont(font2);

		circle = Toolkit.getDefaultToolkit().getImage("image/back/circle2.png");

		room1.setBounds(96, 40, 300, 80);
		add(room1);
		join1.setBounds(350, 55, 80, 50);
		add(join1);

		room2.setBounds(96, 110, 300, 80);
		add(room2);
		join2.setBounds(350, 125, 80, 50);
		add(join2);

		room3.setBounds(96, 180, 300, 80);
		add(room3);
		join3.setBounds(350, 195, 80, 50);
		add(join3);

		room4.setBounds(96, 250, 300, 80);
		add(room4);
		join4.setBounds(350, 265, 80, 50);
		add(join4);

		room5.setBounds(96, 320, 300, 80);
		add(room5);
		join5.setBounds(350, 335, 80, 50);
		add(join5);

		room6.setBounds(96, 390, 300, 80);
		add(room6);
		join6.setBounds(350, 405, 80, 50);
		add(join6);

		page.setBounds(221, 480, 80, 40);
		add(page);

		qian.setBounds(375, 480, 40, 40);
		add(qian);
		tui.setBounds(89, 480, 40, 40);
		add(tui);

		create_label.setBounds(60, 540, 150, 50);
		add(create_label);

		guide.clear();
		join1.addMouseListener(new roomListener(1, this));
		guide.add(room1);
		join2.addMouseListener(new roomListener(2, this));
		guide.add(room2);
		join3.addMouseListener(new roomListener(3, this));
		guide.add(room3);
		join4.addMouseListener(new roomListener(4, this));
		guide.add(room4);
		join5.addMouseListener(new roomListener(5, this));
		guide.add(room5);
		join6.addMouseListener(new roomListener(6, this));
		guide.add(room6);

		qian.addMouseListener(new pageListener(+1));
		tui.addMouseListener(new pageListener(-1));

	}

	// 新增加方法
	HallPanel(MainPanel m, PlayerPO player) {
		this();
		this.mp = m;
		this.pagenumber = 0;

		if (player == null) {
			this.player = new PlayerPO();
			this.player.playerID = Math.random() + "";
		} else {
			this.player = player;
		}
		try {
			RMIController rmi = new RMIController();
			this.nls = rmi.getNetworkService();
			this.list = nls.getRoomList();
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(null, "网络连接中断");
		}
		showRooms();

		alive = true;
		Thread t = new refresh();
		t.start();
	}

	public void enter_room() {
		alive = false;
		mp.setLabelsVisible(false);
		this.setSize(800, 600);

		removeAll();
		add(room);
		repaint();
	}

	public void back_hall() {
		removeAll();
		init();
		room = null;
		mp.chuxian();
		mp.kaisy();
		showRooms();
		mp.setLabelsVisible(true);
		this.setSize(600, 600);
		repaint();

		alive = true;
		Thread t = new refresh();
		t.start();
	}

	// 更新房间信息
	private void showRooms() {
		int startNumber = pagenumber * 6;// 本页起始房间在list的编号
		String gap1 = "      ";
		String gap2 = "   ";
		for (int i = 0; i < 6; i++) {
			JLabel aim = guide.get(i);
			if (startNumber + i < list.size()) {
				RoomPO temp = list.get(startNumber + i);
				int totalnumber = temp.players.size();
				int readynumber = 0;
				for (Boolean b : temp.players.values()) {
					if (b) {
						readynumber++;
					}
				}
				;
				if (temp.roomMaster.length() > 5) {
					aim.setText("No." + i + gap2 + "host:   "
							+ temp.roomMaster.substring(0, 5) + gap1
							+ readynumber + "/" + totalnumber);
				} else {
					aim.setText("No." + i + gap2 + "host:   " + temp.roomMaster
							+ gap1 + readynumber + "/" + totalnumber);
				}

			} else {
				aim.setText("No." + i + gap2 + "host:   " + "null" + gap1
						+ "0/0");
			}
		}
		page.setText("Page " + (pagenumber + 1));
	}

	// 进入房间的Listener
	class roomListener implements MouseListener {
		int number;
		JPanel front;

		public roomListener(int number, JPanel front) {
			this.number = number;
			this.front = front;
		}

		public void mouseClicked(MouseEvent arg0) {
			System.out.println("已经点击");

			int aim = pagenumber * 6 + number - 1;
			if (aim >= list.size()) {
				JOptionPane.showMessageDialog(null, "房间为空");
				return;
			}

			RoomPO temp = list.get(pagenumber * 6 + number - 1);

			if (temp.start) {
				JOptionPane.showMessageDialog(null, "正在游戏中");
				return;
			}
			mp.xiaoqu();
			mp.guansy();
			room = new RoomPanel(hall, player);
			room.setRoomPO(temp);

			repaint();
			netRoomListener rl;
			try {

				rl = new netRoomListener(room);
				temp = nls.joinRoom(temp.id, player.playerID, rl);
				room.setRoomPO(temp);

				alive = false;
				enter_room();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			((JLabel) e.getSource()).setIcon(enter2);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			((JLabel) e.getSource()).setIcon(enter);
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

	// 换页监听
	class pageListener implements MouseListener {

		int direction = 0;

		public pageListener(int direction) {
			this.direction = direction;
		}

		public void mouseClicked(MouseEvent arg0) {
			pagenumber = pagenumber + direction;
			if (pagenumber < 0) {
				pagenumber = 0;
				JOptionPane.showMessageDialog(null, "无法左翻");
			}
			if (pagenumber > ((list.size() + 1) / 6)) {
				pagenumber = ((list.size() + 1) / 6);
				JOptionPane.showMessageDialog(null, "无法右翻");
			}
			showRooms();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == qian) {
				qian.setIcon(q2);
			} else if (e.getSource() == tui) {
				tui.setIcon(t2);
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == qian) {
				qian.setIcon(q1);
			} else if (e.getSource() == tui) {
				tui.setIcon(t1);
			}
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

	// 新建房间的监听，名字不咋滴
	class registryListener implements MouseListener {
		JPanel front;

		public registryListener(JPanel front) {
			this.front = front;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			room = new RoomPanel(hall, player);

			repaint();
			netRoomListener rl;
			try {
				rl = new netRoomListener(room);
				RoomPO temp = nls.createRoom(player.playerID, rl);// 根据服务器传回的数据，重新跟新房间
				room.setRoomPO(temp);
			} catch (RemoteException e2) {
				JOptionPane.showMessageDialog(null, "网络连接中断");
			}
			alive = false;
			enter_room();

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			((JLabel) e.getSource()).setIcon(create_icon2);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			((JLabel) e.getSource()).setIcon(create_icon);
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

	// 不断询问服务器房间情况
	class refresh extends Thread {
		public void run() {
			while (true && alive)
			// while(true)
			{
				try {
					list = nls.getRoomList();
					// System.out.println("已经更新房间列表");
					showRooms();
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					JOptionPane.showMessageDialog(null, "网络连接中断");
				} catch (RemoteException e) {
					JOptionPane.showMessageDialog(null, "网络连接中断");
				}
			}

		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(circle, 60, 50, 400, 60, this);
		g.drawImage(circle, 60, 120, 400, 60, this);
		g.drawImage(circle, 60, 190, 400, 60, this);
		g.drawImage(circle, 60, 260, 400, 60, this);
		g.drawImage(circle, 60, 330, 400, 60, this);
		g.drawImage(circle, 60, 400, 400, 60, this);
	}
}
