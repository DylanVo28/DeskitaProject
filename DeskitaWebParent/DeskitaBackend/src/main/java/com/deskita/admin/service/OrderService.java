package com.deskita.admin.service;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deskita.admin.repository.OrderRepository;
import com.deskita.admin.repository.ProductDetailRepository;
import com.deskita.common.entity.Order;
import com.deskita.common.entity.OrderDetail;
import com.deskita.common.entity.ProductDetail;
import com.deskita.common.entity.type.OrderStatus;

@Service
public class OrderService {
	
	@Autowired
	OrderRepository repo;
	
	@Autowired
	ProductDetailRepository productDetailRepo;
	
	public List<Order> exportOrders(Date startDate,Date endDate){
		return repo.exportOrder(startDate, endDate);
	}
	
	public List<Order> findAll(){
		return repo.findAll();
	}
	
	public Order findById(int id) {
		return repo.findById(id).get();
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
