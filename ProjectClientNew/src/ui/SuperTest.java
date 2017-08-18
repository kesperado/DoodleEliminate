package ui;

import java.rmi.RemoteException;
import java.util.ArrayList;

import logic.equipment.EquipmentLogicService;
import logic.player.PlayerLogicService;
import po.GamePO;
import po.PlayerPO;
import po.RankListPO;
import rmi.RMIController;
/**
 * 
 * @author L.Lawliet
 *
 */
public class SuperTest {

	private static RMIController rmiController = new RMIController();
	/**
	 * 
	 * @param args
	 * @throws RemoteException
	 */
	public static void main(String[] args) throws RemoteException{
		PlayerPO playerPO = new PlayerPO();
		playerPO.playerID="player9";
		playerPO.password="player9";
		playerPO.maxCombo=10;
		playerPO.maxScore=100;
		playerPO.money=1000;
		playerPO.rank=1000;
		
		GamePO gamePO = new GamePO();
		gamePO.gameNO = "2";
		gamePO.playerID="player3";
		gamePO.combo=10;
		gamePO.score=100;
		
		PlayerLogicService playerLogicService = rmiController.getPlayerService();
		EquipmentLogicService equipmentLogicService = rmiController.getEquipmentService();
		
//		System.out.println(playerLogicService.login("player0", "123"));
		
//		System.out.println(playerLogicService.registry("player1", "123"));
		
//		System.out.println(playerLogicService.updatePlayer(playerPO));
		
//		String list[] = playerLogicService.updateRadio();
//		for(int i = 0; i < 5; i++){
//			System.out.println(list[i]);
//		}
		/*
		 * 		
		 */
//		System.out.println(playerLogicService.saveHistory(gamePO));
		//要先init game被调用在数据库中生成了id和Playerid	
		
		/*
		 * 
		 */
//		ArrayList<GamePO> gamePOs = playerLogicService.getHistory("player3");
//		if(gamePOs == null)
//			System.out.println("null");
//			
//		if(gamePOs.isEmpty())
//			System.out.println("listhasnopointer");
//		for(GamePO gamePO2:gamePOs){
//			System.out.println(gamePO2.gameNO+" "+gamePO.playerID);
//		}
		
//		ArrayList<PlayerPO> playerPOs = playerLogicService.getAllPlayers();
//		for(PlayerPO playerPO2: playerPOs){
//			System.out.println(playerPO2.playerID+" "+playerPO2.rank);
//		}
		/*
		 * 数组没反应
		 */
		RankListPO rankListPO = playerLogicService.getAllRank("player3");
		ArrayList<PlayerPO> playerPOs =rankListPO.players;
		if(playerPOs == null)
			System.out.println("null");
		if(playerPOs.isEmpty())
			System.out.println("NoPointer");
		
		for(PlayerPO playerPO2: playerPOs){
			if(playerPO2 == null)
				System.out.println("Nobady");
			System.out.println(playerPO2.playerID+" "+playerPO2.rank);
		}
		//出错，数组问题
		
		
//		System.out.println(equipmentLogicService.buyEquipment("player1", "C", 10));
		
//		ArrayList<EquipmentPO> equipmentPOs = equipmentLogicService.getList("player1");
		
		
		
		
		
		
	}

}
