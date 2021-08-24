package com.deskita.admin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.deskita.common.entity.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {

}
