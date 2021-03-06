package rmi;

import java.io.File;
import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import javax.swing.JOptionPane;

import logic.admin.AdminLogicService;
import logic.equipment.EquipmentLogicService;
import logic.equipment.StoreLogicService;
import logic.game.GameService;
import logic.netcore.NetcoreLogicService;
import logic.player.PlayerLogicService;


public class RMIController 
{
	Registry registry;
	Registry localregistry;
	public RMIController()
	{
		try
		{
			File f = new File("IP.txt");
			if(!f.exists())
			{
				f.createNewFile();
			}
			Scanner s = new Scanner(f);
			String IP = s.nextLine();
			
			registry = LocateRegistry.getRegistry(IP, 8888);
			localregistry = LocateRegistry.createRegistry((int) (6666+(Math.random()*1000)));
		} catch (RemoteException e)
		{
			JOptionPane.showMessageDialog(null,"连接不到服务器！");
			e.printStackTrace();
		} catch (IOException e) 
		{
			e.printStackTrace();
		} 
	}

	public boolean createRMIService(String name,Object o)
	{
		try 
		{
			localregistry.rebind(name, (Remote) o);
		} catch (AccessException e) 
		{
			e.printStackTrace();
			return false;
		} catch (RemoteException e) 
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public GameService getGameService()
	{
		GameService result = null;
		try
		{
			result = (GameService) registry.lookup("game");
		} catch (RemoteException | NotBoundException e)
		{
			JOptionPane.showMessageDialog(null,"连接不到服务器！");
			e.printStackTrace();
		}
		return result;
	}
	
	public NetcoreLogicService getNetworkService()
	{
		NetcoreLogicService result = null;
		try
		{
			result = (NetcoreLogicService) registry.lookup("netcore");
		} catch (RemoteException | NotBoundException e)
		{
			JOptionPane.showMessageDialog(null,"连接不到服务器！");
			e.printStackTrace();
		}
		return result;
	}
	
	public EquipmentLogicService getEquipmentService()
	{
		EquipmentLogicService result = null;
		try
		{
			result = (EquipmentLogicService) registry.lookup("equipment");
		} catch (RemoteException | NotBoundException e)
		{
			JOptionPane.showMessageDialog(null,"EquipmentError");
			e.printStackTrace();
		}
		return result;
	}
	public AdminLogicService getAdminService()
	{
		AdminLogicService result = null;
		try
		{
			result = (AdminLogicService) registry.lookup("admin");
		} catch (RemoteException | NotBoundException e)
		{
			JOptionPane.showMessageDialog(null,"AdminError");
			e.printStackTrace();
		}
		return result;
	}
	
	public PlayerLogicService getPlayerService()
	{
		PlayerLogicService result = null;
		try
		{
			result = (PlayerLogicService) registry.lookup("player");
		} catch (RemoteException | NotBoundException e)
		{
			JOptionPane.showMessageDialog(null,"PlayerError");
			e.printStackTrace();
		}
		return result;
	}
	
	public StoreLogicService getStoreService()
	{
		StoreLogicService result = null;
		try
		{
			result = (StoreLogicService) registry.lookup("store");
		} catch (RemoteException | NotBoundException e)
		{
			JOptionPane.showMessageDialog(null,"StoreError");
			e.printStackTrace();
		}
		return result;
	}
	
}
