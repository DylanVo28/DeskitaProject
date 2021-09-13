package com.deskita.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.deskita.common.entity.Customer;
import com.deskita.common.exception.CustomerNotFoundException;
import com.deskita.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JavaMailSender mailSender;

	public void updateResetPasswordToken(String token, String email) throws CustomerNotFoundException{
		Customer customer=customerRepository.findByEmail(email);
		if(customer!=null) {
			customer.setVerificationCode(token);
			customerRepository.save(customer);
		}
		else {
			throw new CustomerNotFoundException("Could not find any customer with the email "+email);
		}
	}
	
	public Customer getByResetPasswordToken(String token) {
		return customerRepository.findByVerificationCode(token);
	}
	
	public void sendEmail(String recipientEmail, String link)throws MessagingException,UnsupportedEncodingException{
		MimeMessage message=mailSender.createMimeMessage();
		MimeMessageHelper helper=new MimeMessageHelper(message);
		helper.setFrom("contact@deskita.com","Deskita Support");
		helper.setTo(recipientEmail);
		String subject ="Here's the link to reset your password";
		String content="<p>Hello,</p>"
	            + "<p>You have requested to reset your password.</p>"
	            + "<p>Click the link below to change your password:</p>"
	            + "<p><a href=\"" + link + "\">Change my password</a></p>"
	            + "<br>"
	            + "<p>Ignore this email if you do remember your password, "
	            + "or you have not made the request.</p>";
		helper.setSubject(subject);
		helper.setText(content,true);
		mailSender.send(message);
	}
	
	public void updatePasswordToken(Customer customer,String newPassword) {
		String encodedPassword = passwordEncoder.encode(newPassword);
		customer.setPassword(encodedPassword);
		customer.setVerificationCode(null);
		customerRepository.save(customer);
	}
	
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
