package ac.yuhan.persistence;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ac.yuhan.domain.Work_history;

public interface Work_historyRepository extends JpaRepository<Work_history, Long> {
	Page<Work_history> findWork_historyByhnumOrderByHkey(String searchEmployNo, Pageable pageable);
	Page<Work_history> findWork_historyByhdateBetweenAndHnumOrderByHkey(Date startDate, Date endDate, String searchEmployNo, Pageable pageable );
	Optional<Work_history> findWork_historyByhstateAndHnum(int searchWorkState, String searchEmployNo);

}
