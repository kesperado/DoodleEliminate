package data.game;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import data.DBHelper;
import po.GamePO;

public class GameDataHelper {

	private DBHelper db;
	private ResultSet set;

	public GameDataHelper() {
		db = DBHelper.getInstance();
	}

	public static void main(String[] args) {
		GameDataHelper game = new GameDataHelper();
//		
		ArrayList<GamePO> list=new ArrayList<GamePO>();
		GamePO g=new GamePO();
		g.playerID="player1";
		list.add(g);
		System.out.println(game.recordGame(list));
		
	}

	public boolean initGame(ArrayList<GamePO> poList) {
		boolean isSuccess = false;
		for (GamePO gp : poList) {
			if (!(gp.gameNO.equals("") || gp.playerID.equals(""))) {
				set = db.excuteQue("SELECT * from game where gameNO='"
						+ gp.gameNO + "' and playerID='" + gp.playerID + "';");

				try {
					if (!set.next()) {
						isSuccess = isSuccess
								& db.excuteUpdate("insert into game values('"
										+ gp.gameNO + "','" + gp.playerID
										+ "',0,'" + gp.time.toString()
										+ "',0);");
					} else {
						isSuccess = false;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				isSuccess = false;
			}
		}
		db.close();
		return isSuccess;
	}

	public boolean recordGame(ArrayList<GamePO> poList) {
		boolean isSuccess = false;
		for (GamePO gp : poList) {
			if (!(gp.time.equals("") || gp.playerID.equals(""))) {
				System.out.println(gp.time);
				set = db.excuteQue("SELECT * from game where time='"
						+ gp.time + "' and playerID='" + gp.playerID + "';");

				try {
					if (set.next()) {
						isSuccess = false;
					} else {
						isSuccess = isSuccess | db.excuteUpdate("INSERT into game values " +
								"('"+gp.gameNO+"','"+gp.playerID+"',"+gp.score+",'"+gp.time+"',"+gp.combo+");");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				isSuccess = false;
			}
		}
		db.close();
		return isSuccess;
	}

	public ArrayList<GamePO> getGame(String id){
		ArrayList<GamePO> gameList=new ArrayList<GamePO>();
		if(!id.equals("")){
			set=db.excuteQue("select * from game where playerID='"+id+"';");
			try {
				while(set.next()){
					GamePO game=new GamePO();
					game.gameNO=set.getString("gameNO");
					game.playerID=set.getString("playerID");
					game.combo=set.getInt("combo");
					game.score=set.getInt("score");
					game.time=set.getString("time");
					gameList.add(game);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		db.close();
		return gameList;
	}
}
