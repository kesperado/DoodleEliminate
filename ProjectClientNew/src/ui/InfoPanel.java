package ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import logic.player.PlayerLogicService;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import po.PlayerPO;
import rmi.RMIController;

@SuppressWarnings("serial")
public class InfoPanel extends JPanel {
	private PlayerLogicService playerLogicService;

	private static RMIController rmiController;

	private int width = 600;
	private int height = 600;

	private int x = 0;
	private int y = 0;

	private Icon n1;
	private Icon m1;
	private Icon h1;
	private Icon m2;
	private Icon t1;
	private Icon a1;
	private Icon title2;
	private Icon sum1;
	private Icon sum2;
	private Icon sum3;
	private Icon sum4;
	private Icon sum5;

	private JLabel name;
	private JLabel maxcombo;
	private JLabel highstmark;
	private JLabel money;
	private JLabel times;
	private JLabel avgscore;
	private JLabel last1;
	private JLabel last2;
	private JLabel last3;
	private JLabel last4;
	private JLabel last5;

	private JLabel name2;
	private JLabel maxcombo2;
	private JLabel highstmark2;
	private JLabel money2;
	private JLabel times2;
	private JLabel avgscore2;
	private JLabel title;
	private JLabel last12;
	private JLabel last22;
	private JLabel last32;
	private JLabel last42;
	private JLabel last52;

	private MainPanel mp;
	private PlayerPO po;

	private ChartPanel cp1;
	private JFreeChart jf1;
	private ChartPanel cp2;
	private JFreeChart jf2;

	InfoPanel(MainPanel mp, PlayerPO po) {
		this.po = po;
		this.mp = mp;
		this.setLayout(null);
		this.setBounds(x, y, width, height);
		this.setVisible(true);
		this.setOpaque(false);

		t1 = new ImageIcon("image/system/sum.png");
		a1 = new ImageIcon("image/system/avg.png");
		n1 = new ImageIcon("image/system/name.png");
		m1 = new ImageIcon("image/system/maxcombo.png");
		h1 = new ImageIcon("image/system/highstmark.png");
		m2 = new ImageIcon("image/system/money.png");
		title2 = new ImageIcon("image/system/title2.png");
		sum1 = new ImageIcon("image/system/1.png");
		sum2 = new ImageIcon("image/system/2.png");
		sum3 = new ImageIcon("image/system/3.png");
		sum4 = new ImageIcon("image/system/4.png");
		sum5 = new ImageIcon("image/system/5.png");

		name = new JLabel(n1);
		maxcombo = new JLabel(m1);
		highstmark = new JLabel(h1);
		money = new JLabel(m2);
		times = new JLabel(t1);
		avgscore = new JLabel(a1);
		title = new JLabel(title2);

		last1 = new JLabel(sum1);
		last2 = new JLabel(sum2);
		last3 = new JLabel(sum3);
		last4 = new JLabel(sum4);
		last5 = new JLabel(sum5);

		Font font = new Font("微软雅黑", Font.PLAIN, 22);
		name2 = new JLabel();
		name2.setFont(font);

		maxcombo2 = new JLabel();
		maxcombo2.setFont(font);

		highstmark2 = new JLabel();
		highstmark2.setFont(font);

		money2 = new JLabel();
		money2.setFont(font);

		avgscore2 = new JLabel();
		avgscore2.setFont(font);

		times2 = new JLabel();
		times2.setFont(font);

		last12 = new JLabel();
		last12.setFont(font);

		last22 = new JLabel();
		last22.setFont(font);

		last32 = new JLabel();
		last32.setFont(font);

		last42 = new JLabel();
		last42.setFont(font);

		last52 = new JLabel();
		last52.setFont(font);

		rmiController = new RMIController();
		playerLogicService = rmiController.getPlayerService();

		init();
	}

	void init() {
		name.setBounds(57, 30, 80, 40);
		add(name);

		name2.setBounds(142, 30, 100, 40);
		add(name2);

		maxcombo.setBounds(162, 80, 100, 40);
		add(maxcombo);

		maxcombo2.setBounds(324, 80, 100, 40);
		add(maxcombo2);

		highstmark.setBounds(162, 130, 100, 40);
		add(highstmark);

		highstmark2.setBounds(324, 130, 155, 40);
		add(highstmark2);

		money.setBounds(162, 180, 80, 40);
		add(money);

		money2.setBounds(324, 180, 100, 40);
		add(money2);

		times.setBounds(160, 230, 100, 40);
		add(times);

		times2.setBounds(324, 230, 100, 40);
		add(times2);

		avgscore.setBounds(162, 280, 100, 40);
		add(avgscore);

		avgscore2.setBounds(324, 280, 155, 40);
		add(avgscore2);

		title.setBounds(40, 330, 250, 40);
		add(title);

		last1.setBounds(150, 380, 30, 30);
		add(last1);

		last2.setBounds(150, 420, 30, 30);
		add(last2);

		last3.setBounds(150, 460, 30, 30);
		add(last3);

		last4.setBounds(150, 500, 30, 30);
		add(last4);

		last5.setBounds(150, 540, 30, 30);
		add(last5);

		last12.setBounds(320, 380, 200, 30);
		add(last12);

		last22.setBounds(320, 420, 200, 30);
		add(last22);

		last32.setBounds(320, 460, 200, 30);
		add(last32);

		last42.setBounds(320, 500, 200, 30);
		add(last42);

		last52.setBounds(320, 540, 200, 30);
		add(last52);
	}

	void getInfo(PlayerPO po) {
		name2.setText(po.playerID);
		ArrayList<Integer> list = null;
		try {
			list = playerLogicService.getStaticStatistics(po.playerID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		maxcombo2.setText(list.get(2) + " hit");
		highstmark2.setText(list.get(3) + " point");
		money2.setText(po.money + " $");
		avgscore2.setText(list.get(1) + " point");
		times2.setText(list.get(0) + " times");

		last12.setText(list.get(4) + " point");
		last22.setText(list.get(5) + " point");
		last32.setText(list.get(6) + " point");
		last42.setText(list.get(7) + " point");
		last52.setText(list.get(8) + " point");

		repaint();
	}

	void getInfo2() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // 得到屏幕的尺寸
		JFrame temp = new JFrame();
		temp.setBounds((screenSize.width - 420) / 2,
				(screenSize.height - 400) / 2, 420, 400);
		temp.setTitle("Infomaiton");
		temp.getContentPane().setLayout(null);

		jf1 = createChart(createDataset(0), 0);
		cp1 = new ChartPanel(jf1, false);
		cp1.setBounds(0, 0, 390, 190);
		temp.getContentPane().add(cp1);
		temp.setVisible(true);
		jf2 = createChart(createDataset(1), 1);
		cp2 = new ChartPanel(jf2, false);
		cp2.setBounds(0, 190, 390, 190);
		temp.getContentPane().add(cp2);
	}

	private DefaultCategoryDataset createDataset(int type) {
		DefaultCategoryDataset result = null;
		try {
			if (type == 0) {
				result = playerLogicService.getCountOfGameLineData(po.playerID);
			} else if (type == 1) {
				result = playerLogicService.getAverageScorePerDay(po.playerID);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "网络连接中断");
		}
		return result;
	}

	private JFreeChart createChart(DefaultCategoryDataset linedataset, int type) {
		String title = "";
		String xZhou = "";
		String yZhou = "";
		if (type == 0) {
			title = "Daily game statistics";
			xZhou = "Date";
			yZhou = "Times";
		} else if (type == 1) {
			title = "Daily score statistics";
			xZhou = "Date";
			yZhou = "Score";
		}
		JFreeChart chart = ChartFactory.createLineChart(title, // 折线图名称
				xZhou, // 横坐标名称
				yZhou, // 纵坐标名称
				linedataset, // 数据
				PlotOrientation.VERTICAL, // 水平显示图像
				false, // include legend
				false, // tooltips
				false // urls
				);
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setRangeGridlinesVisible(true); // 是否显示格子线
		plot.setBackgroundAlpha(0.3f); // 设置背景透明度
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis.setAutoRangeIncludesZero(true);
		rangeAxis.setUpperMargin(0.20);
		rangeAxis.setLabelAngle(Math.PI / 2.0);
		return chart;
	}

}
