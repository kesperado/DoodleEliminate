package logic.equipment;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import po.EquipmentPO;

public interface EquipmentLogicService extends Remote 
{
	public ArrayList<EquipmentPO> getList(String PlayerID) throws RemoteException;
			//根据PlayerID来获取某位玩家的装备信息
	public boolean sellSingleEquipment(String playerID,String equipmentID) throws RemoteException;
			//出售某玩家、某类型装备一件
	public boolean useEquipment(String playerID, String type) throws RemoteException;
			//
	public boolean addEquipment(String ID,EquipmentPO e) throws RemoteException;
			//某位玩家购买某装备一件
	public int buyEquipment(String playerID, String kind, int number) throws RemoteException;
	public boolean updateEquipment(String ID,EquipmentPO e) throws RemoteException;
			//更改某玩家某种装备的数量信息
	public boolean removeEquipment(String ID) throws RemoteException;
			//根据装备ID移除某种装备
	
}
