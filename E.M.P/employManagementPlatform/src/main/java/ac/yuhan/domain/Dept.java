package ac.yuhan.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Dept {
	@Id
	private Long d_num;
	private String d_name;
}
