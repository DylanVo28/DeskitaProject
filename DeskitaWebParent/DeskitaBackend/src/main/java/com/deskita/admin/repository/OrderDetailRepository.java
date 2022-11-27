package com.deskita.admin.repository;

import com.deskita.common.entity.BillInventory;
import com.deskita.common.entity.Order;
import com.deskita.common.entity.OrderDetail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface OrderDetailRepository extends PagingAndSortingRepository<BillInventory,Integer> {

    @Query(value="from OrderDetail o where o.productDetailId = :productDetailId")
    public List<OrderDetail> getOrderDetailByProductDetail(@Param("productDetailId") Integer productDetailId);
}
