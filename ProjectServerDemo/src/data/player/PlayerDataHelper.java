package data.player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import data.DBHelper;
import po.PlayerPO;

public class PlayerDataHelper {

	private DBHelper db;
	private ResultSet set;

	public PlayerDataHelper() {
		db = DBHelper.getInstance();
	}

	public static void main(String[] args) {
		PlayerDataHelper p = new PlayerDataHelper();
		PlayerPO po = new PlayerPO();
		po.playerID = "13";
		po.password = "1";
		ArrayList<PlayerPO> playerList = new ArrayList<PlayerPO>();
		ArrayList<String> IDList = new ArrayList<String>();
		IDList.add("15");
		playerList = p.getPlayer(IDList);
		for (PlayerPO player : playerList) {
			System.out.println("playerID:" + player.playerID + "combo:"
					+ player.maxCombo + "score:" + player.maxScore);
		}
		System.out.println("finish!");
	}

	public boolean register(PlayerPO po) {
		boolean isSuccess = false;
		if (!(po.playerID.equals("") || po.password.equals(""))) {
			set = db.excuteQue("SELECT * from player where ID='" + po.playerID
					+ "';");

			try {
				if (!set.next()) {
					isSuccess = db.excuteUpdate("insert into player values('"
							+ po.playerID + "','" + po.password + "',0,0,"
							+ po.money + "," + po.rank + ");");
				} else {
					isSuccess = false;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			isSuccess = false;
		}

		db.close();
		return isSuccess;
	}

	public PlayerPO login(PlayerPO po) {
		boolean isSuccess = false;
		if (!(po.playerID.equals("") || po.password.equals(""))) {
			set = db.excuteQue("SELECT * from player where ID='" + po.playerID
					+ "' and password='" + po.password + "';");

			try {
				if (!set.next()) {
					isSuccess = false;
					return null;
				} else {
					po.maxCombo = set.getInt("maxcombo");
					po.maxScore = set.getInt("maxscore");
					po.rank = set.getInt("rank");
					po.money = set.getInt("money");
					isSuccess = true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			isSuccess = false;
		}

		db.close();
		return po;
	}

	public boolean logout(PlayerPO po) {
		boolean isSuccess = false;
		if (!(po.playerID.equals("") || po.password.equals(""))) {
			set = db.excuteQue("SELECT * from player where ID='" + po.playerID
					+ "' and password='" + po.password + "';");

			try {
				if (!set.next()) {
					isSuccess = false;
				} else {
					isSuccess = true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			isSuccess = false;
		}
		db.close();
		return isSuccess;
	}

	public boolean updatePlayer(PlayerPO po) {
		boolean isSuccess = false;
		if (!(po.playerID.equals("") || po.password.equals(""))) {
			set = db.excuteQue("SELECT * from player where ID='" + po.playerID
					+ "';");

			try {
				if (set.next()) {
					PlayerPO pp = new PlayerPO();
					pp.playerID = set.getString("ID");
					pp.password = set.getString("password");
					pp.money = set.getInt("money");
					pp.maxCombo = set.getInt("maxCombo");
					pp.maxScore = set.getInt("maxScore");
					pp.rank = set.getInt("rank");
					String sql = new String();
					sql = "update player set ";
					boolean isModify = false;
					if (!pp.password.equals(po.password)) {
						sql = sql + "password='" + po.password + "'";
						isModify = true;
					}
					if (pp.maxCombo != po.maxCombo) {
						if (isModify) {
							sql = sql + ",";
						} else {
							isModify = true;
						}
						sql = sql + "maxCombo=" + po.maxCombo;
					}
					if (pp.maxScore != po.maxScore) {
						if (isModify) {
							sql = sql + ",";
						} else {
							isModify = true;
						}
						sql = sql + "maxScore=" + po.maxScore;
					}
					if (pp.money != po.money) {
						if (isModify) {
							sql = sql + ",";
						} else {
							isModify = true;
						}
						sql = sql + "money=" + po.money;
					}
					if (pp.rank != po.rank) {
						if (isModify) {
							sql = sql + ",";
						} else {
							isModify = true;
						}
						sql = sql + "rank=" + po.rank;
					}
					if (isModify) {
						sql = sql + " where ID='" + po.playerID + "';";
						isSuccess = db.excuteUpdate(sql);
					}
				} else {
					isSuccess = false;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			isSuccess = false;
		}

		db.close();
		return isSuccess;
	}

	public PlayerPO getPlayer(String id) {
		PlayerPO po = new PlayerPO();
		if (!id.equals("")) {
			set = db.excuteQue("SELECT * from player where ID='" + id + "';");

			try {
				if (set.next()) {
					po.playerID = set.getString("ID");
					po.password = set.getString("password");
					po.money = set.getInt("money");
					po.maxCombo = set.getInt("maxCombo");
					po.maxScore = set.getInt("maxScore");
					po.rank = set.getInt("rank");
				}
				else{
					return null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		db.close();
		return po;
	}

	public ArrayList<PlayerPO> getRecord(ArrayList<PlayerPO> poList) {
		for (PlayerPO po : poList) {
			if (!(po.playerID.equals(""))) {
				set = db.excuteQue("SELECT * from player where ID='"
						+ po.playerID + "';");

				try {
					if (set.next()) {
						po.playerID = set.getString("ID");
						po.money = set.getInt("money");
						po.maxCombo = set.getInt("maxCombo");
						po.maxScore = set.getInt("maxScore");
						po.rank = set.getInt("rank");
					}else{
						return null;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		db.close();
		return poList;
	}

	public ArrayList<PlayerPO> getPlayer(ArrayList<String> IDList) {
		ArrayList<PlayerPO> poList = new ArrayList<PlayerPO>();
		if (IDList.isEmpty()) {
			set = db.excuteQue("SELECT * from player");

			try {
				if(set.wasNull()){
					return null;
				}
				while (set.next()) {
					PlayerPO po = new PlayerPO();
					po.playerID = set.getString("ID");
					po.password = set.getString("password");
					po.money = set.getInt("money");
					po.maxCombo = set.getInt("maxCombo");
					po.maxScore = set.getInt("maxScore");
					po.rank = set.getInt("rank");
					poList.add(po);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			for (String id : IDList) {
				if (!id.equals("")) {
					PlayerPO po = new PlayerPO();
					set = db.excuteQue("SELECT * from player where ID='" + id
							+ "';");

					try {
						while (set.next()) {
							po.playerID = set.getString("ID");
							po.password = set.getString("password");
							po.money = set.getInt("money");
							po.maxCombo = set.getInt("maxCombo");
							po.maxScore = set.getInt("maxScore");
							po.rank = set.getInt("rank");
							poList.add(po);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		db.close();
		return poList;
	}

	public boolean removePlayer(String ID) {
		boolean isSuccess = false;
		if (!ID.equals("")) {
			set = db.excuteQue("SELECT * from player where ID='" + ID + "';");

			try {
				if (set.next()) {
					isSuccess = db.excuteUpdate("delete from player where ID='"
							+ ID + "';");
				} else {
					isSuccess = false;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			isSuccess = false;
		}

		db.close();
		return isSuccess;
	}

	public ArrayList<PlayerPO> getRank(ArrayList<String> IDList) {
		ArrayList<PlayerPO> poList = new ArrayList<PlayerPO>();
		for (String id : IDList) {
			if (!id.equals("")) {
				set = db.excuteQue("SELECT * from player where ID='" + id
						+ "';");

				try {
					while (set.next()) {
						PlayerPO po = new PlayerPO();
						po.playerID = set.getString("ID");
						po.rank = set.getInt("rank");
						poList.add(po);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		db.close();
		return poList;
	}
}
