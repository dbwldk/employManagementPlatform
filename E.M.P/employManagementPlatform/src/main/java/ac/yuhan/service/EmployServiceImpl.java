package ac.yuhan.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ac.yuhan.domain.Employ;
import ac.yuhan.domain.UnitedEmploy;
import ac.yuhan.persistence.EmployRepository;

@Service
public class EmployServiceImpl implements EmployService {
	@Autowired
	private EmployRepository employRepo;
	
	@Override
	public Employ getEmploy(UnitedEmploy employInfo) {
		Optional<Employ> findEmploy = employRepo.findById(employInfo.getE_num());
		if(findEmploy.isPresent())
		{
			return findEmploy.get();
		}
		else return null;
	}
	@Override
	public void updateEmploy(UnitedEmploy employInfo) {
		Employ findEmploy = employRepo.findById(employInfo.getE_num()).get();
		findEmploy.setE_addr(employInfo.getE_addr());
		findEmploy.setE_dept(employInfo.getE_dept_num());
		findEmploy.setE_email(employInfo.getE_email());
		findEmploy.setE_gender(employInfo.getE_gender());
		findEmploy.setE_name(employInfo.getE_name());
		findEmploy.setE_phone(employInfo.getE_phone());
		findEmploy.setE_pic(employInfo.getE_pic());
		findEmploy.setE_pos(employInfo.getE_pos_num());
		findEmploy.setE_pswd(employInfo.getE_pswd());
		
		employRepo.save(findEmploy);
	}
	
	
	@Override
	public void insertEmploy(Employ employ) {
		employRepo.save(employ);
	}
	
	@Override
	public Employ createEmploy(UnitedEmploy employInfo) {
		Employ employ = new Employ();
		employ.setE_num(employInfo.getE_num());
		employ.setE_addr(employInfo.getE_addr());
		employ.setE_birth(employInfo.getE_birth());
		employ.setE_dept(employInfo.getE_dept_num());
		employ.setE_email(employInfo.getE_email());
		employ.setE_gender(employInfo.getE_gender());
		employ.setE_name(employInfo.getE_name());
		employ.setE_phone(employInfo.getE_phone());
		employ.setE_pic(employInfo.getE_pic());
		employ.setE_pos(employInfo.getE_pos_num());
		employ.setE_pswd(employInfo.getE_pswd());
		return employ;
	}

}
