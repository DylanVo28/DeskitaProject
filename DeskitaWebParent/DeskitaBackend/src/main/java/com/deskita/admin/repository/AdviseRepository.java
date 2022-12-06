package com.deskita.admin.repository;

import com.deskita.common.entity.Advise;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdviseRepository extends PagingAndSortingRepository<Advise,Integer> {
}