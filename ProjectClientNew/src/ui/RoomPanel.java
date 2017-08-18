package ui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map.Entry;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import logic.netcore.NetcoreLogicService;
import po.GameSettingPO;
import po.PlayerPO;
import po.RoomPO;
import rmi.RMIController;
import ui.game.common.CoopGamePanel;
import ui.game.common.GameListener;

@SuppressWarnings("serial")
// 到了这个界面 就不是主界面的副界面了 而自己是一个800*600的界面
public class RoomPanel extends JPanel {
	private int width = 800;
	private int height = 600;

	private int x = 0;
	private int y = 0;

	private Image back;

	private Icon h1;
	private Icon h2;
	private Icon h3;
	private Icon h4;
	private Icon h5;// 直接把所有的头像读进来算了

	private JLabel head1;// 四个头像
	private JLabel head2;
	private JLabel head3;
	private JLabel head4;

	private JLabel name1;// 四个名字
	private JLabel name2;
	private JLabel name3;
	private JLabel name4;

	private Icon p1;
	private Icon p2;
	private Icon p3;
	private Icon p4;// 三个道具 一个重力方向（仅房主可见）

	private JLabel pr1;
	private JLabel pr2;
	private JLabel pr3;
	private JLabel gr1;

	private Icon us;
	private Icon se;// 选中未选中

	private JLabel check1;
	private JLabel check2;// 四个选框
	private JLabel check3;
	private JLabel check4;

	private Icon r1;
	private Icon r2;
	private Icon r3;
	private Icon r4;
	private Icon b1;// 准备 取消准备 开始游戏
	private Icon b2;
	private Icon q1;
	private Icon q2;// 一共应该有八张图

	private JLabel red;// 准备按钮
	private JLabel beg;// 开始按钮 (仅房主可见)
	private JLabel quit;// 退出按钮

	private CoopGamePanel game = null;

	// 新增加属性
	private static int MAX_NUMBER = 4;
	HallPanel front;
	PlayerPO player;
	RoomPO room;
	NetcoreLogicService nls;
	GameSettingPO setting;
	GameListener gl;
	ArrayList<JLabel> images = new ArrayList<JLabel>();
	ArrayList<JLabel> names = new ArrayList<JLabel>();
	ArrayList<Icon> heads = new ArrayList<Icon>();

	RoomPanel() {
		this.setLayout(null);
		this.setBounds(x, y, width, height);
		this.setVisible(true);
		this.setOpaque(true);

		init();
	}

	void init() {
		back = Toolkit.getDefaultToolkit().getImage("image/back/circle3.png");
		p1 = new ImageIcon("image/info/have1.png");
		p2 = new ImageIcon("image/info/have2.png");
		p3 = new ImageIcon("image/info/have3.png");
		p4 = new ImageIcon("image/info/have4.png");

		h1 = new ImageIcon("image/head/1.png");
		h2 = new ImageIcon("image/head/2.png");
		h3 = new ImageIcon("image/head/3.png");
		h4 = new ImageIcon("image/head/4.png");
		h5 = new ImageIcon("image/head/5.png");

		head1 = new JLabel(h1);
		head2 = new JLabel(h2);
		head3 = new JLabel(h3);
		head4 = new JLabel(h4);

		Font font = new Font("微软雅黑", Font.PLAIN, 18);
		name1 = new JLabel("Mr.G");
		name1.setFont(font);
		name2 = new JLabel("LLC");
		name2.setFont(font);
		name3 = new JLabel("Lynn");
		name3.setFont(font);
		name4 = new JLabel("Passer");
		name4.setFont(font);

		pr1 = new JLabel(p1);
		pr2 = new JLabel(p2);
		pr3 = new JLabel(p3);
		gr1 = new JLabel(p4);

		us = new ImageIcon("image/info/unselected.png");
		se = new ImageIcon("image/info/selected.png");

		check1 = new JLabel(us);
		check2 = new JLabel(us);
		check3 = new JLabel(us);
		check4 = new JLabel(us);

		check1.addMouseListener(new MyListener());
		check2.addMouseListener(new MyListener());
		check3.addMouseListener(new MyListener());
		check4.addMouseListener(new MyListener());

		r1 = new ImageIcon("image/system/ready.png");
		r2 = new ImageIcon("image/system/cancel.png");
		r3 = new ImageIcon("image/system/ready2.png");
		r4 = new ImageIcon("image/system/cancel2.png");
		b1 = new ImageIcon("image/system/start.png");
		b2 = new ImageIcon("image/system/start2.png");
		q1 = new ImageIcon("image/system/quit.png");
		q2 = new ImageIcon("image/system/quit2.png");

		red = new JLabel(r1);
		beg = new JLabel(b1);
		quit = new JLabel(q1);

		head1.setBounds(65, 80, 80, 80);
		add(head1);

		head2.setBounds(255, 80, 80, 80);
		add(head2);

		head3.setBounds(448, 80, 80, 80);
		add(head3);

		head4.setBounds(645, 80, 80, 80);
		add(head4);

		name1.setBounds(50, 160, 100, 40);
		add(name1);

		name2.setBounds(240, 160, 100, 40);
		add(name2);

		name3.setBounds(430, 160, 100, 40);
		add(name3);

		name4.setBounds(620, 160, 100, 40);
		add(name4);

		pr1.setBounds(30, 350, 40, 40);
		add(pr1);

		check1.setBounds(120, 350, 30, 30);
		add(check1);

		pr2.setBounds(30, 410, 40, 40);
		add(pr2);

		check2.setBounds(120, 410, 30, 30);
		add(check2);

		pr3.setBounds(30, 470, 40, 40);
		add(pr3);

		check3.setBounds(120, 470, 30, 30);
		add(check3);

		gr1.setBounds(30, 530, 40, 40);
		add(gr1);

		check4.setBounds(120, 530, 30, 30);
		add(check4);

		red.setBounds(645, 410, 80, 50);
		add(red);

		beg.setBounds(605, 340, 120, 50);
		add(beg);

		quit.setBounds(625, 491, 100, 50);
		add(quit);

		red.addMouseListener(new readyListener());
		beg.addMouseListener(new startListener());
		quit.addMouseListener(new quitListener());

		images.clear();
		images.add(head1);
		images.add(head2);
		images.add(head3);
		images.add(head4);

		names.clear();
		names.add(name1);
		names.add(name2);
		names.add(name3);
		names.add(name4);

		heads.clear();
		heads.add(h1);
		heads.add(h2);
		heads.add(h3);
		heads.add(h4);
	}

	// 新增加方法
	public RoomPanel(HallPanel front, PlayerPO player) {
		this();
		this.player = player;
		this.setting = new GameSettingPO();
		this.front = front;
		try {
			this.gl = new GameListener();
			RMIController rmi = new RMIController();
			this.nls = rmi.getNetworkService();
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(null, "网络连接中断");
		}

		showRoom();
	}

	// 根据roomPO显示房间内人员信息
	private void showRoom() {
		if (room == null) {
			return;
		}
		for (JLabel j : images) {
			j.setIcon(null);
		}
		for (JLabel j : names) {
			j.setText("暂时没人~");
		}

		head1.setIcon(h1);// 房主排第一位…HashMap没顺序！………
		name1.setText(room.roomMaster);

		int temple = 1;// 计数变量
		for (Entry<String, Boolean> e : room.players.entrySet()) {
			if (e.getKey().equals(room.roomMaster)) {
				continue;
			}
			if (e.getValue())// 这些事准备好的
			{
				System.out.println(e.getKey());
				images.get(temple).setIcon(heads.get(temple));
				names.get(temple).setText(e.getKey());
				temple++;
			} else
			// 这些是没准备好的
			{
				images.get(temple).setIcon(heads.get(temple));
				names.get(temple).setText(e.getKey());
				temple++;
			}
		}
		if (!room.roomMaster.equals(player.playerID)) {
			remove(pr1);
			remove(pr2);
			remove(pr3);
			remove(gr1);
			remove(check1);
			remove(check2);
			remove(check3);
			remove(check4);
			remove(beg);
		} else {
			add(pr1);
			add(pr2);
			add(pr3);
			add(gr1);
			add(check1);
			add(check2);
			add(check3);
			add(check4);
			add(beg);
		}
		repaint();
	}

	// 监视者调用的方法
	public void alert() {
		try {
			if(room==null)
			{
				return;
			}
			room = nls.getRoomInformation(room.id);
			System.out.println("更新房间信息");
			System.out.println(room.roomMaster);
			System.out.println(room.players.size());
			if (room.start) {
				// 开始游戏了，亲
				game = new CoopGamePanel(room.setting, player.playerID,
						room.gls, this);
				gl.setAim(game);
				enter_game();
			} else {
				showRoom();
			}
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(null, "网络连接中断");
		}
	}

	// 设置PO 用于新建房间
	public void setRoomPO(RoomPO room) {
		this.room = room;
		showRoom();
	}

	public void getSetting() {
		boolean p3 = false;
		boolean p4 = false;
		boolean p5 = false;
		int g = 0;
		if (check1.getIcon().equals(se)) {
			p3 = true;
		}
		if (check2.getIcon().equals(se)) {
			p4 = true;
		}
		if (check3.getIcon().equals(se)) {
			p5 = true;
		}
		if (check4.getIcon().equals(se)) {
			g = 1;
		}
		setting = new GameSettingPO(p3, p4, p5, g);
	}

	// 用于Room返回房间
	public void back_to_room() {
		removeAll();
		game = null;
		init();

		showRoom();
		repaint();
	}

	// 进入游戏
	public void enter_game() {
		removeAll();
		add(game);
		repaint();
	}
	
	public void exit_while_game()
	{
		try
		{
			nls.quitRoom(room.id, player.playerID);
		} catch (RemoteException e)
		{
			e.printStackTrace();
		}
		front.back_hall();
	}
	
	// 退出按钮的监听 重新生成一个HallPanel显示
	class quitListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			try {
				nls.quitRoom(room.id, player.playerID);
				front.back_hall();
			} catch (RemoteException e1) {
				JOptionPane.showMessageDialog(null, "网络连接中断");
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			quit.setIcon(q2);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			quit.setIcon(q1);
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

	// ready按钮的监听
	class readyListener implements MouseListener {
		boolean ready = false;

		public readyListener() {
			ready = false;
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			if (ready) {
				try {
					nls.unregister(room.id, player.playerID);
					ready = false;
					red.setIcon(r3);
					repaint();
				} catch (RemoteException e) {
					JOptionPane.showMessageDialog(null, "网络连接中断");
				}
			} else {
				try {
					nls.register(room.id, player.playerID, gl);
					ready = true;
					red.setIcon(r4);
					repaint();
				} catch (RemoteException e) {
					JOptionPane.showMessageDialog(null, "网络连接中断");
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			if (ready) {
				red.setIcon(r4);
			} else {
				red.setIcon(r3);
			}
			repaint();
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			if (ready) {
				red.setIcon(r2);
			} else {
				red.setIcon(r1);
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

	// 开始游戏按钮的监听
	class startListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			getSetting();
			try {
				nls.startGame(room.id, player.playerID, setting);
			} catch (RemoteException e1) {
				JOptionPane.showMessageDialog(null, "网络连接中断");
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			beg.setIcon(b2);
			repaint();
		}

		@Override
		public void mouseExited(MouseEvent e) {
			beg.setIcon(b1);
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

	class MyListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() == check1 || e.getSource() == check2
					|| e.getSource() == check3 || e.getSource() == check4) {
				if (((JLabel) e.getSource()).getIcon() == us) {
					((JLabel) e.getSource()).setIcon(se);
				} else {
					((JLabel) e.getSource()).setIcon(us);
				}
			}
			repaint();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

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

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(back, 0, 0, 800, 600, this);
	}
}
