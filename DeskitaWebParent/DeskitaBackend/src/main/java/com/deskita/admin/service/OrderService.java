package com.deskita.admin.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import com.deskita.admin.repository.OrderDetailRepository;
import com.deskita.common.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.deskita.admin.repository.OrderRepository;
import com.deskita.admin.repository.ProductDetailRepository;
import com.deskita.common.entity.Order;
import com.deskita.common.entity.OrderDetail;
import com.deskita.common.entity.ProductDetail;
import com.deskita.common.entity.type.OrderStatus;

@Service
public class OrderService {

	public static int PAGE_SIZE=10;

	@Autowired
	OrderRepository repo;
	
	@Autowired
	ProductDetailRepository productDetailRepo;

	@Autowired
	OrderDetailRepository orderDetailRepository;
	
	public List<Order> exportOrders(Date startDate,Date endDate){
		return repo.exportOrder(startDate, endDate);
	}

	public Page<Order> pagingOrder(int currentPage){
		Pageable pageable= PageRequest.of(currentPage-1, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "orderTime"));
		Page<Order> page=repo.findAll(pageable);
		return page;
	}

	public List<Order> findAll(){
		return repo.findAll(Sort.by(Sort.Direction.DESC, "orderTime"));
	}
	
	public Order findById(int id) {
		return repo.findById(id).get();
	}

	public List<OrderDetail> getOrderByProductDetail(Integer productDetailId,OrderStatus status){
		List<OrderDetail> orderDetails=orderDetailRepository.getOrderDetailByProductDetail(productDetailId);
		List<OrderDetail> result=new ArrayList<>();
		for(OrderDetail orderDetail:orderDetails){
			if(orderDetail.getOrder().getStatus()==status){
				result.add(orderDetail);
			}
		}
		return result;
	}
	
	public void updateStatus(Order order,String status) throws Exception {
		
		switch(status) {
			case "CANCEL":{
				if(order.getStatus()==OrderStatus.NEW) {
					OrderStatus orderStatus=OrderStatus.CANCELLED;
					order.setStatus(orderStatus);
				}
				break;
			}
			case "CONFIRMED":{
					if(order.getStatus()==OrderStatus.NEW) {
						
						OrderStatus orderStatus=OrderStatus.CONFIRMED;
						
						order.setStatus(orderStatus);
					}
				break;
			}
			case "SHIPPING":{
				if(order.getStatus()==OrderStatus.CONFIRMED) {
					OrderStatus orderStatus=OrderStatus.SHIPPING;
					Set<OrderDetail> orderDetails=order.getOrderDetails();
					
					for(OrderDetail orderDetail :orderDetails) {
					
						ProductDetail pd=productDetailRepo.findById(orderDetail.getProductDetailId()).get();
						
						if(pd.getStock()-orderDetail.getQuantity()<0) {
							throw new Exception("MAX_STOCK");
						}
						pd.setStock(pd.getStock()-orderDetail.getQuantity());
						productDetailRepo.save(pd);
					}
					
					order.setStatus(orderStatus);
					break;
				}
			}
			case "DELIVERED":{
				if(order.getStatus()==OrderStatus.SHIPPING) {
					order.setDeliverDate(new Date(Calendar.getInstance().getTime().getTime()));
					order.setStatus(OrderStatus.DELIVERED);
				}
				break;
			}
			case "PAID":{
				if(order.getStatus()==OrderStatus.DELIVERED) {
					order.setStatus(OrderStatus.PAID);
				}
				break;
			}
		}
		repo.save(order);
	}
}
