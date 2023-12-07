package ac.yuhan.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import ac.yuhan.domain.Dept;

public interface DeptRepository extends JpaRepository<Dept, Long> {

}
