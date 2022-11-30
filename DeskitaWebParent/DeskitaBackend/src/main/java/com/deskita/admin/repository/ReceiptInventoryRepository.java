package com.deskita.admin.repository;

import com.deskita.common.entity.Brand;
import com.deskita.common.entity.ReceiptInventory;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ReceiptInventoryRepository  extends PagingAndSortingRepository<ReceiptInventory,Integer> {
}
