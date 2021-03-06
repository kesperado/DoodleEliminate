package ui.game.single;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import rmi.Listener;
import rmi.RMIController;

public class SingleGameListener extends UnicastRemoteObject implements Listener {
	private static final long serialVersionUID = 1L;
	SingleGamePanel aim;
	boolean newStatement;
	boolean finish;

	public SingleGameListener(SingleGamePanel singleFrame)
			throws RemoteException {
		super();
		this.aim = singleFrame;

		RMIController rmi = new RMIController();
		rmi.createRMIService("gamelistener", this);
		clientListener cl = new clientListener();
		cl.start();
	}

	public void update() {
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

					aim.alert();
				}

				if (finish) {
					System.out.println("警报解除，继续solo");
					break;
				}

			}
		}

	}

}
