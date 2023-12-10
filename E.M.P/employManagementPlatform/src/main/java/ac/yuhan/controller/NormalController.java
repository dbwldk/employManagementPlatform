package ac.yuhan.controller;

import java.io.File;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import ac.yuhan.domain.Work_history;
import ac.yuhan.service.DeptService;
import ac.yuhan.service.EmployService;
import ac.yuhan.service.Employ_nowService;
import ac.yuhan.service.Employ_stateService;
import ac.yuhan.service.PosService;
import ac.yuhan.service.UnitedEmployService;
import ac.yuhan.service.Work_historyService;
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
	private Work_historyService work_historyService;
	

	@Value("${upload.directory}")
	private String uploadDirectory;

	@GetMapping("/pwd_change")
	public String pwdChangeOpen(@RequestParam("pwCheck") int pwCheck, 
			@RequestParam(value="pswd", required = false) String pswd, 
			Model model, 
			HttpSession session) {
		if(session.getAttribute("unitedEmploy") == null)
		{
			return "redirect:/login";
		}
		UnitedEmploy sessionUnitedEmploy = (UnitedEmploy)session.getAttribute("unitedEmploy");
		System.out.println(sessionUnitedEmploy.toString());
		if( pswd != null && !pswd.equals(""))
		{
			if(pswd.equals(sessionUnitedEmploy.getE_pswd()))
			{
				pwCheck=1;
				model.addAttribute("pswd", pswd);
			}
			else
			{
				model.addAttribute("pwCheck", 0);
				return "popup/pwd_change";
			}
		}
		model.addAttribute("pwCheck", pwCheck);
		return "/popup/pwd_change";
	}
	
	
	@PostMapping("/pwd_changed")
	public String pwdChange( 
			@RequestParam(value="e_pswd", required = false) String pswd, 
			Model model, 
			HttpSession session) {
		if(session.getAttribute("unitedEmploy") == null)
		{
			return "redirect:/login";
		}
		UnitedEmploy sessionUnitedEmploy = (UnitedEmploy)session.getAttribute("unitedEmploy");
		sessionUnitedEmploy.setE_pswd(pswd);
		employService.updateEmploy(sessionUnitedEmploy);
		model.addAttribute("windowCloseScript", "window.close();");
		model.addAttribute("pwCheck", 0);
		session.setAttribute("unitedEmploy", sessionUnitedEmploy);
		return "/popup/pwd_change";
	}
	
	@GetMapping("/info_edit")
	public String info_edit(Model model, HttpSession session) {
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
						session.setAttribute("unitedEmploy", sessionUnitedEmploy);
						employService.updateEmploy(sessionUnitedEmploy);
			        }
				}
				
				model.addAttribute("unitedEmploy", sessionUnitedEmploy);
	
				Dept dept = deptService.getDept(sessionUnitedEmploy.getE_dept_num());
				Pos pos = posService.getPos(sessionUnitedEmploy.getE_pos_num());
				model.addAttribute("employDept", dept);
				model.addAttribute("employPos", pos);
				
				return "/normal/info_edit";
			}
			return "redirect:/work_history";
		
		}
		return "redirect:/login";
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
			
		return "redirect:/login";
	}
	

	@GetMapping("/work_history")
	public String work_history(
			@RequestParam( value = "e_history_date_string", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date e_history_date,
			@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
			HttpSession session,	
			Model model) {
		if(session.getAttribute("unitedEmploy") != null)
		{
			UnitedEmploy sessionUnitedEmploy = (UnitedEmploy)session.getAttribute("unitedEmploy");
				if(e_history_date == null)
				{
					Pageable pageable = PageRequest.of(page, size);
					Page<Work_history> work_history = work_historyService.getWork_historyByE_num(sessionUnitedEmploy.getE_num(), pageable);
						if(work_history == null)
						{
							return "redirect:/login";
						}
						
					model.addAttribute("work_history", work_history.getContent());
					model.addAttribute("e_numString", sessionUnitedEmploy.getE_num());
					model.addAttribute("page", work_history);
					System.out.println(work_history.getContent().toString());
					System.out.println(work_history);
					return "/normal/work_history";
				}
				else
				{
					System.out.println(e_history_date.toString());
					Pageable pageable = PageRequest.of(0, size);
					Page<Work_history> work_history = work_historyService.getWork_historyByHistoryDate(e_history_date, sessionUnitedEmploy.getE_num(), pageable);
					if(work_history == null)
					{
						return "redirect:/work_history";
					}

					model.addAttribute("work_history", work_history.getContent());
					model.addAttribute("e_numString", sessionUnitedEmploy.getE_num());
					model.addAttribute("page", work_history);
					System.out.println(work_history.getContent().toString());
					System.out.println(work_history);
					return "/normal/work_history";
				}	
		}
		return "redirect:/login";
	}
	
	@GetMapping("/now_update")
	public String now_update(HttpSession session, Model model,@RequestParam(value="employ_now" , defaultValue="0") int now) {
		if(session.getAttribute("unitedEmploy") != null)
		{
			UnitedEmploy sessionUnitedEmploy = (UnitedEmploy)session.getAttribute("unitedEmploy");

			sessionUnitedEmploy.setE_now(now);
			if(now == 1)
			{
				employ_nowService.updateEmploy_now_One(sessionUnitedEmploy);
				work_historyService.updateWork_history(sessionUnitedEmploy);
			}
			if(now == 2)
			{
				employ_nowService.updateEmploy_now_Two(sessionUnitedEmploy);
				work_historyService.insertWork_history(sessionUnitedEmploy);
			}
			
			session.setAttribute("unitedEmploy", sessionUnitedEmploy);
			return "redirect:/work_history";
		}
		return "redirect:/login";
	}
	
	@GetMapping("/logOut")
	public String logOut(HttpSession session) {
		if(session.getAttribute("unitedEmploy") != null)
		{
			session.removeAttribute("unitedEmploy");
			return "redirect:/login";
		}
		return "redirect:/login";
	}
	
	
}
