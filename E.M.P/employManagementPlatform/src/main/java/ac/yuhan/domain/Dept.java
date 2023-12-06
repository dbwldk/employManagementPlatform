package ac.yuhan.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Dept {
	@Id
	private int d_num;
	private String d_name;
}
