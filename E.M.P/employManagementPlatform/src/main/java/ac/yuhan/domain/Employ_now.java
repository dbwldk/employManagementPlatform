package ac.yuhan.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Employ_now {
	@Id
	private String e_num;
	private int e_now;
}
