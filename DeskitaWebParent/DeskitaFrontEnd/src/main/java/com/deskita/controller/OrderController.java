package com.deskita.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.deskita.common.entity.ProductDetail;
import com.deskita.dto.OrderDTO;

@Controller
public class OrderController {

	@PostMapping("/order-now")
	public String viewOrder(
			@RequestParam(name="quantity_product",required = false) Integer quantityProduct,
			@RequestParam(name="fullname",required = false) String fullname,
			@RequestParam(name="address",required=false) String address,
			@RequestParam(name="phone_number",required = false) String phoneNumber,
			@RequestParam(name="payment_method",required = false) String paymentMethod,
			@RequestParam(name="total_price_order",required = false) String totalPriceOrder,
			OrderDTO orderDto
			
			) {
		System.out.println(orderDto.toString());
		return "redirect:/";
	}
}
