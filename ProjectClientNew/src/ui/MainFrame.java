package ui;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import logic.game.GameService;
import po.PlayerPO;
import rmi.RMIController;
import ui.game.common.GamePanel;

@SuppressWarnings("serial")
public class MainFrame extends JFrame
{
	RMIController rmi = new RMIController();
	GameService gs = rmi.getGameService();
	private int width = 800;
	private int height = 600;

	private RegisterPanel rp;
	private LoginPanel lp;
	private GamePanel sf;
	// private SingleGamePanel sf;
	private MainPanel mp;

	private int state = 0;// 0是login的状态 最初始化的状态 1是register的状态 2就是进去了
							// mainpanel的状态

	private int xx, yy;
	private boolean isDraging = false;

	MainFrame()
	{
		this.setTitle("涂鸦消除");
		this.setSize(width, height);
		this.setUndecorated(true);
		int x = (Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2;
		int y = (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(x, y);
		this.setLayout(null);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(
				"image/system/icon.png"));

		this.addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				isDraging = true;
				xx = e.getX();
				yy = e.getY();
			}

			public void mouseReleased(MouseEvent e)
			{
				isDraging = false;
			}
		});
		this.addMouseMotionListener(new MouseMotionAdapter()
		{
			public void mouseDragged(MouseEvent e)
			{
				if (isDraging)
				{
					int left = getLocation().x;
					int top = getLocation().y;
					setLocation(left + e.getX() - xx, top + e.getY() - yy);
				}
			}
		});
		init();
	}

	void init()
	{

		lp = new LoginPanel(this);
		add(lp);

		rp = new RegisterPanel(this);
	}

	public void changePanel(JPanel aim)
	{
		this.getContentPane().removeAll();
		this.add(aim);
		this.getContentPane().validate();
		this.repaint();
	}

	public void login(PlayerPO po)
	{
		state = 2;
		this.remove(lp);
		this.remove(rp);
		mp = new MainPanel(po);
		add(mp);
		repaint();
	}

	public void register_play()
	{
		state = 1;
		this.remove(lp);
		rp.init();
		add(rp);
		repaint();
	}

	public void back_game()
	{
		state = 0;
		this.remove(mp);
		add(lp);
		repaint();
	}

	public void back_regis()
	{
		state = 0;
		this.remove(rp);
		add(lp);
		repaint();
	}

	public static void main(String args[])
	{
		MainFrame mf = new MainFrame();
		mf.setVisible(true);
	}
}
