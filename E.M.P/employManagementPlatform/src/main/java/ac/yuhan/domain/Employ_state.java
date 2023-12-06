package ac.yuhan.domain;


import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Employ_state {
	@Id
	private String e_s_num;
	private Date e_s_sdate;
	private Date e_s_edate;
	private int e_s_state;
}
