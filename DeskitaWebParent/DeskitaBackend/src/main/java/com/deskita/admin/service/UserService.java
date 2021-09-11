package com.deskita.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.deskita.admin.repository.BrandRepository;



import com.deskita.admin.repository.ProductDetailRepository;
import com.deskita.admin.repository.ProductImageRepository;
import com.deskita.admin.repository.ProductRepository;
import com.deskita.admin.repository.RoleRepository;
import com.deskita.admin.repository.UserRepository;

import com.deskita.common.entity.Brand;
import com.deskita.common.entity.Customer;
import com.deskita.common.entity.Product;
import com.deskita.common.entity.ProductDetail;
import com.deskita.common.entity.ProductImage;
import com.deskita.common.entity.Role;
import com.deskita.common.entity.User;

@Service
public class UserService {

	public static int PAGE_SIZE=10;
	
	@Autowired
	private UserRepository repo;
	
	@Autowired 
	private RoleRepository roleRepository;
		
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<User> listAll(){
		return (List<User>) repo.findAll();
	}
	
	public List<Role> listRoles(){
		return (List<Role>) roleRepository.findAll();
	}
	
	
	public User getUserByEmail(String email) {
		return repo.getUserByEmail(email);
	}
	
	public void saveUser(User user) {
		String encodedPassword=passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		repo.save(user);
	}
	
	public boolean isEmailUnique(String email) {
		User user=repo.getUserByEmail(email);
		
		return user==null;
	}
	
	public boolean isEmailUniqueAndId(String email,int id) {
		User user=repo.getUserByEmail(email);
		if(user.getId()==id) {
			return true;
		}
		return false;
	}
	
	public User getUserById(int id) {
		return repo.findById(id).get();
	}
	
	public void deleteUser(int id) {
		repo.deleteById(id);
	}
	
	public Page<User> pagingUser(int currentPage){
		Pageable pageable=PageRequest.of(currentPage-1, PAGE_SIZE);
		
		Page<User> page=repo.findAll(pageable);
		
		return page;
	}
	
	public void updateAccount(User userInForm) {
		User userInDB=repo.findById(userInForm.getId()).get();
		userInDB.setFirstName(userInForm.getFirstName());
		userInDB.setLastName(userInForm.getLastName());
		userInDB.setEnabled(userInForm.isEnabled());
		userInDB.setRoles(userInForm.getRoles());
		repo.save(userInDB);
	}
}
