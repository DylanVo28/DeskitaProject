package com.deskita.admin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.deskita.common.entity.ProductDetail;

@Repository
public interface ProductDetailRepository extends CrudRepository<ProductDetail,Integer>{

}
