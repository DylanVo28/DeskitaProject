package com.deskita.admin.repository;

import com.deskita.common.entity.Brand;
import com.deskita.common.entity.Order;
import com.deskita.common.entity.ProductDetail;
import com.deskita.common.entity.ReceiptInventory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface ReceiptInventoryRepository  extends PagingAndSortingRepository<ReceiptInventory,Integer> {

    @Query(value="from ReceiptInventory o where o.productDetail = :productDetail ")
    public List<ReceiptInventory> getReceiptInventoryByProductDetail(@Param("productDetail") ProductDetail productDetail);

}
