package data.store;

import java.util.ArrayList;

import po.StorePO;

public class StoreDataController implements StoreDataService{
	StoreDataHelper storeData;
	
	public StoreDataController(){
		storeData=new StoreDataHelper();
	}

	@Override
	public boolean addStore(StorePO po) {
		
		return storeData.addEquipment(po);
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean removeStore(StorePO po) {
		// TODO Auto-generated method stub
		return storeData.removeEquipment(po);
	}

	@Override
	public boolean updateStore(StorePO po) {
		// TODO Auto-generated method stub
		return storeData.updateEquipment(po);
	}

	@Override
	public StorePO getStore(String type) {
		// TODO Auto-generated method stub
		return storeData.getEquipment(type);
	}

	@Override
	public ArrayList<StorePO> getStore() {
		// TODO Auto-generated method stub
		return storeData.getEquipment();
	}
	

}
