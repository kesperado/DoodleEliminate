package ui;

import java.awt.Font;
import java.rmi.RemoteException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import logic.player.PlayerLogicService;
import po.PlayerPO;
import po.RankListPO;
import rmi.RMIController;

@SuppressWarnings("serial")
public class RankPanel extends JPanel {
	private RMIController rmiController = new RMIController();
	private PlayerLogicService playerLogicService = rmiController
			.getPlayerService();

	private int width = 600;
	private int height = 600;

	private int x = 0;
	private int y = 0;

	private Icon t1;
	private Icon t2;
	private Icon t3;
	private Icon t4;
	private Icon t5;
	private Icon t6;

	private JLabel top1;
	private JLabel top2;
	private JLabel top3;
	private JLabel top4;
	private JLabel top5;
	private JLabel mytop2;
	private JLabel mytop;

	private JLabel rank1;
	private JLabel rank2;
	private JLabel rank3;
	private JLabel rank4;
	private JLabel rank5;
	private JLabel myrank;

	private JLabel score1;
	private JLabel score2;
	private JLabel score3;
	private JLabel score4;
	private JLabel score5;
	private JLabel myscore;

	private MainPanel mp;
	private PlayerPO po;

	RankPanel(MainPanel mp, PlayerPO po) {
		this.po = po;
		this.mp = mp;
		this.setLayout(null);
		this.setBounds(x, y, width, height);
		this.setVisible(true);
		this.setOpaque(false);

		t1 = new ImageIcon("image/info/top1.png");
		t2 = new ImageIcon("image/info/top2.png");
		t3 = new ImageIcon("image/info/top3.png");
		t4 = new ImageIcon("image/info/top4.png");
		t5 = new ImageIcon("image/info/top5.png");
		t6 = new ImageIcon("image/info/mytop.png");

		top1 = new JLabel(t1);
		top2 = new JLabel(t2);
		top3 = new JLabel(t3);
		top4 = new JLabel(t4);
		top5 = new JLabel(t5);
		mytop = new JLabel(t6);

		Font font = new Font("微软雅黑", Font.PLAIN, 22);
		rank1 = new JLabel();
		rank1.setFont(font);
		rank2 = new JLabel();
		rank2.setFont(font);
		rank3 = new JLabel();
		rank3.setFont(font);
		rank4 = new JLabel();
		rank4.setFont(font);
		rank5 = new JLabel();
		rank5.setFont(font);
		myrank = new JLabel();
		myrank.setFont(font);
		mytop2 = new JLabel();
		mytop2.setFont(font);
		score1 = new JLabel();
		score1.setFont(font);
		score2 = new JLabel();
		score2.setFont(font);
		score3 = new JLabel();
		score3.setFont(font);
		score4 = new JLabel();
		score4.setFont(font);
		score5 = new JLabel();
		score5.setFont(font);
		myscore = new JLabel();
		myscore.setFont(font);

		init();
	}

	void init() {
		top1.setBounds(130, 50, 60, 60);
		add(top1);
		top2.setBounds(130, 120, 60, 60);
		add(top2);
		top3.setBounds(130, 190, 60, 60);
		add(top3);
		top4.setBounds(130, 260, 60, 60);
		add(top4);
		top5.setBounds(130, 330, 60, 60);
		add(top5);
		mytop.setBounds(130, 423, 60, 60);
		add(mytop);
		mytop2.setBounds(153, 440, 20, 20);
		add(mytop2);

		rank1.setBounds(206, 50, 120, 60);
		add(rank1);
		rank2.setBounds(206, 120, 120, 60);
		add(rank2);
		rank3.setBounds(206, 190, 120, 60);
		add(rank3);
		rank4.setBounds(206, 260, 120, 60);
		add(rank4);
		rank5.setBounds(206, 330, 120, 60);
		add(rank5);
		myrank.setBounds(206, 423, 120, 60);
		add(myrank);

		score1.setBounds(357, 50, 150, 60);
		add(score1);
		score2.setBounds(357, 120, 150, 60);
		add(score2);
		score3.setBounds(357, 190, 150, 60);
		add(score3);
		score4.setBounds(357, 260, 150, 60);
		add(score4);
		score5.setBounds(357, 330, 150, 60);
		add(score5);
		myscore.setBounds(357, 423, 150, 60);
		add(myscore);

		updateData();
	}

	void updateData() {
		RankListPO list = null;
		try {
			list = playerLogicService.getAllRank(po.playerID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "网络连接中断");
		}
		rank1.setText(list.players.get(0).playerID);
		score1.setText(list.players.get(0).maxScore + "");
		System.out.println(list.players.get(0).maxScore + "这是第一名！！！！");
		rank2.setText(list.players.get(1).playerID);
		score2.setText(list.players.get(1).maxScore + "");
		rank3.setText(list.players.get(2).playerID);
		score3.setText(list.players.get(2).maxScore + "");
		rank4.setText(list.players.get(3).playerID);
		score4.setText(list.players.get(3).maxScore + "");
		rank5.setText(list.players.get(4).playerID);
		score5.setText(list.players.get(4).maxScore + "");
		myrank.setText(list.player.playerID);
		mytop2.setText(list.ranking + "");
		try {
			myscore.setText(playerLogicService.getPlayer(po.playerID).maxScore
					+ "");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		repaint();
	}
}
