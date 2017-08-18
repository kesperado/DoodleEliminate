package ui.game.common;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TimePanel extends JPanel {
	Timer timer;
	int second = 60;
	private int width = 500;
	private int height = 80;
	private int x = 10;
	private int y = 520;

	private Image kt;
	private Image at;

	public TimePanel() {
		this.setBounds(x, y, width, height);
		this.setOpaque(false);
		
		kt = Toolkit.getDefaultToolkit().getImage("image/game/time.png");
		at = Toolkit.getDefaultToolkit().getImage("image/game/step.png");

		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				timer = new Timer();
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						second--;
						repaint();
					}
				}, 100, 1000);
			}
		});
		t.start();
	}

	public void paint(Graphics g) {
		super.paintComponent(g);
		g.drawImage(kt, 0, 0, 500, 80, this);
		g.drawImage(at, 0, 0, second * 8, 80, this);
	}
}
