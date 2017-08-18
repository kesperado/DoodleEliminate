package po;

import java.io.Serializable;
import java.util.Date;

public class DatePO extends Date implements Serializable{
	
	public String date;
//	
//	public DatePO(){
//		date=new String();
//		date=this.toString(new Date(System.currentTimeMillis()));
//	}
//	
	public DatePO(long time){
		Date da=new Date(time);
		date=new String();
		date=this.toString(da);
	}
	public DatePO(String s){
		date=new String();
		date=s;
	}
	
	
	
	public static Date getDate(DatePO dp){
		Date da=new Date();
		long time=new Long(dp.date);
		int year=(int) (time/1000000000/10);
		time =time%((long)1000000000*10);
		int month=(int) (time/100000000);
		time=time%100000000;
		int day=(int) (time/1000000);
		time=time%1000000;
		int hour=(int) (time/10000);
		time=time%10000;
		int min=(int) (time/100);
		time=time%100;
		int sec=(int) time;
//		System.out.println(time);
//		System.out.println(year);
//		System.out.println("month:"+month);
//		System.out.println("day:"+day);
//		System.out.println("hour:"+hour);
//		System.out.println("min:"+min);
//		System.out.println("sec:"+sec);
		da.setYear(year);
		da.setMonth(month);
		da.setDate(day);
		da.setHours(hour);
		da.setMinutes(min);
		da.setSeconds(sec);
		return da;
	}
	
	public static String toString(Date date){
		String datetime=new String();
		String year=Integer.toString(date.getYear()+1900);
		String month=Integer.toString(date.getMonth()+1);
		String cudate=Integer.toString(date.getDate());
		String hour=Integer.toString(date.getHours());
		String minute=Integer.toString(date.getMinutes());
		String second=Integer.toString(date.getSeconds());
		if(month.length()==1){
			month="0"+month;
		}
		if(cudate.length()==1){
			cudate="0"+cudate;
		}
		if(hour.length()==1){
			hour="0"+hour;
		}
		if(minute.length()==1){
			minute="0"+minute;
		}
		if(second.length()==1){
			second="0"+second;
		}
		datetime=year+month+cudate+hour+minute+second;
		return datetime;
		
	}
	

}
