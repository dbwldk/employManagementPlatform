package ac.yuhan.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ac.yuhan.domain.Employ_view;

public interface Employ_viewRepository extends JpaRepository<Employ_view, String> {
	Page<Employ_view> findEmploy_viewByenameLikeOrderByEkey(String searchEname, Pageable pageable);
	
	Page<Employ_view> findEmploy_viewByeposOrderByEkey(int searchEpos, Pageable pageable);
	Page<Employ_view> findEmploy_viewByedeptOrderByEkey(int searchEdept, Pageable pageable);
	Page<Employ_view> findEmploy_viewByesstateOrderByEkey(int searchEsstate, Pageable pageable);
	
	Page<Employ_view> findEmploy_viewByeposAndEdeptOrderByEkey(int searchEpos, int searchEdept, Pageable pageable);
	Page<Employ_view> findEmploy_viewByeposAndEsstateOrderByEkey(int searchEpos, int searchEsstate, Pageable pageable);
	Page<Employ_view> findEmploy_viewByesstateAndEdeptOrderByEkey(int searchEsstate, int searchEdept, Pageable pageable);
	
	Page<Employ_view> findEmploy_viewByesstateAndEdeptAndEposOrderByEkey(int searchEsstate, int searchEdept, int searchEpos, Pageable pageable);
	
}
