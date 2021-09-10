package com.deskita.admin.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.deskita.common.entity.Product;
import com.deskita.common.entity.User;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product,Integer>{
}
