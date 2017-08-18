import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import logic.admin.AdminLogicController;
import logic.equipment.EquipmentLogicController;
import logic.equipment.StoreLogicController;
import logic.game.GameCentralController;
import logic.netcore.NetcoreLogicController;
import logic.player.PlayerLogicController;

public class Enterence 
{
	public static void main(String[] args)
	{
		try 
		{ 
			Registry registry = LocateRegistry.createRegistry(8888);
			NetcoreLogicController network = new NetcoreLogicController();
			GameCentralController game = GameCentralController.getGameCentralController();
			
		    AdminLogicController admin = new AdminLogicController();
		    EquipmentLogicController equipment = new EquipmentLogicController();
		    PlayerLogicController player = new PlayerLogicController();
		    StoreLogicController store = new StoreLogicController();
		    
		    
		    registry.bind("netcore", network);
		    registry.bind("game", game);
		    registry.bind("player", player);
		    registry.bind("store", store);
		    registry.bind("admin", admin);
		    registry.bind("equipment", equipment);
//		    registry.bind("logic", logic);
        } catch (RemoteException | AlreadyBoundException e) 
        { 
            System.out.println("RMI远程连接错误"); 
            e.printStackTrace(); 
        } 
	}
}
