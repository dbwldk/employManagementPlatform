package ac.yuhan.service;

import org.springframework.data.jpa.repository.Modifying;

import ac.yuhan.domain.Employ_state;
import ac.yuhan.domain.UnitedEmploy;

public interface Employ_stateService {
	public Employ_state getEmploy_state(UnitedEmploy employInfo);
	@Modifying(clearAutomatically = true)
	public void insertEmploy_state(UnitedEmploy employInfo);
	@Modifying(clearAutomatically = true)
	public void updateEmploy_state(UnitedEmploy employInfo);
	
}
