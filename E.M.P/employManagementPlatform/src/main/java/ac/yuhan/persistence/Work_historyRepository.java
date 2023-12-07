package ac.yuhan.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ac.yuhan.domain.Work_history;

public interface Work_historyRepository extends JpaRepository<Work_history, Long> {
	List<Work_history> findWork_historyByhnum(String searchEmployNo);
	Optional<Work_history> findWork_historyByhstateAndHnum(int searchWorkState, String searchEmployNo);

}
