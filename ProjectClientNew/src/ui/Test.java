package ui;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JPanel;

import logic.admin.AdminLogicService;
import logic.equipment.EquipmentLogicService;
import logic.equipment.StoreLogicService;
import logic.player.PlayerLogicService;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import po.EquipmentPO;
import po.GamePO;
import po.PlayerPO;
import rmi.RMIController;

/**
 * @author 作者 :knox
 * @version 创建时间：2014-6-12 下午2:21:56
 * 类说明
 */
public class Test extends ApplicationFrame{

	public Test(String title,DefaultCategoryDataset dataset) {
		super(title);
		setContentPane(createDemoLine(dataset));
		// TODO Auto-generated constructor stub
	}

	private static RMIController rmiController = new RMIController();
	
	public static void main(String[] args) throws RemoteException{
		PlayerPO playerPO = new PlayerPO();
		playerPO.playerID="player2";
		playerPO.password="123";
		playerPO.maxCombo=10;
		playerPO.maxScore=10;
		playerPO.money=10;
		playerPO.rank=10;
		
		GamePO gamePO = new GamePO();
		gamePO.gameNO = "7";
		gamePO.playerID="player1";
		gamePO.combo=111;
		gamePO.score=1111;
		gamePO.time="2014-6-12";
		PlayerLogicService playerLogicService = rmiController.getPlayerService();
		EquipmentLogicService equipmentLogicService = rmiController.getEquipmentService();
		StoreLogicService storeLogicService = rmiController.getStoreService();
		AdminLogicService adminLogicService = rmiController.getAdminService();
//		
//
//		Test fjc = new Test("折线图",playerLogicService.getCountOfGameLineData("player1"));
//		fjc.pack();
//		RefineryUtilities.centerFrameOnScreen(fjc);
//		fjc.setVisible(true);
		
//		Test fjc = new Test("折线图",playerLogicService.getAverageScorePerDay("player1"));
//		fjc.pack();
//		RefineryUtilities.centerFrameOnScreen(fjc);
//		fjc.setVisible(true);
//		
		
		//数组中有4个int，分别是总局数，平均得分，最高连击，最高得分
//		ArrayList<Integer> result = playerLogicService.getStaticStatistics("player1");
//		for(Integer integer : result){
//			System.out.println(integer+"");
//		}
		
		
//		System.out.println(playerLogicService.login("player0", "123"));
		
//		System.out.println(playerLogicService.registry("player1", "123"));
		
//		System.out.println(playerLogicService.updatePlayer(playerPO));
		
//		System.out.println(playerLogicService.getPlayer("player1").rank);
		
//		System.out.println(adminLogicService.spreadRadio(null));
		
//		String[] result = playerLogicService.updateRadio();
//		for (String one: result){
//			System.out.println(one);
//		}
		
		
//		System.out.println(playerLogicService.saveHistory(gamePO));

//		
//		ArrayList<GamePO> gamePOs = playerLogicService.getHistory("player1");
//		for(GamePO gamePO2:gamePOs){
//			System.out.println(gamePO2.gameNO+" "+gamePO.playerID);
//		}
		
//		ArrayList<PlayerPO> playerPOs = playerLogicService.getAllPlayers();
//		for(PlayerPO playerPO2: playerPOs){
//			System.out.println(playerPO2.playerID+" "+playerPO2.rank);
//		}
		
//		RankListPO rankListPO = playerLogicService.getAllRank("player4");
		
//		ArrayList<PlayerPO> playerPOs =rankListPO.players;
//		for(PlayerPO playerPO2: playerPOs){
//			System.out.println(playerPO2.playerID+" "+playerPO2.rank);
//		}

		
		
//		StorePO storePO = new StorePO();
//		storePO.equipmentType="C";
//		storePO.price=100;
//		System.out.println(storeLogicService.addStore(storePO));
		//既然不能加入两遍C，那么就直接用SQL直接初始化脚本就行
		
		
		
		
		
//		System.out.println(equipmentLogicService.buyEquipment("player1", "C", 10));
		
		
		ArrayList<EquipmentPO> equipmentPOs = equipmentLogicService.getList("player1");
		
		for (EquipmentPO equipmentPO:equipmentPOs){
			System.out.println(equipmentPO.playerID+" "+equipmentPO.type+" "+equipmentPO.num);
			
		}
		
		
//		equipmentLogicService.useEquipment("player1", "C");
		
		
		
		
	}
	
	// 生成显示图表的面板
	public static JPanel createDemoLine(DefaultCategoryDataset dataset) {
	   JFreeChart jfreechart = createChart(dataset);
	   return new ChartPanel(jfreechart);
	}
	// 生成图表主对象JFreeChart
	public static JFreeChart createChart(DefaultCategoryDataset linedataset) {
	   // 定义图表对象
	   JFreeChart chart = ChartFactory.createLineChart("haha", //折线图名称
	     "aa", // 横坐标名称
	     "bb", // 纵坐标名称
	     linedataset, // 数据
	     PlotOrientation.VERTICAL, // 水平显示图像
	     false, // include legend
	     false, // tooltips
	     false // urls
	     );
	   CategoryPlot plot = chart.getCategoryPlot();
	   plot.setRangeGridlinesVisible(true); //是否显示格子线
	   plot.setBackgroundAlpha(0.3f); //设置背景透明度
	   NumberAxis rangeAxis = (NumberAxis)plot.getRangeAxis();
	   rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	   rangeAxis.setAutoRangeIncludesZero(true);
	   rangeAxis.setUpperMargin(0.20);
	   rangeAxis.setLabelAngle(Math.PI / 2.0);
	   return chart;
	}
	

}
