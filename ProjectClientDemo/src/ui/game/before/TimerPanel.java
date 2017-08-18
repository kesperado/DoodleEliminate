//package ui.game.before;
//
//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//import javax.swing.JPanel;
//import javax.swing.JProgressBar;
//import javax.swing.Timer;
//import javax.swing.border.TitledBorder;
//import javax.swing.event.ChangeEvent;
//import javax.swing.event.ChangeListener;
//
//
//public class TimerPanel extends JPanel implements ChangeListener,
//		ActionListener {
//	JProgressBar bar;
//	Timer timer;
//	private SingleFrame frame;
//
//	public TimerPanel(SingleFrame frame) {
//		this.setBounds(177, 10, 470, 73);
//		this.setBorder(new TitledBorder("倒计时"));
//		this.setLayout(new BorderLayout());
//		this.frame = frame;
//
//		bar = new JProgressBar();
//
//		bar.setMinimum(0);
//		bar.setMaximum(60);
//		bar.setValue(60);
//		bar.addChangeListener(this);
//		bar.setOpaque(true);
//		bar.setStringPainted(true);
//		bar.setPreferredSize(new Dimension(200, 25));
//		bar.setForeground(Color.orange);
//		bar.setBorderPainted(true);
//
//		timer = new Timer(1000, this);
//		this.add(bar, BorderLayout.NORTH);
//		timer.start();
//	}
//
//	@Override
//	public void stateChanged(ChangeEvent e) {
//		// TODO Auto-generated method stub
//		int value = bar.getValue();
//		if (e.getSource() == bar) {
//			bar.setString(value + "");
//		}
//		// if (value == 0) {
//		// JOptionPane.showMessageDialog(null, "时间到");
//		// }
//	}
//
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		// TODO Auto-generated method stub
//		if (e.getSource() == timer) {
//			int value = bar.getValue();
//			if (value > 0) {
//				value--;
//				bar.setValue(value);
//			}
//		}
//	}
// }
