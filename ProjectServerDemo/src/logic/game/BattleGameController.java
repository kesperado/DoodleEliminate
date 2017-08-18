package logic.game;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

import logic.player.PlayerLogicController;
import po.GamePO;
import po.GameSettingPO;
import po.PlayerPO;
import rmi.Listener;

public class BattleGameController extends UnicastRemoteObject implements
		GameLogicService
{
	// 游戏ID
	private int gameID;
	// 连击计数
	private int doublehit = 0;
	// 分数
	private HashMap<String, Integer> scoreboard = new HashMap<String, Integer>();
	// 监视者
	private HashMap<String, Listener> listenerlist = new HashMap<String, Listener>();
	// message集合
	private HashMap<String, Message> messagelist = new HashMap<String, Message>();

	private HashMap<String, Integer> offlineCount = new HashMap<String, Integer>();

	int maxHit = 0;
	// 连击时间
	private int DOUBLEHITTIME = 1000;
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
	// 信息准备状态
	private boolean messageready;
	// 游戏方向
	private int direction = 0;

	private int DROPCOUNT = 0;
	
	Message message;
	GameBoard map;

	// 构造器
	public BattleGameController(int ID) throws RemoteException
	{
		super();

		this.gameID = ID;
		this.finish = false;
		this.supermod = false;
		this.doublesupermod = false;
		this.map = new GameBoard();

		System.out.println("COOP Game has created" + ID + " ");
	}

	// 有设置的构造器
	public BattleGameController(int ID, GameSettingPO setting)
			throws RemoteException
	{
		this(ID);

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
		this.direction = setting.direction;

		//startGame();
	}

	// 开始游戏各种计时 游戏开始
	public void startGame() throws RemoteException
	{
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
		while (!map.getReady())
			;
		int[][] currentmap = map.getMap();
		int[][] temp = new int[9][9];
		if (direction == 1)
		{
			for (int i = 0; i < 9; i++)
			{
				for (int j = 0; j < 9; j++)
				{
					temp[i][j] = currentmap[8 - j][i];
				}
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
		System.out.println(ID + "玩家进行了交换操作");
		int singlescore = map.doEliminate(x1, y1, x2, y2);
		System.out.println("得分" + singlescore * GRADEPERCENTAGE);
		if (singlescore >= 0)
		{
			recordTime = System.currentTimeMillis();// 用于连击时间修改
			int oldscore = scoreboard.get(ID);
			singlescore = (int) (singlescore * GRADEPERCENTAGE);
			scoreboard.put(ID, oldscore + singlescore);
			doublehit++;
			if (doublehit >= maxHit)
			{
				maxHit = doublehit;
			}
			if (doublehit > 0 && (doublehit % 4 == 0))
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
			// 生成返回Message
			Message temp = map.getMessage();
			Message result = new Message();
			result.imformation = "Moved";
			Integer[][] list = new Integer[9][9];
			for (int i = 0; i < 9; i++)
			{
				for (int j = 0; j < 9; j++)
				{
					list[i][j] = 0;
				}
			}
			list[x1][y1] = 1;
			list[x2][y2] = 1;
			result.list = (ArrayList<Integer[][]>) temp.list.clone();
			result.list.add(0, list);

			for (String player : messagelist.keySet())
			{
				if (player.equals(ID))
				{
					messagelist.put(player, map.getMessage());
				} else
				{
					messagelist.put(player, result.clone());
				}
			}
			System.out.println(messagelist.size());
			updateMessage();
			notice();
		}

		return singlescore;
	}

	// 获取更新信息
	public Message getMessage(String ID) throws RemoteException
	{

		return messagelist.get(ID);
	}

	// 提示功能
	private void offerHelp()
	{
		Message message = new Message();
		message.imformation = "Help";
		message.list.add(map.getPossibleLocation());
		for (String s : listenerlist.keySet())
		{
			messagelist.put(s, message.clone());
		}
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
			int ox = x;
			int oy = y;
			x = 8 - oy;
			y = ox;
		}
		if (map.useEquipment(x, y))
		{
			for (String s : messagelist.keySet())
			{
				messagelist.put(s, map.getMessage());
			}
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
		for (String s : scoreboard.keySet())
		{
			updatePersonResult(s);
		}
	}

	// 更新个人游戏信息
	private void updatePersonResult(String s) throws RemoteException
	{
		PlayerLogicController plc = new PlayerLogicController();
		GamePO temp = new GamePO();
		temp.gameNO = gameID + "";
		temp.combo = maxHit;
		temp.playerID = s;
		temp.score = scoreboard.get(s);
		System.out.println(temp.score);
		plc.saveHistory(temp);

		PlayerPO temple = plc.getPlayer(s);
		temple.money += (int) (scoreboard.get(s) / 100);
		plc.updatePlayer(temple);
	}

	// 提示客户端进行数据更新
	public void notice()
	{
		for (String s : listenerlist.keySet())
		{
			try
			{
				listenerlist.get(s).update();
			} catch (RemoteException e)
			{
				offLine(s);
			}
		}
	}

	// 断线处理
	private void offLine(String id)
	{
		int count=offlineCount.get(id);
		count++;
		if (count >= DROPCOUNT)
		{
			playerOffline(id);
		}else
		{
			offlineCount.put(id, count);
		}
	}
	
	// 添加监听者
	public void addListener(Listener l, String ID) throws RemoteException
	{
		listenerlist.put(ID, l);
		scoreboard.put(ID, 0);
		messagelist.put(ID, null);
		offlineCount.put(ID, 0);
		System.out.println("新玩家加入" + ID);
		// notice();
	}

	// 删除监听者 作用待定
	public void removeListener(String ID) throws RemoteException
	{
		listenerlist.remove(ID);
		scoreboard.remove(ID);
		messagelist.remove(ID);
	}

	// 检查是否结束
	public boolean checkFinish()
	{
		return finish;
	}

	// 获取分数
	public int getScore(String ID) throws RemoteException
	{
		return scoreboard.get(ID);
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

					Message message = new Message();
					message.imformation = "cancel combo";

					for (String s : messagelist.keySet())
					{
						messagelist.put(s, message.clone());
					}
					notice();
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
					System.out.println("GameOver");
					Message m = new Message();
					m.imformation = "End";
					for (String s : messagelist.keySet())
					{
						Message temp = m.clone();
						Integer[][] temple = new Integer[9][9];
						temple[0][0]=scoreboard.get(s);
						temp.list.add(temple);
						
						messagelist.put(s, temp);
					}

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
					try
					{
						Thread.sleep(50);
					} catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
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
					Message message = new Message();
					message.imformation = "cancel super";
					for (String s : messagelist.keySet())
					{
						messagelist.put(s, message.clone());
					}
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
			System.out.println("退出Super模式");
		}
	}

	@Override
	public boolean checkPlaying() throws RemoteException
	{
		return finish;
	}

	public void updateMessage()
	{
		for (Message m : messagelist.values())
		{
			if (direction == 1)
			{
				for (Integer[][] list : m.list)
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
			m.combo = this.doublehit;
			m.supermod = this.supermod;
		}
	}

	@Override
	// 已废弃
	public Message getMessage() throws RemoteException
	{
		return null;
	}

	private void playerOffline(String id)
	{
		try
		{
			updatePersonResult(id);
			listenerlist.remove(id);
			scoreboard.remove(id);
			messagelist.remove(id);
			offlineCount.remove(id);
		} catch (RemoteException e)
		{
			;
		}
		
		if (listenerlist.size() == 0)
		{
			finish = true;
		} else
		{
			Message m = new Message();
			m.imformation = "missing";
			for (String s : messagelist.keySet())
			{
				messagelist.put(s, m.clone());
			}
			notice();
		}
	}

	@Override
	public void shutdownGame(String id) throws RemoteException
	{
		playerOffline(id);
	}

}