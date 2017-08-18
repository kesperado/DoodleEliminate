package logic.game;

import java.util.ArrayList;

/**
 * @author 作者 :knox
 * @version 创建时间：2014-3-18 下午7:33:05 类说明
 */
public class GameBoard
{

	private static int MAX_ROW = 9;
	private static int MAX_COL = 9;
	private static int KIND = 7;
	private int EQUIPMENTSCORE = 900;// 道具使用分值
	public int[][] map = new int[MAX_ROW][MAX_COL];
	private boolean ready = false;
	private boolean possibleready = false;

	private Integer[][] possiblelocation = new Integer[MAX_ROW][MAX_COL];
	private Message message;

	public GameBoard() // 构造器 进行数组和Message的初始化
	{
		init();
		/*
		 * message = new Message(); Integer[][] oldlist = new
		 * Integer[MAX_ROW][MAX_COL]; for (int i = 0; i < MAX_ROW; i++) { for
		 * (int j = 0; j < MAX_COL; j++) { oldlist[i][j] = map[i][j]; } }
		 * message.list.add(oldlist); message.imformation = "New Game";
		 */
	}

	private void init()// 生成一张新图
	{
		message = new Message();
		createNewMap();
		while (!judgeMap() || selfEliminate())
		{
			createNewMap();
		}
		System.out.println("********服务器原图开始");
		showMap();
		System.out.println("********服务器原图终止");
		ready = true;
	}

	// 检测是否有自消除
	private boolean selfEliminate()
	{
		// System.out.println("开始自检");
		boolean elminated = false;
		int[][] hengtemple = new int[MAX_ROW][MAX_COL];
		int[][] zongtemple = new int[MAX_ROW][MAX_COL];

		for (int i = 0; i < MAX_ROW; i++)
		{
			for (int j = 0; j < MAX_COL; j++)
			{
				zongtemple[i][j] = map[i][j];
				hengtemple[i][j] = map[i][j];
			}
		}

		for (int i = 0; i < MAX_ROW; i++)
		{
			for (int j = 0; j < MAX_COL; j++)
			{
				if (zongtemple[i][j] == 0 || hengtemple[i][j] == 0)
				{
					continue;
				}
				int heng = calSameLength(i, j, 0, 1, map[i][j]);// 横向扫描
				int zong = calSameLength(i, j, 1, 0, map[i][j]);// 纵向扫描
				if (heng >= 2)
				{
					elminated = true;
					// System.out.println("****发现自消除*****");
					// System.out.println("横向"+i+" "+j);
					for (int k = 0; k <= heng; k++)
					{
						hengtemple[i][j + k] = 0;
					}
				}
				if (zong >= 2)
				{
					elminated = true;
					// System.out.println("****发现自消除*****");
					// System.out.println(""+i+" "+j);
					for (int k = 0; k <= zong; k++)
					{
						zongtemple[i + k][j] = 0;
					}
				}
			}
		}
		for (int i = 0; i < MAX_ROW; i++)
		{
			for (int j = 0; j < MAX_COL; j++)
			{
				if (zongtemple[i][j] == 0 || hengtemple[i][j] == 0)
				{
					map[i][j] = 0;
				}
			}
		}
		return elminated;
	}

	// 执行一次操作
	public int doEliminate(int x1, int y1, int x2, int y2)
	{
		System.out.println("First " + x1 + y1 + "Second " + x2 + y2);
		System.out.println("之前的图");
		showMap();

		int temple = map[x1][y1];
		map[x1][y1] = map[x2][y2];
		map[x2][y2] = temple;

		int first = judgeone(x1, y1);
		int second = judgeone(x2, y2);
		int sum = first + second;
		if (sum == 0)
		{
			// System.out.println("移动无效");
			int xx = map[x1][y1];
			map[x1][y1] = map[x2][y2];
			map[x2][y2] = xx;
			return -1;
		} else
		{
			// System.out.println("移动有效");
			message = new Message();
			message.imformation = "Move";
			doSomeAfterEliminate();
			while (selfEliminate())
			{
				// System.out.println("****系统进行了一次自消除*****");
				doSomeAfterEliminate();
			}
			/*
			 * if (sum > 300 && sum < 500) { if (first>0) { rewardA(y1); }else {
			 * rewardA(y2); }
			 * 
			 * } if (sum >= 500) { if(first>0) { rewardB(y1); }else {
			 * rewardB(y2); } }
			 */
		}
		System.out.println("之后的图");
		showMap();
		return sum;
	}

	// 判断某一点是否存在可消除情况，若存在，进行消除操作并奖励道具
	private int judgeone(int x, int y)
	{
		if (map[x][y] == 0)
		{
			return 0;
		}
		int result = 0;
		// System.out.println("X: "+x+"Y: "+y);
		int yf = calSameLength(x, y, -1, 0, map[x][y]);// y轴负方向
		int yz = calSameLength(x, y, 1, 0, map[x][y]);// Y轴正方向
		int xf = calSameLength(x, y, 0, -1, map[x][y]);// X轴负方向
		int xz = calSameLength(x, y, 0, 1, map[x][y]);// X轴正方向

		int ytotal = yf + yz;
		int xtotal = xf + xz;
	

		if (ytotal >= 2)
		{
			for (int i = -yf; i <= yz; i++)
			{
				map[x + i][y] = 0;
			}
			result = result + ytotal + 1;
		}
		if (xtotal >= 2)
		{
			for (int i = -xf; i <= xz; i++)
			{
				map[x][y + i] = 0;
			}
			result = result + xtotal + 1;
		}
		// 奖励道具B
		if (xtotal >= 4 || ytotal >= 4 || (xtotal >= 2 && ytotal >= 3)
				|| (xtotal >= 3 && ytotal >= 2))
		{
			System.out.println("奖励道具");
			rewardB(y);
		}
		// 奖励道具A
		if (xtotal == 3 || ytotal == 3 || (xtotal == 2 && ytotal == 2))
		{
			System.out.println("奖励道具");
			rewardA(y);
		}
		result = result * 100;
		return result;
	}

	// 计算从某一点某一方向
	private int calSameLength(int x, int y, int xtoward, int ytoward, int value)
	{
		int result = 0;

		for (int i = 1; i < 5; i++)
		{
			try
			{
				if (map[x + xtoward * i][y + ytoward * i] >= 20)// 排除道具2影响
				{
					break;
				}
				if (map[x + xtoward * i][y + ytoward * i] % 10 == value)
				{
					result++;
				} else
				{
					break;
				}
			} catch (Exception e)
			{
				break;
			}

		}
		return result;
	}

	// 将被消除之后的图重新调整：将所有块往下移，填充为0的块，并随机生成数字填充顶上的块（那些块过程中会用-1表示）
	private void doSomeAfterEliminate()
	{
		Integer[][] oldlist = new Integer[MAX_ROW][MAX_COL];
		Integer[][] newlist = new Integer[MAX_ROW][MAX_COL];

		for (int i = 0; i < MAX_ROW; i++)
		{
			for (int j = 0; j < MAX_COL; j++)
			{
				oldlist[i][j] = map[i][j];
			}
		}

		for (int j = 0; j < MAX_COL; j++)
		{
			for (int i = MAX_ROW - 1; i >= 0; i--)
			{
				if (map[i][j] == 0)
				{
					int offset = 1;
					for (int k = i - 1; k >= 0; k--)
					{
						if (map[k][j] == 0)
						{
							offset++;
						} else
						{
							break;
						}
					}
					for (int k = i; k >= offset; k--)
					{
						map[k][j] = map[k - offset][j];
					}
					for (int k = 0; k < offset; k++)
					{
						map[k][j] = -1;
					}
				}
			}
		}

		int[][] backup = map.clone();
		for (int i = 0; i < MAX_ROW; i++)
		{
			for (int j = 0; j < MAX_COL; j++)
			{
				if (map[i][j] == -1)
				{
					map[i][j] = (int) (Math.random() * KIND + 1);
					newlist[i][j] = map[i][j];
				}
			}
		}

		while (true)
		{
			if (judgeMap())
			{
				for (int i = 0; i < MAX_ROW; i++)
				{
					for (int j = 0; j < MAX_COL; j++)
					{
						newlist[i][j] = map[i][j];
					}
				}
				break;
			} else
			{
				map = backup;
				for (int i = 0; i < MAX_ROW; i++)
				{
					for (int j = 0; j < MAX_COL; j++)
					{
						if (map[i][j] == -1)
						{
							map[i][j] = (int) (Math.random() * KIND + 1);
							newlist[i][j] = map[i][j];
						} else
						{
							newlist[i][j] = map[i][j];
						}
					}
				}
			}
		}

		message.list.add(oldlist);
		message.list.add(newlist);
		// System.out.println("新图生成完毕，未自检");
		showMap();
	}

	// 随机生成新图 用于初始化
	private void createNewMap()
	{
		for (int i = 0; i < MAX_ROW; i++)
		{
			for (int j = 0; j < MAX_COL; j++)
			{
				map[i][j] = (int) (Math.random() * KIND + 1);
			}
		}
	}

	// 判断图中是否存在可消除块 并记录
	private boolean judgeMap()
	{
		possibleready = false;
		for (int i = 0; i < MAX_ROW; i++)
		{
			for (int j = 0; j < MAX_COL; j++)
			{
				possiblelocation[i][j] = 0;
			}
		}

		for (int i = 0; i < MAX_ROW; i++)
		{
			for (int j = 0; j < MAX_COL; j++)
			{
				if (judgeSingle(i, j))
				{
					possibleready = true;
					return true;
				}
			}
		}
		return false;
	}

	// 判断图中某点是否可消除 并记录
	private boolean judgeSingle(int m, int n)
	{
		for (int i = -1; i <= 1; i++)
		{
			for (int j = -1; j <= 1; j++)
			{
				if (Math.abs(i) == Math.abs(j))
				{
					continue;
				}

				int dir = calSameLength(m + i, n + j, i, j, map[m][n]);
				int dl = 0;
				int dr = 0;
				if (i == 0)// 如果是纵向移动
				{
					dl = calSameLength(m + i, n + j, -1, 0, map[m][n]);
					dr = calSameLength(m + i, n + j, +1, 0, map[m][n]);
				} else
				{
					dl = calSameLength(m + i, n + j, 0, -1, map[m][n]);
					dr = calSameLength(m + i, n + j, 0, +1, map[m][n]);
				}
				if (dir >= 2)
				{
					possiblelocation[m][n] = 1;
					// System.out.println("NOTICE************DIR");
					// System.out.println(""+m+ " "+n+ " "+i+ " "+j+" "+dir);
					for (int a = 1; a <= dir; a++)
					{
						possiblelocation[m + i + i * a][n + j + j * a] = 1;
					}
					return true;
				}
				if (dl + dr >= 2)
				{
					// System.out.println("NOTICE************LFR");
					// System.out.println(""+m+ " "+n+ " "+i+
					// " "+j+" "+dl+" "+dr);
					possiblelocation[m][n] = 1;
					for (int a = -dl; a <= dr; a++)
					{
						if (a == 0)
						{
							continue;
						}
						if (i == 0)
						{
							possiblelocation[m + i + a][n + j] = 1;
						} else
						{
							possiblelocation[m + i][n + j + a] = 1;
						}
					}
					return true;
				}

			}
		}
		return false;
	}

	// 控制台输出数组 用于debug
	private void showMap()
	{
		for (int i = 0; i < MAX_ROW; i++)
		{
			for (int j = 0; j < MAX_COL; j++)
			{
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
	}

	// 使用道具
	public boolean useEquipment(int x, int y)
	{
		boolean result = false;
		if (map[x][y] <= 10)
		{
			return result;
		}
		message = new Message();
		message.imformation = "Prop";
		// System.out.println("道具已使用");
		if (map[x][y] < 20 && map[x][y] > 10)
		{
			result = true;

			for (int i = -1; i <= 1; i++)
			{
				for (int j = -1; j <= 1; j++)
				{
					try
					{
						map[x + i][y + j] = 0;
					} catch (Exception e)
					{
						continue;
					}

				}
			}
		}
		if (map[x][y] >= 20)
		{
			result = true;
			int temp = map[x][y] % 2;
			if (temp == 1)
			{
				for (int i = 0; i < MAX_ROW; i++)
				{
					map[i][y] = 0;
				}
			} else
			{
				for (int i = 0; i < MAX_COL; i++)
				{
					map[x][i] = 0;
				}
			}
		}
		doSomeAfterEliminate();
		while (selfEliminate())
		{
			// System.out.println("****系统进行了一次自消除*****");
			doSomeAfterEliminate();
		}
		return result;
	}

	// 奖励道具A
	private void rewardA(int y)
	{
		while (true)
		{
			int dx = (int) (Math.random() * KIND) + 1;
			if (map[dx][y] >= 10||map[dx][y]==0)
			{
				continue;
			}
			map[dx][y] = map[dx][y] % 10 + 10;
			message.list.get(message.list.size() - 1)[dx][y] = map[dx][y];
			break;
		}

	}

	// 奖励道具B
	private void rewardB(int y)
	{
		int dx = (int) (Math.random() * KIND) + 1;
		map[dx][y] = 20 + (int) (Math.random() * 2);
		message.list.get(message.list.size() - 1)[dx][y] = map[dx][y];
	}

	// 获取MAP
	public int[][] getMap()
	{
		int[][] result = new int[MAX_ROW][MAX_COL];
		for (int i = 0; i < MAX_ROW; i++)
		{
			for (int j = 0; j < MAX_ROW; j++)
			{
				result[i][j] = map[i][j];
			}
		}
		return result;
	}

	public Message getMessage()
	{
		Message result = new Message();
		result.imformation = message.imformation;
		for (Integer[][] I : message.list)
		{
			Integer[][] temp = new Integer[MAX_ROW][MAX_COL];
			for (int i = 0; i < MAX_ROW; i++)
			{
				for (int j = 0; j < MAX_ROW; j++)
				{
					temp[i][j] = I[i][j];
				}
			}
			result.list.add(temp);
		}
		return result;
	}

	public Integer[][] getPossibleLocation()
	{
		Integer[][] result = new Integer[MAX_ROW][MAX_COL];
		while (!possibleready)
			;
		for (int i = 0; i < MAX_ROW; i++)
		{
			for (int j = 0; j < MAX_ROW; j++)
			{
				result[i][j] = possiblelocation[i][j];
			}
		}
		return result;
	}

	public boolean getReady()
	{
		return ready;
	}
}
/*
 * // 保证开局地图本身不存在三消块 方法已停用 备用 private void eliminateBeforeStart() { //
 * 对全图每个方块进行扫描，判断它的下面或者右边是存在连续的可消除的三消块 boolean changed = false; do { //
 * 当有产生可消除的方块时，也就是必须要做出修改，做出修改之后就必须再全局检查一遍，以保证随机出来的不会和原先的组成新的三消块 changed =
 * false; //System.out.println("loop"); showMap(); for (int i = 0; i < MAX_ROW;
 * i++) { for (int j = 0; j < MAX_COL; j++) { try { if (map[i][j] == map[i +
 * 1][j]) { if (map[i][j] == map[i + 2][j]) { changed = true; map[i + (int)
 * (Math.random() * 3)][j] = (int) (Math .random() * KIND + 1); //
 * 在三个可消除的方块中随机选择一个方块 } } } catch (ArrayIndexOutOfBoundsException e) { continue;
 * }
 * 
 * try { if (map[i][j] == map[i][j + 1]) { if (map[i][j] == map[i][j + 2]) {
 * changed = true; map[i][j + (int) (Math.random() * 3)] = (int) (Math .random()
 * * KIND + 1); } } } catch (ArrayIndexOutOfBoundsException e) { continue; } } }
 * } while (changed);
 * 
 * }
 */

/*
 * 命令行入口 用于初步测试 备用 public static void main(String[] args) { GameBoard m = new
 * GameBoard(); m.init(); while (true) { m.run(); } } private void run() { while
 * (true) { //System.out.println("****请操作*****"); showMap(); Scanner s = new
 * Scanner(System.in); int x1 = s.nextInt(); int y1 = s.nextInt(); int x2 =
 * s.nextInt(); int y2 = s.nextInt(); doEliminate(x1, y1, x2, y2); // 用户操作进行消除 }
 * }
 */
/*
 * // 判断图中是否有可以操作的三消块 停用 备用 private boolean judgeMapBackup() { // 从左上往右下角进行斜检测
 * for (int i = 1; i < MAX_ROW; i++) { for (int j = 1; j < MAX_COL; j++) { try {
 * if (map[i][j] == map[i + 1][j + 1]) { int currentInterger = map[i][j]; if
 * (map[i - 1][j] == currentInterger || map[i - 1][j + 1] == currentInterger ||
 * map[i][j - 1] == currentInterger || map[i + 1][j - 1] == currentInterger ||
 * map[i][j + 2] == currentInterger || map[i + 1][j + 2] == currentInterger ||
 * map[i + 2][j + 1] == currentInterger || map[i + 2][j] == currentInterger) {
 * return true; } } } catch (ArrayIndexOutOfBoundsException e) { continue; } } }
 * for (int i = 1; i < MAX_ROW; i++) { for (int j = MAX_COL; j > 1; j--) { try {
 * if (map[i][j] == map[i + 1][j - 1]) { int currentInterger = map[i][j]; if
 * (map[i - 1][j] == currentInterger || map[i - 1][j - 1] == currentInterger ||
 * map[i][j + 1] == currentInterger || map[i + 1][j + 1] == currentInterger ||
 * map[i][j - 2] == currentInterger || map[i + 1][j - 2] == currentInterger ||
 * map[i + 2][j - 1] == currentInterger || map[i + 2][j] == currentInterger) {
 * return true; } } } catch (ArrayIndexOutOfBoundsException e) { continue; }
 * 
 * } }
 * 
 * for (int i = 1; i < MAX_ROW; i++) { for (int j = 1; j < MAX_COL; j++) { try {
 * if (map[i][j] == map[i][j + 2]) { // 横着四块当中有三块同色的 int currentInterger =
 * map[i][j]; if (map[i][j - 1] == currentInterger || map[i][j + 3] ==
 * currentInterger) { return true; } } else if (map[i][j] == map[i + 2][j]) {
 * int currentInterger = map[i][j]; if (map[i - 1][j] == currentInterger ||
 * map[i + 3][j] == currentInterger) { return true; } } } catch
 * (ArrayIndexOutOfBoundsException e) { continue; }
 * 
 * } }
 * 
 * return false;
 * 
 * }
 */
