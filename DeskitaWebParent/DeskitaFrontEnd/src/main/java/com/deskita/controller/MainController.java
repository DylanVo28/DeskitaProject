package com.deskita.controller;
import java.util.List;
import java.util.Locale;

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
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.deskita.common.entity.Product;
import com.deskita.config.WebMvcConfig;
import com.deskita.service.ProductService;
@Controller
public class MainController {

	@Autowired
	ProductService ps;
	
	private int SIZE_PRODUCT_PAGE=8;
	
	@Autowired
	HttpServletRequest request; 
	
	@Autowired
	HttpServletResponse response; 
	
	@GetMapping("")
	public String viewHomePage(Model model,Locale locale) {
		
		List<Product> products=ps.pagingProduct(1).getContent();
		long totalPage= (ps.pagingProduct(1).getTotalElements()/SIZE_PRODUCT_PAGE)+1;
		model.addAttribute("products",products);
		model.addAttribute("totalPage",totalPage);
		model.addAttribute("language",locale.getLanguage());
	
		return "index";
	}
	
	@GetMapping("/products/page/{currentPage}")
	public String pagingProduct(@PathVariable(name="currentPage") int currentPage,Model model) {
		try {
			List<Product> products=ps.pagingProduct(currentPage).getContent();
			Long totalPage=(ps.pagingProduct(currentPage).getTotalElements()/SIZE_PRODUCT_PAGE) +1;
			model.addAttribute("products",products);
			model.addAttribute("totalPage",totalPage);
			return "index";
		}catch(Exception e ) {
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
