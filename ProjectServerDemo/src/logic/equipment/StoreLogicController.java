package logic.equipment;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import po.StorePO;
import data.store.StoreDataController;
import data.store.StoreDataService;

public class StoreLogicController extends UnicastRemoteObject implements StoreLogicService {

	
	StoreDataService sdc;
	
	public StoreLogicController() throws RemoteException {
		super();
		sdc = new StoreDataController();
	}
	@Override
	public boolean addStore(StorePO po) throws RemoteException {
		// TODO Auto-generated method stub
		return sdc.addStore(po);
	}

	@Override
	public boolean removeStore(StorePO po) throws RemoteException {
		// TODO Auto-generated method stub
		return sdc.removeStore(po);
	}

	@Override
	public boolean changeStore(StorePO po) throws RemoteException {
		// TODO Auto-generated method stub
		return sdc.updateStore(po);
	}

	@Override
	public StorePO getStore(StorePO po) throws RemoteException {
		// TODO Auto-generated method stub
		return sdc.getStore(po.equipmentType);
	}
	@Override
	public StorePO getStore(String type) throws RemoteException {
		// TODO Auto-generated method stub
		return sdc.getStore(type);
	}

}
