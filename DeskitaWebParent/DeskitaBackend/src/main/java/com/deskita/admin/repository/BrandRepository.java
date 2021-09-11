package com.deskita.admin.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.deskita.common.entity.Brand;

@Repository
<<<<<<< HEAD
public interface BrandRepository extends  PagingAndSortingRepository<Brand,Integer>{
=======
public interface BrandRepository extends CrudRepository<Brand,Integer>{
>>>>>>> 122cdcaf1bb437564a394eb20f1fdc0b881fd1f0
	
}
