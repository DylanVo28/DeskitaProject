package com.deskita.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.deskita.common.entity.Product;
import com.deskita.common.entity.ProductDetail;
import com.deskita.common.entity.ProductImage;
import com.deskita.repository.ProductDetailRepository;
import com.deskita.repository.ProductImageRepository;
import com.deskita.repository.ProductRepository;

@Service
public class ProductService {
	public static int PAGE_SIZE=8;
	
	@Autowired
	private ProductRepository repo;
	
	@Autowired
	private ProductDetailRepository pdr;
	
	@Autowired
	private ProductImageRepository pir;
	
	public Page<Product> pagingProduct(int currentPage){
		Pageable pageable=PageRequest.of(currentPage-1, PAGE_SIZE);
		Page<Product> page=repo.findAll(pageable);
		return page;
	}	

	public List<Product> listAll(){
		return (List<Product>) repo.findAll();
	}
	
	public Product findProductById(int id) {
		return repo.findById(id).get();
	}
	
	public List<ProductDetail> getAllProductDetails(int id){
		return pdr.getProductDetailsByProductId(id);
	}
	
	public List<ProductImage> getAllProductImages(int id){
		return pir.findImageByProductId(id);
	}
	
	public ProductDetail getProductDetailByProDuctIdAndName(int id, String name) {
		return pdr.getProductDetailByProDuctIdAndName(id, name);
	}
}
