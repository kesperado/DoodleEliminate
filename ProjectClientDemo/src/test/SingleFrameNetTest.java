//package test;
//
//import java.rmi.RemoteException;
//
//import javax.swing.JFrame;
//import javax.swing.JOptionPane;
//
//import logic.game.GameLogicService;
//import logic.game.GameService;
//import logic.game.Message;
//import logic.netcore.NetcoreLogicService;
//import rmi.RMIController;
//import ui.game.GameListener;
//<<<<<<< .mine
//import ui.game.before.CounterPanel;
//import ui.game.before.EliminatePanel;
//import ui.game.before.HeadPanel;
//import ui.game.before.TimerPanel;
//=======
//import ui.game.single.CounterPanel;
//import ui.game.single.EliminatePanel;
//import ui.game.single.HeadPanel;
//import ui.game.single.SingleFrame;
//import ui.game.single.TimerPanel;
//>>>>>>> .r108
//
//public class SingleFrameNetTest extends SingleFrame {
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
//	public static void main(String[] args)
//	{
//		RMIController rmi = new RMIController();
//		NetcoreLogicService ns = rmi.getNetworkService();
//		GameService gcls = rmi.getGameService();
//
//		try
//		{
//			int number = gcls.startCoopGame();
//			System.out.println("This Client Get The Game" + number);
//			GameLogicService gls = gcls.getCoopGame(number);
//			
//			SingleFrameNetTest first = new SingleFrameNetTest(number);
//			SingleFrameNetTest second = new SingleFrameNetTest(number);
//			first.setVisible(true);
//			second.setVisible(true);
//			
//			GameListener f = new GameListener(first);
//			GameListener l = new GameListener(second);
//			
//			gls.addListener(f, "first");
//			gls.addListener(l, "second");
//		} catch (RemoteException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
//	
//	public SingleFrameNetTest(int number) {
//		this.setTitle("单机消除");
//		this.setBounds(270, 80, Frame_width, Frame_height);
//		this.setResizable(false);
//		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		getContentPane().setLayout(null);
//
//		RMIController rmi = new RMIController();
//		GameService gs = rmi.getGameService();
//		int gameno = number;
//		try {
//			GameListener gl = new GameListener(this);
//			System.out.println("游戏编号" + gameno);
//			gls = gs.getCoopGame(gameno);
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
//			JOptionPane.showMessageDialog(null, "Message!");
//			switch (message.imformation) {
//
//			case "End":
//				JOptionPane.showMessageDialog(null, "Times UP!");
//				break;
//			default:
//				//jp1.getRenew();
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
// }
