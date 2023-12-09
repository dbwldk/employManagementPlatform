package ac.yuhan.service;

import org.springframework.stereotype.Service;

import ac.yuhan.domain.Employ;
import ac.yuhan.domain.Employ_now;
import ac.yuhan.domain.Employ_state;
import ac.yuhan.domain.UnitedEmploy;

@Service
public class UnitedEmployServiceImpl implements UnitedEmployService {
	
	@Override
	public UnitedEmploy createUnitedEmploy(Employ employ, Employ_now employ_now, Employ_state employ_state) {
		UnitedEmploy unitedEmploy = new UnitedEmploy();
		unitedEmploy.setE_num(employ.getE_num());
		unitedEmploy.setE_pswd(employ.getE_pswd());
		unitedEmploy.setE_addr(employ.getE_addr());
		unitedEmploy.setE_birth(employ.getE_birth());
		unitedEmploy.setE_dept_num(employ.getEdept());
		unitedEmploy.setE_email(employ.getE_email());
		unitedEmploy.setE_gender(employ.getE_gender());
		unitedEmploy.setE_name(employ.getE_name());
		unitedEmploy.setE_phone(employ.getE_phone());
		unitedEmploy.setE_pos_num(employ.getEpos());
		unitedEmploy.setE_pic(employ.getE_pic());
		
		unitedEmploy.setE_now(employ_now.getE_now());
		
		unitedEmploy.setE_edate(employ_state.getE_s_edate());
		unitedEmploy.setE_sdate(employ_state.getE_s_sdate());
		unitedEmploy.setE_state(employ_state.getE_s_state());
		// TODO Auto-generated method stub
		return unitedEmploy;
	}
}
