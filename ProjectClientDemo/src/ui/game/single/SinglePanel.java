package ui.game.single;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JPanel;

import logic.game.GameLogicService;
import logic.game.Message;
import ui.game.Block;

public class SinglePanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private Graphics g;// 画布
	private int block_row = 9;
	private int block_col = 9;// 棋盘 横纵轴
	private int state = 0;// 状态 0 未选定 1 选定 2交换中 3消除下落中 4结束
	private int pre_x = -1;
	private int pre_y = -1;// 上一个选取的 横 纵下标
	private int now_x = -1;
	private int now_y = -1;// 这一个选取的横纵下标
	private int[][] mat;// 数据层的9*9矩阵
	private Block[][] mat2;// 界面层的9*9矩阵
	private ArrayList<Integer[][]> exlist;// 爆炸序列
	private Integer[][] notice;// 提示矩阵
	private Image back;
	private GameLogicService gs;// 获得信息的对象
	private int gar = 0;// gravity 0是下落 1是左裸
	private String palyerID;
	private SingleGamePanel frame;

	// 流程是 首先自动init 然后 点击触发mylistener

	public SinglePanel(String playerID, GameLogicService gs,
			SingleGamePanel frame)
	{
		this.setBounds(20, 5, 460, 460);
		this.addMouseListener(new MyListener());
		this.addMouseMotionListener(new MyListener2());
		g = this.getGraphics();

		mat = new int[block_row][block_col];// 数字层次
		mat2 = new Block[block_row][block_col];// 界面层次

		// ms = new SingleGS();
		this.gs = gs;
		this.palyerID = playerID;
		this.frame = frame;

		back = Toolkit.getDefaultToolkit().getImage("image/back.jpg");

		init();
	}

	// 初始化

	void init()
	{
		try
		{
			mat = gs.getStart();
		} catch (RemoteException e)
		{
			e.printStackTrace();
		}
		for (int x = 0; x < block_row; x++)
		{
			for (int y = 0; y < block_col; y++)
			{
				mat2[x][y] = new Block(mat[x][y], x, y);
			}
		}
	}

	// 获得最初的矩阵

	void getRenew()
	{
		Message me = null;
		try
		{
			me = gs.getMessage();
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switch (me.imformation)
		{
		case "Move":
			exlist = me.list;
			Iterator<Integer[][]> it = exlist.iterator();
			while (it.hasNext())
			{
				Integer[][] temp = it.next();
				for (int i = 0; i < 9; i++)
				{
					for (int j = 0; j < 9; j++)
					{
						System.out.print(temp[i][j] + " ");
					}
					System.out.println();
				}
				System.out.println("----------------");
			}
			break;
		case "Prop":
			exlist = me.list;
			Iterator<Integer[][]> it2 = exlist.iterator();
			while (it2.hasNext())
			{
				Integer[][] temp = it2.next();
				for (int i = 0; i < 9; i++)
				{
					for (int j = 0; j < 9; j++)
					{
						System.out.print(temp[i][j] + " ");
					}
					System.out.println();
				}
				System.out.println("----------------");
			}
			break;
		case "Help":
			notice = me.list.get(0);
			tix();
			break;
		}
	}

	// 获得新的矩阵序列

	void swap(final Block b1, final Block b2, boolean isDone)
	{
		// 第一个是pre isDone true 不回环 真交换 false 回环 伪交换
		// 获得位置之间的关系 横纵 步数
		if (isDone)
		{
			Thread t = new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					final int dir;
					final int road;
					final int step;
					if (b1.getHzb() == b2.getHzb())
					{
						dir = 1;
						road = b2.getZzb() - b1.getZzb();
						step = road / 5;
					} else
					{
						dir = 0;
						road = b2.getHzb() - b1.getHzb();
						step = road / 5;
					}
					int sec = 0;
					while (sec < 5)
					{
						b1.move(dir, step);
						b2.move(dir, -step);
						sec++;
						repaint();
						try
						{
							Thread.sleep(25);
						} catch (InterruptedException e)
						{
							e.printStackTrace();
						}
						if (sec == 5)
						{
							b1.move(dir, -road);
							b2.move(dir, road);
							int temp = b1.getNum();
							b1.changeIma(b2.getNum());
							b2.changeIma(temp);

							// 界面归位

							int x1 = (b1.getZzb() - 10) / 50;
							int y1 = (b1.getHzb() / 50);
							int x2 = (b2.getZzb() - 10) / 50;
							int y2 = (b2.getHzb() / 50);

							int temp2 = mat[x1][y1];
							mat[x1][y1] = mat[x2][y2];
							mat[x2][y2] = temp2;
							// 数据归位

							pre_x = -1;
							pre_y = -1;
							now_x = -1;
							now_y = -1;
							state = 0;

							for (int i = 0; i < exlist.size(); i++)
							{
								if (i % 2 == 0)
								{
									baoz(exlist.get(i), exlist.get(i + 1));
								}
							}
						}
					}
				}
			});
			t.start();
			b1.lostDianj();
		} else
		{
			Thread t = new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					final int dir;
					final int road;
					final int step;
					if (b1.getHzb() == b2.getHzb())
					{
						dir = 1;
						road = b2.getZzb() - b1.getZzb();
						step = road / 5;
					} else
					{
						dir = 0;
						road = b2.getHzb() - b1.getHzb();
						step = road / 5;
					}
					int sec = 0;
					while (sec < 5)
					{
						b1.move(dir, step);
						b2.move(dir, -step);
						sec++;
						repaint();
						try
						{
							Thread.sleep(15);
						} catch (InterruptedException e)
						{
							e.printStackTrace();
						}
					}
					while (sec < 10)
					{
						b1.move(dir, -step);
						b2.move(dir, step);
						sec++;
						repaint();
						try
						{
							Thread.sleep(10);
						} catch (InterruptedException e)
						{
							e.printStackTrace();
						}
						if (sec == 10)
						{
							pre_x = -1;
							pre_y = -1;
							now_x = -1;
							now_y = -1;
							state = 0;
						}
					}
				}
			});
			t.start();
			b1.lostDianj();
		}
	}

	void baoz(Integer[][] list, Integer[][] list2)
	{
		state = 3;
		ArrayList<Block> que = new ArrayList<Block>();
		Set<Integer> lie = new HashSet<Integer>();
		Set<Integer> hang = new HashSet<Integer>();
		for (int x = 0; x < 9; x++)
		{
			for (int y = 0; y < 9; y++)
			{
				mat[x][y] = list2[x][y];// 数据更新
				if (list[x][y] == 0)
				{
					mat2[x][y].getBaoz();// 表现爆炸
					que.add(mat2[x][y]);// 加入消除队列
					lie.add(y);// 本列列入有影响set
					hang.add(x);// 本行列入有影响set
				}
			}
		}
		repaint();
		try
		{
			Thread.sleep(50);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Iterator<Block> it = que.iterator();
		while (it.hasNext())
		{
			Block temp = it.next();
			if (gar == 0)
			{
				temp.move(1, -(temp.getZzb() + 50));// 下落 注意此处的移动地点
			} else
			{
				temp.move(0, 460 - temp.getHzb());// 左落 注意此处的移动地点
			}
		}
		repaint();
		if (gar == 0)
		{
			xial(list, list2, lie);
		} else
		{
			zuol(list, list2, hang);
		}
	}

	// 爆炸效果

	void xial(Integer[][] list, Integer[][] list2, Set<Integer> lie)
	{
		Iterator<Integer> it = lie.iterator();
		int sec = 0;
		while (sec < 5)
		{
			it = lie.iterator();
			while (it.hasNext())
			{
				int nowY = it.next();
				int nowX = 8;
				int count = 0;
				while (nowX >= 0)
				{
					if (list[nowX][nowY] == 0)
					{
						mat2[nowX][nowY].changeIma(mat[count][nowY]);
						count++;
						mat2[nowX][nowY].move(1, 12 + (count - 1) * 10);
					} else
					{
						mat2[nowX][nowY].move(1, 10 * count);
					}

					nowX--;
				}// 一列中的每一行
			}// 每一列
			sec++;
			repaint();
			try
			{
				Thread.sleep(35);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}// 5个25ms完成

		it = lie.iterator();
		while (it.hasNext())
		{
			int nowY = it.next();
			for (int x = 0; x < 9; x++)
			{
				mat2[x][nowY].init(mat[x][nowY], x, nowY);
			}
		}
		repaint();
		state = 0;
		// 归位
	}

	// 下落效果

	void zuol(Integer[][] list, Integer[][] list2, Set<Integer> hang)
	{
		Iterator<Integer> it = hang.iterator();
		int sec = 0;
		while (sec < 5)
		{
			it = hang.iterator();
			while (it.hasNext())
			{
				int nowX = it.next();
				int nowY = 0;
				int count = 0;
				while (nowY <= 8)
				{
					if (list[nowX][nowY] == 0)
					{
						mat2[nowX][nowY].changeIma(mat[nowX][8 - count]);
						count++;
						mat2[nowX][nowY].move(0, -10 * count);
						// 多了20？ 为什么 从 （0,10） 开始 81个块 450*450 panel长 470 中间20像素
						// 需要这个时候来填补
					} else
					{
						mat2[nowX][nowY].move(0, -10 * count);
					}

					nowY++;
				}// 一列中的每一行
			}// 每一列
			sec++;
			repaint();
			try
			{
				Thread.sleep(25);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}// 5个50ms完成
			//
		it = hang.iterator();
		while (it.hasNext())
		{
			int nowX = it.next();
			for (int y = 0; y < 9; y++)
			{
				mat2[nowX][y].init(mat[nowX][y], nowX, y);
			}
		}
		repaint();
		state = 0;
		// 归位
	}

	// 左落效果

	void tix()
	{
		for (int i = 0; i < 9; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				if (notice[i][j] == 1)
				{
					mat2[i][j].getTis();
				}
			}
		}
		repaint();
	}

	boolean checkNear(int now_x, int now_y, int pre_x, int pre_y)
	{
		if ((now_x == pre_x) && (Math.abs(now_y - pre_y) == 1))
		{
			return true;
		} else if ((now_y == pre_y) && (Math.abs(now_x - pre_x) == 1))
		{
			return true;
		}
		return false;
	}

	// 是否相邻

	public void paint(Graphics g)
	{
		super.paintComponent(g);
		setBackground(Color.WHITE);
		if (back != null)
		{
			int height = back.getHeight(this);
			int width = back.getWidth(this);

			if (height != -1 && height > getHeight())
				height = getHeight();

			if (width != -1 && width > getWidth())
				width = getWidth();

			g.drawImage(back, 0, 10, width, height, this);
		}
		for (int x = 0; x < 9; x++)
		{
			for (int y = 0; y < 9; y++)
			{
				mat2[x][y].draw(g);
			}
		}

	}

	// 重新绘制panel 核心啊！
	class MyListener implements MouseListener
	{

		@Override
		public void mouseClicked(MouseEvent e)
		{

		}

		@Override
		public void mouseEntered(MouseEvent e)
		{
		}

		@Override
		public void mouseExited(MouseEvent e)
		{
		}

		@Override
		public void mousePressed(MouseEvent e)
		{
			int x = (e.getX() / 50);
			int y = ((e.getY() - 10) / 50);
			if (state == 0)
			{
				if (mat[y][x] > 10)
				{
					try
					{
						gs.useEquipment(y, x);
					} catch (RemoteException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Thread t = new Thread(new Runnable()
					{
						@Override
						public void run()
						{
							// TODO Auto-generated method stub
							for (int i = 0; i < exlist.size(); i++)
							{
								if (i % 2 == 0)
								{
									baoz(exlist.get(i), exlist.get(i + 1));
								}
							}
						}
					});
					t.start();
				}// 道具被触发
				else
				{
					mat2[y][x].getDianj();
					repaint();
					state = 1;
					now_y = y;
					now_x = x;
				}// 非道具获得点击
			} else if (state == 1)
			{
				pre_x = now_x;
				pre_y = now_y;
				now_y = y;
				now_x = x;
				if (checkNear(y, x, pre_y, pre_x))
				{
					state = 2;
					int mark = 0;
					boolean result = false;
					try
					{
						mark = gs
								.exchange(pre_y, pre_x, now_y, now_x, palyerID);
					} catch (RemoteException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if (mark < 0)
					{
						result = false;
					} else
					{
						result = true;
					}
					swap(mat2[pre_y][pre_x], mat2[now_y][now_x], result);
					// 成功消除 不回环 失败 回环
				}// 相邻尝试交换
				else if (pre_x == now_x && pre_y == now_y)
				{
					mat2[pre_y][pre_x].lostDianj();
					repaint();
					state = 0;
					pre_x = -1;
					pre_y = -1;
					now_x = -1;
					now_y = -1;
				} else
				{
					state = 1;
					mat2[pre_y][pre_x].lostDianj();
					mat2[now_y][now_x].getJuj();
					repaint();
				}// 不相邻 点击取消
			}
		}

		@Override
		public void mouseReleased(MouseEvent e)
		{

		}

	}

	// 只有点击交换

	class MyListener2 implements MouseMotionListener
	{

		@Override
		public void mouseDragged(MouseEvent e)
		{
			// TODO Auto-generated method stub
			if (state == 1)
			{
				int x = (e.getX() / 50);
				int y = ((e.getY() - 10) / 50);
				if (x == now_x && y == now_y)
				{

				} else if (((x == now_x) && (Math.abs(now_y - y) == 1))
						|| (y == now_y && (Math.abs(now_x - x) == 1)))
				{
					pre_x = now_x;
					pre_y = now_y;
					now_x = x;
					now_y = y;
					state = 2;
					int mark = 0;
					boolean result = false;
					try
					{
						mark = gs
								.exchange(pre_y, pre_x, now_y, now_x, palyerID);
					} catch (RemoteException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if (mark < 0)
					{
						result = false;
					} else
					{
						result = true;
					}
					swap(mat2[pre_y][pre_x], mat2[now_y][now_x], result);
				}
			}
		}

		@Override
		public void mouseMoved(MouseEvent e)
		{
			int x = (e.getX() / 50);
			int y = ((e.getY() - 10) / 50);
			for (int i = 0; i < block_row; i++)
			{
				for (int j = 0; j < block_col; j++)
				{
					// mat2[i][j].lostJuj();
					mat2[i][j].lostTis();
				}
			}
			// if ((0 <= x) && (x < 9) && (0 <= y) && (y < 9)) {
			// mat2[y][x].getJuj();
			// repaint();
			// }
		}
	}
	// 这个是眼睛效果 和未来的拖拽效果
}
