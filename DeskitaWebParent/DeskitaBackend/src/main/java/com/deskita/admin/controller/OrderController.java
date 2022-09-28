package com.deskita.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.deskita.admin.service.OrderService;
import com.deskita.common.entity.Customer;
import com.deskita.common.entity.Order;


@Controller
public class OrderController {

	@Autowired
	OrderService service;
	
	@GetMapping("/orders")
	public String getAllOrders(Model model) {
		List<Order> orders=service.findAll();
		model.addAttribute("orders",orders);
		return "order/orders";
	}
	
	@GetMapping("/order/{orderId}")
	public String orderDetail(Model model,
			@PathVariable(name = "orderId") Integer id) {
		Order order=service.findById(id);
		
		model.addAttribute("order",order);
	
		return "order/order_detail";
	}
	
	@PostMapping("/update-status")
	public String updateStatus(@RequestParam(name="orderId",required = false) Integer id,
			@RequestParam(name="status",required=false) String status
			) {
		Order order=service.findById(id);
		
		try {
			service.updateStatus(order, status);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/order/"+order.getId();
	}
}
