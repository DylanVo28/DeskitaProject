package com.deskita.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.deskita.common.entity.Customer;
import com.deskita.repository.CustomerRepository;
import com.deskita.security.CustomerUserDetails;


public class CustomerUserDetailsService implements UserDetailsService{

	@Autowired
	private CustomerRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Customer customer=repo.findByEmail(email);
		if(customer ==null) {
			throw new UsernameNotFoundException("No customer found with the email "+email);
		}
		return new CustomerUserDetails(customer);
	}

	
}
