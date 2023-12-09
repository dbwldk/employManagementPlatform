package ac.yuhan.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Employ_view {
	@Id
	@Column(name="e_num")
	private String ekey;
	@Column(name="e_s_state")
	private int esstate;
	@Column(name="e_dept")
	private int edept;
	@Column(name="e_pos")
	private int epos;
	@Column(name="e_name")
	private String ename;
}
