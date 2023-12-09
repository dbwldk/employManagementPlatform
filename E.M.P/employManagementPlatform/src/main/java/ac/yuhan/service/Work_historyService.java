package ac.yuhan.service;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;

import ac.yuhan.domain.UnitedEmploy;
import ac.yuhan.domain.Work_history;

public interface Work_historyService {
	
	public Page<Work_history> getWork_history(UnitedEmploy employInfo, Pageable pageable); 
	public Work_history getDailyWork_histoty(int workState, UnitedEmploy employInfo);
	public Page<Work_history> getWork_historyByE_num(String e_num, Pageable pageable); 
	public Page<Work_history> getWork_historyByHistoryDate(Date history_date, String employNo, Pageable pageable); 
	@Modifying(clearAutomatically = true)
	public void updateWork_history(UnitedEmploy employInfo);
	public void insertWork_history(UnitedEmploy employInfo);
	public void insertNewWork_history(UnitedEmploy employInfo);
	@Modifying(clearAutomatically = true)
	public void updateWork_history_comment(Long historyKey, String comment);

}
