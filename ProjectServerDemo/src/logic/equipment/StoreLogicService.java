package logic.equipment;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import po.StorePO;

public interface StoreLogicService extends Remote {
	public boolean addStore(StorePO po) throws RemoteException;
			//增加一种道具
	public boolean removeStore(StorePO po) throws RemoteException;
			//移除一种道具
	public boolean changeStore(StorePO po) throws RemoteException;
			//修改一种道具
	public StorePO getStore(StorePO po) throws RemoteException;
			//根据某些Store属性获取某种道具
	public StorePO getStore(String type) throws RemoteException;
			//根据类型获取某种道具
}
