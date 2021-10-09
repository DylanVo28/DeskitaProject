package com.deskita.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.deskita.common.entity.Customer;
import com.deskita.common.entity.Product;
import com.deskita.common.entity.ProductDetail;
import com.deskita.dto.OrderDTO;
import com.deskita.security.CustomerAuthentication;
import com.deskita.service.CustomerService;
import com.deskita.service.ProductService;

@Controller
public class CartController {

	@Autowired
	public ProductService productservice;
	
	@PostMapping("/add-cart/{productId}")
	public String viewCart(
			
			@PathVariable(name="productId") int productId,
			@RequestParam(name="product_detail_name_selected",required = false) String productDetailName,
			
			Model model) {
		
		Product product=productservice.findProductById(productId);
		ProductDetail productDetail=productservice.getProductDetailByProDuctIdAndName(productId, productDetailName);
		
		model.addAttribute("productDetail",productDetail);
		model.addAttribute("product",product);
		
		return "cart/cart";
	}
	
	

}
