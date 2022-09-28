package com.deskita.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.deskita.common.entity.Product;
import com.deskita.common.entity.ProductDetail;
import com.deskita.common.entity.ProductImage;
import com.deskita.service.ProductService;

@Controller
public class ProductController {
	
	public static int PRODUCT_PER_PAGE=10;
	
	@Autowired
	private ProductService service;
	
	
	
	
	
	@GetMapping("/product/{id}")
	public String viewProductDetail(@PathVariable(name="id") Integer id,Model model,Locale locale) {
		try {
			Product product=service.findProductById(id);
			List<ProductDetail> productDetails=service.getAllProductDetails(id);
			List<ProductImage> productImages=service.getAllProductImages(id);
			model.addAttribute("productDetails",productDetails);
			model.addAttribute("productImages",productImages);
			model.addAttribute("product",product);
			return "product/product_detail";
		}catch(Exception ex) {
			model.addAttribute("title_error","Không tìm thấy sản phẩm cần tìm");
			return "error/404_NotFound";
		}
		
		
	}
	
	
}
