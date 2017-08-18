//package ui.game.before;
//
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.border.TitledBorder;
//
//
//public class CounterPanel extends JPanel {
//	private int mark = 0;
//	private JLabel markLabel;
//	private SingleFrame frame;
//
//	public CounterPanel(SingleFrame frame) {
//		this.setBorder(new TitledBorder(null, "计分器", TitledBorder.LEADING,
//				TitledBorder.TOP, null, null));
//		this.setBounds(10, 10, 157, 163);
//		this.setLayout(null);
//		this.frame = frame;
//
//		markLabel = new JLabel(mark + "");
//		markLabel.setBounds(20, 58, 115, 34);
//		this.add(markLabel);
//	}
//
//	public void updateMark(int mark) {
//		this.mark = (mark + this.mark);
//		markLabel.setText(this.mark + "");
//		repaint();
//	}
// }
