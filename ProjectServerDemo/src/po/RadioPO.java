package po;

import java.io.Serializable;
import java.util.Date;

public class RadioPO implements Serializable{
	public String message=new String();
	public String time=DatePO.toString(new Date(System.currentTimeMillis()));
	public int number=-1;
	
}
