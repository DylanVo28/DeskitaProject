package com.deskita.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.deskita.common.entity.Brand;

@Repository
public interface BrandRepository extends  PagingAndSortingRepository<Brand,Integer>{
}
