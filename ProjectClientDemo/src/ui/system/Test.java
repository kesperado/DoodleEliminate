//package ui.system;
//
//import java.rmi.RemoteException;
//import java.util.Scanner;
//
//import logic.game.GameLogicService;
//import logic.game.GameService;
//import logic.netcore.NetcoreLogicService;
//import rmi.RMIController;
//import ui.game.GameListener;
//import ui.game.single.SingleFrame;
//
//public class Test {
//	public Test() {
//		try {
//
//			RMIController rmi = new RMIController();
//			NetcoreLogicService ns = rmi.getNetworkService();
//			GameService gcls = rmi.getGameService();
//
//			SingleFrame first = new SingleFrame();
//			SingleFrame second = new SingleFrame();
//			GameListener f = new GameListener(first);
//			GameListener l = new GameListener(second);
//			System.out.println("初始化完成");
//
//			int number = gcls.startCoopGame();
//			System.out.println("This Client Get The Game" + number);
//			GameLogicService gls = gcls.getCoopGame(number);
//			// gls.addListener(l);
//
//			Scanner s = new Scanner(System.in);
//			while (true) {
//				String temple = s.nextLine();
//				// gls.exchange(0, 0, 0, 0);
//			}
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
//	}
// }
