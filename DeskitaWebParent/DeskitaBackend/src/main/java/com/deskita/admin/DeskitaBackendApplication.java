package com.deskita.admin;

import javax.persistence.Entity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.deskita.common.entity","com.deskita.admin.user"})
public class DeskitaBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(DeskitaBackendApplication.class, args);
	}

}
