package com.deskita.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.deskita.common.entity.Product;
import com.deskita.service.ProductService;

@Controller
public class ProductController {
	
	public static int PRODUCT_PER_PAGE=10;
	
	@Autowired
	private ProductService service;
	
	@GetMapping("/products")
	public String pagingProduct(Model model) {
		return "product/product_form";
	}
}
