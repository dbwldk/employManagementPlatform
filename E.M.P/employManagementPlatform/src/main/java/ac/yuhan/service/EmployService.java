package ac.yuhan.service;

import org.springframework.data.jpa.repository.Modifying;

import ac.yuhan.domain.Employ;
import ac.yuhan.domain.UnitedEmploy;

public interface EmployService {

	public Employ getEmploy(UnitedEmploy employInfo);
	@Modifying(clearAutomatically = true)
	public void updateEmploy(UnitedEmploy employInfo);
	@Modifying(clearAutomatically = true)
	public void insertEmploy(Employ employ);
}
