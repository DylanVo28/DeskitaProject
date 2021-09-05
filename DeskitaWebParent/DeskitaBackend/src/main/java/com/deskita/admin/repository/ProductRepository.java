package com.deskita.admin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.deskita.common.entity.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product,Integer>{

}
