package com.deskita.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.deskita.common.entity.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
	
	@Query("SELECT c FROM Customer c WHERE c.email=?1")
	public Customer findByEmail(String email);
	
	@Query("select c from Customer c where c.verificationCode=?1")
	public Customer findByVerificationCode(String verificationCode);
	
	@Query("update Customer c set c.enabled=true where c.id=?1")
	@Modifying
	public void enabled(Integer id);

	
}
