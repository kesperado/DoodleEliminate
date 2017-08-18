package test;

import static org.junit.Assert.*;

import java.awt.print.Printable;
import java.rmi.RemoteException;

import logic.game.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author 作者 :knox
 * @version 创建时间：2014-6-8 下午7:12:22
 * 类说明
 */
public class TestGame {

	private GameCentralController gameCentralController ;
	private GameBoard gameBoard = new GameBoard();
	@Before
	public void setup(){
		int[][] map={{1,2,3,4,5,6,7,1,2},
		{2,3,4,5,7,7,1,2,3},
		{3,4,5,4,7,1,2,3,4},
		{4,5,7,7,1,7,3,4,3},
		{5,6,7,1,7,3,4,5,6},
		{6,7,1,2,7,4,5,6,7},
		{7,1,2,3,4,5,6,7,1},
		{1,2,3,4,3,3,7,1,2},
		{2,3,4,3,6,7,1,2,3}};
		gameBoard.map =map;

				
	}
	
	@Test
	public void testStartSingleGame() throws RemoteException{
		gameCentralController = GameCentralController.getGameCentralController();
		
		assertEquals(0,gameCentralController.startSingleGame());
		assertEquals(1,gameCentralController.startSingleGame());
		assertEquals(2,gameCentralController.startSingleGame());
	}
	
	@Test
	public void testStartCoopGame() throws RemoteException{
		gameCentralController = GameCentralController.getGameCentralController();
		
		assertEquals(0,gameCentralController.startCoopGame());
		assertEquals(1,gameCentralController.startCoopGame());
		assertEquals(2,gameCentralController.startCoopGame());
	}
	
	@Test
	public void testEliminate1() throws RemoteException{
		assertEquals(-1,gameBoard.doEliminate(1, 1, 1, 2));
	}
	@Test
	public void testEliminate2() throws RemoteException{
		assertEquals(-1,gameBoard.doEliminate(1, 1, 1, 3));
	}
	@Test
	public void testEliminate3() throws RemoteException{
		assertEquals(300,gameBoard.doEliminate(2, 7, 2, 8));
	}
	@Test
	public void testEliminate4() throws RemoteException{
		assertEquals(300,gameBoard.doEliminate(2, 8, 2, 7));
	}
	@Test
	public void testEliminate5() throws RemoteException{
		assertEquals(300,gameBoard.doEliminate(1, 2, 1, 3));
	}
	@Test
	public void testEliminate6() throws RemoteException{
		assertEquals(600,gameBoard.doEliminate(7, 2, 7, 3));
	}
	@Test
	public void testEliminate7() throws RemoteException{
		assertEquals(800,gameBoard.doEliminate(3, 5, 3, 4));
	}
	


}
