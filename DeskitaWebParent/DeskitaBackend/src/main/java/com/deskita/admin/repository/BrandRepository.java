package com.deskita.admin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.deskita.common.entity.Brands;

@Repository
public interface BrandsRepository extends CrudRepository<Brands,Integer>{
	
}
