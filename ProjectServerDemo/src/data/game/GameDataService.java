package data.game;

import java.util.ArrayList;

import po.GamePO;

public interface GameDataService {
	public boolean initGame(ArrayList<GamePO> poList);

	public boolean recordGame(ArrayList<GamePO> poList);
	
	public ArrayList<GamePO> getGame(String id);
}
