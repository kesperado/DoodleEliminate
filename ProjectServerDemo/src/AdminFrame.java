import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;



public class AdminFrame extends JFrame
{
	JButton unlogin;
	JButton redio;
	JButton addPlayer;
	JButton delPlayer;
	JLabel information;
	public AdminFrame()
	{
		setTitle("Admin Controller");
		setBounds(200,100,300,600);
		setLayout(null);
		setVisible(true);
		
		unlogin = new JButton();
	}
}
