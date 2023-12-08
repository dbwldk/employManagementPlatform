package ac.yuhan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import ac.yuhan.domain.Employ;
import ac.yuhan.domain.Employ_now;
import ac.yuhan.domain.Employ_state;
import ac.yuhan.domain.UnitedEmploy;
import ac.yuhan.service.EmployService;
import ac.yuhan.service.Employ_nowService;
import ac.yuhan.service.Employ_stateService;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginControoller {
	
	@Autowired
	private EmployService employService;
	
	@Autowired
	private Employ_nowService employ_nowService;
	
	@Autowired
	private Employ_stateService employ_stateService;
	
	
	@GetMapping("/login")
	public String loginView()
	{
		return "/login";
	}
	@GetMapping("/")
	public String firstView()
	{
		return "/login";
	}
	
	@PostMapping("/login")
	public String login(Employ employ, HttpSession session) {
		UnitedEmploy unitedEmploy = new UnitedEmploy();
		unitedEmploy.setE_num(employ.getE_num());
		unitedEmploy.setE_pswd(employ.getE_pswd());
		Employ findEmploy = employService.getEmploy(unitedEmploy);
		if(findEmploy != null && findEmploy.getE_pswd().equals(unitedEmploy.getE_pswd()))
		{
			Employ_now employ_now = employ_nowService.getEmploy_now(unitedEmploy);
			Employ_state employ_state = employ_stateService.getEmploy_state(unitedEmploy);
			unitedEmploy.setE_addr(findEmploy.getE_addr());
			unitedEmploy.setE_birth(findEmploy.getE_birth());
			unitedEmploy.setE_dept_num(findEmploy.getE_dept());
			unitedEmploy.setE_email(findEmploy.getE_email());
			unitedEmploy.setE_gender(findEmploy.getE_gender());
			unitedEmploy.setE_name(findEmploy.getE_name());
			unitedEmploy.setE_now(employ_now.getE_now());
			unitedEmploy.setE_phone(findEmploy.getE_phone());
			unitedEmploy.setE_pic(findEmploy.getE_pic());
			unitedEmploy.setE_pos_num(findEmploy.getE_dept());

			unitedEmploy.setE_now(employ_now.getE_now());
			
			unitedEmploy.setE_edate(employ_state.getE_s_edate());
			unitedEmploy.setE_sdate(employ_state.getE_s_sdate());
			unitedEmploy.setE_state(employ_state.getE_s_state());
			session.setAttribute("unitedEmploy", unitedEmploy);
			System.out.println(unitedEmploy.toString());
			return "/normal/work_history";
		}
		else
		{
			return "login";
		}
		
		
	}
}
