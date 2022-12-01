package com.deskita.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deskita.admin.repository.ProductDetailRepository;
import com.deskita.common.entity.ProductDetail;

@Service
public class ProductDetailService {

	@Autowired
	private ProductDetailRepository productDetailRepository;
	
	public List<ProductDetail> findAll(int id){
		return productDetailRepository.getProductDetailsByProductId(id);
	}

	public ProductDetail findByID(int id){
		return productDetailRepository.findById(id).orElse(null);
	}
}
