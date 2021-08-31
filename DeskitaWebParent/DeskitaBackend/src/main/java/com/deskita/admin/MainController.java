package com.deskita.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	
	@GetMapping("/login")
	public String login() {
		return "authen/login";
	}
	
	@GetMapping("/")
	public String homePage() {
		return "index";
	}
}
