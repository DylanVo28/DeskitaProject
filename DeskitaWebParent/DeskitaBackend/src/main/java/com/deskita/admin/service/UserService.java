package com.deskita.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

<<<<<<< HEAD
import com.deskita.admin.repository.BrandRepository;
=======

>>>>>>> 122cdcaf1bb437564a394eb20f1fdc0b881fd1f0
import com.deskita.admin.repository.ProductDetailRepository;
import com.deskita.admin.repository.ProductImageRepository;
import com.deskita.admin.repository.ProductRepository;
import com.deskita.admin.repository.RoleRepository;
import com.deskita.admin.repository.UserRepository;
<<<<<<< HEAD
import com.deskita.common.entity.Brand;
=======

>>>>>>> 122cdcaf1bb437564a394eb20f1fdc0b881fd1f0
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
	
	public List<User> pagingUser(int currentPage){
		
		Pageable pageable=PageRequest.of(currentPage, PAGE_SIZE);
		Page<User> page=repo.findAll(pageable);
		List<User> listUsers=page.getContent();
		return listUsers;
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
