package data.friends;

import java.util.ArrayList;

import po.FriendsPO;
import po.PlayerPO;

public class FriendsDataController implements FriendsDataService{

	private FriendsDataHelper friends;
	
	public FriendsDataController(){
		friends=new FriendsDataHelper();
	}
	
	@Override
	public boolean inviteFriends(FriendsPO po) {
		// TODO Auto-generated method stub
		return friends.inviteFriends(po);
	}

	@Override
	public boolean refuseFriends(FriendsPO po) {
		// TODO Auto-generated method stub
		return friends.refuseFriends(po);
	}

	@Override
	public boolean receiveFriends(FriendsPO po) {
		// TODO Auto-generated method stub
		return friends.receiveFriends(po);
	}

	@Override
	public boolean removeFriends(FriendsPO po) {
		// TODO Auto-generated method stub
		return friends.removeFriends(po);
	}

	@Override
	public ArrayList<PlayerPO> getFriends(String id) {
		// TODO Auto-generated method stub
		return friends.getFriends(id);
	}

}
