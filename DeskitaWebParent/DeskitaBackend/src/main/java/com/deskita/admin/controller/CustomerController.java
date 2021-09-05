package com.deskita.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.deskita.admin.service.CustomerService;
import com.deskita.common.entity.Customer;
import com.deskita.common.exception.CustomerNotFoundException;

@Controller
public class CustomerController {

	public static final long CUSTOMERS_PER_PAGE=10;
	
	@Autowired private CustomerService service;
	
	@GetMapping("/customers")
	public String listFirstPage(Model model) {
		return "redirect:/customer/page/1";
	}
	
	@GetMapping("/customer/page/{pageNum}")
	public String listByPage(Model model, @PathVariable(name="pageNum") int pageNum) {
		List<Customer> customers= service.listByPage(pageNum).getContent();
		Long total=(service.listByPage(pageNum).getTotalElements()/CUSTOMERS_PER_PAGE) +1;
		model.addAttribute("customers",customers);
		model.addAttribute("currentPage",pageNum);
		model.addAttribute("total",total);
		return "customers/customers";
	}
	
	@GetMapping("/customer/detail/{id}")
	public String viewCustomer(@PathVariable("id") Integer id,Model model,RedirectAttributes ra) {
		try {
			Customer customer=service.get(id);
			model.addAttribute("customer", customer);
			return "customers/customer_detail_modal";
		}catch(CustomerNotFoundException ex) {
			ra.addFlashAttribute("message",ex.getMessage());
			return "redirect:/customers";
		}
	}
	
	@GetMapping("/customer/delete/{id}")
	public String deleteCustomer(@PathVariable("id") int id,RedirectAttributes ra) {
		try {
			service.delete(id);
			ra.addFlashAttribute("message","The customer Id "+id +"has been deleted successfull");
		}catch(CustomerNotFoundException ex) {
			ra.addFlashAttribute("message",ex.getMessage());
		}
		return "redirect:/customers";
	}
	
}
