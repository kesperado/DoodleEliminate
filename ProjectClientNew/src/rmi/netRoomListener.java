package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import ui.RoomPanel;

public class netRoomListener extends UnicastRemoteObject implements Listener
{
	RoomPanel aim;
	boolean newStatement;
	
	public netRoomListener(RoomPanel aim) throws RemoteException
	{
		super();
		
		RMIController rmi = new RMIController();
		rmi.createRMIService("roomlistener", this);
		
		this.aim=aim;
		this.newStatement = false;
		
		Thread t = new newStatementListener();
		//t.start();
	}
	
	public void update() throws RemoteException
	{
		aim.alert();
		System.out.println("you xiao xi");
		newStatement=true;
	}

	public void stop() throws RemoteException
	{
		
	}
	class newStatementListener extends Thread
	{
		public newStatementListener()
		{
			super();
		}
		
		public void run()
		{
			while(true)
			{
				
				if(newStatement)
				{
					System.out.println("有消息");
					newStatement = false;
					aim.alert();
				}
			}
		}
	}
}
