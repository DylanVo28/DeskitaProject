package com.deskita.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deskita.admin.repository.ProductImageRepository;
import com.deskita.common.entity.ProductImage;

@Service
public class ProductImageService {

	@Autowired
	private ProductImageRepository repo;
	
	public List<ProductImage> findImageByProductId(int id){
		return repo.findImageByProductId(id);
	}
}
