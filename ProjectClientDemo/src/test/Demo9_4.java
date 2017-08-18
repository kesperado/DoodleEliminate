package test;

/*
 * 通过上下左右键控制小球移动
 */
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class Demo9_4 extends JFrame {
	MyPanel4 mp;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Demo9_4();
	}

	public Demo9_4() {
		mp = new MyPanel4();
		mp.setBorder(new TitledBorder(new EtchedBorder()));

		this.add(mp);

		this.addKeyListener(mp);

		this.setSize(400, 300);
		this.setLocation(200, 200);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

}

// 定义自己的面板
class MyPanel4 extends JPanel implements KeyListener {
	int x = 370;
	int y = 250;

	public void paint(Graphics g) {
		super.paint(g);
		g.fillOval(x, y, 10, 10);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		// System.out.println("被按下"+e.getKeyChar());
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			y--;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			y++;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			x--;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			x++;
		}

		if (x <= 0) {
			x = 0;
		}
		if (y <= 0) {
			y = 0;
		}
		if (x >= 370) {
			x = 370;
		}
		if (y >= 250) {
			y = 250;
		}

		// 调用repaint函数，重新绘制界面
		this.repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}