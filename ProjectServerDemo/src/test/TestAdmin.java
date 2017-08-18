package test;

import static org.junit.Assert.*;

import java.rmi.RemoteException;

import logic.admin.*;

import org.junit.Test;

/**
 * @author 作者 :knox
 * @version 创建时间：2014-6-8 下午4:06:46
 * 类说明
 */
public class TestAdmin {

	private AdminLogicController adminLogicController ;
	
	
	
//	@Test
//	public void testAdminRemovewPlayer() throws RemoteException {
//		adminLogicController= new AdminLogicController();
//		assertTrue(adminLogicController.removePlayer("player1"));
//		assertFalse(adminLogicController.removePlayer("player2"));
//	}
//	
//	
	@Test 
	public void testAdminSpreadRadio() throws RemoteException{
		adminLogicController= new AdminLogicController();
		assertTrue(adminLogicController.spreadRadio("testinfo"));
		assertFalse(adminLogicController.spreadRadio(""));
	}

}
