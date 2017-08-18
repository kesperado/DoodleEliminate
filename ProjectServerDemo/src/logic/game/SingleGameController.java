package logic.game;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import logic.equipment.EquipmentLogicController;
import logic.player.PlayerLogicController;
import po.GamePO;
import po.GameSettingPO;
import po.PlayerPO;
import rmi.Listener;

public class SingleGameController extends UnicastRemoteObject implements
		GameLogicService
{
	// 游戏ID
	private int gameID;
	// 连击计数
	private int doublehit = 0;
	// 分数
	private int score = 0;
	// 连击时间
	private int DOUBLEHITTIME = 1500;
	// SUPER模式时间
	private int SUPERTIME = 4000;
	// 游戏时间
	private int FINISHTIME = 60000;
	// 提示时间
	private int HELPTIME = 3000;
	// 得分倍数
	private double GRADEPERCENTAGE = 1.0;
	private boolean supermod;
	private boolean doublesupermod;
	private boolean finish;
	// 提示计时
	private long operateTime;
	// 连击计时
	private long recordTime;
	// 游戏时间计时
	private long gameStartTime;
	// 游戏方向
	private int direction;
	// 最大连击数
	private int maxHit;

	private String player;

	private int DROPCOUNT = 0;

	private GameSettingPO setting;

	Message message;// 最新更新信息
	GameBoard map;// 逻辑对象
	Listener listener;// 监视着

	int count = 0;

	// 构造器
	public SingleGameController(int ID) throws RemoteException
	{
		super();

		this.gameID = ID;
		this.finish = false;
		this.supermod = false;
		this.doublesupermod = false;
		this.map = new GameBoard();
		System.out.println("Game has created" + ID + " ");
	}

	// 有设置的构造器
	public SingleGameController(int ID, GameSettingPO setting)
			throws RemoteException
	{
		this(ID);
		this.setting = setting;
		this.direction = setting.direction;
		// startGame();
	}

	// 开始游戏各种计时 游戏开始
	public void startGame() throws RemoteException
	{
		EquipmentLogicController elc = new EquipmentLogicController();
		if (setting.third)
		{
			setting.third = elc.useEquipment(player, "C");
		}
		if (setting.fourth)
		{
			setting.fourth = elc.useEquipment(player, "D");
		}
		if (setting.fifth)
		{
			setting.fifth = elc.useEquipment(player, "E");
		}

		if (setting.third)
		{
			this.useEquipment(3);
		}
		if (setting.fourth)
		{
			this.useEquipment(4);
		}
		if (setting.fifth)
		{
			this.useEquipment(5);
		}

		
		 //this.direction = 1;

		gameStartTime = System.currentTimeMillis();

		Thread tend = new Thread(new endListener());
		tend.start();

		Thread thit = new Thread(new hitListener());
		thit.start();

		Thread thelp = new Thread(new helpListener());
		thelp.start();

		this.message = new Message();
		this.message.imformation = "Game Start";
	}

	// 获取开始界面
	public int[][] getStart() throws RemoteException
	{
		int[][] currentmap = map.getMap();
		int[][] temp = new int[9][9];
		if (direction == 1)
		{
			System.out.println("************START********");
			for (int i = 0; i < 9; i++)
			{
				for (int j = 0; j < 9; j++)
				{
					temp[i][j] = currentmap[8 - j][i];
					System.out.print(" " + temp[i][j]);
				}
				System.out.println();
			}
			return temp;
		}

		return currentmap;
	}

	// 进行交换
	public int exchange(int x1, int y1, int x2, int y2, String ID)
			throws RemoteException
	{
		if (direction == 1)
		{
			int ox1 = x1;
			int oy1 = y1;
			int ox2 = x2;
			int oy2 = y2;
			x1 = 8 - oy1;
			y1 = ox1;
			x2 = 8 - oy2;
			y2 = ox2;
		}
		int singlescore = map.doEliminate(x1, y1, x2, y2);
		System.out.println("得分" + singlescore * GRADEPERCENTAGE);

		if (singlescore >= 0)
		{
			this.message = map.getMessage();
			recordTime = System.currentTimeMillis();// 用于连击时间修改
			operateTime = System.currentTimeMillis();// 用于提示时间修改
			singlescore = (int) (singlescore * GRADEPERCENTAGE);
			score = (int) (score + singlescore);
			doublehit++;
			if (doublehit >= maxHit)
			{
				maxHit = doublehit;
			}
			if (doublehit % 4 == 0 && doublehit > 0)
			{
				if (supermod)// 如果已经处于SUPER模式
				{
					doublesupermod = true;
				} else
				{
					supermod = true;
					GRADEPERCENTAGE = GRADEPERCENTAGE * 2;
					Thread t = new Thread(new superListener());
					t.start();
				}
			}
			System.out.println("当前连击数 " + doublehit);
			updateMessage();
			notice();
		}

		return singlescore;
	}

	// 获取更新信息
	public Message getMessage()
	{
		for(Integer[][] I:message.list)
		{
			System.out.println("*****************");
			for(int i=0;i<9;i++)
			{
				for(int j=0;j<9;j++)
				{
					System.out.print(I[i][j]);
				}
				System.out.println();
			}
			System.out.println("*****************");
		}
		return this.message;
	}

	// 获取更新信息
	public Message getMessage(String ID) throws RemoteException
	{
		return this.message;
	}

	// 提示功能
	private void offerHelp()
	{
		this.message = new Message();
		this.message.imformation = "Help";
		this.message.list.add(map.getPossibleLocation());

		updateMessage();
		notice();
	}

	// 使用道具
	public void useEquipment(int number) throws RemoteException
	{
		switch (number)
		{
		case 3:
			DOUBLEHITTIME = 2000;
			System.out.println("使用道具3");
			break;
		case 4:
			if (supermod)
			{
				GRADEPERCENTAGE = GRADEPERCENTAGE + 0.2;
			} else
			{
				GRADEPERCENTAGE = GRADEPERCENTAGE + 0.1;
			}
			System.out.println("使用道具4");
			break;
		case 5:
			HELPTIME = 2000;
			System.out.println("使用道具5");
			break;
		default:
			System.out.println("我擦 什么新鲜玩意");
			break;
		}
	}

	// 使用道具A、B
	public boolean useEquipment(int x, int y) throws RemoteException
	{
		if (direction == 1)
		{
			System.out.println("前输入：" + x + "," + y);
			int ox = x;
			int oy = y;
			x = 8 - oy;
			y = ox;
			System.out.println("置换输入：" + x + "," + y);
		}
		if (map.useEquipment(x, y))
		{
			System.out.println("使用了道具");
			this.message = map.getMessage();
			updateMessage();
			notice();
			return true;
		} else
		{
			return false;
		}
	}

	// 用于游戏后更新数据库
	private void updateResult() throws RemoteException
	{
		if (player.equals("Visitor"))
		{
			return;
		}
		System.out.println("记录历史中");
		// System.out.println("Score: "+score);
		// System.out.println("maxHIT: "+maxHit);
		PlayerLogicController plc = new PlayerLogicController();
		GamePO temp = new GamePO();
		temp.gameNO = gameID + "";
		temp.combo = maxHit;
		temp.playerID = player;
		temp.score = score;
		System.out.println(temp.score);
		System.out.println(plc.saveHistory(temp));

		PlayerPO player = plc.getPlayer(this.player);
		player.money += (int) (score / 100);
		plc.updatePlayer(player);

	}

	// 提示客户端进行数据更新
	public void notice()
	{
		try
		{
			listener.update();
		} catch (RemoteException e)
		{
			offLine();
		}
	}

	// 添加监听者
	public void addListener(Listener l, String ID) throws RemoteException
	{
		player = ID;
		listener = l;
	}

	// 删除监听者 作用待定
	public void removeListener(String ID) throws RemoteException
	{
		listener = null;
	}

	// 断线处理
	private void offLine()
	{
		count++;
		if (count > DROPCOUNT)
		{
			playerOffline(player);
		}
	}

	// 检查是否结束
	public boolean checkFinish()
	{
		return finish;
	}

	// 获取分数
	public int getScore(String ID) throws RemoteException
	{
		return score;
	}

	// 检查游戏是否结束
	public boolean checkPlaying()
	{
		return finish;
	}

	private void playerOffline(String id)
	{
		finish = true;
		try
		{
			updateResult();
		} catch (RemoteException e)
		{
			;
		}
	}

	@Override
	public void shutdownGame(String id) throws RemoteException
	{
		playerOffline(id);
	}

	// 提示时间监听
	class helpListener implements Runnable
	{
		public void run()
		{
			while (!finish)
			{
				long now = System.currentTimeMillis();
				if (now - operateTime > HELPTIME)
				{
					offerHelp();
					System.out.println("进行提示");
					operateTime = System.currentTimeMillis();
				}
			}
		}
	}

	// 连击监听
	class hitListener implements Runnable
	{
		public void run()
		{
			recordTime = System.currentTimeMillis();
			while (!finish)
			{
				long now = System.currentTimeMillis();
				if (now - recordTime > DOUBLEHITTIME)
				{
					recordTime = System.currentTimeMillis();
					doublehit = 0;
					System.out.println("连击归0");
					message = new Message();
					message.imformation = "cancel combo";

					updateMessage();
					notice();

					continue;
				}

				try
				{
					Thread.sleep(50);
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			System.out.println("退出hit模式监听");
		}
	}

	// 游戏时间监听
	class endListener implements Runnable
	{
		public void run()
		{
			System.out.println("开始计时");
			while (!finish)
			{
				long now = System.currentTimeMillis();
				if (now - gameStartTime > FINISHTIME)
				{
					message = new Message();
					message.imformation = "End";
					Integer[][] temple = new Integer[9][9];
					temple[0][0] = score;
					message.list.add(temple);

					notice();

					finish = true;
					try
					{
						updateResult();
					} catch (RemoteException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				try
				{
					Thread.sleep(50);
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			System.out.println("停止时间计时");
		}
	}

	// 超级模式监听
	class superListener implements Runnable
	{
		long startTime;

		public superListener()
		{
			startTime = System.currentTimeMillis();
		}

		public void run()
		{
			System.out.println("进入Super模式");
			while (!finish)
			{
				long now = System.currentTimeMillis();
				if (doublesupermod)
				{
					startTime = now;
					doublesupermod = false;
					continue;
				}
				if (now - startTime > SUPERTIME)
				{
					GRADEPERCENTAGE = GRADEPERCENTAGE / 2;
					System.out.println("退出超级模式");
					message = new Message();
					message.imformation = "cancel super";
					supermod = false;

					updateMessage();
					notice();

					break;
				}

				try
				{
					Thread.sleep(50);
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			System.out.println("退出Super模式监听");
		}
	}

	public void updateMessage()
	{
		if (direction == 1)
		{
			for (Integer[][] list : this.message.list)
			{
				int[][] temp = new int[9][9];
				for (int i = 0; i < 9; i++)
				{
					for (int j = 0; j < 9; j++)
					{
						temp[i][j] = list[i][j];
					}
				}
				for (int i = 0; i < 9; i++)
				{
					for (int j = 0; j < 9; j++)
					{
						list[i][j] = temp[8 - j][i];
					}
				}
			}
		}
		message.combo = this.doublehit;
		message.supermod = this.supermod;
	}

}
