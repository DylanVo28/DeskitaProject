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
	
	public boolean findImage(int id,String[] imageIds) {
		for(String idImage:imageIds) {
			if(id==Integer.parseInt(idImage) ) {
				return true;
			}
		}
		return false;
	}
	
	
	
	public void saveProduct(Product product,String[] detailName,String[] detailValue,
			String[] detailStock,List<String> fileNameImage,String[] imageIDs,String[] detailIds) {
		
		product.setImage(fileNameImage.get(0));
		Product savedProduct=productRepository.save(product);
		List<ProductImage> listImage=new ArrayList<ProductImage>();
		List<ProductDetail> list=new ArrayList<ProductDetail>();
		
		//update product detail
		if(detailIds==null && product.getId()!=null ) {
			productDetailRepository.deleteProductDetailByProductId(product.getId());
		}
		if((detailIds!=null && product.getId()!=null)|| (detailIds==null && product.getId()!=null && detailName!=null)) {
			List<ProductDetail> listPDExisted= productDetailRepository.getProductDetailsByProductId(product.getId());
			int idxPD=0;
			for(ProductDetail pd:listPDExisted) {
				if(!findImage(pd.getId(),detailIds)) {
					productDetailRepository.deleteById(pd.getId());
				}
				
			}
			for(String name:detailName) {
				if(detailIds!=null && idxPD<detailIds.length) {
					ProductDetail productDetail=new ProductDetail(Integer.parseInt(detailIds[idxPD]) ,
							new BigDecimal(detailValue[idxPD]),
							detailName[idxPD],
							Integer.parseInt(detailStock[idxPD]),
							savedProduct.getId());
					list.add(productDetail);
				}
				else {
					ProductDetail productDetail=new ProductDetail(
							new BigDecimal(detailValue[idxPD]),
							detailName[idxPD],
							Integer.parseInt(detailStock[idxPD]),
							savedProduct.getId());
					list.add(productDetail);
				}
					
				idxPD++;
			}
		}
		if(product.getId()==null) {
			int i=0;
			for(String name:detailName) {
				ProductDetail productDetail=new ProductDetail(
						new BigDecimal(detailValue[i]),
						detailName[i],
						Integer.parseInt(detailStock[i]),
						savedProduct.getId());
				list.add(productDetail);
				i++;
			}
		}
		//end update product detail
		
		//update image
		int idxImage=0;
		if(product.getId()!=null) {
			
			if(imageIDs==null) {
				productImageRepository.deleteImageProductByProductId(product.getId());
			}
			else {
				List<ProductImage> listImageExisted=productImageRepository.findImageByProductId(savedProduct.getId());
				for(ProductImage pi: listImageExisted) {
					if(!findImage(pi.getId(),imageIDs)) {
						productImageRepository.deleteById(pi.getId());
					}
				}
			}
			
		}
		for(String image:fileNameImage) {
			if(imageIDs==null) {
				ProductImage productImage=new ProductImage(image, savedProduct.getId());
				listImage.add(productImage);
			}
			else {
				if(imageIDs!=null && idxImage<imageIDs.length) {
					ProductImage productImage=new ProductImage(Integer.parseInt(imageIDs[idxImage]),image,savedProduct.getId());
					listImage.add(productImage);
				}
				if(idxImage>=imageIDs.length) {
					ProductImage productImage=new ProductImage(image, savedProduct.getId());
					listImage.add(productImage);
				}
			}
			
			
			idxImage++;
		}
		//end update image
		
		productImageRepository.saveAll(listImage);
		
		productDetailRepository.saveAll(list);
	}
	
}
