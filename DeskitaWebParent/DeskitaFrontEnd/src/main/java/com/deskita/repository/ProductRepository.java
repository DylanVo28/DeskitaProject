package com.deskita.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.deskita.common.entity.Product;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {
	
}
