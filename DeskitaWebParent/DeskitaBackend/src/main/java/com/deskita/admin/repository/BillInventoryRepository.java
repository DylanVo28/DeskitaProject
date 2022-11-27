package com.deskita.admin.repository;

import com.deskita.common.entity.BillInventory;
import com.deskita.common.entity.ReceiptInventory;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BillInventoryRepository extends PagingAndSortingRepository<BillInventory,Integer> {
}
