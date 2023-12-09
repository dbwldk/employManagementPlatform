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
	@Column(name = "h_key")
	private Long hkey;
	@Column(name = "h_num")
	private String hnum;
	@Column(name = "h_date")
	private Date hdate;
	@Column(name = "h_state")
	private int hstate;
	private String h_comment;
}
