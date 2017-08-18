package test;

import static org.junit.Assert.*;

import java.rmi.RemoteException;

import logic.equipment.EquipmentLogicController;

import org.junit.Test;

import po.EquipmentPO;

/**
 * @author 作者 :knox
 * @version 创建时间：2014-6-8 下午4:33:03
 * 类说明
 */
public class TestEquipment {

	
	private EquipmentLogicController equipmentLogicController;
	
	@Test
	public void testAddEquipment1() throws RemoteException {
		equipmentLogicController= new EquipmentLogicController();
		EquipmentPO ePo= new EquipmentPO();
		
		ePo.type="type1";
		ePo.num=10;
		ePo.singlePrice=10;
		assertTrue(equipmentLogicController.addEquipment("player3", ePo));

	}
	@Test
	public void testAddEquipment0() throws RemoteException {
		equipmentLogicController= new EquipmentLogicController();
		EquipmentPO ePo= new EquipmentPO();
		ePo.playerID="player3";
		ePo.type="type1";
		ePo.num=10;
		ePo.singlePrice=10;
		assertTrue(equipmentLogicController.addEquipment("player3", ePo));

	}
	@Test
	public void testAddEquipment2() throws RemoteException {
		equipmentLogicController= new EquipmentLogicController();
		EquipmentPO ePo= new EquipmentPO();
		
		ePo.type="type1";
		ePo.num=10;
		ePo.singlePrice=10;
		ePo.playerID="player1";
		assertFalse(equipmentLogicController.addEquipment("player3", ePo));
	
	}
	@Test
	public void testAddEquipment3() throws RemoteException {
		equipmentLogicController= new EquipmentLogicController();
		EquipmentPO ePo= new EquipmentPO();
		
		ePo.type="type1";
		ePo.singlePrice=10;
		ePo.playerID="player3";
		ePo.num=-1;
		assertFalse(equipmentLogicController.addEquipment("player3", ePo));
		
	}
	@Test
	public void testAddEquipment4() throws RemoteException {
		equipmentLogicController= new EquipmentLogicController();
		EquipmentPO ePo= new EquipmentPO();
		
		ePo.type="type1";
		ePo.num=10;
		ePo.singlePrice=-1;
		ePo.playerID="player3";
		ePo.num=10;
		assertFalse(equipmentLogicController.addEquipment("player3", ePo));
		
	}
	

	

	
	
	

}
