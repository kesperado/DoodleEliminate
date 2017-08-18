package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Listener extends Remote 
{
	public void update() throws RemoteException;
	public void stop() throws RemoteException;
}
