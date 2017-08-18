package logic.game;

import java.rmi.Remote;
import java.rmi.RemoteException;

import po.GameSettingPO;

public interface GameService extends Remote
{
	public int startSingleGame() throws RemoteException;
	public int startCoopGame() throws RemoteException;
	public GameLogicService startSingleGame(GameSettingPO setting) throws RemoteException;
	public GameLogicService startCoopGame(GameSettingPO setting) throws RemoteException;
	public GameLogicService getSingleGame(int number)throws RemoteException;
	public GameLogicService getCoopGame(int number)throws RemoteException;
}
