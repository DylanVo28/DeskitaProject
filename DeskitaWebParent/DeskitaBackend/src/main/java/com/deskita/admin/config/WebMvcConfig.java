package com.deskita.admin.config;


import java.util.Locale;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

	@Bean(name = "localeResolver")
	public LocaleResolver getLocaleResolver()  {
		SessionLocaleResolver resolver= new SessionLocaleResolver();
		resolver.setDefaultLocale(Locale.US);
		return resolver;		
	} 
	
	@Bean(name = "messageSource")
	public MessageSource getMessageResource()  {
		ResourceBundleMessageSource messageResource= new ResourceBundleMessageSource();
		
		// Read i18n/messages_xxx.properties file.
		// For example: i18n/messages_en.properties
		messageResource.setBasename("i18n/messages");
		messageResource.setDefaultEncoding("UTF-8");
		return messageResource;
	}
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
		localeInterceptor.setParamName("lang");
		
		
		registry.addInterceptor(localeInterceptor);
	}
	
}
