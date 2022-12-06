package com.deskita.repository;


import java.util.List;
import java.util.function.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.deskita.common.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	@Query("select p from Product p where p.brand.id=:brandId")
	public Page<Product> filterBrand(int brandId,Pageable pageable);
	
	@Query("select p from Product p where p.category.id=:categoryId")
	public Page<Product> filterCategory(int categoryId,Pageable pageable);
	
	@Query("select p from Product p where p.category.id=:categoryId and p.brand.id=:brandId")
	public Page<Product> filterBrandAndCategory(int categoryId,int brandId,Pageable pageable);

	@Query("select p from Product p where p.name like %:name%")
	public List<Product> findProductsByName(String name);
	
}
