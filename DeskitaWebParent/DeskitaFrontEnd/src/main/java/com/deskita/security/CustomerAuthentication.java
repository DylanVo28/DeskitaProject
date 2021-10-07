package com.deskita.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.deskita.common.entity.Customer;
import com.deskita.service.CustomerService;

public class CustomerAuthentication {

	public static Customer getAuthenticatedCustomer(HttpServletRequest request, CustomerService customerService) throws Exception 
			 {
		
		
		
		String email = AccountAuthentication.getEmailOfAuthenticatedCustomer(request);
		
		if (email == null) {
			throw new Exception("No authenticated customer");
		}
		
		
		return customerService.getCustomerByEmail(email);
	}
}
