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
	
	private int ConvertIntoNumeric(String xVal)
	{
	 try
	  { 
	     return Integer.parseInt(xVal);
	  }
	 catch(Exception ex) 
	  {
	     return 0; 
	  }
	}
	
	public Page<Product> pagingProduct(int currentPage,String brandId,String categoryId){
		
		Pageable pageable=PageRequest.of(currentPage-1, PAGE_SIZE);
		Page<Product> page;
		int brand=ConvertIntoNumeric(brandId);
		int category=ConvertIntoNumeric(categoryId);
		
		
		if(brand!=0  && category!=0) {
			System.out.println("hello ae1");
			page=repo.filterBrandAndCategory(Integer.parseInt(categoryId), Integer.parseInt(brandId), pageable);
		}else if(brand!=0) {
			System.out.println("hello ae2");
			page=repo.filterBrand(Integer.parseInt(brandId), pageable);
		}
		else if(category!=0) {
			System.out.println("hello ae3");
			page=repo.filterCategory(Integer.parseInt(categoryId), pageable);
		}
		else {
			System.out.println("hello ae4");
			page=repo.findAll(pageable);
		}
		
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
