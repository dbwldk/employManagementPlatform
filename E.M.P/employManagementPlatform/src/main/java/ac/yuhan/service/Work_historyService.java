package ac.yuhan.service;

import java.util.List;

import ac.yuhan.domain.UnitedEmploy;
import ac.yuhan.domain.Work_history;

public interface Work_historyService {
	
	public List<Work_history> getWork_history(UnitedEmploy employInfo); 
	public Work_history getDailyWork_histoty(int workState, UnitedEmploy employInfo);
	public void updateWork_history(UnitedEmploy employInfo);
	public void insertWork_history(UnitedEmploy employInfo);
	public void insertNewWork_history(UnitedEmploy employInfo);
	public void updateWork_history_comment(Long historyKey, String comment);

}