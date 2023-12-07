package ac.yuhan.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Pos {
	@Id
	private Long p_num;
	private String p_name;
}
