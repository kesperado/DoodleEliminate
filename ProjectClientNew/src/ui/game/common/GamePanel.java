package ui.game.common;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import logic.game.GameLogicService;
import logic.game.Message;
import po.GameSettingPO;
import ui.MusicPlay;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	protected SinglePanel mp;
	protected GameLogicService gls;// 获得信息的对象
	protected Message message;
	protected Image back;
	protected String name;

	protected Icon exit_icon;// 退出图片
	protected JLabel exit_label;// 退出按钮
	protected TimePanel t;// 计时面板
	protected CounterPanel cp;// 分数面板
	private MusicPlay plm;

	// 新增变量

	public GamePanel(GameSettingPO setting, String name, GameLogicService g) {
		this.setOpaque(false);
		this.setSize(800, 600);
		this.setLayout(null);
		this.name = name;

		back = Toolkit.getDefaultToolkit().getImage("image/back/back.jpg");

		this.gls = g;
		mp = new SinglePanel(name, gls, this);
		this.add(mp);// 成功把监听限制在panel上
		mp.setGar(setting.direction);

		t = new TimePanel();
		add(t);

		cp = new CounterPanel();
		cp.setBounds(540, 20, 220, 444);
		add(cp);

		exit_icon = new ImageIcon("image/system/exit.png");
		exit_label = new JLabel(exit_icon);
		exit_label.setBounds(680, 490, 80, 80);
		add(exit_label);

		plm = new MusicPlay(MusicPlay.class.getResource("/Yellow.wav"));
		plm.start();

		repaint();
	}

	public void tuichu_guansheng() {
		plm.stop();
	}

	void updateMark(int i) {
		cp.updateMark(i);
	}

	void updateCombo(int i) {
		cp.updateCombo(i);
	}

	public void alert() {

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int height = back.getHeight(this);
		int width = back.getWidth(this);
		int x = (int) (((double) (getWidth() - width)) / 2.0);
		int y = (int) (((double) (getHeight() - height)) / 2.0);
		g.drawImage(back, x, y, width, height, this);
	}
}
