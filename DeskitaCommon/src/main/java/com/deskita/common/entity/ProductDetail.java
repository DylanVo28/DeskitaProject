package com.deskita.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ProductDetail")
public class ProductDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="value",length = 150)
	private String value;
	
	@Column(name="name",length = 50)
	private String name;
	
	@Column(name="stock",length=50)
	private Integer stock;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
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

	public ProductDetail(Integer id, String value, String name, Integer stock) {
		super();
		this.id = id;
		this.value = value;
		this.name = name;
		this.stock = stock;
	}

	public ProductDetail() {
	}

	@Override
	public String toString() {
		return "ProductDetail [id=" + id + ", value=" + value + ", name=" + name + ", stock=" + stock + "]";
	}
	
	
}
