package ui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import logic.equipment.EquipmentLogicService;
import logic.equipment.StoreLogicService;
import logic.player.PlayerLogicService;
import po.EquipmentPO;
import po.PlayerPO;
import rmi.RMIController;

@SuppressWarnings("serial")
public class StorePanel extends JPanel {
	private RMIController rmiController = new RMIController();
	private EquipmentLogicService equipmentLogicService = rmiController
			.getEquipmentService();
	private PlayerLogicService playerService = rmiController.getPlayerService();
	private int width = 600;
	private int height = 600;

	private int x = 0;
	private int y = 0;
	private Image back;

	private Icon p1;
	private Icon p2;
	private Icon p3;

	private Icon b;
	private Icon b2;

	private Icon pe1;
	private Icon pe2;
	private Icon pe3;

	private Icon dec1;
	private Icon dec2;
	private Icon dec3;

	private Icon ha;
	private Icon ha1;
	private Icon ha2;
	private Icon ha3;
	private Icon ha_m;

	// 分别是 道具1 描述1 价格1 购买1 以此类推
	private JLabel prop_1;
	private JLabel des_1;
	private JLabel price_1;
	private JLabel buy_1;

	private JLabel prop_2;
	private JLabel des_2;
	private JLabel price_2;
	private JLabel buy_2;

	private JLabel prop_3;
	private JLabel des_3;
	private JLabel price_3;
	private JLabel buy_3;

	// 已拥有 C D E 道具 和 金钱
	private JLabel have;
	private JLabel have_1;
	private JLabel have_2;
	private JLabel have_3;
	private JLabel have_money;

	private JLabel sum_1;
	private JLabel sum_2;
	private JLabel sum_3;
	private JLabel sum_m;

	private MainPanel mp;
	private PlayerPO po;

	StorePanel(MainPanel mp, PlayerPO po) {
		this.po = po;
		this.mp = mp;
		this.setLayout(null);
		this.setBounds(x, y, width, height);
		this.setOpaque(false);

		b = new ImageIcon("image/system/buy.png");
		b2 = new ImageIcon("image/system/buy2.png");

		p1 = new ImageIcon("image/info/prop_1.png");
		prop_1 = new JLabel(p1);

		dec1 = new ImageIcon("image/info/dec1.png");
		des_1 = new JLabel(dec1);

		pe1 = new ImageIcon("image/info/price.png");
		price_1 = new JLabel(pe1);

		buy_1 = new JLabel(b);
		buy_1.addMouseListener(new MyListener());

		p2 = new ImageIcon("image/info/prop_2.png");
		prop_2 = new JLabel(p2);

		dec2 = new ImageIcon("image/info/dec2.png");
		des_2 = new JLabel(dec2);

		pe2 = new ImageIcon("image/info/price.png");
		price_2 = new JLabel(pe2);

		buy_2 = new JLabel(b);
		buy_2.addMouseListener(new MyListener());

		p3 = new ImageIcon("image/info/prop_3.png");
		prop_3 = new JLabel(p3);

		dec3 = new ImageIcon("image/info/dec3.png");
		des_3 = new JLabel(dec3);

		pe3 = new ImageIcon("image/info/price.png");
		price_3 = new JLabel(pe3);

		buy_3 = new JLabel(b);
		buy_3.addMouseListener(new MyListener());

		ha = new ImageIcon("image/system/have.png");
		have = new JLabel(ha);

		ha1 = new ImageIcon("image/info/have1.png");
		have_1 = new JLabel(ha1);

		ha2 = new ImageIcon("image/info/have2.png");
		have_2 = new JLabel(ha2);

		ha3 = new ImageIcon("image/info/have3.png");
		have_3 = new JLabel(ha3);

		ha_m = new ImageIcon("image/info/haveMoney.png");
		have_money = new JLabel(ha_m);

		Font font = new Font("微软雅黑", Font.PLAIN, 18);
		sum_1 = new JLabel();
		sum_1.setFont(font);

		sum_2 = new JLabel();
		sum_2.setFont(font);

		sum_3 = new JLabel();
		sum_3.setFont(font);

		sum_m = new JLabel();
		sum_m.setFont(font);

		back = Toolkit.getDefaultToolkit().getImage("image/system/circle.png");

		init();
	}

	void init() {
		prop_1.setBounds(66, 120, 80, 80);
		add(prop_1);

		des_1.setBounds(172, 120, 230, 80);
		add(des_1);

		price_1.setBounds(305, 150, 80, 40);
		add(price_1);

		buy_1.setBounds(438, 150, 80, 40);
		add(buy_1);

		prop_2.setBounds(66, 210, 80, 80);
		add(prop_2);

		des_2.setBounds(172, 210, 230, 80);
		add(des_2);

		price_2.setBounds(305, 243, 80, 40);
		add(price_2);

		buy_2.setBounds(438, 243, 80, 40);
		add(buy_2);

		prop_3.setBounds(66, 310, 80, 80);
		add(prop_3);

		des_3.setBounds(172, 310, 230, 80);
		add(des_3);

		price_3.setBounds(300, 338, 80, 40);
		add(price_3);

		buy_3.setBounds(438, 338, 80, 40);
		add(buy_3);

		have.setBounds(30, 550, 80, 40);
		add(have);

		have_1.setBounds(120, 550, 40, 40);
		add(have_1);

		sum_1.setBounds(170, 550, 60, 40);
		add(sum_1);

		have_2.setBounds(225, 550, 40, 40);
		add(have_2);

		sum_2.setBounds(265, 550, 60, 40);
		add(sum_2);

		have_3.setBounds(318, 550, 40, 40);
		add(have_3);

		sum_3.setBounds(360, 550, 60, 40);
		add(sum_3);

		have_money.setBounds(422, 550, 40, 40);
		add(have_money);

		sum_m.setBounds(470, 550, 100, 40);
		add(sum_m);

		getData();
	}

	void getData() {
		ArrayList<EquipmentPO> list = null;
		try {
			list = equipmentLogicService.getList(po.playerID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sum_1.setText("X " + list.get(0).num);
		System.out.println(list.get(0).num);
		sum_2.setText("X " + list.get(1).num);
		System.out.println(list.get(0).num);
		sum_3.setText("X " + list.get(2).num);
		System.out.println(list.get(0).num);
		try {
			sum_m.setText("X " + playerService.getPlayer(po.playerID).money);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int height = back.getHeight(this);
		int width = back.getWidth(this);
		g.drawImage(back, 0, 0, width, height, this);

	}

	class MyListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			int temp = 0;
			if (arg0.getSource() == buy_1) {
				// 购买道具c
				try {
					temp = equipmentLogicService.buyEquipment(po.playerID, "C",
							1);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "网络连接中断");
				}
			} else if (arg0.getSource() == buy_2) {
				// 购买道具d
				try {
					temp = equipmentLogicService.buyEquipment(po.playerID, "D",
							1);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "网络连接中断");
				}
			} else if (arg0.getSource() == buy_3) {
				// 购买道具e
				try {
					temp = equipmentLogicService.buyEquipment(po.playerID, "E",
							1);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "网络连接中断");
				}
			}
			if (temp < 0) {
				JOptionPane.showMessageDialog(null, "Need more golds");
			} else if (temp > 0) {
				po.money -= temp;
			}
			getData();
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			if (arg0.getSource() == buy_1) {
				buy_1.setIcon(b2);
			} else if (arg0.getSource() == buy_2) {
				buy_2.setIcon(b2);
			} else if (arg0.getSource() == buy_3) {
				buy_3.setIcon(b2);
			}
			repaint();
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			if (arg0.getSource() == buy_1) {
				buy_1.setIcon(b);
			} else if (arg0.getSource() == buy_2) {
				buy_2.setIcon(b);
			} else if (arg0.getSource() == buy_3) {
				buy_3.setIcon(b);
			}
			repaint();
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	}
}
