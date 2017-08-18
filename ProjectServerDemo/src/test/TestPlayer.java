package test;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Collection;

import logic.player.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import po.PlayerPO;

/**
 * @author 作者 :knox
 * @version 创建时间：2014-6-8 上午10:35:27 类说明
 */
public class TestPlayer {

	// @Test
	// public void test() {
	// fail("尚未实现");
	// }
//	private PlayerLogicController playerLogicController = new PlayerLogicController();
//	private PlayerPO playerPO = new PlayerPO();
//
//
//	@Test
//	public void testRegistry() throws RemoteException {
//		assertTrue(playerLogicController.registry("player2", "111"));//真测试时可以取消掉注释，或换一个用户名或者密码
//		assertFalse(playerLogicController.registry("", ""));
//		assertFalse(playerLogicController.registry("player2", ""));
//		assertFalse(playerLogicController.registry("", "222"));
//		assertFalse(playerLogicController.registry("player3", "111"));
//	}
//	
//	//测试登陆
//	@Test
//	public void testLogin() throws RemoteException {
//		playerPO.playerID="player3";
//		playerPO.password="111";
//		PlayerPO testPlayerPO=playerLogicController.login("player3", "111");
//		boolean isSame=(testPlayerPO.playerID.equals(playerPO.playerID)
//				&&testPlayerPO.password.equals(playerPO.password)
//				&&testPlayerPO.maxCombo==(playerPO.maxCombo)
//				&&testPlayerPO.maxScore==(playerPO.maxScore)
//				&&testPlayerPO.money==testPlayerPO.money
//				&&testPlayerPO.rank==testPlayerPO.rank);
//		assertTrue(isSame);
//		
//		testPlayerPO=playerLogicController.login("player3", "");
//		isSame=(testPlayerPO.playerID.equals(playerPO.playerID)
//				&&testPlayerPO.password.equals(playerPO.password)
//				&&testPlayerPO.maxCombo==(playerPO.maxCombo)
//				&&testPlayerPO.maxScore==(playerPO.maxScore)
//				&&testPlayerPO.money==testPlayerPO.money
//				&&testPlayerPO.rank==testPlayerPO.rank);
//		assertFalse(isSame);
//		
//		testPlayerPO=playerLogicController.login("player3", "123");
//		isSame=(testPlayerPO.playerID.equals(playerPO.playerID)
//				&&testPlayerPO.password.equals(playerPO.password)
//				&&testPlayerPO.maxCombo==(playerPO.maxCombo)
//				&&testPlayerPO.maxScore==(playerPO.maxScore)
//				&&testPlayerPO.money==testPlayerPO.money
//				&&testPlayerPO.rank==testPlayerPO.rank);
//		assertFalse(isSame);
//		
//		testPlayerPO=playerLogicController.login("", "111");
//		isSame=(testPlayerPO.playerID.equals(playerPO.playerID)
//				&&testPlayerPO.password.equals(playerPO.password)
//				&&testPlayerPO.maxCombo==(playerPO.maxCombo)
//				&&testPlayerPO.maxScore==(playerPO.maxScore)
//				&&testPlayerPO.money==testPlayerPO.money
//				&&testPlayerPO.rank==testPlayerPO.rank);
//		assertFalse(isSame);
//		
//		testPlayerPO=playerLogicController.login("", "");
//		isSame=(testPlayerPO.playerID.equals(playerPO.playerID)
//				&&testPlayerPO.password.equals(playerPO.password)
//				&&testPlayerPO.maxCombo==(playerPO.maxCombo)
//				&&testPlayerPO.maxScore==(playerPO.maxScore)
//				&&testPlayerPO.money==testPlayerPO.money
//				&&testPlayerPO.rank==testPlayerPO.rank);
//		assertFalse(isSame);
//	}
//	
//	@Test
//	public void testUpdatePlayerAndGetPlayer() throws RemoteException {
//		playerPO.playerID="player3";
//		playerPO.password="123";
//		playerPO.maxCombo=10;
//		playerPO.maxScore=10;
//		playerPO.money=10;
//		playerPO.rank=10;
//		playerLogicController.updatePlayer(playerPO);
//		PlayerPO testPlayerPO=playerLogicController.getPlayer("player3");
//		boolean isSame=(testPlayerPO.password.equals("123")
//				&&testPlayerPO.maxCombo==10
//				&&testPlayerPO.maxScore==10
//				&&testPlayerPO.money==10
//				&&testPlayerPO.rank==10);
//		assertTrue(isSame);
//		
//	}
//	
//	//getFriends方法没测试
//


}
