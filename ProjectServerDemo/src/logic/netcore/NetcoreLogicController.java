package logic.netcore;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import logic.game.SingleGameController;
import po.BattleRoomPO;
import po.GameSettingPO;
import po.RoomPO;
import rmi.Listener;


public class NetcoreLogicController extends UnicastRemoteObject implements NetcoreLogicService
{
	private static int NUMBER = 10;
	//SingleRoom roomlist[] = new SingleRoom[NUMBER];
	ArrayList<SingleRoom> roomlist = new ArrayList<SingleRoom>();
	
	public NetcoreLogicController() throws RemoteException 
	{
		super();
	}

	//客户端获取服务器所有房间信息
	public ArrayList<RoomPO> getRoomList() throws RemoteException
	{
		ArrayList<RoomPO> result = new ArrayList<RoomPO>();
		for(SingleRoom s:roomlist)
		{
			if(s.getAlive())
			{
				result.add(s.getRoomInformation());
			}
		}
		return result;
	}

	//客户端获取服务器房间详细信息
	public RoomPO getRoomInformation(int number) throws RemoteException
	{
		return getRoom(number).getRoomInformation();
	}

	public RoomPO joinRoom(int roomID, String playerID,Listener l) throws RemoteException
	{
		SingleRoom aim = getRoom(roomID);
		if (aim.addPlayer(playerID,l))
		{
			return aim.getRoomInformation();
		}else
		{
			return null;
		}
		
	}

	public boolean quitRoom(int roomID, String playerID) throws RemoteException
	{
		return getRoom(roomID).deletePlayer(playerID);
	}

	public boolean register(int roomID, String playerID, Listener l)
			throws RemoteException
	{
		return getRoom(roomID).addListener(playerID, l);
	}

	public boolean unregister(int roomID, String playerID)
			throws RemoteException
	{
		return getRoom(roomID).removeListener(playerID);
	}

	public boolean startGame(int roomID,String ID,GameSettingPO setting) throws RemoteException
	{
		return getRoom(roomID).startGame(ID,setting);
	}


	public RoomPO createRoom(String playerID,Listener l) throws RemoteException
	{
		SingleRoom sr = null;
		for(int i=0;i<roomlist.size();i++)
		{
			if(!roomlist.get(i).getAlive())
			{
				sr = new SingleRoom(i,playerID,l);
				roomlist.set(i, sr);
				return sr.getRoomInformation();
			}
			
		}
		sr = new SingleRoom(roomlist.size(),playerID,l);
		roomlist.add(sr);
		return sr.getRoomInformation();
	}

	//使用道具
	public void useEquipment(int roomID,int number) throws RemoteException
	{
		getRoom(roomID).useEquipment(number);
	}
	//
	private SingleRoom getRoom(int number)
	{
		for(SingleRoom s:roomlist)
		{
			if(s.getID()==number)
			{
				return s;
			}
		}
		return null;
	}

	@Override
	public BattleRoomPO getBattleRoomInformation() throws RemoteException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BattleRoomPO getBattleRoomInformation(int number)
			throws RemoteException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BattleRoomPO createBattleRoom(String playerID, Listener l)
			throws RemoteException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BattleRoomPO joinBattleRoom(int roomID, String playerID, Listener l)
			throws RemoteException
	{
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean quitBattleRoom(int roomID, String playerID)
			throws RemoteException
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean registerBattleRoom(int roomID, String playerID, Listener l)
			throws RemoteException
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean unregisterBattleRoom(int roomID, String playerID)
			throws RemoteException
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean startBattleGame(int roomID, String ID, GameSettingPO setting)
			throws RemoteException
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean selectTeam(String playerID, int teamnumber)
			throws RemoteException
	{
		// TODO Auto-generated method stub
		return false;
	}
}
