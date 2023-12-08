package ac.yuhan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NormalController {

	@GetMapping("/pwd_change")
	public String pwdChangeOpen() {
		return "/popup/pwd_change";
	}
}
