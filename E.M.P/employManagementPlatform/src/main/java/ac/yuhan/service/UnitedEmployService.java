package ac.yuhan.service;

import ac.yuhan.domain.Employ;
import ac.yuhan.domain.Employ_now;
import ac.yuhan.domain.Employ_state;
import ac.yuhan.domain.UnitedEmploy;

public interface UnitedEmployService {
	
	public UnitedEmploy createUnitedEmploy(Employ employ, Employ_now employ_now, Employ_state employ_state);
}
