package com.deskita.admin.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.deskita.common.entity.ProductImage;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Integer>{

	@Transactional
	@Modifying
	@Query("DELETE FROM ProductImage pi "
			+ " WHERE pi.productId=:#{#id}")
	public void deleteImageProductByProductId(int id);
	
	@Query("select pi from ProductImage pi where pi.productId=:#{#id}")
	public List<ProductImage> findImageByProductId(int id);
}
