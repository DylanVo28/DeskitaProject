package com.deskita.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.deskita.common.entity.Product;
import com.deskita.repository.ProductRepository;

@Service
public class ProductService {
	public static int PAGE_SIZE=10;
	@Autowired
	private ProductRepository repo;
	
	public Page<Product> pagingProduct(int currentPage){
		Pageable pageable=PageRequest.of(currentPage-1, PAGE_SIZE);
		Page<Product> page=repo.findAll(pageable);
		return page;
	}	

}
