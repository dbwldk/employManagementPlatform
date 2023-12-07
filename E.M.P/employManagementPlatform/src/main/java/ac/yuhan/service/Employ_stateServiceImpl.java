package ac.yuhan.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ac.yuhan.domain.Employ_state;
import ac.yuhan.domain.UnitedEmploy;
import ac.yuhan.persistence.Employ_stateRepository;

@Service
public class Employ_stateServiceImpl implements Employ_stateService {
	@Autowired
	private Employ_stateRepository employ_stateRepo;
	
	@Override
	public Employ_state getEmploy_state(UnitedEmploy employInfo) {
		Optional<Employ_state> findEmploy_state = employ_stateRepo.findById(employInfo.getE_num());
		if(findEmploy_state.isPresent())
		{
			return findEmploy_state.get();
		}
		else return null;
	}
	
	@Override
	public void insertEmploy_state(UnitedEmploy employInfo) {
		Employ_state employ_state = new Employ_state();
		employ_state.setE_s_num(employInfo.getE_num());
		employ_state.setE_s_sdate(new Date());
		employ_state.setE_s_state(0);
		
		employ_stateRepo.save(employ_state);
		
	}
	@Override
	public void updateEmploy_state(UnitedEmploy employInfo) {
		Employ_state findEmploy_state = employ_stateRepo.findById(employInfo.getE_num()).get();
		findEmploy_state.setE_s_state(1);
		findEmploy_state.setE_s_edate(new Date());
		
		employ_stateRepo.save(findEmploy_state);
	}

}
