package com.deskita.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.deskita.admin.dto.ProductDetailsDTO;
import com.deskita.admin.service.ProductService;
import com.deskita.common.entity.Brand;
import com.deskita.common.entity.Category;
import com.deskita.common.entity.Product;
import com.deskita.common.entity.ProductDetail;

@Controller
public class ProductController {
	
	public static int PRODUCT_PER_PAGE=10;
	
	@Autowired
	private ProductService service;
	
	@GetMapping("/products")
	public String listAll(Model model) {
		
		return "redirect:/products/page/1";
	}
	
	@GetMapping("/products/page/{currentPage}")
	public String pagingProduct(@PathVariable(name="currentPage") int currentPage,Model model) {
		List<Product> listProducts=service.pagingProduct(currentPage).getContent();
		Long total=(service.pagingProduct(currentPage).getTotalElements()/PRODUCT_PER_PAGE) +1;
		System.out.println(listProducts);
		
		model.addAttribute("listProducts",listProducts);
		model.addAttribute("totalPage",total);
		return "product/products";
	}
	
	
	@GetMapping("/products/new")
	public String createProduct(Model model) {
		Product product=new Product();
		ProductDetailsDTO productDetails=new ProductDetailsDTO();
		
		model.addAttribute("product",product);
		
		model.addAttribute("listProductDetails",productDetails);
		model.addAttribute("actionSave","/DeskitaAdmin/products/save");
		
		return "product/product_form";
	}
	
	@PostMapping("/products/save")
	public String saveProduct(Product product,Model model,HttpServletRequest request,
			@RequestParam(name="nameDetail",required=false) String[] nameDetail,
			@RequestParam(name="valueDetail",required=false) String[] valueDetail,
			@RequestParam(name="stockDetail",required=false) String[] stockDetail,
			@RequestParam(name="fileImage",required=false) List<MultipartFile> images
			) {
		
		List<String> listImage=new ArrayList<>();
		for(MultipartFile image:images) {
			listImage.add(StringUtils.cleanPath(image.getOriginalFilename()));
		}
				
			service.saveProduct(product, nameDetail,valueDetail, stockDetail,listImage);
			return "redirect:/products";
		
	}
		
	@PostMapping("/products/save/{id}")
	public String saveProductById(Product product,Model model) {
			List<ProductDetail> listProductDetails= service.listProductDetails();
			model.addAttribute("product",product);
			model.addAttribute("listProductDetails",listProductDetails);
			model.addAttribute("actionSave","/DeskitaAdmin/products/save/"+product.getId());		
			service.saveProduct(product);
		return "redirect:/products";
	}
	
	@GetMapping("/products/edit/{id}")
	public String editProduct(@PathVariable(name="id") Integer id,Model model) {
		
		Product product=service.getProductById(id);
		
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
