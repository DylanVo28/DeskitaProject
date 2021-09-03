package com.deskita.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.deskita.common.entity.Customer;
import com.deskita.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public void saveCustomer(Customer customer) {
		String passwordEncodered=passwordEncoder.encode(customer.getPassword());
		customer.setEnabled(true);
		customer.setCreatedTime(new Date());
		customer.setPassword(passwordEncodered);
		customerRepository.save(customer);
	}
}
