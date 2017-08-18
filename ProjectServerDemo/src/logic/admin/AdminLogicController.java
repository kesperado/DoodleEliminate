package logic.admin;

import java.io.FileReader;
import java.io.FileWriter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

import po.DatePO;
import po.PlayerPO;
import po.RadioPO;
import data.player.PlayerDataController;
import data.radio.RadioDataController;

public class AdminLogicController extends UnicastRemoteObject implements AdminLogicService  
{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	RadioDataController rdc;
	PlayerDataController pdc;
	public AdminLogicController() throws RemoteException {
		super();
		rdc = new RadioDataController();
		pdc = new PlayerDataController();
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean newPlayer(PlayerPO player) throws RemoteException {
		// TODO Auto-generated method stub
				//参数有问题
		//PlayerPO pp = new PlayerPO();                       //此处尚未进行PlayerPO的初始化
		boolean whether = pdc.register(player);
		return whether;
	}

	@Override
	public boolean removePlayer(String ID) throws RemoteException {
		// TODO Auto-generated method stub
		boolean whether = pdc.removePlayer(ID);
		return whether;
	}

	@Override
	public PlayerPO getPlayer(String ID) throws RemoteException {
		// TODO Auto-generated method stub
		PlayerPO po = pdc.getPlayer(ID);
		return po;
	}

	@Override
	public boolean changePlayer(PlayerPO po) throws RemoteException {
		// TODO Auto-generated method stub
		boolean whether = pdc.updatePlayer(po);
		return whether;
	}
	
	@Override
	public boolean spreadRadio(String information) throws RemoteException {
		RadioPO po = new RadioPO();                   //此处尚未进行RadioPO的初始化
		//po.time = DatePO.toString(new Date(System.currentTimeMillis()));
		if(information == null)
			return false;
		else if(information.equals(""))
			return false;
		else {
			po.message = information;
			// po.number = getRadioNumber();
			return rdc.addRadio(po);
		}
	}

	@SuppressWarnings("resource")
	int getRadioNumber() {
		int radioNumber = 0;
		FileReader reader;
		FileWriter writer;
		int tempchar;
		try {
			reader = new FileReader("Radio.txt");
			writer = new FileWriter("Radio.txt");
			if((tempchar = reader.read()) != -1) {
				radioNumber = tempchar + 1;
				writer.write(radioNumber);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return radioNumber;
	}

}
