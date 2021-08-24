package com.deskita.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deskita.admin.repository.RoleRepository;
import com.deskita.admin.repository.UserRepository;
import com.deskita.common.entity.Role;
import com.deskita.common.entity.User;

@Service
public class UserService {

	@Autowired
	private UserRepository repo;
	
	@Autowired 
	private RoleRepository roleRepository;
	public List<User> listAll(){
		return (List<User>) repo.findAll();
	}
	
	public List<Role> listRoles(){
		return (List<Role>) roleRepository.findAll();
	}
	
	public void saveUser(User user) {
		repo.save(user);
	}
}
