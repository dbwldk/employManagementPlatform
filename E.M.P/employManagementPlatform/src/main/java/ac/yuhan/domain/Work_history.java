package ac.yuhan.domain;


import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Work_history {
	@Id
	private Long h_key;
	@Column(name = "h_num")
	private String hnum;
	private Date h_date;
	@Column(name = "h_state")
	private int hstate;
	private String h_comment;
}
