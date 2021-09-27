package com.deskita.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.deskita.common.entity.ProductDetail;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail,Integer>{

	@Query("SELECT c FROM  ProductDetail c "
			+ "WHERE c.productId =?1")
	public List<ProductDetail> getProductDetailsByProductId(int productId);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM ProductDetail pd "
			+ " WHERE pd.productId=:#{#id}")
	public void deleteProductDetailByProductId(int id);
	
	
}
