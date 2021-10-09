package com.deskita.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.deskita.common.entity.Product;
import com.deskita.common.entity.ProductDetail;


public class OrderDTO {

	
	ProductDetail productDetail;

	Product product;

	public OrderDTO(ProductDetail productDetail, Product product) {
		super();
		this.productDetail = productDetail;
		this.product = product;
	}

	
	
	public ProductDetail getProductDetail() {
		return productDetail;
	}



	public void setProductDetail(ProductDetail productDetail) {
		this.productDetail = productDetail;
	}



	public Product getProduct() {
		return product;
	}



	public void setProduct(Product product) {
		this.product = product;
	}



	@Override
	public String toString() {
		return "OrderDTO [productDetail=" + productDetail + ", product=" + product + "]";
	}
	
	
}
