package logic.game;

import java.rmi.Remote;
import java.rmi.RemoteException;

import rmi.Listener;

public interface GameLogicService extends Remote 
{
	public void startGame() throws RemoteException;
	public void shutdownGame(String id) throws RemoteException;
	public void useEquipment(int number)throws RemoteException;
	public void removeListener(String ID) throws RemoteException;
	public void addListener(Listener l ,String ID) throws RemoteException;
	
	public int[][] getStart()throws RemoteException;
	public int getScore(String ID) throws RemoteException;
	public int exchange(int x1,int y1,int x2,int y2,String ID)throws RemoteException;
	
	public boolean checkPlaying() throws RemoteException;;
	public boolean useEquipment(int x,int y) throws RemoteException;

	public Message getMessage() throws RemoteException;
	public Message getMessage(String ID) throws RemoteException;
}
