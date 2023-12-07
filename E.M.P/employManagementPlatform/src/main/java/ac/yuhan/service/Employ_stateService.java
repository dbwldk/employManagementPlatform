package ac.yuhan.service;

import ac.yuhan.domain.Employ_state;
import ac.yuhan.domain.UnitedEmploy;

public interface Employ_stateService {
	public Employ_state getEmploy_state(UnitedEmploy employInfo);
	public void insertEmploy_state(UnitedEmploy employInfo);
	public void updateEmploy_state(UnitedEmploy employInfo);
	
}
