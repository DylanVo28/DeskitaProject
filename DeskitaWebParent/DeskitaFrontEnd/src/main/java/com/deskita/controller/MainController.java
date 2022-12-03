package com.deskita.controller;
import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.deskita.common.entity.Brand;
import com.deskita.common.entity.Category;
import com.deskita.common.entity.Product;
import com.deskita.common.entity.ProductDetail;
import com.deskita.config.WebMvcConfig;
import com.deskita.service.BrandService;
import com.deskita.service.CategoryService;
import com.deskita.service.ProductService;
@Controller
public class MainController {

	@Autowired
	ProductService ps;
	
	@Autowired
	BrandService brandService;
	
	@Autowired
	CategoryService categoryService;
	
	
	private int SIZE_PRODUCT_PAGE=8;
	
	@Autowired
	HttpServletRequest request; 
	
	@Autowired
	HttpServletResponse response; 
	
	@GetMapping("")
	public String viewHomePage(Model model,Locale locale) {
		
		
		return "redirect:/products/1/page";
	}
	
	@GetMapping("/products/{currentPage}/page")
	public String pagingProduct(@PathVariable(name="currentPage") int currentPage,
			@RequestParam(value="brand",required = false) String brandId,
			@RequestParam(value="category",required=false) String categoryId,
			Model model) {
		try {
			
			
			List<Brand> brands=brandService.listAll();
			List<Category> categories= categoryService.listAll();
			List<Product> products=ps.pagingProduct(currentPage,brandId,categoryId).getContent();
			List <BigDecimal> listPrice=new ArrayList<>();
			List <Category> listCategory=new ArrayList<>();


			for (Product product : products) {
				List<ProductDetail> productDetails=ps.getAllProductDetails(product.getId());
				if(productDetails.size()>0)
				{
//					prices.add(productDetails.get(0).getValue().toString());
					System.out.println("category:"+product.getCategory().getName());
					System.out.println("price:"+productDetails.get(0));
					listPrice.add(productDetails.get(0).getValue());
					listCategory.add(product.getCategory() );
				}
			}
			Long totalPage=(ps.pagingProduct(currentPage,brandId,categoryId).getTotalElements()/SIZE_PRODUCT_PAGE) +1;
			model.addAttribute("categories",categories);
			model.addAttribute("brands",brands);
			model.addAttribute("products",products);
			model.addAttribute("totalPage",totalPage);
			model.addAttribute("prices",listPrice);


			System.out.println(categoryId+"|"+brandId);
			if(brandId != null) {
				model.addAttribute("brandId",brandId);
			}
			if(categoryId!=null) {
				model.addAttribute("categoryId",categoryId);
			}
			return "index";
		}catch(NumberFormatException  e ) {
			System.out.println(e.getMessage());
			return "error/404_NotFound";
		}
		
	}
	
	
	@GetMapping("/login")
	public String viewLoginPage() {
		
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		if(authentication==null || authentication instanceof AnonymousAuthenticationToken) {
			return "authen/login";
		}
		
		return "redirect:/";
	}
	
}
