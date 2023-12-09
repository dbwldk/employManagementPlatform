package ac.yuhan.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ac.yuhan.domain.Employ_now;
import ac.yuhan.domain.UnitedEmploy;
import ac.yuhan.persistence.Employ_nowRepository;

@Service
public class Employ_nowServiceImpl implements Employ_nowService {
	@Autowired
	private Employ_nowRepository employ_nowRepo;
	
	@Override
	public Employ_now getEmploy_now(UnitedEmploy employInfo) {
		Optional<Employ_now> findEmploy_now = employ_nowRepo.findById(employInfo.getE_num());
		if(findEmploy_now.isPresent())
		{
			return findEmploy_now.get();
		}
		else return null;
	}
	@Override
	public void insertEmploy_now(UnitedEmploy employInfo) {
		Employ_now employ_now = new Employ_now();
		employ_now.setE_now(0);
		employ_now.setE_num(employInfo.getE_num());
		employ_nowRepo.save(employ_now);
		
	}
	@Override
	public void updateEmploy_now_One(UnitedEmploy employInfo) {
		Employ_now findEmploy_now = employ_nowRepo.findById(employInfo.getE_num()).get();
		findEmploy_now.setE_now(1);
		employ_nowRepo.save(findEmploy_now);
	}
	
	@Override
	public void updateEmploy_now_Two(UnitedEmploy employInfo) {
		Employ_now findEmploy_now = employ_nowRepo.findById(employInfo.getE_num()).get();
		findEmploy_now.setE_now(2);
		employ_nowRepo.save(findEmploy_now);
	}
	

}
