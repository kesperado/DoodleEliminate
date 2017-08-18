package data.equipment;

import java.util.ArrayList;

import po.EquipmentPO;


public class EquipmentDataController implements EquipmentDataService {
	
	private EquipmentDataHelper equipment;
	
	public EquipmentDataController(){
		equipment=new EquipmentDataHelper();
		
	}

	@Override
	public boolean addEquipment(EquipmentPO po) {
		// TODO Auto-generated method stub
		
		return equipment.addEquipment(po);
	}

	@Override
	public boolean deleteEquipment(EquipmentPO po) {
		// TODO Auto-generated method stub
		return equipment.deleteEquipment(po);
	}

	@Override
	public ArrayList<EquipmentPO> getEquipment(EquipmentPO po) {
		// TODO Auto-generated method stub
		return equipment.getEquipment(po);
	}

	
}
