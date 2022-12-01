package com.deskita.admin.repository;


import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deskita.common.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	
	@Query(value="from Order o where o.orderTime between :startDate and :endDate")
	public List<Order> exportOrder(@Param("startDate")Date startDate,@Param("endDate")Date endDate);

	
}
