package com.deskita.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.deskita.admin.service.UserService;
import com.deskita.common.entity.Role;
import com.deskita.common.entity.User;

@Controller
public class UserController {

	@Autowired
	private UserService service;
	
	@GetMapping("/users")
	public String listAll(Model model) {
		List<User> listUsers=service.listAll();
		model.addAttribute("listUsers",listUsers);
		return "user/users";
	}
	
	@GetMapping("/users/new")
	public String createUser(Model model) {
		User user=new User();
		user.setEnabled(true);
		List<Role> listRoles= service.listRoles();
		model.addAttribute("user",user);
		model.addAttribute("listRoles",listRoles);
		return "user/user_form";
	}
	
	@PostMapping("/users/save")
	public String saveUser(User user) {
		service.saveUser(user);
		
		return "redirect:/users";
	}
}
