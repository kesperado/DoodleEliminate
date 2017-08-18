package data.radio;

import po.RadioPO;


public interface RadioDataService {
	
	public boolean addRadio(RadioPO po);
	public RadioPO getRadio(int number);
	public boolean update(RadioPO po);
	public boolean delete(RadioPO po);
	public RadioPO getLastRadio();
	
}