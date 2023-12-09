package ac.yuhan.service;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ac.yuhan.domain.UnitedEmploy;
import ac.yuhan.domain.Work_history;
import ac.yuhan.persistence.Work_historyRepository;

@Service
public class Work_historyServiceImpl implements Work_historyService {
	@Autowired
	private Work_historyRepository work_historyRepo;
	
	@Override
	public Page<Work_history> getWork_history(UnitedEmploy employInfo, Pageable pageable) {
		Page<Work_history> findWork_history = work_historyRepo.findWork_historyByhnumOrderByHkey(employInfo.getE_num(), pageable);
		if(!findWork_history.isEmpty())
		{
			return findWork_history;
		}
		else 
		{

			return null;
			
		}
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
	public Page<Work_history> getWork_historyByE_num(String e_num, Pageable pageable) {
		Page<Work_history> findWork_history = work_historyRepo.findWork_historyByhnumOrderByHkey(e_num, pageable);
		if(!findWork_history.isEmpty())
		{
			return findWork_history;
		}
		return null;
	}
	
	@Override
	public Page<Work_history> getWork_historyByHistoryDate(Date history_date, String employNo, Pageable pageable) {
		// 시작 날짜를 인자로 받은 날짜의 자정으로 설정
		LocalDateTime startOfDay = LocalDateTime.ofInstant(history_date.toInstant(), ZoneId.systemDefault());

		System.out.println(startOfDay);
		// 끝 날짜를 인자로 받은 날짜의 다음 날 자정으로 설정
		LocalDateTime endOfDay = LocalDateTime.ofInstant(history_date.toInstant(), ZoneId.systemDefault()).plusDays(1);
		System.out.println(endOfDay);
		System.out.println(employNo);
		Page<Work_history> findWork_history = work_historyRepo.findWork_historyByhdateBetweenAndHnumOrderByHkey(
				Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant()),
		        Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant()),
		        employNo, pageable);
		if(!findWork_history.isEmpty())
		{
			return findWork_history;
		}
		return null;
		
	}
	
	@Override
	public void updateWork_history(UnitedEmploy employInfo) {
		Work_history work_history = work_historyRepo.findWork_historyByhstateAndHnum(0, employInfo.getE_num()).get();
		work_history.setH_comment("");
		work_history.setHdate(new Date());
		work_history.setHstate(1);
		work_historyRepo.save(work_history);
		
	}
	
	@Override
	public void insertWork_history(UnitedEmploy employInfo) {
		Work_history work_history = new Work_history();
		work_history.setH_comment("");
		work_history.setHdate(new Date());
		work_history.setHnum(employInfo.getE_num());
		work_history.setHstate(2);
		work_history.setHkey(Long.valueOf(work_historyRepo.findAll().size()));
		work_historyRepo.save(work_history);
		
	}
	
	@Override
	public void insertNewWork_history(UnitedEmploy employInfo) {
		Work_history work_history = new Work_history();
		work_history.setH_comment("");
		work_history.setHdate(new Date());
		work_history.setHnum(employInfo.getE_num());
		work_history.setHstate(1);
		work_history.setHkey(Long.valueOf(work_historyRepo.findAll().size()));
		work_historyRepo.save(work_history);
		
	}
	@Override
	public void updateWork_history_comment(Long historyKey, String comment) {
		Work_history findWork_history = work_historyRepo.findById(historyKey).get();
		findWork_history.setH_comment(comment);
		work_historyRepo.save(findWork_history);
		
	}
	
}
