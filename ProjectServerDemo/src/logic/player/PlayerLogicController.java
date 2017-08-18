package logic.player;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;

import org.jfree.data.category.DefaultCategoryDataset;

import po.GamePO;
import po.PlayerPO;
import po.RadioPO;
import po.RankListPO;
import data.friends.FriendsDataController;
import data.friends.FriendsDataService;
import data.game.GameDataController;
import data.game.GameDataService;
import data.player.PlayerDataController;
import data.player.PlayerDataService;
import data.radio.RadioDataController;
import data.radio.RadioDataService;

public class PlayerLogicController extends UnicastRemoteObject implements PlayerLogicService{

	static ArrayList<String> onLine = new ArrayList<String>();
	PlayerDataService pdc;
	FriendsDataService fdc;
	static GameDataService gdc;
	RadioDataService rdc;

	
	public PlayerLogicController() throws RemoteException{
		super();
		pdc = new PlayerDataController();
		fdc = new FriendsDataController();
		gdc = new GameDataController();
		rdc = new RadioDataController();
	}
	public static void main(String args[]) {
		GamePO gamePO = new GamePO();
		gamePO.gameNO = "4";
		gamePO.playerID="player9";
		gamePO.combo=10;
		gamePO.score=100;
		gamePO.time="2014-6-13";
		try {
			PlayerLogicController p=new PlayerLogicController();
			ArrayList<Integer> list=p.getStaticStatistics("player1");
			for(Integer i:list){
				System.out.println(i);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println(gdc.initGame(poList));
	}
	@Override
	public boolean registry(String name, String code) throws RemoteException {
		// TODO Auto-generated method stub
		PlayerPO player = new PlayerPO();
		player.playerID = name;
		player.password = code;
		player.money = 2000;
		
		return pdc.register(player);
	}
	@Override
	public boolean logout(String name, String code) throws RemoteException {
		// TODO Auto-generated method stub
		PlayerPO player = new PlayerPO();
		player.playerID = name;
		player.password = code;
		
		boolean bool =  pdc.logout(player);
		if(bool)
			onLine.remove(name);
		return bool;
	}
	@Override
	public PlayerPO login(String name, String password) throws RemoteException {
		// TODO Auto-generated method stub
		PlayerPO player = new PlayerPO();
		if(onLine.contains(name)){
			return null;
		}
		player.playerID = name;
		player.password = password;
		PlayerPO rePlayer = pdc.login(player);
		if(rePlayer != null)
			onLine.add(name);
		return rePlayer;
	}
	@Override
	public boolean updatePlayer(PlayerPO player) throws RemoteException {
		// TODO Auto-generated method stub
		return pdc.updatePlayer(player);
	}
	@Override
	public PlayerPO getPlayer(String playerID) throws RemoteException {
		// TODO Auto-generated method stub
		return pdc.getPlayer(playerID);
	}
	@Override
	public ArrayList<PlayerPO> getFreinds(String playerID)
			throws RemoteException {
		// TODO Auto-generated method stub
		return fdc.getFriends(playerID);
	}


	@Override
	public RankListPO getFriendRank(String playerID) throws RemoteException {
		// TODO Auto-generated method stub
		//PlayerPO po = pdc.getPlayer(ID);
		RankListPO list = new RankListPO();
		ArrayList<PlayerPO> friends = getFreinds(playerID);
		PlayerPO player = getPlayer(playerID);
		int inter = 1;
		int myRank = player.rank;
		for(int i = 0; i < friends.size(); i++) {
			if(myRank < friends.get(i).rank) {
 				inter++;
			}
		}
		int size = friends.size();
		for(int k = 0; k < size; k++) {
			PlayerPO maxRank = friends.get(0);
			for(int j = 0; j < friends.size(); j++) {
				if(maxRank.rank < friends.get(j).rank)
					maxRank = friends.get(j);
			}
			friends.remove(maxRank);
			list.players.add(maxRank);
		}
		
		
		list.ranking = inter;
		list.player = player;
		
		
		return list;
	}
	@Override
	public String[] updateRadio() throws RemoteException {
		// TODO Auto-generated method stub
		//System.out.println("come in");
		String[] radioList = new String[5];
		RadioPO lastRadio = rdc.getLastRadio();
		int number = lastRadio.number;
		radioList[0] = lastRadio.message;
		System.out.println(lastRadio.message);
		for(int i = 1; i < 5; i++){
			if(rdc.getRadio(number - i) == null)
				radioList[i] = "";
			else
				radioList[i] = rdc.getRadio(number - i).message;
			System.out.println("int "+i+" message:"+radioList[i]);
		}
		System.out.println(radioList);
		return radioList;
		
	}
	@Override
	public ArrayList<GamePO> getHistory(String name) throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<GamePO> gamePOs = gdc.getGame(name);
		for(int i=gamePOs.size()-1;i>0;i--){
			for(int j=1;j<=i;j++){
				if(Integer.parseInt(gamePOs.get(j-1).time.substring(0, 8))>Integer.parseInt(gamePOs.get(j).time.substring(0,8))){
					gamePOs.add(j+1, gamePOs.get(j-1));
					gamePOs.remove(j-1);
					
				}
			}
		}
		return gamePOs;
	}
	@Override
	public boolean saveHistory(GamePO gameRecord) throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<GamePO> gameList = new ArrayList<GamePO>();
		gameList.add(gameRecord);
		PlayerPO player=null;
		try
		{
			 player = getPlayer(gameRecord.playerID);
		}catch(NullPointerException e)
		{
			player = new PlayerPO();
		}
		
		if(player.maxCombo<gameRecord.combo){
			player.maxCombo=gameRecord.combo;
		}
		if(player.maxScore<gameRecord.score){
			player.maxScore=gameRecord.score;
		}
		System.out.println("记录："+player.maxScore);
		System.out.println("记录："+player.maxCombo);
		return gdc.recordGame(gameList)&&pdc.updatePlayer(player);
	}
	@Override
	public ArrayList<PlayerPO> getAllPlayers() throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<String> IDlist = new ArrayList<String>();
		return pdc.getPlayer(IDlist);
	}
	@Override
	public RankListPO getAllRank(String playerID) throws RemoteException {
		// TODO Auto-generated method stub
		RankListPO list = new RankListPO();
		ArrayList<PlayerPO> topDown = new ArrayList<PlayerPO>();
		ArrayList<PlayerPO> players = getAllPlayers(); 
		
		if(players == null)
			System.out.println("null");
		if(players.isEmpty())
			System.out.println("Empty");
														//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
																	//此处改为获取所有用户
		PlayerPO player = getPlayer(playerID);
		System.out.println(player);
		if(player == null) {
			return null;
		}else{
		int inter = 1;
		int myScore = player.maxScore;
		for(int i = 0; i < players.size(); i++) {
			if(myScore < players.get(i).maxScore) {
 				inter++;
			}
		}
		list.ranking = inter;
		list.player = player;
		
		int size = players.size();
		for(int k = 0; k < 5; k++) {
			PlayerPO maxScore = new PlayerPO();
			if(players.isEmpty()){
				
				return list;
			}
			maxScore = players.get(0);
			for(int j = 0; j < players.size(); j++) {
				if(maxScore.maxScore < players.get(j).maxScore)
					maxScore = players.get(j);
			}
			players.remove(maxScore);
			topDown.add(maxScore);
			list.players = topDown;
		}
		return list;
		}
	}
	
	@Override
	public ArrayList<Integer> getStaticStatistics(String name) throws RemoteException {
		ArrayList<Integer> result = new ArrayList<>();
		ArrayList<GamePO> gamePOs = getHistory(name);
		PlayerPO playerPO = getPlayer(name);
		int countOfGame = gamePOs.size();
		int averageScore = 0;
		for(GamePO gamePO : gamePOs){
			averageScore+=gamePO.score;
		}
		
		int count=gamePOs.size();
		int left = 5 - count;
		for(int i=0;i<count-1;i++){
			for(int j=0;j<count-i-1;j++){
				if(gamePOs.get(j).score<gamePOs.get(j+1).score){
					Collections.swap(gamePOs, j, j+1);
				}
			}
		}
		if(gamePOs.size()==0){
			averageScore =0;
		}else{
			averageScore=averageScore/(gamePOs.size());
		}
		int maxCombo = playerPO.maxCombo;
		int maxScore = playerPO.maxScore;
		result.add(countOfGame);
		result.add(averageScore);
		result.add(maxCombo);
		result.add(maxScore);
		//新加五个最近得分纪录
		if(gamePOs.size()>5){
			for(int i=0;i<5;i++){
				result.add(gamePOs.get(i).score);
			}
		}else{
			for(GamePO gp:gamePOs){
				result.add(gp.score);
			}
			for(int i = 0; i < left; i++) {
				
				result.add(0);
			}
		}
		
		return result;
	}
	
	@Override
	public DefaultCategoryDataset getCountOfGameLineData(String name)throws RemoteException{
		DefaultCategoryDataset linedataset = new DefaultCategoryDataset();
		   // 各曲线名称
		String series1 = "";
		   // 横轴名称(列名称)
		
		ArrayList<GamePO> gamePOs = getHistory(name);
		String currentDay =null;
		int countOfCertainDay=0;

		for(int i=0;i<gamePOs.size();i++){

			if(gamePOs.get(i).time.substring(0, 8).equals(currentDay)){
				countOfCertainDay++;
			}//如果相同的话，局数增加
			else{//不相同，则将现在的东西
				if(currentDay!=null){//如果执行第一次判断可能会出现null的情况
					linedataset.addValue(countOfCertainDay, series1, currentDay.substring(4, 8));
				}
				currentDay = gamePOs.get(i).time.substring(0, 8);
				countOfCertainDay=1;

			}
		}
		if(currentDay!=null){
			linedataset.addValue(countOfCertainDay, series1, currentDay.substring(4, 8));//加入最后一条信息
		}
		int column = linedataset.getColumnCount();
		if(column>7){
			for(int i=column-7;i>0;i--){
				linedataset.removeColumn(0);
			}
		}
			
		
		return linedataset;

		
	}

	@Override
	public DefaultCategoryDataset getAverageScorePerDay(String name)throws RemoteException{
		DefaultCategoryDataset linedataset = new DefaultCategoryDataset();
		   // 各曲线名称
		String series1 = "";
		   // 横轴名称(列名称)
		
		ArrayList<GamePO> gamePOs = getHistory(name);
		String currentDay =null;
		int countOfCertainDay=0;
		int averageScore=0;

		for(int i=0;i<gamePOs.size();i++){

			if(gamePOs.get(i).time.substring(0, 8).equals(currentDay)){
				countOfCertainDay++;
				averageScore+=gamePOs.get(i).score;
			}//如果相同的话，局数增加
			else{//不相同，则将现在的东西
				if(currentDay!=null){//如果执行第一次判断可能会出现null的情况
					linedataset.addValue(averageScore/countOfCertainDay, series1, currentDay.substring(4, 8));
				}
				currentDay = gamePOs.get(i).time.substring(0, 8);
				countOfCertainDay=1;
				averageScore=gamePOs.get(i).score;
			}
		}
		if(currentDay!=null){
			linedataset.addValue(averageScore/countOfCertainDay, series1, currentDay.substring(4, 8));//加入最后一条信息
		}
		int column = linedataset.getColumnCount();
		if(column>7){
			for(int i=column-7;i>0;i--){
				linedataset.removeColumn(0);
			}
		}
			
		
		return linedataset;
		
	}




}

