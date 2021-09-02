package com.deskita.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.deskita.common.entity.Customer;
import com.deskita.service.CustomerService;

@Controller
public class CustomerController {

	@Autowired private CustomerService service;
	
	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("customer",new Customer());
		return "register/register_form";
	}
	
	
	
	@PostMapping("/customer/save")
	public String saveCustomer(Customer customer) {
		System.out.println(customer.toString());
		
		service.saveCustomer(customer);
		return "redirect:/register";
	}
}
