package com.deskita.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class AccountAuthentication {

	public static String getEmailOfAuthenticatedCustomer(HttpServletRequest request) {
		Object principal = request.getUserPrincipal();
		String customerEmail = null;
		if (principal instanceof UsernamePasswordAuthenticationToken 
				|| principal instanceof RememberMeAuthenticationToken) {
			customerEmail = request.getUserPrincipal().getName();
		} 
		return customerEmail;
	}
}
