package data;

import java.sql.*;
import java.util.Date;

public class DBHelper {

	private static Connection con;
	private static Statement stmt;
	private static ResultSet resultset;
	private static DBHelper dbhelper = new DBHelper();

	private DBHelper() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static DBHelper getInstance() {

		return dbhelper;

	}

	public static void main(String[] args) {
		DBHelper d =DBHelper.getInstance();
		boolean is= d.excuteUpdate("insert into equipment values('12','1',1)"); 
		System.out.println(is);
		while(true);
//		try {
//			while(rs.next()){
////				String id=rs.getString("id");
////				System.out.println(id);
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
////			e.printStackTrace();
//			System.out.println("false");
//		}finally{
//			d.close();
//		}
//		// d.connect();
	}

	
	
	private Connection connect() {
		// 数据库所对应位置
		String url = "jdbc:mysql://127.0.0.1:3306/bighomework";
		String user = "root";
		String pw = "215898";
		try {
			con = DriverManager.getConnection(url, user, pw);
		} catch (SQLException e) {
			System.out.println("System Error");
			e.printStackTrace();
		}
		return con;
	}

	//using for excuting  insert update or delect statement
	public boolean excuteUpdate(String sql){
		System.out.println(sql);
		int isExcute=0;
		try {
			this.connect();
			stmt =  con.createStatement();
			isExcute = stmt.executeUpdate(sql);
			//this.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(isExcute==0){
			return false;
		}else{
		return true;
		}
	}
	
	
	//using for excuting select statement
	public ResultSet excuteQue(String sql) {
		System.out.println(sql);
		try {
			this.connect();
			stmt =  con.createStatement();
			resultset = stmt.executeQuery(sql);
			//this.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultset;
	}

	public void close() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (java.lang.NullPointerException e) {
		}
	}

}
