package com.deskita.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.deskita.admin.service.BrandService;
import com.deskita.admin.service.CategoryService;
import com.deskita.common.entity.Brand;
import com.deskita.common.entity.Category;

@Controller
public class BrandController {
	
	@Autowired
	private BrandService service;
	
	@GetMapping("/brands")
	public String listAll(Model model) {
		return "redirect:/brands/page/1";
	}
	
	@GetMapping("/brands/page/{currentPage}")
	public String pagingBrand(@PathVariable(name="currentPage") int currentPage,Model model) {
		List<Brand> allBrands=service.listAll();
		int totalPage = allBrands.size()/10+1;
		List<Brand> listBrands=service.pagingBrand(currentPage);
		model.addAttribute("listBrands",listBrands);
		model.addAttribute("totalPage",totalPage);
		return "brand/brands";
	}
	
	
	@GetMapping("/brands/new")
	public String createBrand(Model model) {
		Brand brand = new Brand();
		model.addAttribute("brand",brand);
		model.addAttribute("actionSave","/DeskitaAdmin/brands/save");
		
		return "brand/brand_form";
	}
	
	@PostMapping("/brands/save")
	public String saveBrand(Brand brand,Model model) {
			model.addAttribute("brand",brand);
			model.addAttribute("actionSave","/DeskitaAdmin/brands/save");
			System.out.println(brand);
			service.saveBrand(brand);
			return "redirect:/brands";
		
	}
		
	@PostMapping("/brands/save/{id}")
	public String saveBrandById(Brand brand,Model model) {
			model.addAttribute("brand",brand);
			model.addAttribute("actionSave","/DeskitaAdmin/brands/save/"+brand.getId());		
			service.saveBrand(brand);
		return "redirect:/brands";
	}
	
	@GetMapping("/brands/edit/{id}")
	public String editBrand(@PathVariable(name="id") Integer id,Model model) {
		System.out.println(id);
		Brand brand=service.getBrandById(id);
		System.out.println(brand.toString());
		model.addAttribute("brand",brand);	
		model.addAttribute("actionSave","/DeskitaAdmin/brands/save/"+brand.getId());
		return "brand/brand_form";
	}
	
	@GetMapping("brands/delete/{id}")
	public String deleteBrand(@PathVariable(name="id") Integer id) {
		service.deleteBrand(id);
		return "redirect:/brands";
	}
}
