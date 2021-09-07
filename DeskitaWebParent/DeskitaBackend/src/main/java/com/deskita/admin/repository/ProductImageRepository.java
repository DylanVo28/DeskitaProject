package com.deskita.admin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.deskita.common.entity.ProductImage;

@Repository
public interface ProductImageRepository extends CrudRepository<ProductImage, Integer>{

}
