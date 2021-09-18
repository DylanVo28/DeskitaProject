package com.deskita.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="name",length = 50)
	private String name;
	
	@Column(name="image")
	private String image;
	
	@Column(name="shortdescription",length=150)
	private String shortDescription;
	
	@Column(name="fulldescription",length=300)
	private String fullDescription;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinTable(
			name="products_brands",
			joinColumns = @JoinColumn(name="product_id"),
			inverseJoinColumns = @JoinColumn(name="brand_id")
			)
	private Brand brand=new Brand();
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinTable(
			name="products_categories",
			joinColumns = @JoinColumn(name="product_id"),
			inverseJoinColumns = @JoinColumn(name="category_id")
			)
	private Category category =new Category();	
	
	
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Product(Integer id, String name, String shortDescription, String fullDescription,String image) {
		this.id = id;
		this.name = name;
		this.shortDescription = shortDescription;
		this.fullDescription = fullDescription;
		this.image=image;
	}

	public Product() {
		
	}

	@Override
	public String toString() {
		return "products [id=" + id + ", name=" + name + ", shortDescription=" + shortDescription
				+ ", fullDescription=" + fullDescription + "]";
	}
	
	
}
