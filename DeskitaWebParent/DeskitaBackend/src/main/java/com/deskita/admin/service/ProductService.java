package com.deskita.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.deskita.admin.repository.BrandRepository;

import com.deskita.admin.repository.ProductDetailRepository;
import com.deskita.admin.repository.ProductImageRepository;
import com.deskita.admin.repository.ProductRepository;
import com.deskita.common.entity.Brand;
import com.deskita.common.entity.Product;
import com.deskita.common.entity.ProductDetail;
import com.deskita.common.entity.ProductImage;

@Service
public class ProductService {
	public static int PAGE_SIZE=10;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductDetailRepository productDetailRepository;
	
	@Autowired
	private BrandRepository brandsRepository;
	
	@Autowired
	private ProductImageRepository productImageRepository;
	
	public List<Product> listAll(){
		return (List<Product>) productRepository.findAll();
	}
		
	public List<ProductImage> listProductImages(){
		return (List<ProductImage>) productImageRepository.findAll();
	}
	
	public List<ProductDetail> listProductDetails(){
		return (List<ProductDetail>) productDetailRepository.findAll();
	}
	
	public List<Brand> listBrands(){
		return (List<Brand>) brandsRepository.findAll();
	}
		
	public List<Product> pagingProduct(int currentPage){
		
		Pageable pageable=PageRequest.of(currentPage, PAGE_SIZE);
		Page<Product> page=productRepository.findAll(pageable);
		List<Product> listProducts=page.getContent();
		return listProducts;
	}
	
	
	public Product getProductById(int id) {
		return productRepository.findById(id).get();
	}
	
	public void deleteProduct(int id) {
		productRepository.deleteById(id);
	}
	
	public void saveProduct(Product product) {
		productRepository.save(product);
	}
	
}
