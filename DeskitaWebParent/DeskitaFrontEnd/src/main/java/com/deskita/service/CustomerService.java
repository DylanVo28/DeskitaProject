package com.deskita.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	
	public Customer getCustomerByEmail(String email) {
		Customer customer= customerRepository.findByEmail(email);
		return customer;
	}
	
	public void updateAccount(Customer customer) {
		Customer findCustomer=customerRepository.findById(customer.getId()).get();
		findCustomer.setAddress(customer.getAddress());
		findCustomer.setEmail(customer.getEmail());
		findCustomer.setFirstName(customer.getFirstName());
		findCustomer.setLastName(customer.getLastName());
		findCustomer.setPhoneNumber(customer.getPhoneNumber());
		customerRepository.save(findCustomer);
	}
	
	public boolean updatePassword(Customer customer,String oldPassword, String newPassword, String confirmPassword) {
		if(!passwordEncoder.matches(oldPassword, customer.getPassword())) {
			return false;
		}
		if(!newPassword.equals(confirmPassword)) {
			return false;
		}
		String passwordEncoded=passwordEncoder.encode(newPassword);
		customer.setPassword(passwordEncoded);
		customerRepository.save(customer);
		return true;
	}
}
