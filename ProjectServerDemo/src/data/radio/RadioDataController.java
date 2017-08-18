package data.radio;

import java.util.ArrayList;

import po.RadioPO;

public class RadioDataController implements RadioDataService {
	private static int num;
	private RadioDataHelper radioData;
	
	public RadioDataController() {
		radioData = new RadioDataHelper();
		num = radioData.getLastRadio().number;
	}
	
	public static void main(String[] args){
		RadioDataController radio=new RadioDataController();
		RadioPO rp=new RadioPO();
		rp.number=7;
		System.out.println(radio.delete(rp));
	}

	@Override
	public boolean update(RadioPO po) {
		// TODO Auto-generated method stub
		if ((po.number < 0)) {
			return false;
		} else {
			return radioData.update(po);
		}
	}

	@Override
	public boolean delete(RadioPO po) {
		// TODO Auto-generated method stub
		if (po.number < 0) {
			return false;
		} else {
			return radioData.delete(po);
		}
	}

	@Override
	public boolean addRadio(RadioPO po) {
		// TODO Auto-generated method stub
		num++;
		po.number = num;
		return radioData.addRadio(po);
	}

	@Override
	public RadioPO getRadio(int number) {
		// TODO Auto-generated method stub
		if(number<0){
			return null;
		}else if(number > num){
			return null;
		}
			else {
			return radioData.getRadio(number);
		}
	}

	@Override
	public RadioPO getLastRadio() {
		// TODO Auto-generated method stub
		return radioData.getLastRadio();
	}

}