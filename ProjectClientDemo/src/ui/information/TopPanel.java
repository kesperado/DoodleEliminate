package ui.information;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TopPanel extends JPanel {
	private Image back;// 背景

	private Icon top1;
	private Icon top2;
	private Icon top3;
	private Icon top4;
	private Icon top5;
	private Icon mytop;// 盾徽对应的图片

	private JLabel t1;
	private JLabel t2;
	private JLabel t3;
	private JLabel t4;
	private JLabel t5;
	private JLabel mt;// 盾徽载体

	private Icon rank;// 排名的图片

	private JLabel r1;
	private JLabel r2;
	private JLabel r3;
	private JLabel r4;
	private JLabel r5;// 记录载体
	private JLabel r6;

	private JLabel n1;
	private JLabel n2;
	private JLabel n3;
	private JLabel n4;
	private JLabel n5; // 单纯的名字

	private JLabel s1;
	private JLabel s2;
	private JLabel s3;
	private JLabel s4;
	private JLabel s5;// 分数

	private JLabel myRank;// 我的排名
	private JLabel myScore;// 我的分数

	public TopPanel() {
		this.setSize(600, 600);
		setLayout(null);
		this.setOpaque(true);
		back = Toolkit.getDefaultToolkit().getImage("system/right2.png");

		top1 = new ImageIcon("system/top1.png");
		top2 = new ImageIcon("system/top2.png");
		top3 = new ImageIcon("system/top3.png");
		top4 = new ImageIcon("system/top4.png");
		top5 = new ImageIcon("system/top5.png");
		mytop = new ImageIcon("system/top6.png");
		rank = new ImageIcon("system/rank.png");

		init();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.WHITE);
		if (back != null) {
			int height = back.getHeight(this);
			int width = back.getWidth(this);
			if (height != -1 && height > getHeight())
				height = getHeight();
			if (width != -1 && width > getWidth())
				width = getWidth();
			int x = (int) (((double) (getWidth() - width)) / 2.0);
			int y = (int) (((double) (getHeight() - height)) / 2.0);
			g.drawImage(back, x, y, width, height, this);
		}
	}

	void init() {
		t1 = new JLabel(top1);
		t1.setBounds(50, 25, 100, 75);
		add(t1);

		t2 = new JLabel(top2);
		t2.setBounds(50, 110, 100, 75);
		add(t2);

		t3 = new JLabel(top3);
		t3.setBounds(50, 195, 100, 75);
		add(t3);

		t4 = new JLabel(top4);
		t4.setBounds(50, 280, 100, 75);
		add(t4);

		t5 = new JLabel(top5);
		t5.setBounds(50, 365, 100, 75);
		add(t5);

		mt = new JLabel(mytop);
		mt.setBounds(50, 475, 100, 75);
		add(mt);

		r1 = new JLabel(rank);
		r1.setBounds(200, 25, 300, 75);
		add(r1);

		r2 = new JLabel(rank);
		r2.setBounds(200, 110, 300, 75);
		add(r2);

		r3 = new JLabel(rank);
		r3.setBounds(200, 195, 300, 75);
		add(r3);

		r4 = new JLabel(rank);
		r4.setBounds(200, 280, 300, 75);
		add(r4);

		r5 = new JLabel(rank);
		r5.setBounds(200, 365, 300, 75);
		add(r5);

		r6 = new JLabel(rank);
		r6.setBounds(200, 475, 300, 75);
		add(r6);

		n1 = new JLabel();
		n1.setBounds(250, 25, 100, 75);
		add(n1);

		n2 = new JLabel();
		n2.setBounds(250, 110, 100, 75);
		add(n2);

		n3 = new JLabel();
		n3.setBounds(250, 195, 100, 75);
		add(n3);

		n4 = new JLabel();
		n4.setBounds(250, 280, 100, 75);
		add(n4);

		n5 = new JLabel();
		n5.setBounds(250, 365, 100, 75);
		add(n5);

		myRank = new JLabel();
		myRank.setBounds(250, 475, 100, 75);
		add(myRank);

		s1 = new JLabel();
		s1.setBounds(375, 25, 200, 75);
		add(s1);

		s2 = new JLabel();
		s2.setBounds(375, 110, 200, 75);
		add(s2);

		s3 = new JLabel();
		s3.setBounds(375, 195, 200, 75);
		add(s3);

		s4 = new JLabel();
		s4.setBounds(375, 280, 200, 75);
		add(s4);

		s5 = new JLabel();
		s5.setBounds(375, 365, 200, 75);
		add(s5);

		myScore = new JLabel();
		myScore.setBounds(375, 475, 200, 75);
		add(myScore);

	}

	public void update() {
		n1.setText("用户1");
		n2.setText("用户2");
		n3.setText("用户3");
		n4.setText("用户4");
		n5.setText("用户5");

		s1.setText("1000000");
		s2.setText("2000000");
		s3.setText("3000000");
		s4.setText("4000000");
		s5.setText("5000000");

		myRank.setText("第三十二位");

		myScore.setText("1000");
	}
}
