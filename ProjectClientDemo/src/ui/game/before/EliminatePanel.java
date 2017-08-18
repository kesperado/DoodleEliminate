//package ui.game.before;
//
//import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.Image;
//import java.awt.Toolkit;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.rmi.RemoteException;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.Set;
//
//import javax.swing.JPanel;
//
//import logic.game.GameLogicService;
//import logic.game.Message;
//
//public class EliminatePanel extends JPanel {
//	private static final long serialVersionUID = 1L;
//	private static int Block_vertical = 9;// 纵向格数
//	private static int Block_horizontal = 9;// 横向格数
//	private static int Paint_Width = 50;// 图片宽
//	private static int Paint_Height = 50;// 图片高
//
//	private int blocks[][];// 这是色块的数字组织 是逻辑层面的表现
//	private Block blocks2[][];// 这是色块的的界面组织 是界面层面的表现
//
//	private int times = 0;// 这是表现点击的第几个
//	private Block pre;// 这是上次选定的色块
//	private Block now;// 这是这次选定的色块
//
//	private Image back;// 背景图片
//
//	private String playerID;// 用户id
//
//	private GameLogicService gs;// 获得信息的对象
//
//	private SingleFrame frame;
//
//	public EliminatePanel(String playerID, GameLogicService gs,
//			SingleFrame frame) {
//		this.playerID = playerID;
//		this.setBounds(177, 92, 470, 470);
//		this.setLayout(null);
//		this.setOpaque(true);
//		this.gs = gs;
//		this.frame = frame;
//
//		blocks = new int[Block_vertical][Block_horizontal];// 数字层次
//		blocks2 = new Block[Block_vertical][Block_horizontal];// 界面层次
//
//		back = Toolkit.getDefaultToolkit().getImage("image/back.jpg");
//		getInitialMap();
//	}
//
//	public void paintComponent(Graphics g) {
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
//			int x = (int) (((double) (getWidth() - width)) / 2.0);
//			int y = (int) (((double) (getHeight() - height)) / 2.0);
//			g.drawImage(back, x, y, width, height, this);
//		}
//	}
//
//	// 写背景的方法
//
//	void getInitialMap() {
//		try {
//			blocks = gs.getStart();
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		Block struc = null;
//		for (int i = 0; i < Block_vertical; i++) {
//			for (int j = 0; j < Block_horizontal; j++) {
//				// System.out.print(blocks[i][j] + " ");
//				// if (j == Block_horizontal - 1) {
//				// System.out.println();
//				// }
//				struc = new Block(blocks[i][j], i, j);
//				struc.addMouseListener(new MyListener());
//				blocks2[i][j] = struc;
//				blocks2[i][j].setLocation(0 + j * Paint_Width, 0 + i
//						* Paint_Height);
//				this.add(blocks2[i][j]);
//			}
//		}
//		// getRenew();
//	}
//
//	// 这是可消除色块的初始化
//
//	boolean checkNear(Block one, Block another) {
//		boolean result = false;
//		if (((one.getHenzb() == another.getHenzb()) && (Math.abs(one
//				.getZongzb() - another.getZongzb()) == 1))
//				|| ((one.getZongzb() == another.getZongzb()) && (Math.abs(one
//						.getHenzb() - another.getHenzb()) == 1))) {
//			result = true;
//		}
//		return result;
//	}
//
//	// 检测色块是否相邻
//
//	void swapWeizhi() {
//		// 真交换
//		final int tempX = pre.getX() - now.getX();// 横向矢量
//		final int tempY = pre.getY() - now.getY();// 纵向矢量
//		Thread t = new Thread(new Runnable() {
//			// @Override
//			public void run() {
//				int i = 0;
//				while (i < 25) {
//					pre.setLocation((int) (pre.getX() - (tempX / 25)),
//							(int) (pre.getY() - (tempY / 25)));
//					now.setLocation((int) (now.getX() + (tempX / 25)),
//							(int) (now.getY() + (tempY / 25)));
//					if (i == 24) {
//						int swap = blocks[pre.getHenzb()][pre.getZongzb()];
//						blocks[pre.getHenzb()][pre.getZongzb()] = blocks[now
//								.getHenzb()][now.getZongzb()];
//						blocks[now.getHenzb()][now.getZongzb()] = swap;
//						// 更新数据层数据
//
//						int number = pre.getTu();
//						pre.changeImg(now.getTu());
//						now.changeImg(number);
//
//						pre.guiwei();
//						now.guiwei();
//
//						pre.lostDianj();
//						pre = null;
//						times = 0;
//						// getRenew();
//						break;
//					}
//					i++;
//					try {
//						Thread.sleep(5);
//					} catch (InterruptedException e) {
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
//	// 会引发消除的交换
//
//	void swapWeizhi2() {
//		// 伪交换
//		final int tempX = pre.getX() - now.getX();// 横向矢量
//		final int tempY = pre.getY() - now.getY();// 纵向矢量
//		Thread t = new Thread(new Runnable() {
//			public void run() {
//				int i = 0;
//				while (i < 25) {
//					pre.setLocation((int) (pre.getX() - (tempX / 25)),
//							(int) (pre.getY() - (tempY / 25)));
//					now.setLocation((int) (now.getX() + (tempX / 25)),
//							(int) (now.getY() + (tempY / 25)));
//					i++;
//					try {
//						Thread.sleep(5);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//					pre.repaint();
//					now.repaint();
//				}
//				i = 0;
//				while (i < 25) {
//					pre.setLocation((int) (pre.getX() + (tempX / 25)),
//							(int) (pre.getY() + (tempY / 25)));
//					now.setLocation((int) (now.getX() - (tempX / 25)),
//							(int) (now.getY() - (tempY / 25)));
//					if (i == 24) {
//						pre.lostDianj();
//						pre = null;
//						times = 0;
//						break;
//					}
//					i++;
//					try {
//						Thread.sleep(5);
//					} catch (InterruptedException e) {
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
//	// 不会引发消除的交换
//
//	void getRenew() {
//		Message me = null;
//		try {
//			me = gs.getMessage();
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
//		for (int i = 0; i < me.list.size(); i++) {
//			if (i % 2 == 0) {
//				showExplosion(me.list.get(i), me.list.get(i + 1));
//			}
//		}// 其中 偶数是有零的矩阵 奇数是没有之后的矩阵
//	}
//
//	// 服务器调用方法 获得Message
//
//	void showExplosion(Integer[][] ms, Integer[][] ms2) {
//		ArrayList<Block> list = new ArrayList<Block>();
//		Set<Integer> lie = new HashSet<Integer>();
//		for (int i = 0; i < 9; i++) {
//			boolean hasZero = false;
//			for (int j = 0; j < 9; j++) {
//				if (ms[j][i] == 0) {
//					list.add(blocks2[j][i]);
//					hasZero = true;
//				}
//			}
//			if (hasZero) {
//				lie.add(i);
//			}
//		}
//		Iterator<Block> it = list.iterator();
//		while (it.hasNext()) {
//			it.next().getBaoz();
//		}
//		try {
//			Thread.sleep(75);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Iterator<Block> it2 = list.iterator();
//		while (it2.hasNext()) {
//			it2.next().getXiaoc();
//		}
//		showFall(ms, ms2, lie);
//	}
//
//	// 表现爆炸效果
//
//	void showFall(Integer[][] ms, final Integer[][] ms2, Set<Integer> lie) {
//		int count;
//		ArrayList<Integer> list;
//		Iterator<Integer> st = lie.iterator();
//		while (st.hasNext()) {
//			int a = st.next();
//			count = 0;
//			list = new ArrayList<Integer>();
//			for (int b = 8; b >= 0; b--) {
//				if (ms[b][a] == 0) {
//					count++;
//					list.add(b);
//				} else if (count != 0) {
//					final int ta = a;
//					final int tb = b;
//					final int tc = count;
//					Thread t = new Thread(new Runnable() {
//						@Override
//						public void run() {
//							int i = 0;
//							while (i < 10) {
//								blocks2[tb][ta].luoxia(tc * 5);
//								try {
//									Thread.sleep(50);
//								} catch (InterruptedException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								}
//								i++;
//							}
//						}
//					});
//					t.start();
//				}
//			}
//			// 原有色块下落
//			int t = 0;
//			while (count > 0) {
//				final int tb = list.get(t);
//				final int ta = a;
//				final int tc = count;
//				blocks2[tb][a].changeImg(ms2[count - 1][a]);
//				Thread t2 = new Thread(new Runnable() {
//					@Override
//					public void run() {
//						int i = 0;
//						while (i < 10) {
//							blocks2[tb][ta].luoxia(tc * 5);
//							try {
//								Thread.sleep(25);
//							} catch (InterruptedException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//							i++;
//						}
//						if (i == 10 && tc == 1) {
//							for (int z = 0; z < 9; z++) {
//								blocks2[z][ta].guiwei();
//								blocks2[z][ta].changeImg(ms2[z][ta]);
//							}
//						}
//					}
//				});
//				t2.start();
//				count--;
//				t++;
//			}
//		}
//	}
//
//	// 表现下落效果
//
//	class MyListener implements MouseListener {
//		@Override
//		public void mouseClicked(MouseEvent e) {
//			// TODO Auto-generated method stub
//			System.out.println(e.getPoint());
//			if (times == 0) {
//				pre = (Block) e.getSource();
//				times = 1;
//				pre.getDianj();
//			} else if (times == 1) {
//				if (checkNear(pre, (Block) e.getSource())) {
//					System.out.println("选定正确");
//					now = (Block) e.getSource();
//
//					// 这里会向核心传送交换指令 并得到布尔值的结果
//					boolean result = true;
//					int mark = 0;
//					try {
//						mark = gs.exchange(pre.getHenzb(), pre.getZongzb(),
//								now.getHenzb(), now.getZongzb(), playerID);
//					} catch (RemoteException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//					if (mark < 0) {
//						result = false;
//					} else if (mark > 0) {
//						result = true;
//						System.out.println(mark);
//						frame.updateMark(mark);
//					}
//					if (result) {
//						swapWeizhi();
//					} else {
//						swapWeizhi2();
//					}
//					System.out.println("已经更新数据");
//				} else {
//					pre.lostDianj();
//					pre = null;
//					times = 0;
//					System.out.println("不相邻");
//				}
//			} else {
//				System.out.println("程序出错了");
//			}
//		}
//
//		@Override
//		public void mouseEntered(MouseEvent e) {
//			// 完成
//			((Block) e.getSource()).getJuj();
//		}
//
//		@Override
//		public void mouseExited(MouseEvent e) {
//			// 完成
//			((Block) e.getSource()).lostJuj();
//		}
//
//		@Override
//		public void mousePressed(MouseEvent e) {
//			// 没用
//
//		}
//
//		@Override
//		public void mouseReleased(MouseEvent e) {
//			// 没用
//
//		}
//	}
// }
