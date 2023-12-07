package ac.yuhan.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ac.yuhan.domain.UnitedEmploy;
import ac.yuhan.domain.Work_history;
import ac.yuhan.persistence.Work_historyRepository;

@Service
public class Work_historyServiceImpl implements Work_historyService {
	@Autowired
	private Work_historyRepository work_historyRepo;
	
	@Override
	public List<Work_history> getWork_history(UnitedEmploy employInfo) {
		List<Work_history> findWork_history = work_historyRepo.findWork_historyByhnum(employInfo.getE_num());
		if(!findWork_history.isEmpty())
		{
			return findWork_history;
		}
		else return null;
	}
	
	@Override
	public Work_history getDailyWork_histoty(int workState, UnitedEmploy employInfo) {
		Optional<Work_history> findDailyWork_history = work_historyRepo.findWork_historyByhstateAndHnum(workState, employInfo.getE_num());
		if(findDailyWork_history.isPresent())
		{
			return findDailyWork_history.get();
		}
		else return null;
	}
	
	@Override
	public void updateWork_history(UnitedEmploy employInfo) {
		Work_history work_history = work_historyRepo.findWork_historyByhstateAndHnum(0, employInfo.getE_num()).get();
		work_history.setH_comment("");
		work_history.setH_date(new Date());
		work_history.setHstate(1);
		work_historyRepo.save(work_history);
		
	}
	
	@Override
	public void insertWork_history(UnitedEmploy employInfo) {
		Work_history work_history = new Work_history();
		work_history.setH_comment("");
		work_history.setH_date(new Date());
		work_history.setHnum(employInfo.getE_num());
		work_history.setHstate(2);
		work_history.setH_key(Long.valueOf(work_historyRepo.findAll().size()));
		work_historyRepo.save(work_history);
		
	}
	
	@Override
	public void insertNewWork_history(UnitedEmploy employInfo) {
		Work_history work_history = new Work_history();
		work_history.setH_comment("");
		work_history.setH_date(new Date());
		work_history.setHnum(employInfo.getE_num());
		work_history.setHstate(1);
		work_history.setH_key(Long.valueOf(work_historyRepo.findAll().size()));
		work_historyRepo.save(work_history);
		
	}
	@Override
	public void updateWork_history_comment(Long historyKey, String comment) {
		Work_history findWork_history = work_historyRepo.findById(historyKey).get();
		findWork_history.setH_comment(comment);
		work_historyRepo.save(findWork_history);
		
	}
}
