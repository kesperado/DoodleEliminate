package logic.game;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable ,Cloneable
{
	public int combo;
	public boolean supermod;
	public String imformation;
	public ArrayList<Integer[][]> list = new ArrayList<Integer[][]>();
	public Message clone()
	{
		Message result = new Message();
		result.combo=this.combo;
		result.supermod=this.supermod;
		result.imformation=this.imformation;
		for(Integer[][] I:list)
		{
			Integer[][] temp = new Integer[I.length][];
			for(int i=0;i<I.length;i++)
			{
				temp[i]=I[i].clone();
			}
			result.list.add(temp);
		}
		return result;
	}
}
