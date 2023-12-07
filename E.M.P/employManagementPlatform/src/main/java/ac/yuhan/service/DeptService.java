package ac.yuhan.service;

import ac.yuhan.domain.Dept;

public interface DeptService {
	public Dept getDept(Long deptNo);
	public void insertDept(Dept dept);
	public void deleteDept(Long deptNo);
}
