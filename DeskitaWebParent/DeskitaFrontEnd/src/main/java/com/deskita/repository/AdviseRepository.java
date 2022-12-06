package com.deskita.repository;

import com.deskita.common.entity.Advise;
import com.deskita.common.entity.Brand;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdviseRepository extends PagingAndSortingRepository<Advise,Integer> {
}