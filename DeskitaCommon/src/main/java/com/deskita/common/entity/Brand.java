package com.deskita.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="brand")
public class Brands {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="logo")
	private String logo;
	
	@Column(name="name",length = 128)
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Brands(Integer id, String logo, String name) {
		super();
		this.id = id;
		this.logo = logo;
		this.name = name;
	}

	public Brands() {
		super();
	}

	@Override
	public String toString() {
		return "Brand [id=" + id + ", logo=" + logo + ", name=" + name + "]";
	}
	
	
}
