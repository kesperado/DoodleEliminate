package po;

import java.io.Serializable;
import java.util.HashMap;

import logic.game.GameLogicService;

public class RoomPO implements Serializable
{
	public int id;
	public boolean start;
	public String roomMaster;
	public HashMap<String, Boolean> players = new HashMap<>();
	public GameSettingPO setting;
	public GameLogicService gls;
	
	public RoomPO()
	{
		
	}
}
