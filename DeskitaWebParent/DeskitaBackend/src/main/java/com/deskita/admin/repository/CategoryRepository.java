package com.deskita.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.deskita.common.entity.Category;

@Repository
public interface CategoryRepository extends  PagingAndSortingRepository<Category, Integer> {

	@Query("Select c from Category c where c.enabled=true")
	public List<Category> getListCategoryIsEnabled();
}