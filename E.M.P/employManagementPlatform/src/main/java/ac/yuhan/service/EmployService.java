package ac.yuhan.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;

import ac.yuhan.domain.Employ;
import ac.yuhan.domain.UnitedEmploy;

public interface EmployService {

	public Employ getEmploy(UnitedEmploy employInfo);
	@Modifying(clearAutomatically = true)
	public void updateEmploy(UnitedEmploy employInfo);
	public void insertEmploy(Employ employ);
	public Employ createEmploy(UnitedEmploy employInfo); 
	
	public Page<Employ> getAllEmploy(Pageable pageable);
}
