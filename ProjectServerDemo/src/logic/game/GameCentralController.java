package logic.game;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import po.GameSettingPO;
import ui.game.GameUIService;

public class GameCentralController extends UnicastRemoteObject implements GameService 
{
	private static GameCentralController gcc=null;
	private static int count=0;
	ArrayList<SingleGameController> singlelist = new ArrayList<SingleGameController>();
	ArrayList<CoopGameController> cooplist = new ArrayList<CoopGameController>();
	//final int MAXSINGLE=20;
	//final int MAXCOOP=20;
	//SingleGameController singlelist[] = new SingleGameController[MAXSINGLE];
	//CoopGameController cooplist[] = new CoopGameController[MAXSINGLE];
	
	private GameCentralController() throws RemoteException
	{
		
	}
	
	public static GameCentralController getGameCentralController() throws RemoteException
	{
		if(count==0)
		{
			count++;
			gcc = new GameCentralController();
		}
		return gcc;
	}
	
	public int startSingleGame() throws RemoteException
	{
		for(int i=0;i<singlelist.size();i++)
		{
			if(singlelist.get(i).checkFinish())
			{
				singlelist.set(i, new SingleGameController(i));
				return i;
			}
		}
		singlelist.add(new SingleGameController(singlelist.size()));
		return singlelist.size()-1;
	}
	
	public int startCoopGame() throws RemoteException
	{
		for(int i=0;i<cooplist.size();i++)
		{
			if(cooplist.get(i).checkFinish())
			{
				cooplist.set(i, new CoopGameController(i));
				return i;
			}
		}
		cooplist.add(new CoopGameController(singlelist.size()));
		return cooplist.size()-1;
	}
	
	public GameLogicService getSingleGame(int number)
	{
		return singlelist.get(number);
	}
	
	public GameLogicService getCoopGame(int number)
	{
		return cooplist.get(number);
	}

	public GameLogicService startSingleGame(GameSettingPO setting)
			throws RemoteException
	{
		
		SingleGameController sgc = null;
		for(int i=0;i<singlelist.size();i++)
		{
			if(singlelist.get(i).checkFinish())
			{
				sgc = new SingleGameController(i,setting);
				singlelist.set(i, sgc);
				return sgc;
			}
			
		}
		sgc = new SingleGameController(singlelist.size(),setting);
		singlelist.add(sgc);
		return sgc;
		
	}

	public GameLogicService startCoopGame(GameSettingPO setting)
			throws RemoteException
	{
		CoopGameController sgc = null;
		for(int i=0;i<cooplist.size();i++)
		{
			if(cooplist.get(i).checkFinish())
			{
				sgc = new CoopGameController(i,setting);
				cooplist.set(i, sgc);
				return sgc;
			}
			
		}
		sgc = new CoopGameController(cooplist.size(),setting);
		cooplist.add(sgc);
		return sgc;
	}

}
