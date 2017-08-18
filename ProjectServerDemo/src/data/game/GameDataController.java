package data.game;

import java.util.ArrayList;

import po.GamePO;

public class GameDataController implements GameDataService {

	GameDataHelper game;

	public GameDataController() {
		game = new GameDataHelper();
	}

	@Override
	public boolean initGame(ArrayList<GamePO> poList) {
		// TODO Auto-generated method stub
		return game.initGame(poList);
	}

	@Override
	public boolean recordGame(ArrayList<GamePO> poList) {
		// TODO Auto-generated method stub
		return game.recordGame(poList);
	}

	@Override
	public ArrayList<GamePO> getGame(String id) {
		// TODO Auto-generated method stub
		return game.getGame(id);
	}

}
