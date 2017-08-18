package data.friends;

import java.util.ArrayList;

import po.FriendsPO;
import po.PlayerPO;

public interface FriendsDataService {
	public boolean inviteFriends(FriendsPO po);
	public boolean refuseFriends(FriendsPO po);
	public boolean receiveFriends(FriendsPO po);
	public boolean removeFriends(FriendsPO po);
	public ArrayList<PlayerPO> getFriends(String id);
}
