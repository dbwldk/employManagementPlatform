package ac.yuhan.controller;

import java.io.File;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;

import ac.yuhan.domain.Dept;
import ac.yuhan.domain.Employ;
import ac.yuhan.domain.Pos;
import ac.yuhan.domain.UnitedEmploy;
import ac.yuhan.service.DeptService;
import ac.yuhan.service.EmployService;
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
	
	@GetMapping("/add")
	public String employ_add(Model model) {
		List<Dept> deptList = deptService.getAllDept();
		List<Pos> posList = posService.getAllPos();
		model.addAttribute("deptList", deptList);
		model.addAttribute("posList", posList);
		return "/auth/emp_add";
	}
	
	@PostMapping("/add")
	public String employ_add_func(@RequestParam("e_birth_string") 	@DateTimeFormat(pattern = "yyyy-MM-dd") Date e_birth, @RequestParam("e_sdate_string") 	@DateTimeFormat(pattern = "yyyy-MM-dd") Date e_sdate, UnitedEmploy unitedEmploy, @RequestBody MultipartFile uploadFile, MultipartHttpServletRequest request ) throws Exception{
	
		unitedEmploy.setE_birth(e_birth);
		unitedEmploy.setE_sdate(e_sdate);
		String path = "C:/employManagementPlatform/resources/Pictures";
		File pathDir = new File(path);
		if (!pathDir.exists()) {
			pathDir.mkdirs(); // 디렉토리가 없다면 생성
	       }
		String fileName =uploadFile.getOriginalFilename();
		String filePath = path +"/" +  fileName;
		uploadFile.transferTo(new File(filePath));
		unitedEmploy.setE_pic(filePath);
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
}
