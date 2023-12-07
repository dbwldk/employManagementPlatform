package ac.yuhan.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import ac.yuhan.domain.Pos;

public interface PosRepository extends JpaRepository<Pos, Long> {

}
