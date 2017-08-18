package logic.admin;

import java.rmi.Remote;
import java.rmi.RemoteException;
import po.PlayerPO;


public interface AdminLogicService extends Remote
{
	public boolean newPlayer(PlayerPO player) throws RemoteException;
			//根据information创建新玩家，需要解析information参数
	public boolean removePlayer(String ID) throws RemoteException;
			//根据ID属性移除某玩家
	public PlayerPO getPlayer(String ID) throws RemoteException;
			//根据ID属性获取PlayerPO属性
	public boolean changePlayer(PlayerPO po) throws RemoteException;
			//根据PO参数修改同ID的Player对象
	public boolean spreadRadio(String information) throws RemoteException;
			//发布系统公告
}
