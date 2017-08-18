package data.equipment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import po.EquipmentPO;
import data.DBHelper;

public class EquipmentDataHelper {

	private DBHelper db;
	private ResultSet set;

	public EquipmentDataHelper() {
		db = DBHelper.getInstance();

	}

	public static void main(String[] args) {
		EquipmentDataHelper e = new EquipmentDataHelper();
		EquipmentPO po = new EquipmentPO();
		po.num = 2;
		po.type = "a";
		po.playerID = "12";
		e.getEquipment(po);
	}

	public boolean addEquipment(EquipmentPO po) {
		boolean isSuccess = false;
		if (!(po.playerID.equals("") || po.type.equals("") || (po.num == 0))) {
			set = db.excuteQue("SELECT * from equipment where type='" + po.type
					+ "' and playerID='" + po.playerID + "';");

			try {
				if (!set.next()) {
					isSuccess = db
							.excuteUpdate("insert into equipment values('"
									+ po.playerID + "','" + po.type + "',"
									+ po.num + ");");
				} else {
					isSuccess = db.excuteUpdate("update equipment set num=num+"
							+ po.num + " where playerID='" + po.playerID
							+ "' and type='" + po.type + "';");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		db.close();
	//	System.out.println(isSuccess);
		return isSuccess;
	}

	public boolean deleteEquipment(EquipmentPO po) {
		boolean isSuccess = false;
		if (!(po.playerID.equals("") || po.type.equals("") || (po.num == 0))) {
			set = db.excuteQue("SELECT * from equipment where type='" + po.type
					+ "' and playerID='" + po.playerID + "';");

			try {
				if (!set.next()) {
					isSuccess = false;
				} else {
					int num = set.getInt("num");
					System.out.println(num);
					if (num == po.num) {
						isSuccess = db
								.excuteUpdate("delete from equipment where playerID='"
										+ po.playerID
										+ "' and type='"
										+ po.type
										+ "';");
					} 
					else if(num<po.num){
						isSuccess=false;
					}
					else {	isSuccess = db
								.excuteUpdate("update equipment set num=num-"
										+ po.num + " where playerID='"
										+ po.playerID + "' and type='"
										+ po.type + "';");
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		db.close();
	//	System.out.println(isSuccess);
		return isSuccess;
	}

	public ArrayList<EquipmentPO> getEquipment(EquipmentPO po) {
		ArrayList<EquipmentPO> poList=new ArrayList<EquipmentPO>();
		
		if((po.playerID.equals(""))&&po.type.equals("")){
			return null;
		}
		else if(po.playerID.equals("")&&(!po.type.equals(""))){
			set=db.excuteQue("select * from equipment where type='"+po.type+"';");
			try {
				while(set.next()){
					EquipmentPO ep=new EquipmentPO();
					ep.playerID=set.getString("playerID");
					ep.type=set.getString("type");
					ep.num=set.getInt("num");
					poList.add(ep);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if ((!po.playerID.equals(""))&&(po.type.equals(""))){
			set=db.excuteQue("select * from equipment where playerID='"+po.playerID+"';");
			try {
				while(set.next()){
					EquipmentPO ep=new EquipmentPO();
					ep.playerID=set.getString("playerID");
					ep.type=set.getString("type");
					ep.num=set.getInt("num");
					poList.add(ep);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(!((po.playerID.equals("")||po.playerID.equals("")))){
			set=db.excuteQue("select * from equipment where type='"+po.type+"' and playerID='"+po.playerID+"';");
			try {
				while(set.next()){
					EquipmentPO ep=new EquipmentPO();
					ep.playerID=set.getString("playerID");
					ep.type=set.getString("type");
					ep.num=set.getInt("num");
					poList.add(ep);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		db.close();
//		for(EquipmentPO po1: poList){
//			System.out.println(po.type+" "+po.playerID);
//		}
		return poList;
	}

}
