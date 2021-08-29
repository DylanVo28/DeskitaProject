package com.deskita.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.deskita.admin.service.UserService;
import com.deskita.common.entity.Role;
import com.deskita.common.entity.User;

@Controller
public class UserController {

	@Autowired
	private UserService service;
	
	
	
	@GetMapping("/users")
	public String listAll(Model model) {
		return "redirect:/users/page/1";
//		List<User> listUsers=service.listAll();
//		int totalPage=listUsers.size()/10+1;
//		model.addAttribute("listUsers",listUsers);
//		model.addAttribute("totalPage",totalPage);
//		
//		return "user/users";
	}
	
	@GetMapping("/users/page/{currentPage}")
	public String pagingUser(@PathVariable(name="currentPage") int currentPage,Model model) {
		List<User> allUsers=service.listAll();
		int totalPage=allUsers.size()/10+1;
		List<User> listUsers=service.pagingUser(currentPage);
		model.addAttribute("totalPage",totalPage);
		model.addAttribute("listUsers",listUsers);
		
		return "user/users";
	}
	
	@GetMapping("/users/new")
	public String createUser(Model model) {
		User user=new User();
		List<Role> listRoles= service.listRoles();
		model.addAttribute("user",user);
		model.addAttribute("listRoles",listRoles);
		model.addAttribute("actionSave","/DeskitaAdmin/users/save");
		
		return "user/user_form";
	}
	
	@PostMapping("/users/save")
	public String saveUser(User user,Model model) {
		if(!service.isEmailUnique(user.getEmail())) {
			List<Role> listRoles= service.listRoles();
			String error="Duplicate Email";
			model.addAttribute("error",error);
			model.addAttribute("user",user);
			model.addAttribute("listRoles",listRoles);
			model.addAttribute("actionSave","/DeskitaAdmin/users/save");
			
			return "user/user_form";
		}
		
		service.saveUser(user);
		return "redirect:/users";
	}
	
	@PostMapping("/users/save/{id}")
	public String saveUserById(@PathVariable(name="id") Integer id,User user,Model model) {
		if(!service.isEmailUniqueAndId(user.getEmail(),id)) {
			List<Role> listRoles= service.listRoles();
			String error="Duplicate Email";
			model.addAttribute("error",error);
			model.addAttribute("user",user);
			model.addAttribute("listRoles",listRoles);
			model.addAttribute("actionSave","/DeskitaAdmin/users/save/"+user.getId());
			return "user/user_form";
		}
		
		service.saveUser(user);
		return "redirect:/users";
	}
	
	@GetMapping("/users/edit/{id}")
	public String editUser(@PathVariable(name="id") Integer id,Model model) {
		System.out.println(id);
		User user=service.getUserById(id);
		System.out.println(user.toString());
		List<Role> listRoles= service.listRoles();
		String error="";
		model.addAttribute("error",error);
		model.addAttribute("user",user);
		model.addAttribute("listRoles",listRoles);
		
		model.addAttribute("actionSave","/DeskitaAdmin/users/save/"+user.getId());
		return "user/user_form";
	}
	
	@GetMapping("users/delete/{id}")
	public String deleteUser(@PathVariable(name="id") Integer id) {
		service.deleteUser(id);
		return "redirect:/users";
	}
}
