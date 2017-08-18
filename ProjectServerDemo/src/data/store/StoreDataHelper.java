package data.store;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import data.DBHelper;
import po.StorePO;

public class StoreDataHelper {
	private DBHelper db;
	private ResultSet set;

	public StoreDataHelper() {
		db = DBHelper.getInstance();
	}

	public boolean addEquipment(StorePO po) {
		boolean isSuccess = false;
		if (!po.equipmentType.equals("")) {
			set = db.excuteQue("select * from store where equipmentType='="
					+ po.equipmentType + "';");

			try {
				if (!set.next()) {
					isSuccess = db.excuteUpdate("insert into store values('"
							+ po.equipmentType + "'," + po.price + ");");
				} else {
					isSuccess = false;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		db.close();
		return isSuccess;
	}

	public boolean updateEquipment(StorePO po) {
		boolean isSuccess = false;
		if (!po.equipmentType.equals("")) {
			set = db.excuteQue("select * from store where equipmentType="
					+ po.equipmentType + ";");

			try {
				if (!set.next()) {
					isSuccess = db.excuteUpdate("update store set price="
							+ po.price + " where equipmentType='" + po.price
							+ "';");
				} else {
					isSuccess = false;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		db.close();
		return isSuccess;
	}

	public ArrayList<StorePO> getEquipment() {
		ArrayList<StorePO> poList=new ArrayList<StorePO>();
		set = db.excuteQue("select * from store;");
		
		try {
			while(set.next()){
				StorePO po=new StorePO();
				po.equipmentType=set.getString("equipmentType");
				po.price=set.getInt("price");
				poList.add(po);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return poList;
	}

	public StorePO getEquipment(String type) {
		StorePO po=new StorePO();
		set = db.excuteQue("select * from store where equipmentType='"
				+ type + "';");
		try {
			if(set.next()){
				po.equipmentType=set.getString("equipmentType");
				po.price=set.getInt("price");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return po;
	}

	public boolean removeEquipment(StorePO po) {
		boolean isSuccess = false;
		if (!po.equipmentType.equals("")) {
			set = db.excuteQue("select * from store where equipmentType='"
					+ po.equipmentType + "';");

			try {
				if (set.next()) {
					isSuccess = db
							.excuteUpdate("delete from radio where equipmentType='"
									+ po.equipmentType + "';");
				} else {
					isSuccess = false;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		db.close();
		return isSuccess;
	}
}
