package com.deskita.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.deskita.admin.repository.BrandRepository;
import com.deskita.common.entity.Brand;

@Service
public class BrandService {
	public static int PAGE_SIZE=10;
	
	@Autowired
	private BrandRepository brandRepository;
	
	public List<Brand> listAll(){
		return (List<Brand>) brandRepository.findAll();
	}
	
	public List<Brand> pagingBrand(int currentPage){
		
		Pageable pageable=PageRequest.of(currentPage, PAGE_SIZE);
		Page<Brand> page=brandRepository.findAll(pageable);
		List<Brand> listBrands=page.getContent();
		return listBrands;	
	}
	
	public Brand getBrandById(int id) {
		return brandRepository.findById(id).get();
	}
	
	public void deleteBrand(int id) {
		brandRepository.deleteById(id);
	}
	
	public void saveBrand(Brand brand) {
		brandRepository.save(brand);
	}
}