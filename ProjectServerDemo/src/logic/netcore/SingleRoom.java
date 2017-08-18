package logic.netcore;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map.Entry;

import logic.equipment.EquipmentLogicController;
import logic.game.GameCentralController;
import logic.game.GameLogicService;
import po.GameSettingPO;
import po.RoomPO;
import rmi.Listener;

/**
 * @author 作者 :knox
 * @version 创建时间：2014-5-15 上午9:21:57 类说明
 */
public class SingleRoom
{
	// 最大玩家数
	private int MAX_NUMBER = 4;
	// 房间号
	private int id;
	// 房主
	private String roomMaster;
	// 成员列表
	private HashMap<String, Boolean> players = new HashMap<String, Boolean>();
	// 监视者列表
	private HashMap<String, Listener> roomListeners = new HashMap<String, Listener>();
	// 成员监听列表
	private HashMap<String, Listener> gamelisteners = new HashMap<String, Listener>();
	// 游戏编号
	private int gameno = -1;
	// 游戏对象
	private GameLogicService gls;
	// 游戏标志
	private boolean start;
	// 存货标志
	private boolean alive;

	private GameSettingPO setting = null;
	
	public SingleRoom(int number) throws RemoteException
	{
		this.id = number;
		this.alive = true;
		this.start = false;
	}

	public SingleRoom(int number, String playerID, Listener l)
			throws RemoteException
	{
		this.id = number;
		this.alive = true;
		this.start = false;
		this.roomMaster = playerID;

		this.players.put(playerID, false);
		this.roomListeners.put(playerID, l);
		
		Thread t = new Thread(new aliveTester());
		t.start();
	}

	// 获取房间id
	public int getID()
	{
		return id;
	}

	// 获取房间id
	public boolean getAlive()
	{
		return alive;
	}

	// 加入玩家（玩家加入房间）
	public boolean addPlayer(String ID, Listener game) throws RemoteException
	{
		System.out.println("有人加入");
		if (players.size() == MAX_NUMBER)
		{
			return false;
		}
		if(start)
		{
			return false;
		}
		players.put(ID, false);
		roomListeners.put(ID, game);
		notice();

		return true;
	}

	// 玩家准备（玩家正式加入到游戏，既准备）
	public boolean addListener(String playerID, Listener l)
			throws RemoteException
	{
		
		if (!players.containsKey(playerID))
		{
			return false;
		}
		players.put(playerID, true);
		gamelisteners.put(playerID, l);
		System.out.println(players.size());
		
		notice();
		return true;
	}

	// 删除游戏Listener，用于取消准备状态
	public boolean removeListener(String playerID) throws RemoteException
	{
		if (!players.containsKey(playerID))
		{
			return false;
		}

		players.put(playerID, false);
		gamelisteners.remove(playerID);

		notice();
		return true;
	}

	// 删除玩家（用于玩家退出房间）
	public boolean deletePlayer(String playerID) throws RemoteException
	{
		System.out.println(playerID + "请求退出房间");
		if (!players.containsKey(playerID))
		{
			return false;
		}

		if (roomMaster.equals(playerID))
		{
			if (players.size() != 1)
			{
				if (players.get(playerID))
				{
					gamelisteners.remove(playerID);
				}
				roomListeners.remove(playerID);
				players.remove(playerID);
				roomMaster = players.keySet().iterator().next();
			} else
			{
				this.alive = false;
				this.roomMaster=null;
				System.out.println("本房间挂掉");
				return true;
			}
		}else
		{
			if (players.get(playerID))
			{
				gamelisteners.remove(playerID);
			}
			roomListeners.remove(playerID);
			players.remove(playerID);
		}
		
		System.out.println("新房主"+roomMaster);
		System.out.println(roomListeners.size());
		
		if(!start)
		{
			notice();
		}
		
		return true;
	}

	// 开始游戏
	public boolean startGame(String playerid, GameSettingPO setting)
			throws RemoteException
	{
		if(start)
		{
			return false;
		}
		boolean ready = true;
		for (Boolean b : players.values())
		{
			ready = ready && b;
		}
		
		if (roomMaster.equals(playerid) && ready && gamelisteners.size() > 1)
		{
			gameStart(setting);
			return true;
		} else
		{
			return false;
		}
	}

	//开始游戏的一堆设置
	private void gameStart(GameSettingPO setting) throws RemoteException
	{
		GameCentralController gcc = GameCentralController
				.getGameCentralController();
		this.gls = gcc.startCoopGame(setting);
		this.start = true;
		this.setting=setting;
		
		gls.startGame();
		EquipmentLogicController elc = new EquipmentLogicController();
		if(setting.third)
		{
			
			setting.third=elc.useEquipment(roomMaster, "C");
		}
		if(setting.fourth)
		{
			setting.fourth=elc.useEquipment(roomMaster, "D");
		}
		if(setting.fifth)
		{
			setting.fifth=elc.useEquipment(roomMaster, "E");
		}
		
		for(Entry<String,Listener> e:gamelisteners.entrySet())
		{
			this.gls.addListener(e.getValue(), e.getKey());
		}
		for (String s : players.keySet())
		{
			players.put(s, false);
		}
		Thread t = new Thread(new finishListener());
		t.start();
		notice();
	}

	// 通知所有本room玩家在room信息变更时进行更新
	private void notice() 
	{
		for(String s:roomListeners.keySet())
		{
			try
			{
				roomListeners.get(s).update();
			} catch (RemoteException e)
			{
				try
				{
					deletePlayer(s);
				} catch (RemoteException e1)
				{
					;
				}
			}
		}
	}

	// 获取房间信息
	public RoomPO getRoomInformation() throws RemoteException
	{
		RoomPO result = new RoomPO();
		result.id = this.id;
		result.roomMaster = this.roomMaster;
		result.setting=this.setting;
		result.players = (HashMap<String, Boolean>) players.clone();
		result.gls = this.gls;
		result.start = this.start;
		return result;
	}

	
	// 使用道具 废弃
	public void useEquipment(int number) throws RemoteException
	{
		gls.useEquipment(number);
	}
	class finishListener implements Runnable
	{
		public void run()
		{
			while(true)
			{
				try
				{
					if(gls.checkPlaying())
					{
						break;
					}
					Thread.sleep(50);

				} catch (RemoteException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("结束");
			start=false;
		}
	}
	
	class aliveTester implements Runnable
	{
		public void run()
		{
			while(alive)
			{
				if(!start)
				{
					notice();
				}
				
				System.out.println("沉睡中");
				try
				{
					Thread.sleep(10000);
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("结束");
			start=false;
		}
	}
}
    