package logic;

import java.rmi.RemoteException;

import po.PlayerPO;

public class test {
	
	static LogicController apple;
	static PlayerPO testPlayer = new PlayerPO();

	/**
	 * @param args
	 * @throws RemoteException 
	 */
	public static void main(String[] args) throws RemoteException {
		// TODO Auto-generated method stub
		apple = new LogicController();
		System.out.println(apple.registry("testPlayer", "testPlayer"));
		System.out.println(apple.checkName("testPlayer"));
		System.out.println(apple.login("testPlayer", "testPlayer"));
		testPlayer = apple.login("testPlayer", "testPlayer");
		initPlayer();
		System.out.println(apple.savePlayer(testPlayer));
		//System.out.println(apple.buy("testPlayer", "1", 1));
		
	}

	private static void initPlayer() {
		// TODO Auto-generated method stub
		testPlayer.maxCombo = 5;
		testPlayer.maxScore = 200;
		testPlayer.money = 1000;
		testPlayer.rank = 2000;
		testPlayer.totalGame = 100;
		testPlayer.password = "123456";
		
	}

}
