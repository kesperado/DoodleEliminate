package logic;

import java.rmi.RemoteException;
import java.util.ArrayList;

import po.EquipmentPO;
import po.GamePO;
import po.PlayerPO;
import po.RankListPO;

public interface LogicService {
	public boolean checkName(String name) throws RemoteException;
	//验证用户名是否存在
	public boolean registry(String name, String password) throws RemoteException;

	// 注册 用户名和密码 false 只有一种情况 那就是用户名重叠

	
	public PlayerPO login(String name, String password) throws RemoteException;

	// 登陆 用户名和密码 false 即是用户名和密码不符合 或者无此用户名
	public boolean logout(String name) throws RemoteException;
	//用户登出
	
	public PlayerPO getPersonalInformation(String name) throws RemoteException;

	// 从用户名得到该用户信息 金币 各道具数量道具数量

	

	public PlayerPO getPersonalInformation(PlayerPO player) throws RemoteException;
	
	
	public boolean saveSinglePersonalInformation(String name, int score, int link) throws RemoteException;
	
	public ArrayList<GamePO> getHistory(String playerID) throws RemoteException;

	public boolean saveHistory(GamePO gameRecord) throws RemoteException;
	
	public ArrayList<EquipmentPO> getEquipmentInformation(String name) throws RemoteException;
	
	public int buy(String playerID, String kind, int number) throws RemoteException;
		// 该用户购买某一种道具 kind指种类 number是数量
		// 并花费相应金钱 返回是花费金额
	public boolean useEquipment(String playerID, String type) throws RemoteException;
		//某玩家使用某类型道具
	public RankListPO getFriendRank(String name) throws RemoteException;
		// 以一个RankListPO的格式返回，格式参见PO包
	
	public RankListPO getAllRank(String name) throws RemoteException;
		//获取所有玩家的rank排名，返回前五
	
	public boolean spreadRadio(String message) throws RemoteException;
	
	public String[] getRadio() throws RemoteException;
	
	public boolean savePlayer(PlayerPO player) throws RemoteException;
}
