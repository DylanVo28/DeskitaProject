package com.deskita.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.deskita.admin.service.ProductService;
import com.deskita.common.entity.Customer;
import com.deskita.common.entity.Product;
import com.deskita.common.entity.ProductDetail;
import com.deskita.common.entity.Role;
import com.deskita.common.entity.User;
import com.deskita.common.exception.CustomerNotFoundException;

@Controller
public class ProductController {
	public static final long PAGE_SIZE=10;
	
	@Autowired
	private ProductService service;
	
	@GetMapping("/products")
	public String listAll(Model model) {
		System.out.println("hello ae");
		return "redirect:/products/page/1";
	}
	
	@GetMapping("/products/page/{currentPage}")
	public String pagingProduct(@PathVariable(name="currentPage") int currentPage,Model model) {
		List<Product> allProducts=service.listAll();
		int totalPage=allProducts.size()/10+1;
		List<Product> listProducts=service.pagingProduct(currentPage);
		model.addAttribute("listProducts",listProducts);
		model.addAttribute("totalPage",totalPage);
		return "product/products";
	}
	
	
	@GetMapping("/products/new")
	public String createProduct(Model model) {
		Product product=new Product();
		List<ProductDetail> listProductDetails= service.listProductDetails();
		model.addAttribute("product",product);
		model.addAttribute("listProductDetails",listProductDetails);
		model.addAttribute("actionSave","/DeskitaAdmin/products/save");
		
		return "product/product_form";
	}
		
	@PostMapping("/products/save/{id}")
	public String saveProductById(Product product,Model model) {
			List<ProductDetail> listProductDetails= service.listProductDetails();
			model.addAttribute("product",product);
			model.addAttribute("listProductDetails",listProductDetails);
			model.addAttribute("actionSave","/DeskitaAdmin/products/save/"+product.getId() == null);		
			service.saveProduct(product);
		return "redirect:/products";
	}
	
	@GetMapping("/products/edit/{id}")
	public String editProduct(@PathVariable(name="id") Integer id,Model model) {
		System.out.println(id);
		Product product=service.getProductById(id);
		System.out.println(product.toString());
		List<ProductDetail> listProductDetails= service.listProductDetails();
		model.addAttribute("product",product);
		model.addAttribute("listProductDetails",listProductDetails);
		
		model.addAttribute("actionSave","/DeskitaAdmin/users/save/"+product.getId());
		return "product/product_form";
	}
	
	@GetMapping("products/delete/{id}")
	public String deleteProduct(@PathVariable(name="id") Integer id) {
		service.deleteProduct(id);
		return "redirect:/products";
	}
}
