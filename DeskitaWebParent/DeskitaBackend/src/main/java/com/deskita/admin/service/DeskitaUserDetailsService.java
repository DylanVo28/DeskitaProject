package com.deskita.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.deskita.admin.repository.UserRepository;
import com.deskita.admin.security.DeskitaUserDetails;
import com.deskita.common.entity.User;

public class DeskitaUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user=userRepo.getUserByEmail(email);
		if(user!=null) {
			return new DeskitaUserDetails(user);
		}
		throw new UsernameNotFoundException("could not find user with email: "+email);
	}

}
