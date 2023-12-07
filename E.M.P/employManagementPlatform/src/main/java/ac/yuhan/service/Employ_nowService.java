package ac.yuhan.service;

import ac.yuhan.domain.Employ_now;
import ac.yuhan.domain.UnitedEmploy;

public interface Employ_nowService {
	public Employ_now getEmploy_now(UnitedEmploy employInfo);
	public void insertEmploy_now(UnitedEmploy employInfo);
	public void updateEmploy_now_One(UnitedEmploy employInfo);
	public void updateEmploy_now_Two(UnitedEmploy employInfo);
}
