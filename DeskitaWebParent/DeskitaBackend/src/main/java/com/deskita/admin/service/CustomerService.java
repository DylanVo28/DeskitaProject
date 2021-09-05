package com.deskita.admin.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.deskita.admin.repository.CustomerRepository;
import com.deskita.common.entity.Customer;
import com.deskita.common.entity.User;
import com.deskita.common.exception.CustomerNotFoundException;

@Service
public class CustomerService {

	public static final int CUSTOMERS_PER_PAGE=10;
	
	@Autowired private CustomerRepository customerRepo;
	
	@Autowired private PasswordEncoder passwordEncoder;
	
	public Page<Customer> listByPage(int pageNum){
		Pageable pageable=PageRequest.of(pageNum-1, CUSTOMERS_PER_PAGE);
		Page<Customer> page=customerRepo.findAll(pageable);
		
		return page;
	}
	
	public void updateCustomerEnabledStatus(Integer id, boolean enabled) {
		customerRepo.updateEnabledStatus(id, enabled);
	}
	
	public Customer get(Integer id) throws CustomerNotFoundException {
		try {
			return customerRepo.findById(id).get();
			
		}catch(NoSuchElementException ex){
			throw new CustomerNotFoundException("Could not find any customers with Id "+id);
		}
	}
	
	public boolean isEmailUnique(Integer id,String email) {
		Customer existCustomer=customerRepo.findByEmail(email);
		if(existCustomer !=null && existCustomer.getId()!=id) {
			return false;
		}
		return true;
	}
	
	public void save(Customer customerInForm) {
		if(!customerInForm.getPassword().isEmpty()) {
			String encodedPassword=passwordEncoder.encode(customerInForm.getPassword());
			customerInForm.setPassword(encodedPassword);
		}else {
			Customer customerInDB=customerRepo.findById(customerInForm.getId()).get();
			customerInForm.setPassword(customerInDB.getPassword());
		}
		customerRepo.save(customerInForm);
	}
	
	public void delete(Integer id) throws CustomerNotFoundException{
		Long count=customerRepo.countById(id);
		if(count==null || count==0) {
			throw new CustomerNotFoundException("Could not find any customers with Id "+id);
		}
		customerRepo.deleteById(id);
	}
	
	
}
