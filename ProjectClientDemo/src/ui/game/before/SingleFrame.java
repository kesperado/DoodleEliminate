//package ui.game.before;
//
//import java.rmi.RemoteException;
//
//import javax.swing.JFrame;
//import javax.swing.JOptionPane;
//
//import logic.game.GameLogicService;
//import logic.game.GameService;
//import logic.game.Message;
//import rmi.RMIController;
//import ui.game.GameListener;
//
//public class SingleFrame extends JFrame {
//	private static int Frame_width = 700;// 界面宽
//	private static int Frame_height = 600;// 界面高
//
//	private EliminatePanel jp1;// 这是消除主面板
//	private HeadPanel jp2;// 这是头像和道具面板
//	private TimerPanel jp3;// 这是倒计时面板
//	private CounterPanel jp4;// 这是计分器面板
//
//	private GameLogicService gls;// 获得信息的对象
//
//	private Message message;
//
//	public SingleFrame() {
//		this.setTitle("单机消除");
//		this.setBounds(270, 80, Frame_width, Frame_height);
//		this.setResizable(false);
//		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		getContentPane().setLayout(null);
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
//		jp1 = new EliminatePanel("Raven", gls, this);
//		jp1.setLocation(10, 10);
//		getContentPane().add(jp1);
//
//		jp2 = new HeadPanel(this);
//		jp2.setLocation(507, 199);
//		getContentPane().add(jp2);
//
//		jp3 = new TimerPanel(this);
//		jp3.setLocation(10, 489);
//		getContentPane().add(jp3);
//
//		jp4 = new CounterPanel(this);
//		jp4.setLocation(507, 10);
//		getContentPane().add(jp4);
//
//	}
//
//	public void alert() {
//		try {
//			message = gls.getMessage();
//			for(int i=0;i<9;i++)
//			{
//				for(int j=0;j<9;j++)
//				{
//					System.out.print(message.list.get(message.list.size()-1)[i][j]);
//				}
//				System.out.println();
//			}
//			switch (message.imformation) {
//
//			case "End":
//				JOptionPane.showMessageDialog(null, "Times UP!");
//				break;
//			default:
//				jp1.getRenew();
//				break;
//			}
//
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public void updateMark(int mark) {
//		jp4.updateMark(mark);
//	}
//
//	public static void main(String args[]) {
//
//		SingleFrame sf = new SingleFrame();
//		sf.setVisible(true);
//
//	}
// }
