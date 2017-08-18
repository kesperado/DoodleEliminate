package ui.game;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Block {
	private int number;

	private int block_width = 50;
	private int block_height = 50;// 棋子横纵距离

	private ImageIcon s1;
	private ImageIcon s2;
	private ImageIcon s3;
	private ImageIcon s4;
	private ImageIcon s5;

	private int xzb;
	private int yzb;// 图上的位置 相对于panel的位置

	private int state = 0;// 0正常 1聚焦 2点击 3爆炸 4提示

	public Block(int i, int x, int y) {
		init(i, x, y);
	}

	public void init(int i, int x, int y) {
		this.number = i;
		s1 = new ImageIcon("image/" + number + ".png");
		s2 = new ImageIcon("image/" + (number + 50) + ".png");
		s3 = new ImageIcon("image/" + (number + 100) + ".png");
		s4 = new ImageIcon("image/" + (number + 150) + ".png");
		s5 = new ImageIcon("image/" + (number + 200) + ".png");
		xzb = y * 50;
		yzb = x * 50 + 10;
		state = 0;
	}

	public void draw(Graphics g) {
		g.drawImage(getNow(), getHzb(), getZzb(), block_width, block_height,
				null);
	}

	public void getJuj() {
		if (state == 0 || state == 4) {
			state = 1;
		}
	}

	// 获得聚焦

	public void lostJuj() {
		if (state == 1) {
			state = 0;
		}
	}

	// 失去聚焦
	public void getDianj() {
		if (state == 1 || state == 4 || state == 0) {
			state = 2;
		}
	}

	// 获得点击
	public void lostDianj() {
		if (state == 2) {
			state = 0;
		}
	}

	// 失去点击

	public void getBaoz() {
		if (state == 0) {
			state = 3;
		}
	}

	// 获得爆炸

	public void getTis() {
		state = 4;
	}

	// 获得提示
	public void lostTis() {
		if (state == 4) {
			state = 0;
		}
	}

	public Image getNow() {
		switch (state) {
		case 0:
			return s1.getImage();
		case 1:
			return s2.getImage();
		case 2:
			return s3.getImage();
		case 3:
			return s4.getImage();
		case 4:
			return s5.getImage();
		}
		return null;
	}

	public int getHzb() {
		return xzb;
	}

	public int getZzb() {
		return yzb;
	}

	public int getNum() {
		return number;
	}

	public void move(int dire, int dis) {
		// x轴 y轴
		if (dire == 0) {
			xzb += dis;
		} else if (dire == 1) {
			yzb += dis;
		}
	}

	public void changeIma(int i) {
		this.number = i;
		s1 = new ImageIcon("image/" + number + ".png");
		s2 = new ImageIcon("image/" + (number + 50) + ".png");
		s3 = new ImageIcon("image/" + (number + 100) + ".png");
		s4 = new ImageIcon("image/" + (number + 150) + ".png");
		s5 = new ImageIcon("image/" + (number + 200) + ".png");
		state = 0;
	}
}
