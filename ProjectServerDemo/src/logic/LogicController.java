package logic;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import logic.admin.AdminLogicController;
import logic.admin.AdminLogicService;
import logic.equipment.EquipmentLogicController;
import logic.equipment.EquipmentLogicService;
import logic.equipment.StoreLogicController;
import logic.equipment.StoreLogicService;
import logic.player.PlayerLogicController;
import logic.player.PlayerLogicService;
import po.EquipmentPO;
import po.GamePO;
import po.PlayerPO;
import po.RankListPO;
import po.StorePO;

public class LogicController extends UnicastRemoteObject implements LogicService{
	
	AdminLogicService alc;
	EquipmentLogicService elc;
	//InformationLogicService ilc;
	PlayerLogicService plc;
	StoreLogicService slc;
	
	public LogicController() throws RemoteException {
		alc = new AdminLogicController();
		elc = new EquipmentLogicController();
		//ilc = new InformationLogicController();
		plc = new PlayerLogicController();
		slc = new StoreLogicController();
	}
	
	@Override
	/*
	 * (non-Javadoc)
	 * @see logic.LogicService#getPersonalInformation(java.lang.String)
	 */
	public PlayerPO getPersonalInformation(String name) throws RemoteException {
		// TODO Auto-generated method stub
		return plc.getPlayer(name);
	}

	@Override
	public int buy(String playerID, String kind, int number) throws RemoteException{
		// TODO Auto-generated method stub
		return elc.buyEquipment(playerID, kind, number);
	}

	@Override
	public boolean registry(String name, String password) throws RemoteException{
		// TODO Auto-generated method stub
		return plc.registry(name, password);
	}

	@Override
	public PlayerPO login(String name, String password) throws RemoteException{
		// TODO Auto-generated method stub
		return plc.login(name, password);
	}

	
	@Override
	public boolean checkName(String name) throws RemoteException {
		// TODO Auto-generated method stub
		PlayerPO player = plc.getPlayer(name);
		if(player != null)
			return false;
		return true;
	}

	@Override
	public boolean logout(String name) throws RemoteException {
		// TODO Auto-generated method stub
		return plc.logout(name, null);
	}

	@Override
	public PlayerPO getPersonalInformation(PlayerPO player)
			throws RemoteException {
		// TODO Auto-generated method stub
		return plc.getPlayer(player.playerID);
	}



	@Override
	public boolean saveSinglePersonalInformation(String player1, int score1, int link1)
			throws RemoteException {
		// TODO Auto-generated method stub
		
		PlayerPO firstPlayer = plc.getPlayer(player1);
		firstPlayer.totalGame++;
		if(firstPlayer.maxCombo < link1)
			firstPlayer.maxCombo = link1;
		if(firstPlayer.maxScore < score1)
			firstPlayer.maxScore = score1;
		
		return plc.updatePlayer(firstPlayer);
	}


	
	@Override
	public ArrayList<EquipmentPO> getEquipmentInformation(String name)
			throws RemoteException {
		// TODO Auto-generated method stub
		return elc.getList(name);
	}

	@Override
	public boolean spreadRadio(String message) throws RemoteException {
		// TODO Auto-generated method stub
		return alc.spreadRadio(message);
	}

	@Override
	public String[] getRadio() throws RemoteException {
		// TODO Auto-generated method stub
		return plc.updateRadio();
	}

	
	@Override
	public RankListPO getFriendRank(String name) throws RemoteException {
		// TODO Auto-generated method stub
		return plc.getFriendRank(name);
	}

	@Override
	public RankListPO getAllRank(String name) throws RemoteException {
		return plc.getAllRank(name);
	}

	@Override
	public boolean useEquipment(String playerID, String type)
			throws RemoteException {
		// TODO Auto-generated method stub
		return elc.useEquipment(playerID, type);
	}

	@Override
	public boolean saveHistory(GamePO gameRecord) throws RemoteException {
		// TODO Auto-generated method stub
		return plc.saveHistory(gameRecord);
	}
	
	@Override
	public ArrayList<GamePO> getHistory(String playerID) throws RemoteException {
		// TODO Auto-generated method stub
		return plc.getHistory(playerID);
	}

	@Override
	public boolean savePlayer(PlayerPO player) throws RemoteException {
		// TODO Auto-generated method stub
		return plc.updatePlayer(player);
	}
		
}
