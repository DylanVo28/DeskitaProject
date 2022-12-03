package com.deskita.admin.service;

import com.deskita.admin.repository.BillInventoryRepository;
import com.deskita.admin.repository.ProductDetailRepository;
import com.deskita.admin.repository.ReceiptInventoryRepository;
import com.deskita.admin.security.DeskitaUserDetails;
import com.deskita.common.entity.BillInventory;
import com.deskita.common.entity.Product;
import com.deskita.common.entity.ProductDetail;
import com.deskita.common.entity.ReceiptInventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private ReceiptInventoryRepository repository;

    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private BillInventoryRepository billInventoryRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    public List<ReceiptInventory> getReceiptByProductDetail(ProductDetail productDetail){
        return repository.getReceiptInventoryByProductDetail(productDetail);
    }

    public BigDecimal averagePriceByProduct(Product product){
        List<ProductDetail> productDetails=productDetailService.findAll(product.getId());
        BigDecimal sumQuantityReceipt=BigDecimal.ZERO;
        BigDecimal sumValueReceipt=BigDecimal.ZERO;
        List<List<ReceiptInventory>> sumReceiptInventories=new ArrayList<>();
        for(ProductDetail productDetail:productDetails){
            List<ReceiptInventory> receiptInventories=getReceiptByProductDetail(productDetail);
            if(receiptInventories.size()!=0){
                sumReceiptInventories.add(receiptInventories);
            }
        }
        for(List<ReceiptInventory> receiptInventories: sumReceiptInventories){

            sumValueReceipt=sumValueReceipt.add(receiptInventories.stream().reduce(BigDecimal.ZERO,(bd,item)->bd.add(
                    BigDecimal.valueOf(item.getQuantity()).movePointLeft(2).multiply(item.getValue())
            ),BigDecimal::add));
            sumQuantityReceipt=sumQuantityReceipt.add(receiptInventories.stream().reduce(BigDecimal.ZERO,(bd,item)->bd.add(
                    BigDecimal.valueOf(item.getQuantity())
            ),BigDecimal::add));
        }
        if(sumQuantityReceipt==BigDecimal.ZERO){
            return BigDecimal.ZERO;
        }
        BigDecimal sum= sumValueReceipt.divide(sumQuantityReceipt,2, RoundingMode.HALF_UP);
        return sum;
    }

    public void saveReceipt(ReceiptInventory receiptInventory,Integer productDetailId,DeskitaUserDetails loggedUser){
        ProductDetail productDetail= productDetailRepository.findById(productDetailId).get();
        if(productDetail.getStockHouse()==null){
            productDetail.setStockHouse(receiptInventory.getQuantity());
        }
        else{
            productDetail.setStockHouse(productDetail.getStockHouse()+ receiptInventory.getQuantity());
        }
        receiptInventory.setUserName(loggedUser.getFullname());
        receiptInventory.setProductDetail(productDetail);
        repository.save(receiptInventory);

    }

    public void saveBill(BillInventory billInventory, Integer productDetailId, DeskitaUserDetails loggedUser){
        ProductDetail productDetail= productDetailRepository.findById(productDetailId).get();
        if(productDetail.getStockHouse()==null){
            throw new RuntimeException("STOCK_HOUSE_IS_NULL");
        }
        else if (productDetail.getStockHouse()-billInventory.getQuantity()<0){
            throw new RuntimeException("QUANTiTY_LARGE_THAN_STOCK_HOUSE");

        }
        else{
            productDetail.setStockHouse(productDetail.getStockHouse()-billInventory.getQuantity());
            productDetail.setStock(productDetail.getStock()+billInventory.getQuantity());
        }
        billInventory.setUserName(loggedUser.getFullname());
        billInventory.setProductDetail(productDetail);
        billInventoryRepository.save(billInventory);

    }
}
