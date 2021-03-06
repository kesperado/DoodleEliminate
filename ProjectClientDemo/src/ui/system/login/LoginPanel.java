package ui.system.login;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class LoginPanel extends JPanel {
	private JTextField id_field;// 用户名
	private JPasswordField pw_field;// 密码1

	private JLabel id_label;// 用户名
	private JLabel pw_label;// 密码1
	private JLabel done_label;// 确定

	private Icon id_icon;// 用户名
	private Icon pw_icon;// 密码1
	private Icon finish_icon;// 确定

	private Image back;// 背景

	public LoginPanel() {
		this.setSize(600, 600);
		setLayout(null);
		this.setOpaque(true);
		id_icon = new ImageIcon("system/id.png");
		pw_icon = new ImageIcon("system/password.png");
		finish_icon = new ImageIcon("system/finish.png");
		back = Toolkit.getDefaultToolkit().getImage("system/right2.png");
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
		id_label = new JLabel(id_icon);
		id_label.setBounds(150, 150, 100, 45);
		add(id_label);

		id_field = new JTextField();
		id_field.setBounds(275, 150, 200, 25);
		add(id_field);
		id_field.setColumns(10);

		pw_label = new JLabel(pw_icon);
		pw_label.setBounds(150, 225, 100, 45);
		add(pw_label);

		pw_field = new JPasswordField();
		pw_field.setBounds(275, 225, 200, 25);
		add(pw_field);
		pw_field.setColumns(10);

		done_label = new JLabel(finish_icon);
		done_label.setBounds(350, 325, 100, 45);
		add(done_label);
		repaint();
	}

}
