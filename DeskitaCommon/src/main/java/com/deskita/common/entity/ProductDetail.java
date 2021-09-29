package com.deskita.common.entity;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.text.DecimalFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ProductDetails")
public class ProductDetail {
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="value",length = 150)
	private BigDecimal value;
	
	@Column(name="name",length = 50)
	private String name;
	
	@Column(name="stock",length=50)
	private Integer stock;

	@Column(name="product_id",length = 150)
	private Integer productId;

	public BigDecimal getValue() {
	
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ProductDetail(BigDecimal value, String name, Integer stock, Integer productId) {
		super();
		
		this.value = value;
		this.name = name;
		this.stock = stock;
		this.productId = productId;
	}

	
	
	public ProductDetail(Integer id, BigDecimal value, String name, Integer stock, Integer productId) {
		super();
		this.id = id;
		this.value = value;
		this.name = name;
		this.stock = stock;
		this.productId = productId;
	}

	public ProductDetail() {
		super();
	}
	
	
	
	
}
