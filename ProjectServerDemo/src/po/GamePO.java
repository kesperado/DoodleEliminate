package po;

import java.io.Serializable;
import java.util.Date;




public class GamePO implements Serializable{
	public String gameNO=new String();
	public String playerID=new String();
	public int score=0;
	public String time=new String(DatePO.toString(new Date(System.currentTimeMillis())));
	public int combo=0;
	
}
