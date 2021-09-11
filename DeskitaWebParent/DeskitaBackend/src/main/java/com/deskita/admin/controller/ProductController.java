package com.deskita.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.deskita.admin.service.ProductService;
import com.deskita.common.entity.Brand;
import com.deskita.common.entity.Category;
import com.deskita.common.entity.Product;
import com.deskita.common.entity.ProductDetail;

@Controller
public class ProductController {
	
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
		System.out.println(allProducts);
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
	
	@PostMapping("/products/save")
	public String saveProduct(Product product,Model model) {
			List<ProductDetail> listProductDetails= service.listProductDetails();
			Brand brand=new Brand(1,"https://www.apple.com/ac/structured-data/images/knowledge_graph_logo.png?202101262201","apple");
			product.setBrand(brand);
			Category category = new Category(1, true, "dien thoai");
			product.setCategory(category);
			model.addAttribute("product",product);
			model.addAttribute("listProductDetails",listProductDetails);
			model.addAttribute("actionSave","/DeskitaAdmin/products/save");
			service.saveProduct(product);
			return "redirect:/products";
		
	}
		
	@PostMapping("/products/save/{id}")
	public String saveProductById(Product product,Model model) {
			List<ProductDetail> listProductDetails= service.listProductDetails();
	//		Brand brand=new Brand(1,"https://www.apple.com/ac/structured-data/images/knowledge_graph_logo.png?202101262201","apple");
			
	//		product.setBrand(brand);
			model.addAttribute("product",product);
			model.addAttribute("listProductDetails",listProductDetails);
			model.addAttribute("actionSave","/DeskitaAdmin/products/save/"+product.getId());		
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
