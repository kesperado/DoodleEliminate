package data.radio;

import java.sql.ResultSet;
import java.sql.SQLException;

import data.DBHelper;
import po.DatePO;
import po.RadioPO;

public class RadioDataHelper {

	private DBHelper db;
	private ResultSet set;

	public RadioDataHelper() {
		db = DBHelper.getInstance();
	}

	public static void main(String[] args) {

	}

	public boolean addRadio(RadioPO po) {
		boolean isSuccess = false;
		if (po.number >= 0) {
			set = db.excuteQue("select * from radio where number=" + po.number
					+ ";");

			try {
				if (!set.next()) {

					isSuccess = db.excuteUpdate("insert into radio values("
							+ po.number + ",'" + po.time + "','" + po.message
							+ "');");
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

	public boolean update(RadioPO po) {
		boolean isSuccess = false;
		if(!(po.message.equals("")||po.number<0)){
			set=db.excuteQue("select * from radio where num="+po.number+";");
			
			try {
				if(!set.next()){
					isSuccess=db.excuteUpdate("update radio set message='"+po.message+"' where num="+po.number+";");
				}
				else{
					isSuccess=false;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		db.close();
		return isSuccess;
	}

	public boolean delete(RadioPO po) {
		
		boolean isSuccess = false;
		if(!(po.number<0)){
			set=db.excuteQue("select * from radio where number="+po.number+";");
			
			try {
				if(set.next()){
					System.out.println("R&(*(");
					isSuccess=db.excuteUpdate("delete from radio where number="+po.number+";");
				}
				else{
					isSuccess=false;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		db.close();
		return isSuccess;
	}

	public RadioPO getRadio(int number) {
		RadioPO po = new RadioPO();
		po.number=number;
		if(po.number>=0){
			set=db.excuteQue("select * from radio where number="+po.number+";");
			
			try {
				if(set.next()){
					System.out.println("set.next");
					po.time=set.getString("date");
					po.message=set.getString("message");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		db.close();
		return po;

	}
	
	public RadioPO getLastRadio(){
		RadioPO po=new RadioPO();
		set=db.excuteQue("select max(number) as max from radio;");
		
		try {
			if(set.next())
				po.number = set.getInt("max");
				db.close();
				set=db.excuteQue("select * from radio where number="+po.number+";");
				if(set.next()){
					po.time=set.getString("date");
					po.message=set.getString("message");
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.close();
		return po;
	}
}
