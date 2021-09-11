package com.deskita.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.deskita.admin.service.CategoryService;
import com.deskita.common.entity.Category;
import com.deskita.common.entity.Product;

@Controller
public class CategoryController {
	
	@Autowired
	private CategoryService service;
	
	@GetMapping("/categories")
	public String listAll(Model model) {
		return "redirect:/categories/page/1";
	}
	
	@GetMapping("/categories/page/{currentPage}")
	public String pagingCategory(@PathVariable(name="currentPage") int currentPage,Model model) {
		List<Category> allCategories=service.listAll();
		int totalPage = allCategories.size()/10+1;
		List<Category> listCategories=service.pagingCategory(currentPage);
		model.addAttribute("listCategories",listCategories);
		model.addAttribute("totalPage",totalPage);
		return "category/categories";
	}
	
	
	@GetMapping("/categories/new")
	public String createCategory(Model model) {
		Category category=new Category();
		model.addAttribute("category",category);
		model.addAttribute("actionSave","/DeskitaAdmin/categories/save");
		
		return "category/category_form";
	}
	
	@PostMapping("/categories/save")
	public String saveCategory(Category category,Model model) {
			model.addAttribute("category",category);
			model.addAttribute("actionSave","/DeskitaAdmin/categories/save");
			service.saveCategory(category);
			return "redirect:/categories";
		
	}
		
	@PostMapping("/categories/save/{id}")
	public String saveCategoryById(Category category,Model model) {
			model.addAttribute("category",category);
			model.addAttribute("actionSave","/DeskitaAdmin/categories/save/"+category.getId());		
			service.saveCategory(category);
		return "redirect:/categories";
	}
	
	@GetMapping("/categories/edit/{id}")
	public String editCategory(@PathVariable(name="id") Integer id,Model model) {
		System.out.println(id);
		Category category=service.getCategoryById(id);
		System.out.println(category.toString());
		model.addAttribute("category",category);	
		model.addAttribute("actionSave","/DeskitaAdmin/categories/save/"+category.getId());
		return "category/category_form";
	}
	
	@GetMapping("categories/delete/{id}")
	public String deleteCategory(@PathVariable(name="id") Integer id) {
		service.deleteCategory(id);
		return "redirect:/categories";
	}
}
