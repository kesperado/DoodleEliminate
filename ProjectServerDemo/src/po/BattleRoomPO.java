package po;

import java.util.HashMap;

import logic.game.GameLogicService;

public class BattleRoomPO
{
	public int id;
	public boolean start;
	public String roomMaster;
	public HashMap<String, Boolean> players = new HashMap<>();
	public HashMap<String, Boolean> team = new HashMap<>();
	public GameSettingPO setting;
	public GameLogicService gls;
	
	public BattleRoomPO()
	{
		
	}
}
