package logic.netcore;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import po.BattleRoomPO;
import po.GameSettingPO;
import po.RoomPO;
import rmi.Listener;


public interface NetcoreLogicService extends Remote
{
	public ArrayList<RoomPO> getRoomList() throws RemoteException;
	
	public RoomPO getRoomInformation(int number) throws RemoteException;
	public RoomPO createRoom(String playerID,Listener l) throws RemoteException;
	public RoomPO joinRoom(int roomID, String playerID,Listener l) throws RemoteException;
	
	public boolean quitRoom(int roomID,String playerID) throws RemoteException;
	public boolean register(int roomID,String playerID,Listener l) throws RemoteException;
	public boolean unregister(int roomID,String playerID) throws RemoteException;
	public boolean startGame(int roomID,String ID,GameSettingPO setting) throws RemoteException;
	
	public void useEquipment(int roomID,int number) throws RemoteException;

	public BattleRoomPO getBattleRoomInformation() throws RemoteException;
	public BattleRoomPO getBattleRoomInformation(int number) throws RemoteException;
	public BattleRoomPO createBattleRoom(String playerID,Listener l) throws RemoteException;
	public BattleRoomPO joinBattleRoom(int roomID, String playerID,Listener l) throws RemoteException;
	
	public boolean selectTeam(String playerID,int teamnumber) throws RemoteException;
	public boolean quitBattleRoom(int roomID,String playerID) throws RemoteException;
	public boolean registerBattleRoom(int roomID,String playerID,Listener l) throws RemoteException;
	public boolean unregisterBattleRoom(int roomID,String playerID) throws RemoteException;
	public boolean startBattleGame(int roomID,String ID,GameSettingPO setting) throws RemoteException;
	
	//public boolean startSingleGame(String roomid) throws RemoteException;
}
