package logic.equipment;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import logic.player.PlayerLogicController;
import logic.player.PlayerLogicService;
import po.EquipmentPO;
import po.PlayerPO;
import po.StorePO;
import data.equipment.EquipmentDataController;
import data.equipment.EquipmentDataService;
import data.player.PlayerDataController;
import data.player.PlayerDataService;
import data.store.StoreDataController;
import data.store.StoreDataService;

public class EquipmentLogicController extends UnicastRemoteObject implements EquipmentLogicService {

	EquipmentDataService edc;
	StoreDataService sdc;
	PlayerDataService pdc;
	StoreLogicService slc;
	PlayerLogicService plc;
	
	public EquipmentLogicController() throws RemoteException{
		super();
		edc = new EquipmentDataController();
		sdc = new StoreDataController();
		pdc = new PlayerDataController();
		slc = new StoreLogicController();
		plc = new PlayerLogicController();
	}
	
	@Override
	public ArrayList<EquipmentPO> getList(String playerID) throws RemoteException {
		// TODO Auto-generated method stub
		EquipmentPO po = new EquipmentPO();
		po.playerID = playerID;
		ArrayList<EquipmentPO> list = edc.getEquipment(po);
		ArrayList<EquipmentPO> relist = new ArrayList<EquipmentPO>();
		String str1 = "C";
		String str2 = "D";
		String str3 = "E";
		EquipmentPO equip1 = new EquipmentPO();
		EquipmentPO equip2 = new EquipmentPO();
		EquipmentPO equip3 = new EquipmentPO();
		for(EquipmentPO equip : list) {
			if(equip.type.equals(str1))
				equip1 = equip;
			if(equip.type.equals(str2))
				equip2 = equip;
			if(equip.type.equals(str3))
				equip3 = equip;
		}
		System.out.println(equip1.num);
		System.out.println(equip2.num);
		System.out.println(equip3.num);
		if(equip1.type.equals("")) {
			equip1.num = 0;
			equip1.playerID = playerID;
			equip1.type = str1;
		}
		if(equip2.type.equals("")) {
			equip2.num = 0;
			equip2.playerID = playerID;
			equip2.type = str2;
		}
		if(equip3.type.equals("")) {
			equip3.num = 0;
			equip3.playerID = playerID;
			equip3.type = str3;
		}
		relist.add(equip1);
		relist.add(equip2);
		relist.add(equip3);
		return relist;
	}

	@Override
	public boolean sellSingleEquipment(String playerID,String equipmentID) throws RemoteException {
		// TODO Auto-generated method stub
		EquipmentPO epo = new EquipmentPO();
		epo.playerID = playerID;
		epo.type = equipmentID;
		boolean whether = edc.deleteEquipment(epo);
		StorePO spo = new StorePO();
		spo.equipmentType = equipmentID;
		int money = sdc.getStore(equipmentID).price;
		PlayerPO ppo = pdc.getPlayer(playerID);
		ppo.money = ppo.money - money;
		whether = whether & pdc.updatePlayer(ppo);
		return whether;
	}

	@Override
	public boolean useEquipment(String playerID, String type) throws RemoteException {
		// TODO Auto-generated method stub
		EquipmentPO epo = new EquipmentPO();
		epo.playerID = playerID;
		epo.type = type;
		epo.num = 1;
		return edc.deleteEquipment(epo);
	
	}

	@Override
	public boolean addEquipment(String ID, EquipmentPO e)
			throws RemoteException {
		// TODO Auto-generated method stub
		return edc.addEquipment(e);
	}

	@Override
	public boolean updateEquipment(String ID, EquipmentPO e)
			throws RemoteException {
		// TODO Auto-generated method stub
		//return edc.updateEquipment(e);
		return false;
	}

	@Override
	public boolean removeEquipment(String ID) throws RemoteException {
		// TODO Auto-generated method stub
		
		EquipmentPO po = new EquipmentPO();
		po.type = ID;
		return edc.deleteEquipment(po);
	}

	@Override
	public int buyEquipment(String playerID, String kind, int number) throws RemoteException {
		// TODO Auto-generated method stub
		StorePO store = slc.getStore(kind);
		int cost = number * (store.price); 
		EquipmentPO equipment = new EquipmentPO();
		equipment.type = kind;
		equipment.playerID = playerID;
		equipment.num = number;
		
		PlayerPO player = plc.getPlayer(playerID);
		
		if(player.money < cost)
			return -1;
		
		
		addEquipment(kind, equipment);
		
		player.money = player.money - cost;
		boolean bool = plc.updatePlayer(player);
		if(bool) {
			return cost;
		}else
			return -1;
	}

	

}
