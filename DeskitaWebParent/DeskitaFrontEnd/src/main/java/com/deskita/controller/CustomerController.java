package com.deskita.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.deskita.common.entity.Customer;
import com.deskita.common.exception.CustomerNotFoundException;
import com.deskita.service.CustomerService;
import com.sun.mail.imap.Utility;

import net.bytebuddy.utility.RandomString;

@Controller
public class CustomerController {

	@Autowired private CustomerService service;
	
	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("customer",new Customer());
		return "register/register_form";
	}
	
	@GetMapping("/forgot-password")
	public String showForgotPasswordForm() {
		return "authen/forgot-password-form";
	}
	
	@PostMapping("/forgot-password")
	public String processForgotPassword(HttpServletRequest request,Model model) {
		String email=request.getParameter("email");
		String token=RandomString.make(30);
		try {
			service.updateResetPasswordToken(token, email);
			String resetPasswordLink= request.getRequestURL().toString().replace(request.getServletPath(), "")+"/reset-password?token="+token;
			service.sendEmail(email, resetPasswordLink);
			model.addAttribute("message","We have sent a reset password link to your email. Please check.");
		}catch(CustomerNotFoundException ex){
			model.addAttribute("error",ex.getMessage());
		}catch(UnsupportedEncodingException | MessagingException e) {
			model.addAttribute("error","Error while sending email");
		}
		return "authen/forgot-password-form";
	}
	
	@GetMapping("/reset-password")
	public String showResetPasswordForm(@Param(value="token") String token, Model model) {
		Customer customer=service.getByResetPasswordToken(token);
		model.addAttribute("token",token);
		if(customer==null) {
			model.addAttribute("message","invalid token");
			return "authen/message";
		}
		return "authen/reset-password-form";
	}
	
	@PostMapping("reset-password")
	public String processResetPassword(HttpServletRequest request,Model model) {
		String token=request.getParameter("token");
		String password=request.getParameter("password");
		Customer customer=service.getByResetPasswordToken(token);
		model.addAttribute("title","Reset your Password");
		if(customer==null) {
			model.addAttribute("message","Invalid Token");
			return "authen/message";
		}
		else {
			service.updatePasswordToken(customer, password);
			model.addAttribute("message","You have successfully changed your password");
		}
		return "authen/message";
	}
	
	@PostMapping("/customer/save")
	public String saveCustomer(Customer customer) {
		
		service.saveCustomer(customer);
		return "redirect:/register";
	}
}
