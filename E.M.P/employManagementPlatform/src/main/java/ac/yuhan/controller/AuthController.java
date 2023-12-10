package ac.yuhan.controller;

import java.io.File;
import java.util.Date;
import java.util.List;

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
import ac.yuhan.domain.Employ_view;
import ac.yuhan.domain.Pos;
import ac.yuhan.domain.UnitedEmploy;
import ac.yuhan.domain.Work_history;
import ac.yuhan.service.DeptService;
import ac.yuhan.service.EmployService;
import ac.yuhan.service.Employ_PicSaveStorage;
import ac.yuhan.service.Employ_nowService;
import ac.yuhan.service.Employ_stateService;
import ac.yuhan.service.Employ_viewService;
import ac.yuhan.service.PosService;
import ac.yuhan.service.UnitedEmployService;
import ac.yuhan.service.Work_historyService;
import jakarta.servlet.http.HttpSession;

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
	private UnitedEmployService unitedEmployService;

	@Autowired
	private Work_historyService work_historyService;
	
	@Autowired
	private Employ_viewService employ_viewService;
	
	@Autowired
	private Employ_PicSaveStorage storageServce;
	

	@Value("${upload.directory}")
	private String uploadDirectory;
	
	@GetMapping("/add")
	public String employ_add(Model model, HttpSession session) {
		if(session.getAttribute("unitedEmploy") != null)
		{
			UnitedEmploy sessionUnitedEmploy = (UnitedEmploy)session.getAttribute("unitedEmploy");
			if(sessionUnitedEmploy.getE_dept_num() >= 1 && sessionUnitedEmploy.getE_dept_num() <= 9 )
			{
				List<Dept> deptList = deptService.getAllDept();
				List<Pos> posList = posService.getAllPos();
				model.addAttribute("deptList", deptList);
				model.addAttribute("posList", posList);
				return "/auth/emp_add";
			}
			return "redirect:/work_history";
		}
		return "redirect:/login";
	
	}
	
	@PostMapping("/add")
	public String employ_add_func(@RequestParam("e_birth_string") 	@DateTimeFormat(pattern = "yyyy-MM-dd") Date e_birth, @RequestParam("e_sdate_string") 	@DateTimeFormat(pattern = "yyyy-MM-dd") Date e_sdate, UnitedEmploy unitedEmploy, @RequestBody MultipartFile uploadFile, HttpSession session ) throws Exception{
		if(session.getAttribute("unitedEmploy") != null)
		{
			UnitedEmploy sessionUnitedEmploy = (UnitedEmploy)session.getAttribute("unitedEmploy");
			if(sessionUnitedEmploy.getE_dept_num() >= 1 && sessionUnitedEmploy.getE_dept_num() <= 9 )
			{
				Employ findEmploy = employService.getEmploy(unitedEmploy);
				if(findEmploy == null)
				{
					unitedEmploy.setE_birth(e_birth);
					unitedEmploy.setE_sdate(e_sdate);
					if(!uploadFile.isEmpty())
					{
						storageServce.saveFile(uploadFile);
						unitedEmploy.setE_pic("/imgs/img/" + uploadFile.getOriginalFilename());
					}
					else
					{
						unitedEmploy.setE_pic("/imgs/img/normal.png");
					}
					unitedEmploy.setE_pswd("0000");
					
					Employ employ = employService.createEmploy(unitedEmploy);
					
					employService.insertEmploy(employ);
					employ_nowService.insertEmploy_now(unitedEmploy);
					employ_stateService.insertEmploy_state(unitedEmploy);
				
					employ_nowService.updateEmploy_now_One(unitedEmploy);
					work_historyService.insertNewWork_history(unitedEmploy);
					return "redirect:/emp_list";
				}
				return "redirect:/emp_list";
			}
			return "redirect:/work_history";
		}
		return "redirect:/login";
	}
	
	@GetMapping("/authInfo_edit")
	public String authInfo_edit(@RequestParam("e_num_String") String e_num, Model model, HttpSession session) {
		if(session.getAttribute("unitedEmploy") != null)
		{
			UnitedEmploy sessionUnitedEmploy = (UnitedEmploy)session.getAttribute("unitedEmploy");
			if(sessionUnitedEmploy.getE_dept_num() >= 1 && sessionUnitedEmploy.getE_dept_num() <= 9 )
			{
				UnitedEmploy unitedEmploy = new UnitedEmploy();
				unitedEmploy.setE_num(e_num);
				Employ findEmploy = employService.getEmploy(unitedEmploy);
				if(findEmploy != null)
				{
					unitedEmploy.setE_pic(findEmploy.getE_pic());
					
					Employ_now employ_now = employ_nowService.getEmploy_now(unitedEmploy);
					Employ_state employ_state = employ_stateService.getEmploy_state(unitedEmploy);
		
					unitedEmploy = unitedEmployService.createUnitedEmploy(findEmploy, employ_now, employ_state);
					if(findEmploy.getE_pic() != null)
					{
						File existFile = new File(uploadDirectory + unitedEmploy.getE_pic().substring(10));
						
						if (!existFile.exists()) {
							System.out.println("이미지 못 찾음");
							findEmploy.setE_pic("/imgs/img/normal.png");
							unitedEmploy.setE_pic(findEmploy.getE_pic());
							employService.updateEmploy(unitedEmploy);
				        }
					}
					
					model.addAttribute("unitedEmploy", unitedEmploy);
		
					List<Dept> deptList = deptService.getAllDept();
					List<Pos> posList = posService.getAllPos();
					model.addAttribute("deptList", deptList);
					model.addAttribute("posList", posList);
					
					Dept dept = deptService.getDept(unitedEmploy.getE_dept_num());
					Pos pos = posService.getPos(unitedEmploy.getE_pos_num());
					
					model.addAttribute("employDept", dept);
					model.addAttribute("employPos", pos);
		
					return "/auth/info_edit";
				}
				return "redirect:/emp_list";
			}
			return "redirect:/work_history";
		}
		return "redirect:/login";
	}
	
	@PostMapping("/authInfo_edit")
	public String employ_edit_func(@RequestParam("e_birth_string") 	@DateTimeFormat(pattern = "yyyy-MM-dd") Date e_birth, @RequestParam("e_sdate_string") 	@DateTimeFormat(pattern = "yyyy-MM-dd") Date e_sdate,UnitedEmploy unitedEmploy, @RequestBody MultipartFile uploadFile, HttpSession session ) throws Exception{
		if(session.getAttribute("unitedEmploy") != null)
		{
			UnitedEmploy sessionUnitedEmploy = (UnitedEmploy)session.getAttribute("unitedEmploy");
			if(sessionUnitedEmploy.getE_dept_num() >= 1 && sessionUnitedEmploy.getE_dept_num() <= 9 )
			{
				unitedEmploy.setE_birth(e_birth);
				unitedEmploy.setE_sdate(e_sdate);
				
				if(!uploadFile.isEmpty())
				{ 
					File deleteFile = new File(uploadDirectory + unitedEmploy.getE_pic());
				    if (deleteFile.exists()) {
			            if (deleteFile.delete()) {
			                System.out.println("삭제 성공");
			              
			            } else {
			                System.out.println("삭제 실패");
			            }
			        } else {
			            System.out.println("이미지 못 찾음");
			        }
					unitedEmploy.setE_pic("/imgs/img/" + uploadFile.getOriginalFilename());
					storageServce.saveFile(uploadFile);
				}
				
				employService.updateEmploy(unitedEmploy);
				if(unitedEmploy.getE_state() == 1)
				{
					employ_stateService.updateEmploy_state(unitedEmploy);
				}
				
				return "redirect:/emp_list";
			}
			return "redirect:/work_history";
		}
		return "redirect:/login";
	}
	
	@GetMapping("/auth/work_history")
	public String authWork_history(@RequestParam(value = "e_num_string", required = false) String e_num,
			@RequestParam( value = "e_history_date_string", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date e_history_date,
			@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
			HttpSession session,	
			Model model) {
		if(session.getAttribute("unitedEmploy") != null || e_num != null)
		{
			UnitedEmploy sessionUnitedEmploy = (UnitedEmploy)session.getAttribute("unitedEmploy");
			if(sessionUnitedEmploy.getE_dept_num() >= 1 && sessionUnitedEmploy.getE_dept_num() <= 9 )
			{
				if(e_history_date == null)
				{
					Pageable pageable = PageRequest.of(page, size);
					Page<Work_history> work_history = work_historyService.getWork_historyByE_num(e_num, pageable);
						if(work_history == null)
						{
							return "redirect:/work_history";
						}
						
					model.addAttribute("work_history", work_history.getContent());
					model.addAttribute("e_numString", e_num);
					model.addAttribute("page", work_history);
					System.out.println(work_history.getContent().toString());
					System.out.println(work_history);
					return "/auth/work_history";
				}
				else
				{
					System.out.println(e_history_date.toString());
					Pageable pageable = PageRequest.of(0, size);
					Page<Work_history> work_history = work_historyService.getWork_historyByHistoryDate(e_history_date, e_num, pageable);
					if(work_history == null)
					{
						return "redirect:/auth/work_history?e_num_string=" + e_num;
					}

					model.addAttribute("work_history", work_history.getContent());
					model.addAttribute("e_numString", e_num);
					model.addAttribute("page", work_history);
					System.out.println(work_history.getContent().toString());
					System.out.println(work_history);
					return "/auth/work_history";
				}
				
			}
			return "redirect:/work_history";	
		}
		return "redirect:/login";
	}
	
	@PostMapping("/auth/work_history")
	public String authWork_historyFunc(@RequestParam(value = "e_num_string", required = false) String e_num,
			Work_history updateWork_history,
			HttpSession session,	
			Model model) {
		if(session.getAttribute("unitedEmploy") != null || e_num != null)
		{
			UnitedEmploy sessionUnitedEmploy = (UnitedEmploy)session.getAttribute("unitedEmploy");
			if(sessionUnitedEmploy.getE_dept_num() >= 1 && sessionUnitedEmploy.getE_dept_num() <= 9 )
			{
					Pageable pageable = PageRequest.of(0, 10);
					work_historyService.updateWork_history_comment(updateWork_history.getHkey(), updateWork_history.getH_comment());
					Page<Work_history> work_history = work_historyService.getWork_historyByE_num(e_num, pageable);
						if(work_history == null)
						{
							return "redirect:/auth/work_history";
						}
						
					model.addAttribute("work_history", work_history.getContent());
					model.addAttribute("e_numString", e_num);
					model.addAttribute("page", work_history);
					System.out.println(work_history.getContent().toString());
					System.out.println(work_history);
					return "redirect:/auth/work_history?e_num_string=" + e_num;
			}
			return "redirect:/work_history";	
		}
		return "redirect:/login";
	}
	
	@GetMapping("/emp_list")
	public String emp_list(
			@RequestParam(value = "e_name_string", required = false) String e_name,
			@RequestParam(value = "e_dept_string", defaultValue="0") int e_dept,
			@RequestParam(value = "e_pos_string", defaultValue="0") int e_pos,
			@RequestParam(value = "e_s_state_string", defaultValue="0") int e_s_state,
			@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
			HttpSession session,	
			Model model) {
		if(session.getAttribute("unitedEmploy") != null)
		{
			UnitedEmploy sessionUnitedEmploy = (UnitedEmploy)session.getAttribute("unitedEmploy");
			if(sessionUnitedEmploy.getE_dept_num() >= 1 && sessionUnitedEmploy.getE_dept_num() <= 9 )
			{
			
				if(e_name != null && !e_name.equals(""))
				{
					e_dept = 0;
					e_pos = 0;
					e_s_state = 0;
					model.addAttribute("employ_name", e_name);
					
				}
				else
				{
					System.out.println("뭐");
					model.addAttribute("employ_name", "");
				}
				Pageable pageable = PageRequest.of(page, size);
				Page<Employ_view> employ_view = employ_viewService.findEmploy_view(e_name, e_dept, e_pos, e_s_state, pageable);

				if(employ_view == null)
				{
					return "redirect:/work_history";
				}

				model.addAttribute("dept_num", e_dept);
				model.addAttribute("pos_num", e_pos);
				model.addAttribute("state_num", e_s_state);
				
				model.addAttribute("employ_view", employ_view.getContent());
				model.addAttribute("page", employ_view);
				
				
				List<Dept> deptList = deptService.getAllDept();
				List<Pos> posList = posService.getAllPos();
				model.addAttribute("deptList", deptList);
				model.addAttribute("posList", posList);
				
				return "/auth/emp_list";
			}
			return "redirect:/work_history";	
		}
		return "redirect:/login";
	}
}
