package ac.yuhan.service;

import java.util.List;

import ac.yuhan.domain.Dept;

public interface DeptService {
	public Dept getDept(Long deptNo);
	public void insertDept(Dept dept);
	public void deleteDept(Long deptNo);
	public List<Dept> getAllDept();
}
