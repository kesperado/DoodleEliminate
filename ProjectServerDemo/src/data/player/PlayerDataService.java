package data.player;

import java.util.ArrayList;

import po.PlayerPO;

public interface PlayerDataService {
	public boolean register(PlayerPO po);

	public PlayerPO login(PlayerPO po);

	public boolean logout(PlayerPO po);

	public boolean updatePlayer(PlayerPO po);

	public ArrayList<PlayerPO> getRecord(ArrayList<PlayerPO> poList);

	public ArrayList<PlayerPO> getPlayer(ArrayList<String> IDList);

	public PlayerPO getPlayer(String id);

	public boolean removePlayer(String ID);

	public ArrayList<PlayerPO> getRank(ArrayList<String> IDList);

}
