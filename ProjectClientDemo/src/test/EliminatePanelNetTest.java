//package test;
//
//import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.Image;
//import java.awt.Point;
//import java.awt.Toolkit;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.rmi.RemoteException;
//
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//
//import logic.game.GameLogicService;
//import logic.game.GameService;
//import logic.game.Message;
//import rmi.RMIController;
//import ui.game.before.Block;
//
//public class EliminatePanelNetTest extends JPanel
//{
//	private static int Block_vertical = 9;// 纵向格数
//	private static int Block_horizontal = 9;// 横向格数
//	private static int Paint_Width = 50;// 图片宽
//	private static int Paint_Height = 50;// 图片高
//
//	private int blocks[][];// 这是色块的数字组织 是逻辑层面的表现
//	private Block blocks2[][];// 这是色块的的界面组织 是界面层面的表现
//	private int times = 0;// 这是表现点击的第几个
//	private Block pre;// 这是上次选定的色块
//	private Block now;// 这是这次选定的色块
//
//	private Image back;
//	private int serverNumber;
//	private GameLogicService server;
//	private Message receive;
//	private boolean moving;
//	
//	public EliminatePanelNetTest(int number)
//	{
//
//		this.setBounds(177, 92, 470, 470);
//		this.setLayout(null);
//		this.setOpaque(true);
//		this.moving=false;
//		blocks = new int[Block_vertical][Block_horizontal];// 数字层次
//		blocks2 = new Block[Block_vertical][Block_horizontal];// 界面层次
//
//		back = Toolkit.getDefaultToolkit().getImage("image/back.jpg");
//		getInitialMap();
//
//		serverNumber = number;
//		RMIController rmi = new RMIController();
//		GameService gs = rmi.getGameService();
//		try
//		{
//			server = gs.getSingleGame(serverNumber);
//			init();
//		} catch (RemoteException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
//
//	public void init() throws RemoteException
//	{
//		int[][] temp=server.getStart();
//		for (int i = 0; i < Block_horizontal; i++)
//		{
//			for (int j = 0; j < Block_vertical; j++)
//			{
//				blocks[i][j] = temp[i][j];
//				blocks2[i][j].changeImg(temp[i][j]);
//				//System.out.print(temp[i][j]);
//			}
//		}
//		repaint();
//	}
//
//	public void paintComponent(Graphics g)
//	{
//		super.paintComponent(g);
//		setBackground(Color.WHITE);
//		if (back != null)
//		{
//			int height = back.getHeight(this);
//			int width = back.getWidth(this);
//
//			if (height != -1 && height > getHeight())
//				height = getHeight();
//
//			if (width != -1 && width > getWidth())
//				width = getWidth();
//
//			int x = (int) (((double) (getWidth() - width)) / 2.0);
//			int y = (int) (((double) (getHeight() - height)) / 2.0);
//			g.drawImage(back, x, y, width, height, this);
//		}
//	}
//
//	void getInitialMap()
//	{
//		for (int t1 = 0; t1 < 9; t1++)
//		{
//			for (int t2 = 0; t2 < 9; t2++)
//			{
//				blocks[t1][t2] = (int) (Math.random() * 7) + 1;
//			}
//		}
//
//		Block struc = null;
//		for (int i = 0; i < Block_vertical; i++)
//		{
//			for (int j = 0; j < Block_horizontal; j++)
//			{
//				struc = new Block(blocks[i][j], i, j);
//				struc.addMouseListener(new MyListener());
//				blocks2[i][j] = struc;
//				blocks2[i][j].setLocation(0 + j * Paint_Width, 0 + i
//						* Paint_Height);
//				this.add(blocks2[i][j]);
//			}
//		}// 这是可消除色块的初始化
//	}
//
//	boolean checkNear(Block one, Block another)
//	{
//		boolean result = false;
//		if (((one.getHenzb() == another.getHenzb()) && (Math.abs(one
//				.getZongzb() - another.getZongzb()) == 1))
//				|| ((one.getZongzb() == another.getZongzb()) && (Math.abs(one
//						.getHenzb() - another.getHenzb()) == 1)))
//		{
//			result = true;
//		}
//		return result;
//	}
//
//	void swapWeizhi()
//	{
//		// 真交换
//		final int tempX = pre.getX() - now.getX();// 横向矢量
//		final int tempY = pre.getY() - now.getY();// 纵向矢量
//		Thread t = new Thread(new Runnable()
//		{
//			// @Override
//			public void run()
//			{
//				int i = 0;
//				while (i < 25)
//				{
//					pre.setLocation((int) (pre.getX() - (tempX / 25)),
//							(int) (pre.getY() - (tempY / 25)));
//					now.setLocation((int) (now.getX() + (tempX / 25)),
//							(int) (now.getY() + (tempY / 25)));
//					if (i == 24)
//					{
//						
//						int number = pre.getTu();
//						pre.changeImg(now.getTu());
//						now.changeImg(number);
//						Point p = pre.getLocation();
//						pre.setLocation(now.getLocation());
//						now.setLocation(p);
//						
//						Block tempB = pre;
//						pre = now;
//						now = tempB;
//						// 更新界面层数据
//						int swap = blocks[pre.getHenzb()][pre.getZongzb()];
//						blocks[pre.getHenzb()][pre.getZongzb()] = blocks[now
//								.getHenzb()][now.getZongzb()];
//						blocks[now.getHenzb()][now.getZongzb()] = swap;
//						// 更新数据层数据
//
//						pre.lostDianj();
//						pre = null;
//						times = 0;
//						moving = false;
//						break;
//					}
//					i++;
//					try
//					{
//						Thread.sleep(10);
//					} catch (InterruptedException e)
//					{
//						e.printStackTrace();
//					}
//					pre.repaint();
//					now.repaint();
//				}
//			}
//		});
//		t.start();
//	}
//
//	void swapWeizhi2()
//	{
//		// 伪交换
//		final int tempX = pre.getX() - now.getX();// 横向矢量
//		final int tempY = pre.getY() - now.getY();// 纵向矢量
//		Thread t = new Thread(new Runnable()
//		{
//			public void run()
//			{
//				int i = 0;
//				while (i < 25)
//				{
//					pre.setLocation((int) (pre.getX() - (tempX / 25)),
//							(int) (pre.getY() - (tempY / 25)));
//					now.setLocation((int) (now.getX() + (tempX / 25)),
//							(int) (now.getY() + (tempY / 25)));
//					i++;
//					try
//					{
//						Thread.sleep(5);
//					} catch (InterruptedException e)
//					{
//						e.printStackTrace();
//					}
//					pre.repaint();
//					now.repaint();
//				}
//				i = 0;
//				while (i < 25)
//				{
//					pre.setLocation((int) (pre.getX() + (tempX / 25)),
//							(int) (pre.getY() + (tempY / 25)));
//					now.setLocation((int) (now.getX() - (tempX / 25)),
//							(int) (now.getY() - (tempY / 25)));
//					if (i == 24)
//					{
//						pre.lostDianj();
//						pre = null;
//						times = 0;
//						break;
//					}
//					i++;
//					try
//					{
//						Thread.sleep(5);
//					} catch (InterruptedException e)
//					{
//						e.printStackTrace();
//					}
//					pre.repaint();
//					now.repaint();
//				}
//			}
//		});
//		t.start();
//	}
//
//	public void reFresh(Integer[][] list)
//	{
//		while(moving);
//		this.removeAll();
//		System.out.println("(*)(&#)@(&^()@#&%)*^#(^583&^)");
//		Block struc = null;
//		for (int i = 0; i < Block_horizontal; i++)
//		{
//			for (int j = 0; j < Block_vertical; j++)
//			{
//				blocks[i][j] = list[i][j];
//				//System.out.print(list[i][j]+" ");
//				
//				struc = new Block(blocks[i][j], i, j);
//				struc.addMouseListener(new MyListener());
//				blocks2[i][j] = struc;
//				blocks2[i][j].setLocation(0 + j * Paint_Width, 0 + i
//						* Paint_Height);
//				this.add(blocks2[i][j]);
//			}
//		}
//		repaint();
//		System.out.println("(*)(&#)@(&^()@#&%)*^#(^583&^)");
//	}
//
//	public void alert(Message m) throws RemoteException
//	{
//		//JOptionPane.showMessageDialog(null, "获得新界面");
//		switch (m.imformation)
//		{
//		case "New Game":
//			reFresh(m.list.get(0));
//			break;
//		default:
//			reFresh(m.list.get(m.list.size()-1));
//			
//			break;
//		}
//	}
//
//	class MyListener implements MouseListener
//	{
//		@Override
//		public void mouseClicked(MouseEvent e)
//		{
//			// TODO Auto-generated method stub
//			Block temple = (Block) e.getSource();
//			
//			System.out.println(" "+temple.getHenzb()+" "+temple.getZongzb());
//			System.out.println(" "+temple.getTu());
//			if (times == 0)
//			{
//				pre = (Block) e.getSource();
//				times = 1;
//				pre.getDianj();
//			} else if (times == 1)
//			{
//				if (checkNear(pre, (Block) e.getSource()))
//				{
//					System.out.println("选定正确");
//					now = (Block) e.getSource();
//					int x1 = pre.getHenzb();
//					int y1 = pre.getZongzb();
//					int x2 = now.getHenzb();
//					int y2 = now.getZongzb();
//					int score =0;
//					moving = true;
//					try
//					{
//						score=server.exchange(x1, y1, x2, y2, "TestPlayer");
//					}catch(RemoteException ee)
//					{
//						;
//					}
//
//					// 这里会向核心传送交换指令 并得到布尔值的结果
//					boolean result = true;
//					if (score>=0)
//					{
//						swapWeizhi();
//					} else
//					{
//						swapWeizhi2();
//					}
//					repaint();
//					System.out.println("已经更新数据");
//				} else
//				{
//					pre.lostDianj();
//					pre = null;
//					times = 0;
//					System.out.println("不相邻");
//				}
//			} else
//			{
//				System.out.println("程序出错了");
//			}
//		}
//
//		@Override
//		public void mouseEntered(MouseEvent e)
//		{
//			// 完成
//			((Block) e.getSource()).getJuj();
//		}
//
//		@Override
//		public void mouseExited(MouseEvent e)
//		{
//			// 完成
//			((Block) e.getSource()).lostJuj();
//		}
//
//		@Override
//		public void mousePressed(MouseEvent e)
//		{
//			// 没用
//
//		}
//
//		@Override
//		public void mouseReleased(MouseEvent e)
//		{
//
//		}
//	}
// }
