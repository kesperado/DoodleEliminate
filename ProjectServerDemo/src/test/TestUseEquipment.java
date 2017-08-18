package test;

import static org.junit.Assert.*;
import logic.game.GameBoard;
import logic.game.GameCentralController;

import org.junit.Before;
import org.junit.Test;

/**
 * @author 作者 :knox
 * @version 创建时间：2014-6-8 下午8:44:58
 * 类说明
 */
public class TestUseEquipment {
	
	private GameCentralController gameCentralController ;
	private GameBoard gameBoard = new GameBoard();
	@Before
	public void setup(){
		int[][] map=
		{{1,2,3,4,5,6,7,1,2},
		{2,3,14,5,7,7,1,2,3},
		{3,4,5,4,7,1,2,3,4},
		{4,5,7,7,1,7,3,4,3},
		{5,6,7,1,7,3,4,5,6},
		{6,7,1,2,7,4,5,6,7},
		{7,1,2,3,4,5,6,7,1},
		{1,2,3,4,3,3,7,1,2},
		{2,3,4,3,20,7,1,2,3}};
		gameBoard.map =map;
	}
	
	@Test
	public void testUseEquipment1(){
		assertTrue(gameBoard.useEquipment(8, 4));
	}
	
	@Test
	public void testUseEquipment2(){
		assertFalse(gameBoard.useEquipment(1, 1));
	}
	
	@Test
	public void testUseEquipment3(){
		assertTrue(gameBoard.useEquipment(1, 2));
	}
	
	@Test
	public void testUseEquipment4(){
		assertFalse(gameBoard.useEquipment(0, 0));
	}
	
}
