package ac.yuhan.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ac.yuhan.domain.Employ_view;

public interface Employ_viewService {
	public Page<Employ_view> findEmploy_view(String searchEname, int searchDept, int searchPos, int searchEsstate, Pageable pageable);
	
}
