package data.store;

import java.util.ArrayList;

import po.StorePO;

public interface StoreDataService {
	
	public boolean addStore(StorePO po);
	public boolean updateStore(StorePO po);
	public StorePO getStore(String type);
	public boolean removeStore(StorePO po);
	public ArrayList<StorePO> getStore();
}
