package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.rmi.RemoteException;
import java.util.ArrayList;

import logic.LogicController;
import logic.LogicService;

import org.junit.Before;
import org.junit.Test;

import po.PlayerPO;
import po.RankListPO;

public class LogicControllerTest {

	private LogicService apple;
	private PlayerPO playerPO = new PlayerPO();
	@Before
	public void setUp() throws Exception {
		//PlayerPO playerPO = new PlayerPO();
	}

	@Test
	public void testLogicController() throws RemoteException {
		apple = new LogicController();
	}

	@Test
	public void testRegistry() throws RemoteException {
		assertTrue(apple.registry("tiger", ""));
		assertTrue(apple.registry("tiger", "tiger"));
		assertTrue(apple.registry("player3", "111"));
		assertFalse(apple.registry("", ""));
		assertFalse(apple.registry("player2", ""));
		assertFalse(apple.registry("", "222"));
		assertFalse(apple.registry("player3", "111"));
	}
	
	@Test
	public void testCheckName() throws RemoteException {
		assertTrue(apple.checkName("player3"));
		assertTrue(apple.checkName("cat"));
	}
	
	@Test
	public void testLogin() throws RemoteException {
		playerPO.playerID="player3";
		playerPO.password="111";
		PlayerPO testPlayerPO=apple.login("player3", "111");
		boolean isSame=(testPlayerPO.playerID.equals(playerPO.playerID)
				&&testPlayerPO.password.equals(playerPO.password)
				&&testPlayerPO.maxCombo==(playerPO.maxCombo)
				&&testPlayerPO.maxScore==(playerPO.maxScore)
				&&testPlayerPO.money==testPlayerPO.money
				&&testPlayerPO.rank==testPlayerPO.rank);
		assertTrue(isSame);
		
		testPlayerPO=apple.login("player3", "");
		isSame=(testPlayerPO.playerID.equals(playerPO.playerID)
				&&testPlayerPO.password.equals(playerPO.password)
				&&testPlayerPO.maxCombo==(playerPO.maxCombo)
				&&testPlayerPO.maxScore==(playerPO.maxScore)
				&&testPlayerPO.money==testPlayerPO.money
				&&testPlayerPO.rank==testPlayerPO.rank);
		assertFalse(isSame);
		
		testPlayerPO=apple.login("player3", "123");
		isSame=(testPlayerPO.playerID.equals(playerPO.playerID)
				&&testPlayerPO.password.equals(playerPO.password)
				&&testPlayerPO.maxCombo==(playerPO.maxCombo)
				&&testPlayerPO.maxScore==(playerPO.maxScore)
				&&testPlayerPO.money==testPlayerPO.money
				&&testPlayerPO.rank==testPlayerPO.rank);
		assertFalse(isSame);
		
		testPlayerPO=apple.login("", "111");
		isSame=(testPlayerPO.playerID.equals(playerPO.playerID)
				&&testPlayerPO.password.equals(playerPO.password)
				&&testPlayerPO.maxCombo==(playerPO.maxCombo)
				&&testPlayerPO.maxScore==(playerPO.maxScore)
				&&testPlayerPO.money==testPlayerPO.money
				&&testPlayerPO.rank==testPlayerPO.rank);
		assertFalse(isSame);
		
		testPlayerPO=apple.login("", "");
		isSame=(testPlayerPO.playerID.equals(playerPO.playerID)
				&&testPlayerPO.password.equals(playerPO.password)
				&&testPlayerPO.maxCombo==(playerPO.maxCombo)
				&&testPlayerPO.maxScore==(playerPO.maxScore)
				&&testPlayerPO.money==testPlayerPO.money
				&&testPlayerPO.rank==testPlayerPO.rank);
		assertFalse(isSame);
	}

	@Test
	public void testLogout() throws RemoteException {
		assertTrue(apple.checkName("tiger"));
		assertTrue(apple.checkName("player3"));
	}
	
	@Test
	public void testGetPersonalInformationString() throws RemoteException {
		playerPO.playerID="player3";
		playerPO.password="123";
		playerPO.maxCombo=10;
		playerPO.maxScore=10;
		playerPO.money=10;
		playerPO.rank=10;
		apple.savePlayer(playerPO);
		PlayerPO testPlayerPO=apple.getPersonalInformation("player3");
		boolean isSame=(testPlayerPO.password.equals("123")
				&&testPlayerPO.maxCombo==10
				&&testPlayerPO.maxScore==10
				&&testPlayerPO.money==10
				&&testPlayerPO.rank==10);
		assertTrue(isSame);
	}

	@Test
	public void testBuy() throws RemoteException {
		fail("Not yet implemented");
	}

	@Test
	public void testUseEquipment() throws RemoteException {
		fail("Not yet implemented");
	}

	@Test
	public void testSaveSinglePersonalInformation() throws RemoteException {
		assertTrue(apple.saveSinglePersonalInformation("player3", 1000, 5));
		assertTrue(apple.saveSinglePersonalInformation("player3", 1000, 10));
		
		
	}

	@Test
	public void testGetEquipmentInformation() throws RemoteException {
		playerPO = apple.getPersonalInformation("player3");
		boolean bool1 = (playerPO.maxScore == 1000) && (playerPO.maxCombo == 10) 
				&& (playerPO.playerID == "player3");
		assertTrue(bool1);
		playerPO = apple.getPersonalInformation("player2");
		boolean bool2 = (playerPO.maxScore == 1000) && (playerPO.maxCombo == 10) 
				&& (playerPO.playerID == "player3");
		assertFalse(bool2);
	}
	
	@Test
	public void testGetPersonalInformationPlayerPO() throws RemoteException {
		PlayerPO player = new PlayerPO();
		player.playerID = "player3";
		playerPO = apple.getPersonalInformation(player);
		boolean bool = (playerPO.maxScore == 1000) && (playerPO.maxCombo == 10) 
				&& (playerPO.playerID == "player3");
		assertTrue(bool);
	}

	@Test
	public void testSpreadRadio() throws RemoteException {
		assertTrue(apple.spreadRadio("testinfo"));
		assertFalse(apple.spreadRadio(""));
	}

	@Test
	public void testGetRadio() throws RemoteException {
		String[] list = apple.getRadio();
		boolean bool = ((list[0].equals("testinfo")) && (list[1].equals("")) && (list[2] == null));
		assertTrue(bool);
	}

	@Test
	public void testGetFriendRank() throws RemoteException {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllRank() throws RemoteException {
		RankListPO list = apple.getAllRank("player3");
		boolean bool = list.player.playerID.equals("player3");
		assertTrue(bool);
	}



	@Test
	public void testSaveHistory() throws RemoteException {
		fail("Not yet implemented");
	}

	@Test
	public void testGetHistory() throws RemoteException {
		fail("Not yet implemented");
	}

}
