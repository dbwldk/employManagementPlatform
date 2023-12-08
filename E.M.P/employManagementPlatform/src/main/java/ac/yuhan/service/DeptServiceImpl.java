package ac.yuhan.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ac.yuhan.domain.Dept;
import ac.yuhan.persistence.DeptRepository;

@Service
public class DeptServiceImpl implements DeptService {
	@Autowired
	private DeptRepository deptRepo;
	
	@Override
	public Dept getDept(Long deptNo) {
		Optional<Dept> findDept = deptRepo.findById(deptNo);
		if(findDept.isPresent())
		{
			return findDept.get();
		}
		else return null;
	}
	
	@Override
	public void insertDept(Dept dept) {
		deptRepo.save(dept);
		
	}
	
	@Override
	public void deleteDept(Long deptNo) {
		deptRepo.deleteById(deptNo);
	}

	@Override
	public List<Dept> getAllDept() {
		List<Dept> deptList = deptRepo.findAll();
		return deptList; 
	}
}
