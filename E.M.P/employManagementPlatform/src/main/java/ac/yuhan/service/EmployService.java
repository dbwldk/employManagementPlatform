package ac.yuhan.service;

import ac.yuhan.domain.Employ;
import ac.yuhan.domain.UnitedEmploy;

public interface EmployService {

	public Employ getEmploy(UnitedEmploy employInfo);
	public void updateEmploy(UnitedEmploy employInfo);
	public void insertEmploy(Employ employ);
}
