package com.deskita.admin.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.deskita.admin.repository.ProductDetailRepository;
import com.deskita.admin.repository.ProductImageRepository;
import com.deskita.admin.repository.ProductRepository;


import com.deskita.admin.repository.BrandRepository;

import com.deskita.admin.repository.ProductDetailRepository;
import com.deskita.admin.repository.ProductImageRepository;
import com.deskita.admin.repository.ProductRepository;
import com.deskita.common.entity.Brand;
import com.deskita.common.entity.Category;
import com.deskita.common.entity.Customer;
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
	
	public List<ProductDetail> listProductDetails(int productId){
		return (List<ProductDetail>) productDetailRepository.getProductDetailsByProductId(productId);
	}
	


	public List<Brand> listBrands(){
		return (List<Brand>) brandsRepository.findAll();
	}

		
	public Page<Product> pagingProduct(int currentPage){
		Pageable pageable=PageRequest.of(currentPage-1, PAGE_SIZE);
		Page<Product> page=productRepository.findAll(pageable);
		return page;
	}	

	public Product getProductById(int id) {
		return productRepository.findById(id).get();
	}
	
	public void deleteProduct(int id) {
		productRepository.deleteById(id);
		productImageRepository.deleteImageProductByProductId(id);
		productDetailRepository.deleteProductDetailByProductId(id);
	}
	
	public void saveProduct(Product product,String[] detailName,String[] detailValue,
			String[] detailStock,List<String> fileNameImage) {
		
		product.setImage(fileNameImage.get(0));
		Product savedProduct=productRepository.save(product);
		List<ProductImage> listImage=new ArrayList<ProductImage>();
		List<ProductDetail> list=new ArrayList<ProductDetail>();
		for(int i=0;i<detailName.length;i++) {
			ProductDetail productDetail=new ProductDetail(
					new BigDecimal(detailValue[i]),
					detailName[i],
					Integer.parseInt(detailStock[i]),
					savedProduct.getId());
			list.add(productDetail);
		}
		for(String image:fileNameImage) {
			ProductImage productImage=new ProductImage(image, savedProduct.getId());
			listImage.add(productImage);
		}
		
		productImageRepository.saveAll(listImage);
		
		productDetailRepository.saveAll(list);
	}
	
}
