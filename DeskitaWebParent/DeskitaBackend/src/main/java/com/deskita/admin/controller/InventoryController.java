package com.deskita.admin.controller;

import com.deskita.admin.security.DeskitaUserDetails;
import com.deskita.admin.service.OrderService;
import com.deskita.admin.service.ProductDetailService;
import com.deskita.admin.service.ProductService;
import com.deskita.admin.service.InventoryService;
import com.deskita.common.entity.*;
import com.deskita.common.entity.type.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class InventoryController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private InventoryService service;

    @Autowired
    private OrderService orderService;

    public static int PRODUCT_PER_PAGE = 10;

    @GetMapping("/inventories")
    public String listAll(Model model) {

        return "redirect:/inventories/page/1";
    }

    @GetMapping("/inventories/page/{currentPage}")
    public String pagingProduct(@PathVariable(name = "currentPage") int currentPage, Model model) {
        List<Product> listProducts = productService.pagingProduct(currentPage).getContent();
        Long total = (productService.pagingProduct(currentPage).getTotalElements() / PRODUCT_PER_PAGE) + 1;
        List<Integer> stockHouse=new ArrayList<>();
        List<Integer> stockBill=new ArrayList<>();

        for(Product product : listProducts){
            List<ProductDetail> productDetails=productDetailService.findAll(product.getId());
            int sumStockHouse=0;
            int sumBillHouse=0;
            for(ProductDetail productDetail:productDetails){
                if(productDetail.getStockHouse()!=null){
                    sumStockHouse+=productDetail.getStockHouse();
                }
                sumBillHouse+=productDetail.getStock();
            }
            stockHouse.add(sumStockHouse);
            stockBill.add(sumBillHouse);
        }
        model.addAttribute("stockHouse", stockHouse);
        model.addAttribute("stockBill", stockBill);

        model.addAttribute("listProducts", listProducts);
        model.addAttribute("totalPage", total);
        return "inventories/inventories";
    }

    @GetMapping("/inventories/edit/{id}")
    public String editInventory(@PathVariable(name = "id") Integer id, Model model) {

        Product product = productService.getProductById(id);
        List<ProductDetail> listProductDetails = productDetailService.findAll(id);
        List<Integer> quantityShipping=new ArrayList<>();
        for(ProductDetail productDetail:listProductDetails){
            List<OrderDetail> orderDetails=orderService.getOrderByProductDetail(productDetail.getId(), OrderStatus.SHIPPING);
            int sumShipping=0;
            for (OrderDetail orderDetail:orderDetails){
                sumShipping+=orderDetail.getQuantity();
            }
            quantityShipping.add(sumShipping);
        }
        ReceiptInventory receiptInventory=new ReceiptInventory();
        BillInventory billInventory=new BillInventory();
        model.addAttribute("product", product);
        model.addAttribute("quantityShipping", quantityShipping);

        model.addAttribute("receiptInventory", receiptInventory);

        model.addAttribute("listProductDetails", listProductDetails);
        model.addAttribute("billInventory", billInventory);
        model.addAttribute("actionSave", "/DeskitaAdmin/inventories/save");

        return "inventories/inventory_form";
    }

    @PostMapping("/receipt-inventory/save")
    public String saveReceipt(ReceiptInventory receiptInventory, @RequestParam("productDetailId") String productDetailId,
                              @AuthenticationPrincipal DeskitaUserDetails loggedUser){

        service.saveReceipt(receiptInventory,Integer.parseInt(productDetailId),loggedUser);
        return "redirect:/inventories/page/1";
    }

    @PostMapping("/bill-inventory/save")
    public String saveBill(BillInventory billInventory, @RequestParam("productDetailId") String productDetailId,
                              @AuthenticationPrincipal DeskitaUserDetails loggedUser){
        service.saveBill(billInventory,Integer.parseInt(productDetailId),loggedUser);
        return "redirect:/inventories/page/1";
    }
}
