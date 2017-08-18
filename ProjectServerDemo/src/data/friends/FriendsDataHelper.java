package data.friends;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import data.DBHelper;
import data.player.PlayerDataHelper;
import po.FriendsPO;
import po.PlayerPO;

public class FriendsDataHelper {

	private DBHelper db;
	private ResultSet set;

	public FriendsDataHelper() {
		db = DBHelper.getInstance();
	}

	public static void main(String[] args) {
		FriendsDataHelper f = new FriendsDataHelper();
		FriendsPO fp = new FriendsPO();
		fp.invitee = "12";
		fp.inviter = "13";
		ArrayList<PlayerPO> fList=new ArrayList<PlayerPO>();
		fList=f.getFriends("12");
		System.out.println(fList.size());
		for(PlayerPO po:fList){
			System.out.println(po);
		}
	}

	public boolean inviteFriends(FriendsPO po) {
		boolean isSuccess = false;
		if (!(po.invitee.equals("") || po.inviter.equals("") || po.invitee
				.equals(po.inviter))) {
			set = db.excuteQue("select * from friends where inviter='"
					+ po.inviter + "' and invitee='" + po.invitee + "';");
			try {
				if (set.next()) {
					isSuccess = false;
				} else {
					set.close();
					set = db.excuteQue("select * from friends where inviter='"
							+ po.invitee + "' and invitee='" + po.inviter
							+ "';");
					if (set.next()) {
						isSuccess = false;
					} else {
						isSuccess = db
								.excuteUpdate("insert into friends values('"
										+ po.inviter + "','" + po.invitee
										+ "'," + po.status + ")");
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		 System.out.println(isSuccess);
		return isSuccess;
	}

	public boolean refuseFriends(FriendsPO po) {
		boolean isSuccess = false;

		if (!(po.invitee.equals("") || po.inviter.equals("") || po.invitee
				.equals(po.inviter))) {
			set = db.excuteQue("select * from friends where inviter='"
					+ po.inviter + "' and invitee='" + po.invitee
					+ "' and status=" + po.status + ";");

			try {
				if (set.next()) {
					isSuccess = db
							.excuteUpdate("delete from friends where invitee='"
									+ po.invitee + "' and inviter='"
									+ po.inviter + "';");
				} else {
					isSuccess = false;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		db.close();
		return false;
	}

	public boolean receiveFriends(FriendsPO po) {
		boolean isSuccess = false;

		if (!(po.invitee.equals("") || po.inviter.equals("") || po.invitee
				.equals(po.inviter))) {
			set = db.excuteQue("select * from friends where inviter='"
					+ po.inviter + "' and invitee='" + po.invitee
					+ "' and status=" + false + ";");

			try {
				if (set.next()) {

					isSuccess = db.excuteUpdate("update friends set status="
							+ true + " where invitee='" + po.invitee
							+ "' and inviter='" + po.inviter + "';");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return isSuccess;
	}

	public boolean removeFriends(FriendsPO po) {
		boolean isSuccess = false;

		if (!(po.invitee.equals("") || po.inviter.equals("") || po.invitee
				.equals(po.inviter))) {
			set = db.excuteQue("select * from friends where inviter='"
					+ po.inviter + "' and invitee='" + po.invitee
					+ "' and status=" + true + ";");

			try {
				if (set.next()) {

					isSuccess = db
							.excuteUpdate("delete from friends where invitee='"
									+ po.invitee + "' and inviter='"
									+ po.inviter + "';");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return isSuccess;
	}

	public ArrayList<PlayerPO> getFriends(String id) {
		PlayerDataHelper playerDB = new PlayerDataHelper();

		ArrayList<PlayerPO> poList = new ArrayList<PlayerPO>();
		ArrayList<String> fList = new ArrayList<String>();

		if (!id.equals("")) {
			set = db.excuteQue("select *from friends where (inviter='" + id
					+ "' or invitee='" + id + "') and status=" + true + ";");
			try {
				while (set.next()) {
					String inviter = set.getString("inviter");
					String invitee = set.getString("invitee");
					if (inviter.equals(id)) {
						fList.add(invitee);
					} else {
						fList.add(inviter);
					}
					System.out.println(fList.size());
					poList = playerDB.getPlayer(fList);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return poList;
	}
}
