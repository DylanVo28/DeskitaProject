package com.deskita.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.deskita.DeskitaFrontEndApplication;

import com.deskita.common.entity.Customer;
import com.deskita.common.entity.Role;
import com.deskita.common.entity.User;
import com.deskita.security.DeskitaCustomerDetails;
import com.deskita.service.CustomerDetailsService;
import com.deskita.service.CustomerService;

@Controller
public class AccountController {
	
	@Autowired
	private CustomerService service;
	
	@Autowired CustomerDetailsService customerDetailsService;
	
	@GetMapping("/account")
	public String viewDetails(@AuthenticationPrincipal DeskitaCustomerDetails loggedUser,Model model) {
		String email=loggedUser.getUsername();
		
		Customer customer=service.getCustomerByEmail(email);
		
		model.addAttribute("customer",customer);
//		model.addAttribute("user",user);
//		model.addAttribute("listRoles",listRoles);
//		
		return "account/account-form";
	}
	
	@PostMapping("/account/update")
	public String updateAccount(Customer customer,RedirectAttributes redirectAttributes,@AuthenticationPrincipal DeskitaCustomerDetails loggedUser) {
//		System.out.println(customer);
		service.updateAccount(customer);
		
		return "redirect:/account";
	}
	
	@PostMapping("/update/password")
	public String changeUserPassword(@AuthenticationPrincipal DeskitaCustomerDetails loggedUser,Locale locale, 	
	  @RequestParam("newPassword") String newPassword, 
	  @RequestParam("oldPassword") String oldPassword,
	  @RequestParam("confirmPassword") String confirmPassword) {
	    Customer customer=service.getCustomerByEmail(loggedUser.getUsername());
	    boolean checkSave=service.updatePassword(customer, oldPassword, newPassword, confirmPassword);
		if(!checkSave) {
			return "redirect:/change-password";
		}
		return "redirect:/account";
	}
	
	@GetMapping("/change-password")
	public String viewChangePassword(Model model) {
		return "account/account-change-password";
	}
}
