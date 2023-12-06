package ac.yuhan.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import ac.yuhan.domain.Employ;

public interface EmployRepository extends JpaRepository<Employ, String> {

}
