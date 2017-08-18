package logic.player;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import org.jfree.data.category.DefaultCategoryDataset;

import po.GamePO;
import po.PlayerPO;
import po.RankListPO;

public interface PlayerLogicService extends Remote{
	
	public boolean registry(String name, String code) throws RemoteException;
			//玩家注册，ID和密码
	public boolean logout(String name, String code) throws RemoteException;
			//玩家登出
	public PlayerPO login(String name, String password) throws RemoteException;
			//玩家登录
	public boolean updatePlayer(PlayerPO player) throws RemoteException;
			//更新玩家信息
	public PlayerPO getPlayer(String playerID) throws RemoteException;
			//获取玩家信息
	public ArrayList<PlayerPO> getFreinds(String playerID) throws RemoteException;
			//获取玩家好友列表
	public RankListPO getFriendRank(String playerID) throws RemoteException;
			//获取玩家rank排级
	public RankListPO getAllRank(String playerID) throws RemoteException;
	public String[] updateRadio() throws RemoteException;
			//获取系统公告
	public ArrayList<GamePO> getHistory(String name) throws RemoteException;
	
	public boolean saveHistory(GamePO gameRecord) throws RemoteException;
	
	public ArrayList<PlayerPO> getAllPlayers() throws RemoteException;
	
	public ArrayList<Integer> getStaticStatistics(String name)throws RemoteException;
	
	public DefaultCategoryDataset getCountOfGameLineData(String name)throws RemoteException;

	public DefaultCategoryDataset getAverageScorePerDay(String name)throws RemoteException;
	
	
}
