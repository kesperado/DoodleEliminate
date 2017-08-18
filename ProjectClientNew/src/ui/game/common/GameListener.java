package ui.game.common;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import rmi.Listener;
import rmi.RMIController;
import ui.game.common.GamePanel;

public class GameListener extends UnicastRemoteObject implements Listener {
	private static final long serialVersionUID = 1L;
	GamePanel aim;
	boolean newStatement;
	boolean finish;

	public GameListener() throws RemoteException
	{
		super();
	}
	
	public GameListener(GamePanel singleFrame)
			throws RemoteException {
		this();
		setAim(singleFrame);

	}
	
	public void setAim(GamePanel singleFrame)
	{
		this.aim = singleFrame;

		RMIController rmi = new RMIController();
		rmi.createRMIService("gamelistener", this);
		clientListener cl = new clientListener();
		cl.start();
	}

	public void update() {
		//aim.alert();
		newStatement = true;
	}

	public void stop() throws RemoteException {
		finish = true;
	}

	class clientListener extends Thread {

		public clientListener() {
			super();
			finish = false;

			System.out.println("gouzao");
		}

		public void run() {
			while (true) {
				if (newStatement == true) {
					newStatement = false;
					System.out.println("收到信号");
					aim.alert();
				}

				if (finish) {
					System.out.println("警报解除，继续solo");
					break;
				}
				try
				{
					sleep(50);
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	}

}
