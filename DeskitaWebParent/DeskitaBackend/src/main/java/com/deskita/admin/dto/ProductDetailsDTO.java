package com.deskita.admin.dto;

import java.util.List;

import com.deskita.common.entity.ProductDetail;

public class ProductDetailsDTO {

	private List<ProductDetail> productDetails;
	
	public void addProductDetail(ProductDetail productDetail) {
		this.productDetails.add(productDetail);
	}
}
