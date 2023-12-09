package ac.yuhan.service;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;

import ac.yuhan.domain.Dept;

public interface DeptService {
	public Dept getDept(Long deptNo);
	public void insertDept(Dept dept);
	@Modifying(clearAutomatically = true)
	public void deleteDept(Long deptNo);
	public List<Dept> getAllDept();
}
