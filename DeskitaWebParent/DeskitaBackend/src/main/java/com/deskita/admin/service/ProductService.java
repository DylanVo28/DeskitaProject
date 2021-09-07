package com.deskita.admin.service;

import java.util.List;

import org.dom4j.Branch;
import org.springframework.beans.factory.annotation.Autowired;

import com.deskita.admin.repository.BrandsRepository;
import com.deskita.admin.repository.ProductDetailRepository;
import com.deskita.admin.repository.ProductImageRepository;
import com.deskita.admin.repository.ProductRepository;
import com.deskita.common.entity.Brands;
import com.deskita.common.entity.Product;
import com.deskita.common.entity.ProductDetail;
import com.deskita.common.entity.ProductImage;

public class ProductService {
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductDetailRepository productDetailRepository;
	
	@Autowired
	private BrandsRepository brandsRepository;
	
	@Autowired
	private ProductImageRepository productImageRepository;
	
	public List<Product> listProducts(){
		return (List<Product>) productRepository.findAll();
	}
		
	public List<ProductImage> listProductImages(){
		return (List<ProductImage>) productImageRepository.findAll();
	}
	
	public List<ProductDetail> listProductDetails(){
		return (List<ProductDetail>) productDetailRepository.findAll();
	}
	
	public List<Brands> listBrands(){
		return (List<Brands>) brandsRepository.findAll();
	}
}
