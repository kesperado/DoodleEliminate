package po;

import java.io.Serializable;

public class GameSettingPO implements Serializable {
	public boolean third;
	public boolean fourth;
	public boolean fifth;
	public int direction;

	public GameSettingPO() {

	}

	public GameSettingPO(boolean third, boolean fourth, boolean fifth,
			int direction) {
		this.third = third;
		this.fourth = fourth;
		this.fifth = fifth;
		this.direction = direction;

	}
}
