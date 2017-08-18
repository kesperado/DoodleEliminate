package ui.store;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class StorePanel extends JPanel {
	private Image back;// 背景

	private Icon prop3;
	private Icon prop4;
	private Icon prop5;
	private Icon price;
	private Icon buy;// 道具345 购买金额 购买图标

	private JLabel dc;
	private JLabel dd;
	private JLabel de;

	private JLabel pr1;
	private JLabel pr2;
	private JLabel pr3;// 道具345 分别价格

	private JLabel b1;
	private JLabel b2;
	private JLabel b3;// 三个购买

	private JLabel t1;
	private JLabel t2;
	private JLabel t3;// 三个描述

	public StorePanel() {
		this.setSize(600, 600);
		setLayout(null);
		this.setOpaque(true);
		back = Toolkit.getDefaultToolkit().getImage("system/right2.png");

		prop3 = new ImageIcon("system/prop_C.png");
		prop4 = new ImageIcon("system/prop_D.png");
		prop5 = new ImageIcon("system/prop_E.png");
		price = new ImageIcon("system/price.png");
		buy = new ImageIcon("system/buy.png");

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
		dc = new JLabel(prop3);
		dc.setBounds(50, 25, 100, 100);
		add(dc);

		pr1 = new JLabel(price);
		pr1.setBounds(150, 80, 100, 35);
		add(pr1);

		b1 = new JLabel(buy);
		b1.setBounds(400, 50, 100, 50);
		add(b1);

		t1 = new JLabel("道具1");
		t1.setBounds(150, 25, 200, 25);
		add(t1);

		dd = new JLabel(prop4);
		dd.setBounds(50, 175, 100, 100);
		add(dd);

		pr2 = new JLabel(price);
		pr2.setBounds(150, 230, 100, 35);
		add(pr2);

		b2 = new JLabel(buy);
		b2.setBounds(400, 200, 100, 50);
		add(b2);

		t2 = new JLabel("道具2");
		t2.setBounds(150, 175, 200, 25);
		add(t2);

		de = new JLabel(prop5);
		de.setBounds(50, 325, 100, 100);
		add(de);

		pr3 = new JLabel(price);
		pr3.setBounds(150, 380, 100, 35);
		add(pr3);

		b3 = new JLabel(buy);
		b3.setBounds(400, 330, 100, 50);
		add(b3);

		t3 = new JLabel("道具3");
		t3.setBounds(150, 325, 200, 25);
		add(t3);
	}
}
