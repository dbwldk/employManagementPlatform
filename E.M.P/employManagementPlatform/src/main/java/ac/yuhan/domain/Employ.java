package ac.yuhan.domain;


import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Employ {
	@Id
	private String e_num;
	private String e_name;
	private int e_dept;
	private int e_pos;
	private int e_gender;
	private String e_phone;
	private String e_addr;
	private String e_email;
	private Date e_birth;
	private String e_pic;
	private String e_pswd;
}
