//package test;
//
//import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.Image;
//import java.awt.Toolkit;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.awt.event.MouseMotionListener;
//import java.rmi.RemoteException;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.Set;
//
//import javax.swing.JPanel;
//
//import ui.game.before.SingleFrame;
//
//import logic.game.GameLogicService;
//import logic.game.Message;
//
//public class MockPanel extends JPanel {
//	private static final long serialVersionUID = 1L;
//
//	@SuppressWarnings("unused")
//	private Graphics g;// 画布
//
//	private int block_row = 9;
//	private int block_col = 9;// 棋盘 横纵轴
//
//	private int state = 0;// 状态 0 未选定 1 选定 2交换中 3消除下落中 4结束
//	private int pre_x = -1;
//	private int pre_y = -1;// 上一个选取的 横 纵下标
//
//	private int[][] mat;// 数据层的9*9矩阵
//
//	private MockBlock[][] mat2;// 界面层的9*9矩阵
//
//	private Image back;
//
//	// private MockGS ms;// 更新数据的来源
//	private GameLogicService gs;// 获得信息的对象
//
//	private int gar = 0;// gravity 0是下落 1是左裸
//
//	private String palyerID;
//	private MockFrame frame;
//
//	public MockPanel(String playerID, GameLogicService gs, MockFrame frame) {
//		this.setBounds(200, 0, 470, 470);
//		this.addMouseListener(new MyListener());
//		this.addMouseMotionListener(new MyListener2());
//		g = this.getGraphics();
//
//		mat = new int[block_row][block_col];// 数字层次
//		mat2 = new MockBlock[block_row][block_col];// 界面层次
//
//		// ms = new MockGS();
//		this.gs = gs;
//		this.palyerID = playerID;
//		this.frame = frame;
//
//		back = Toolkit.getDefaultToolkit().getImage("image/back.jpg");
//
//		init();
//	}
//
//	// 初始化
//	void init() {
//		try {
//			mat = gs.getStart();
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		for (int x = 0; x < block_row; x++) {
//			for (int y = 0; y < block_col; y++) {
//				mat2[x][y] = new MockBlock(mat[x][y], x, y);
//			}
//		}
//	}
//
//	// 获得最初的矩阵
//
//	void getRenew() {
//		Message me = null;
//		try {
//			me = gs.getMessage();
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		for (int i = 0; i < me.list.size(); i++) {
//			if (i % 2 == 0) {
//				baoz(me.list.get(i), me.list.get(i + 1));
//			}
//		}
//	}
//
//	// 获得新的矩阵
//
//	void swap(MockBlock b1, MockBlock b2, boolean isDone) {
//		// 第一个是pre isDone true 不回环 真交换 false 回环 伪交换
//		final int dir;
//		final int road;
//		final int step;
//		System.out.println("有没有开始");
//		if (b1.getHzb() == b2.getHzb()) {
//			dir = 1;
//			road = b2.getZzb() - b1.getZzb();
//			step = road / 5;
//		} else {
//			dir = 0;
//			road = b2.getHzb() - b1.getHzb();
//			step = road / 5;
//		}
//		if (isDone) {
//			int t = 0;
//			while (t < 5) {
//				b1.move(dir, step);
//				b2.move(dir, -step);
//				System.out.println("开始了");
//				t++;
//				try {
//					Thread.sleep(50);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				repaint();
//				if (t == 5) {
//					b1.lostDianj();
//
//					b1.move(dir, -road);
//					b2.move(dir, road);
//					int temp = b1.getNum();
//					b1.changeIma(b2.getNum());
//					b2.changeIma(temp);
//					// 界面归位
//
//					int x1 = (b1.getZzb() - 10) / 50;
//					System.out.print(x1 + " ");
//					int y1 = (b1.getHzb() / 50);
//					System.out.print(y1 + " ");
//					int x2 = (b2.getZzb() - 10) / 50;
//					System.out.print(x2 + " ");
//					int y2 = (b2.getHzb() / 50);
//					System.out.print(y2 + " ");
//
//					int temp2 = mat[x1][y1];
//					mat[x1][y1] = mat[x2][y2];
//					mat[x2][y2] = temp2;
//
//					state = 0;
//					repaint();
//				}
//			}
//		} else {
//			int t = 0;
//			while (t < 10) {
//				if (t < 5) {
//					b1.move(dir, step);
//					b2.move(dir, -step);
//				} else {
//					b1.move(dir, -step);
//					b2.move(dir, step);
//				}
//				t++;
//				repaint();
//				try {
//					Thread.sleep(25);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				if (t == 10) {
//					b1.lostDianj();
//					state = 0;
//					repaint();
//				}
//			}
//		}
//	}
//
//	// 交换位置
//
//	void baoz(Integer[][] list, Integer[][] list2) {
//		while (state != 0) {
//
//		}
//		state = 3;
//		ArrayList<MockBlock> que = new ArrayList<MockBlock>();
//		Set<Integer> lie = new HashSet<Integer>();
//		Set<Integer> hang = new HashSet<Integer>();
//		for (int x = 0; x < 9; x++) {
//			for (int y = 0; y < 9; y++) {
//				mat[x][y] = list2[x][y];// 数据归位
//				if (list[x][y] == 0) {
//					mat2[x][y].getBaoz();// 表现爆炸
//					que.add(mat2[x][y]);// 加入消除队列
//					lie.add(y);// 本列列入有影响set
//					hang.add(x);// 本行列入有影响set
//				}
//			}
//		}
//		repaint();
//		try {
//			Thread.sleep(50);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Iterator<MockBlock> it = que.iterator();
//		while (it.hasNext()) {
//			MockBlock temp = it.next();
//			if (gar == 0) {
//				temp.move(1, -(temp.getZzb() + 50));// 下落 注意此处的移动地点
//			} else {
//				temp.move(0, 470 - temp.getHzb());// 左落 注意此处的移动地点
//			}
//		}
//		repaint();
//		if (gar == 0) {
//			xial(list, list2, lie);
//		} else {
//			zuol(list, list2, hang);
//		}
//	}
//
//	// 爆炸效果
//
//	void xial(Integer[][] list, Integer[][] list2, Set<Integer> lie) {
//		Iterator<Integer> it = lie.iterator();
//		int sec = 0;
//		while (sec < 5) {
//			it = lie.iterator();
//			while (it.hasNext()) {
//				int nowY = it.next();
//				int nowX = 8;
//				int count = 0;
//				while (nowX >= 0) {
//					if (list[nowX][nowY] == 0) {
//						mat2[nowX][nowY].changeIma(mat[count][nowY]);
//						count++;
//						mat2[nowX][nowY].move(1, 12 + (count - 1) * 10);
//					} else {
//						mat2[nowX][nowY].move(1, 10 * count);
//					}
//
//					nowX--;
//				}// 一列中的每一行
//			}// 每一列
//			sec++;
//			repaint();
//			try {
//				Thread.sleep(50);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}// 5个50ms完成
//
//		it = lie.iterator();
//		while (it.hasNext()) {
//			int nowY = it.next();
//			for (int x = 0; x < 9; x++) {
//				mat2[x][nowY].init(mat[x][nowY], x, nowY);
//			}
//		}
//		repaint();
//		state = 0;
//		// 归位
//	}
//
//	// 下落效果
//
//	void zuol(Integer[][] list, Integer[][] list2, Set<Integer> hang) {
//		Iterator<Integer> it = hang.iterator();
//		int sec = 0;
//		while (sec < 5) {
//			it = hang.iterator();
//			while (it.hasNext()) {
//				int nowX = it.next();
//				int nowY = 0;
//				int count = 0;
//				while (nowY <= 8) {
//					if (list[nowX][nowY] == 0) {
//						mat2[nowX][nowY].changeIma(mat[nowX][8 - count]);
//						count++;
//						mat2[nowX][nowY].move(0, -14 * count);
//						// 多了20？ 为什么 从 （0,10） 开始 81个块 450*450 panel长 470 中间20像素
//						// 需要这个时候来填补
//					} else {
//						mat2[nowX][nowY].move(0, -10 * count);
//					}
//
//					nowY++;
//				}// 一列中的每一行
//			}// 每一列
//			sec++;
//			repaint();
//			try {
//				Thread.sleep(50);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}// 5个50ms完成
//			//
//		it = hang.iterator();
//		while (it.hasNext()) {
//			int nowX = it.next();
//			for (int y = 0; y < 9; y++) {
//				mat2[nowX][y].init(mat[nowX][y], nowX, y);
//			}
//		}
//		repaint();
//		state = 0;
//		// 归位
//	}
//
//	// 左落效果
//
//	boolean checkNear(int now_x, int now_y, int pre_x, int pre_y) {
//		if ((now_x == pre_x) && (Math.abs(now_y - pre_y) == 1)) {
//			return true;
//		} else if ((now_y == pre_y) && (Math.abs(now_x - pre_x) == 1)) {
//			return true;
//		}
//		return false;
//	}
//
//	public void paint(Graphics g) {
//		super.paintComponent(g);
//		setBackground(Color.WHITE);
//		if (back != null) {
//			int height = back.getHeight(this);
//			int width = back.getWidth(this);
//
//			if (height != -1 && height > getHeight())
//				height = getHeight();
//
//			if (width != -1 && width > getWidth())
//				width = getWidth();
//
//			g.drawImage(back, 0, 10, width, height, this);
//		}
//		for (int x = 0; x < 9; x++) {
//			for (int y = 0; y < 9; y++) {
//				mat2[x][y].draw(g);
//			}
//		}
//
//	}
//
//	// 重新绘制panel 核心啊！
//	class MyListener implements MouseListener {
//
//		@Override
//		public void mouseClicked(MouseEvent e) {
//			int x = (e.getX() / 50);
//			int y = ((e.getY() - 10) / 50);
//			if (state == 0) {
//				mat2[y][x].getDianj();
//				repaint();
//				state = 1;
//				pre_y = y;
//				pre_x = x;
//			} else if (state == 1) {
//				if (checkNear(y, x, pre_y, pre_x)) {
//					state = 2;
//					int mark = 0;
//					boolean result = false;
//					try {
//						mark = gs.exchange(pre_y, pre_x, y, x, palyerID);
//					} catch (RemoteException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//					if (mark < 0) {
//						result = false;
//					} else {
//						result = true;
//					}
//					swap(mat2[pre_y][pre_x], mat2[y][x], result);
//				} else {
//					state = 0;
//					mat2[pre_y][pre_x].lostDianj();
//					repaint();
//				}
//			}
//		}
//
//		@Override
//		public void mouseEntered(MouseEvent e) {
//			// TODO Auto-generated method stub
//			// System.out.println("有效果么");// 出去再进来才会触发
//		}
//
//		@Override
//		public void mouseExited(MouseEvent e) {
//			// TODO Auto-generated method stub
//
//		}
//
//		@Override
//		public void mousePressed(MouseEvent e) {
//			// TODO Auto-generated method stub
//
//		}
//
//		@Override
//		public void mouseReleased(MouseEvent e) {
//			// TODO Auto-generated method stub
//
//		}
//
//	}
//
//	class MyListener2 implements MouseMotionListener {
//
//		@Override
//		public void mouseDragged(MouseEvent arg0) {
//			// TODO Auto-generated method stub
//
//		}
//
//		@Override
//		public void mouseMoved(MouseEvent e) {
//			int x = (e.getX() / 50);
//			int y = ((e.getY() - 10) / 50);
//			for (int i = 0; i < block_row; i++) {
//				for (int j = 0; j < block_col; j++) {
//					mat2[i][j].lostJuj();
//				}
//			}
//			if ((0 <= x) && (x < 9) && (0 <= y) && (y < 9)) {
//				mat2[y][x].getJuj();
//				repaint();
//			}
//		}
//	}
// }
