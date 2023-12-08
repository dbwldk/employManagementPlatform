package ac.yuhan.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ac.yuhan.domain.Dept;
import ac.yuhan.domain.Employ;
import ac.yuhan.domain.Employ_now;
import ac.yuhan.domain.Employ_state;
import ac.yuhan.domain.Pos;
import ac.yuhan.domain.UnitedEmploy;
import ac.yuhan.service.DeptService;
import ac.yuhan.service.EmployService;
import ac.yuhan.service.Employ_PicSaveStorage;
import ac.yuhan.service.Employ_nowService;
import ac.yuhan.service.Employ_stateService;
import ac.yuhan.service.PosService;

@Controller
public class AuthController {

	@Autowired
	private DeptService deptService;
	
	@Autowired
	private PosService posService;
	
	@Autowired
	private EmployService employService;
	
	@Autowired
	private Employ_nowService employ_nowService;
	
	@Autowired
	private Employ_stateService employ_stateService;
	
	@Autowired
	private Employ_PicSaveStorage storageServce;
	
	@GetMapping("/add")
	public String employ_add(Model model) {
		List<Dept> deptList = deptService.getAllDept();
		List<Pos> posList = posService.getAllPos();
		model.addAttribute("deptList", deptList);
		model.addAttribute("posList", posList);
		return "/auth/emp_add";
	}
	
	@PostMapping("/add")
	public String employ_add_func(@RequestParam("e_birth_string") 	@DateTimeFormat(pattern = "yyyy-MM-dd") Date e_birth, @RequestParam("e_sdate_string") 	@DateTimeFormat(pattern = "yyyy-MM-dd") Date e_sdate, UnitedEmploy unitedEmploy, @RequestBody MultipartFile uploadFile ) throws Exception{
	
		unitedEmploy.setE_birth(e_birth);
		unitedEmploy.setE_sdate(e_sdate);
		storageServce.saveFile(uploadFile);
		unitedEmploy.setE_pic("/imgs/img/" + uploadFile.getOriginalFilename());
		unitedEmploy.setE_pswd("0000");
		Employ employ = new Employ();
		employ.setE_num(unitedEmploy.getE_num());
		employ.setE_addr(unitedEmploy.getE_addr());
		employ.setE_birth(unitedEmploy.getE_birth());
		employ.setE_dept(unitedEmploy.getE_dept_num());
		employ.setE_email(unitedEmploy.getE_email());
		employ.setE_gender(unitedEmploy.getE_gender());
		employ.setE_name(unitedEmploy.getE_name());
		employ.setE_phone(unitedEmploy.getE_phone());
		employ.setE_pic(unitedEmploy.getE_pic());
		employ.setE_pos(unitedEmploy.getE_pos_num());
		employ.setE_pswd(unitedEmploy.getE_pswd());
		
		employService.insertEmploy(employ);
		employ_nowService.insertEmploy_now(unitedEmploy);
		employ_stateService.insertEmploy_state(unitedEmploy);
	
		return "/login";
	}
	
	@GetMapping("/authInfo_edit")
	public String authInfo_edit(@RequestParam("e_num_String") String e_num, Model model) {
		UnitedEmploy unitedEmploy = new UnitedEmploy();
		unitedEmploy.setE_num(e_num);
		Employ findEmploy = employService.getEmploy(unitedEmploy);
		if(findEmploy != null)
		{
			Employ_now employ_now = employ_nowService.getEmploy_now(unitedEmploy);
			Employ_state employ_state = employ_stateService.getEmploy_state(unitedEmploy);

			unitedEmploy.setE_pswd(findEmploy.getE_pswd());
			unitedEmploy.setE_addr(findEmploy.getE_addr());
			unitedEmploy.setE_birth(findEmploy.getE_birth());
			unitedEmploy.setE_dept_num(findEmploy.getE_dept());
			unitedEmploy.setE_email(findEmploy.getE_email());
			unitedEmploy.setE_gender(findEmploy.getE_gender());
			unitedEmploy.setE_name(findEmploy.getE_name());
			unitedEmploy.setE_now(employ_now.getE_now());
			unitedEmploy.setE_phone(findEmploy.getE_phone());
			unitedEmploy.setE_pic(findEmploy.getE_pic());
			unitedEmploy.setE_pos_num(findEmploy.getE_pos());

			unitedEmploy.setE_now(employ_now.getE_now());
			
			unitedEmploy.setE_edate(employ_state.getE_s_edate());
			unitedEmploy.setE_sdate(employ_state.getE_s_sdate());
			unitedEmploy.setE_state(employ_state.getE_s_state());
			model.addAttribute("unitedEmploy", unitedEmploy);

			List<Dept> deptList = deptService.getAllDept();
			List<Pos> posList = posService.getAllPos();
			model.addAttribute("deptList", deptList);
			model.addAttribute("posList", posList);
			System.out.println(unitedEmploy.toString());

			return "/auth/info_edit";
		}
		return "/normal/work_history";
	}
	
	@PostMapping("/authInfo_edit")
	public String employ_edit_func(UnitedEmploy unitedEmploy, @RequestBody MultipartFile uploadFile ) throws Exception{

		unitedEmploy.setE_pic("/imgs/img/" + uploadFile.getOriginalFilename());
		storageServce.saveFile(uploadFile);
		employService.updateEmploy(unitedEmploy);
		
		if(unitedEmploy.getE_state() == 1)
		{
			employ_stateService.updateEmploy_state(unitedEmploy);
		}
	
		return "/login";
	}
}
