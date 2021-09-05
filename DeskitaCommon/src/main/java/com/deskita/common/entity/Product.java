package com.deskita.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.aspectj.weaver.ast.Not;

@Entity
@Table(name="Product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="alias",length = 50)
	private String alias;
	
	@Column(name="name",length = 50)
	private String name;
	
	@Column(name="shortdescription",length=150,nullable = false)
	private String shortDescription;
	
	@Column(name="fulldescription",length=300,nullable = false)
	private String fullDescription;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getFullDescription() {
		return fullDescription;
	}

	public void setFullDescription(String fullDescription) {
		this.fullDescription = fullDescription;
	}

	

	public Product(Integer id, String alias, String name, String shortDescription, String fullDescription) {
		this.id = id;
		this.alias = alias;
		this.name = name;
		this.shortDescription = shortDescription;
		this.fullDescription = fullDescription;
		
	}

	public Product() {
		
	}

	@Override
	public String toString() {
		return "products [id=" + id + ", alias=" + alias + ", name=" + name + ", shortDescription=" + shortDescription
				+ ", fullDescription=" + fullDescription + "]";
	}
	
	
}
