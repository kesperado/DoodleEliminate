//package test;
//
//import java.rmi.RemoteException;
//
//import javax.swing.JFrame;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.border.TitledBorder;
//
//import logic.game.GameLogicService;
//import logic.game.GameService;
//import logic.game.Message;
//import rmi.RMIController;
//import ui.game.GameListener;
//
//@SuppressWarnings("serial")
//public class MockFrame extends JFrame {
//	private MockPanel mp;
//	private GameLogicService gls;// 获得信息的对象
//	private Message message;
//
//	public MockFrame() {
//		this.setBounds(270, 40, 800, 600);
//		this.setLayout(null);
//
//		JPanel jp = new JPanel();
//		jp.setBounds(0, 0, 200, 600);
//		jp.setBorder(new TitledBorder("旁边的功能框"));
//		this.add(jp);
//
//		RMIController rmi = new RMIController();
//		GameService gs = rmi.getGameService();
//		int gameno = 0;
//		try {
//			GameListener gl = new GameListener(this);
//			gameno = gs.startSingleGame();
//			System.out.println("游戏编号" + gameno);
//			gls = gs.getSingleGame(gameno);
//			gls.addListener(gl, "TestPlayer");
//		} catch (RemoteException e1) {
//			System.out.println("Error!");
//			e1.printStackTrace();
//		}
//
//		mp = new MockPanel("Rave", gls, this);
//		this.add(mp);// 成功把监听限制在panel上
//	}
//
//	public void alert() {
//		try {
//			message = gls.getMessage();
//			for (int i = 0; i < 9; i++) {
//				for (int j = 0; j < 9; j++) {
//					System.out
//							.print(message.list.get(message.list.size() - 1)[i][j]);
//				}
//				System.out.println();
//			}
//			switch (message.imformation) {
//
//			case "End":
//				JOptionPane.showMessageDialog(null, "Times UP!");
//				break;
//			default:
//				mp.getRenew();
//				break;
//			}
//
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public static void main(String args[]) {
//		new MockFrame().setVisible(true);
//	}
//
// }
