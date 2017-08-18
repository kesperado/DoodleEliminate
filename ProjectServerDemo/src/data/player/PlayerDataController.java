package data.player;

import java.util.ArrayList;

import po.PlayerPO;

public class PlayerDataController implements PlayerDataService {

	private PlayerDataHelper player;

	public PlayerDataController() {
		player = new PlayerDataHelper();
	}

	@Override
	public boolean register(PlayerPO po) {
		// TODO Auto-generated method stub
		return player.register(po);
	}

	@Override
	public PlayerPO login(PlayerPO po) {
		// TODO Auto-generated method stub
		return player.login(po);
	}

	@Override
	public boolean logout(PlayerPO po) {
		// TODO Auto-generated method stub
		return player.logout(po);
	}

	@Override
	public boolean updatePlayer(PlayerPO po) {
		// TODO Auto-generated method stub
		return player.updatePlayer(po);
	}

	@Override
	public ArrayList<PlayerPO> getRecord(ArrayList<PlayerPO> poList) {
		// TODO Auto-generated method stub
		return player.getRecord(poList);
	}

	@Override
	public ArrayList<PlayerPO> getPlayer(ArrayList<String> IDList) {
		// TODO Auto-generated method stub
		return player.getPlayer(IDList);
	}

	@Override
	public boolean removePlayer(String ID) {
		// TODO Auto-generated method stub
		return player.removePlayer(ID);
	}

	@Override
	public ArrayList<PlayerPO> getRank(ArrayList<String> IDList) {
		// TODO Auto-generated method stub
		return player.getRank(IDList);
	}

	@Override
	public PlayerPO getPlayer(String id) {
		// TODO Auto-generated method stub
		return player.getPlayer(id);
	}
	
}
