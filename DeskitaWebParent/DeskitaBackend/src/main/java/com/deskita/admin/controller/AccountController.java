package com.deskita.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.deskita.admin.security.DeskitaUserDetails;
import com.deskita.admin.service.UserService;
import com.deskita.common.entity.Role;
import com.deskita.common.entity.User;

@Controller
public class AccountController {

	@Autowired 
	private UserService service;
	
	@GetMapping("/account")
	public String viewDetails(@AuthenticationPrincipal DeskitaUserDetails loggedUser,Model model) {
		String email=loggedUser.getUsername();
		List<Role> listRoles= service.listRoles();
		User user=service.getUserByEmail(email);
		model.addAttribute("user",user);
		model.addAttribute("listRoles",listRoles);
		
		return "user/account-form";
	}
	
	@PostMapping("/account/update")
	public String saveDetails(User user, RedirectAttributes redirectAttributes,@AuthenticationPrincipal DeskitaUserDetails loggedUser) {
		service.updateAccount(user);
		return "redirect:/account";
	}
}
