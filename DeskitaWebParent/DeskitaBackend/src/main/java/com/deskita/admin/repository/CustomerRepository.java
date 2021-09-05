package com.deskita.admin.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.deskita.common.entity.Customer;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Integer>{

//	@Query("Select c from Customer where concat(c.id,' ',c.firstName,' ', c.lastName,' ',c.created_time,' ',c.phone_number,' ',c.enabled) like %?1%")
//	public Page<Customer> findAll(Pageable pageable);
	
	@Query("Update Customer c set c.enabled = ?2 where c.id= ?1")
	@Modifying
	public void updateEnabledStatus(Integer id, boolean enabled);
	
	@Query("select c from Customer c where c.email= ?1")
	public Customer findByEmail(String email);
	
	public Long countById(Integer id);
	
	
}
