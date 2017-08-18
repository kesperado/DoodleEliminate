package po;

import java.io.Serializable;
import java.util.ArrayList;

public class RankListPO implements Serializable{
	
	public PlayerPO player = new PlayerPO();				//当前玩家
	public int ranking = 1;									//玩家排名	
	public ArrayList<PlayerPO> players = new ArrayList<PlayerPO>(); 		//rank降序排列玩家好友

}
