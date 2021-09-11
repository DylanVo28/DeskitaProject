package com.deskita.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.deskita.service.CustomerUserDetailsService;



@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider=new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	
	@Override
	protected void configure(HttpSecurity http) throws Exception{

		http.authorizeRequests().antMatchers("/customer").authenticated()
			.anyRequest().permitAll().and().formLogin().loginPage("/login")
			.usernameParameter("email").permitAll().and().logout().permitAll().and().rememberMe()
			.key("1234567890_aBcDeFgHiJkLmNoPqRsTuVwXyZ")
			.tokenValiditySeconds(14*24*60*60);
//		 http.authorizeRequests().anyRequest().permitAll();
		
		//		http.authorizeRequests()
//        .anyRequest()
//        .authenticated()
//        .and()
//        .formLogin()
//        .loginPage("/login")
//        .permitAll()
//        .and()
//        .logout()
//        .permitAll();

			
			
		
	}
	
	@Override
	public void configure(WebSecurity web)throws Exception{
		web.ignoring().antMatchers("/css/**","/webjars/**","/js/**");
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomerUserDetailsService();
	}
}
