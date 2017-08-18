package data.equipment;

import java.util.ArrayList;

import po.EquipmentPO;

public interface EquipmentDataService {
	public boolean addEquipment(EquipmentPO po);//买道具
	public boolean deleteEquipment(EquipmentPO po);//用道具
	public ArrayList<EquipmentPO> getEquipment(EquipmentPO po);//获取玩家道具信息
//	public boolean updateEquipment(EquipmentPO po);//暂时没用

}
