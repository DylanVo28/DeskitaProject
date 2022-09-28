package com.deskita.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ProductImages")
public class ProductImage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="url")
	private String url;

	@Column(name="product_id",length = 150)
	private Integer productId;

	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public ProductImage(String url, Integer productId) {
		super();
		this.url = url;
		this.productId = productId;
	}
	
	public ProductImage(Integer id, String url, Integer productId) {
		super();
		this.id = id;
		this.url = url;
		this.productId = productId;
	}

	public ProductImage() {
		super();
	}

		
	
	
	
	
}
