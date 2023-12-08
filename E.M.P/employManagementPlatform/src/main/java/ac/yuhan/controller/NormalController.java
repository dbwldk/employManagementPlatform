package ac.yuhan.controller;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import ac.yuhan.service.UnitedEmployService;
import jakarta.servlet.http.HttpSession;

@Controller
public class NormalController {
	
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
	private UnitedEmployService unitedEmployService;
	
	@Autowired
	private Employ_PicSaveStorage storageServce;
	

	@Value("${upload.directory}")
	private String uploadDirectory;

	@GetMapping("/pwd_change")
	public String pwdChangeOpen() {
		return "/popup/pwd_change";
	}
	
	@GetMapping("/info_edit")
	public String authInfo_edit(Model model, HttpSession session) {
		if(session.getAttribute("unitedEmploy") != null)
		{
			UnitedEmploy sessionUnitedEmploy = (UnitedEmploy)session.getAttribute("unitedEmploy");
			
			Employ findEmploy = employService.getEmploy(sessionUnitedEmploy);
			if(findEmploy != null)
			{
				sessionUnitedEmploy.setE_pic(findEmploy.getE_pic());
				
				Employ_now employ_now = employ_nowService.getEmploy_now(sessionUnitedEmploy);
				Employ_state employ_state = employ_stateService.getEmploy_state(sessionUnitedEmploy);
	
				sessionUnitedEmploy = unitedEmployService.createUnitedEmploy(findEmploy, employ_now, employ_state);
				if(findEmploy.getE_pic() != null)
				{
					File existFile = new File(uploadDirectory + sessionUnitedEmploy.getE_pic().substring(10));
					
					if (!existFile.exists()) {
						System.out.println("이미지 못 찾음");
						findEmploy.setE_pic("/imgs/img/normal.png");
						sessionUnitedEmploy.setE_pic(findEmploy.getE_pic());
			        }
				}
				employService.updateEmploy(sessionUnitedEmploy);
				model.addAttribute("unitedEmploy", sessionUnitedEmploy);
	
				Dept dept = deptService.getDept(sessionUnitedEmploy.getE_dept_num());
				Pos pos = posService.getPos(sessionUnitedEmploy.getE_pos_num());
				model.addAttribute("employDept", dept);
				model.addAttribute("employPos", pos);
				
				return "/normal/info_edit";
			}
			return "/normal/work_history";
		
		}
		return "/login";
	}
	@PostMapping("/info_edit")
	public String employ_edit_func(UnitedEmploy unitedEmploy, @RequestBody MultipartFile uploadFile, HttpSession session ) throws Exception{
		if(session.getAttribute("unitedEmploy") != null)
		{
			UnitedEmploy sessionUnitedEmploy = (UnitedEmploy)session.getAttribute("unitedEmploy");
			System.out.println(sessionUnitedEmploy.toString());
			sessionUnitedEmploy.setE_email(unitedEmploy.getE_email());
			sessionUnitedEmploy.setE_phone(unitedEmploy.getE_phone());
			System.out.println(sessionUnitedEmploy.toString());
			employService.updateEmploy(sessionUnitedEmploy);
			session.setAttribute("unitedEmploy", sessionUnitedEmploy);
			System.out.println(sessionUnitedEmploy.toString());
			System.out.println(session.getAttribute("unitedEmploy").toString());
			return "redirect:/info_edit";
		}
			
		return "/login";
	}
}
