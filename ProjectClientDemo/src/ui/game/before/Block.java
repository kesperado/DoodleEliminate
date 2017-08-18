//package ui.game.before;
//
//import java.awt.Image;
//
//import javax.swing.Icon;
//import javax.swing.ImageIcon;
//import javax.swing.JLabel;
//
//public class Block extends JLabel {
//	private static int Width = 50;
//	private static int Height = 50;
//	private int number = 0;// 这是自身的图片编号
//	private int grid_x = 0;
//	private int grid_y = 0;// 这是色块的界面横纵坐标 虽然感觉有点冗余 但是也没什么更好的办法
//
//	private Icon icon_normal;// 正常态
//	private Icon icon_focus;// 聚焦态
//	private Icon icon_click;// 点击态
//	private Icon icon_explosion;// 爆炸态
//
//	public Block(int i, int x, int y) {
//		grid_x = x;
//		grid_y = y;
//		number = i;
//		icon_normal = getTupian(i);
//		icon_focus = getTupian(i + 50);
//		icon_click = getTupian(i + 100);
//		icon_explosion = getTupian(i + 150);
//		this.setIcon(icon_normal);
//		this.setSize(Width, Height);
//	}// 初始方法
//
//	public int getHenzb() {
//		return grid_x;
//	}
//
//	public int getZongzb() {
//		return grid_y;
//	}// 不要用 getX 和 getY 方法 这是确定位置需要的方法 妈蛋！！！！！！
//
//	public int getTu() {
//		return number;
//	}
//
//	public void changeImg(int i) {
//		this.number = i;
//		icon_normal = getTupian(i);
//		icon_focus = getTupian(i + 50);
//		icon_click = getTupian(i + 100);
//		icon_explosion = getTupian(i + 150);
//		this.setIcon(icon_normal);
//		repaint();
//	}// 更新图片方法
//
//	public void getBaoz() {
//		this.setIcon(icon_explosion);
//		repaint();
//	}// 图片爆炸
//
//	public void getXiaoc() {
//		// this.setVisible(false);
//		this.setLocation(this.getX(), -50);
//		// 机智如我
//		repaint();
//	}// 图片消除
//
//	public void getJuj() {
//		this.setIcon(icon_focus);
//		repaint();
//	}// 获得聚焦
//
//	public void lostJuj() {
//		if (this.getIcon() == this.icon_focus) {
//			this.setIcon(icon_normal);
//		}
//		repaint();
//	}// 失去聚焦
//
//	public void getDianj() {
//		this.setIcon(icon_click);
//		repaint();
//	}// 获得点击
//
//	public void lostDianj() {
//		this.setIcon(icon_normal);
//		repaint();
//	}// 失去点击
//
//	public Icon getTupian(int i) {
//		Icon icon = new ImageIcon("image/" + i + ".png");
//		icon = new ImageIcon(((ImageIcon) icon).getImage().getScaledInstance(
//				Width, Height, Image.SCALE_SMOOTH));
//		return icon;
//	}// 获得图片方法
//
//	public void yidong(int x, int y) {
//		this.setLocation(this.getX() + x, this.getY() + y);
//		repaint();
//	}// 移动方法
//
//	public void guiwei() {
//		this.setLocation(this.grid_y * 50, this.grid_x * 50);
//		repaint();
//	}// 回到原位
//
//	public void luoxia(int ju) {
//		if (ju == 0) {
//
//		} else {
//			this.setLocation(this.getX(), this.getY() + ju);
//			repaint();
//		}
//	}// 落下
// }
