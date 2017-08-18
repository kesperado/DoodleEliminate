package ui.game.common;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ui.MusicPlay;

@SuppressWarnings("serial")
public class CounterPanel extends JPanel {
	private int width = 220;
	private int height = 500;
	private int x = 540;
	private int y = 20;

	private Image num_1;
	private Image num_2;
	private Image num_3;
	private Image num_4;
	private Image num_5;
	private Image num_6;
	private Image num_7;
	private Image num_8;
	private Image num_9;
	private Image num_0;
	private Icon star;
	private Icon star2;
	private JLabel s_1;
	private JLabel s_2;
	private JLabel s_3;
	private JLabel s_4;
	private ArrayList<Image> shu;

	private int mark = 0;
	private ArrayList<Mark> list;

	private MusicPlay plm;

	public CounterPanel() {
		this.setBounds(x, y, width, height);
		this.setLayout(null);
		this.setOpaque(false);

		star = new ImageIcon("image/info/star.png");
		star2 = new ImageIcon("image/info/star2.png");

		s_1 = new JLabel(star);
		s_2 = new JLabel(star);
		s_3 = new JLabel(star);
		s_4 = new JLabel(star);

		s_1.setBounds(0, 300, 50, 50);
		add(s_1);
		s_2.setBounds(50, 300, 50, 50);
		add(s_2);
		s_3.setBounds(100, 300, 50, 50);
		add(s_3);
		s_4.setBounds(150, 300, 50, 50);
		add(s_4);

		list = new ArrayList<Mark>();
		shu = new ArrayList<Image>();

		num_0 = Toolkit.getDefaultToolkit().getImage("image/info/0.png");
		shu.add(num_0);
		num_1 = Toolkit.getDefaultToolkit().getImage("image/info/1.png");
		shu.add(num_1);
		num_2 = Toolkit.getDefaultToolkit().getImage("image/info/2.png");
		shu.add(num_2);
		num_3 = Toolkit.getDefaultToolkit().getImage("image/info/3.png");
		shu.add(num_3);
		num_4 = Toolkit.getDefaultToolkit().getImage("image/info/4.png");
		shu.add(num_4);
		num_5 = Toolkit.getDefaultToolkit().getImage("image/info/5.png");
		shu.add(num_5);
		num_6 = Toolkit.getDefaultToolkit().getImage("image/info/6.png");
		shu.add(num_6);
		num_7 = Toolkit.getDefaultToolkit().getImage("image/info/7.png");
		shu.add(num_7);
		num_8 = Toolkit.getDefaultToolkit().getImage("image/info/8.png");
		shu.add(num_8);
		num_9 = Toolkit.getDefaultToolkit().getImage("image/info/9.png");
		shu.add(num_9);

		int init_x = 15;
		int init_y = 85;
		for (int i = 0; i < 7; i++) {
			Mark temp = new Mark(0, init_x + i * 25, init_y);
			list.add(temp);
		}
	}

	public void updateMark(int ad) {
		int now = mark + ad;
		mark += ad;
		int pre = 0;
		for (int i = 0; i < 7; i++) {
			pre = (now / (int) Math.pow(10, 6 - i)) % 10;
			list.get(i).changeShu(pre);
		}
		repaint();
	}

	public void updateCombo(int now) {
		switch (now) {
		case 0:
			s_1.setIcon(star);
			s_2.setIcon(star);
			s_3.setIcon(star);
			s_4.setIcon(star);
			break;
		case 1:
			s_1.setIcon(star2);
			s_2.setIcon(star);
			s_3.setIcon(star);
			s_4.setIcon(star);
			break;
		case 2:
			s_1.setIcon(star2);
			s_2.setIcon(star2);
			s_3.setIcon(star);
			s_4.setIcon(star);
			plm = new MusicPlay(MusicPlay.class.getResource("/2k.wav"));
			plm.start();
			break;
		case 3:
			s_1.setIcon(star2);
			s_2.setIcon(star2);
			s_3.setIcon(star2);
			s_4.setIcon(star);
			plm = new MusicPlay(MusicPlay.class.getResource("/3k.wav"));
			plm.start();
			break;
		case 4:
			s_1.setIcon(star2);
			s_2.setIcon(star2);
			s_3.setIcon(star2);
			s_4.setIcon(star2);
			plm = new MusicPlay(MusicPlay.class.getResource("/4k.wav"));
			plm.start();
			break;
		}
	}

	public void paint(Graphics g) {
		super.paint(g);
		Iterator<Mark> it = list.iterator();
		while (it.hasNext()) {
			Mark temp = it.next();
			g.drawImage(temp.t, temp.he, temp.zo, temp.ku, temp.ga, this);
		}
	}

	class Mark {
		private Image t;// 图
		private int f = 0;// 分
		private int ku;// 宽
		private int ga;// 高
		private int he;// 横
		private int zo;// 纵

		Mark(int i, int h, int z) {
			this.f = i;
			this.ku = 20;
			this.ga = 40;
			this.he = h;
			this.zo = z;
			t = shu.get(f);
		}

		public void changeShu(int i) {
			this.f = i;
			t = shu.get(f);
		}
	}
}
