package ac.yuhan.domain;


import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Work_history {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int h_key;
	private String h_num;
	private Date h_date;
	private int h_state;
	private String h_comment;
}
