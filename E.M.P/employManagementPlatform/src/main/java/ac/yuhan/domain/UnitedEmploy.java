package ac.yuhan.domain;

import java.util.Date;

import lombok.Data;

@Data
public class UnitedEmploy {
	private String e_num;
	private String e_name;
	private int e_dept_num;
	private int e_pos_num;
	private int e_gender;
	private String e_phone;
	private String e_addr;
	private String e_email;
	private Date e_birth;
	private String e_pic;
	private String e_pswd;
	private Date e_sdate;
	private Date e_edate;
	private int e_state;
	private int e_now;
}
