package com.deskita.service;


import java.sql.Date;
import java.util.Calendar;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deskita.common.entity.Customer;
import com.deskita.common.entity.Order;
import com.deskita.common.entity.OrderDetail;
import com.deskita.common.entity.Product;
import com.deskita.common.entity.ProductDetail;
import com.deskita.common.entity.type.OrderStatus;
import com.deskita.common.entity.type.PaymentMethod;
import com.deskita.dto.OrderDTO;
import com.deskita.repository.OrderRepository;
import com.deskita.repository.ProductDetailRepository;
import com.deskita.repository.ProductRepository;

@Service
public class OrderService {

	
	@Autowired
	private OrderRepository repo;
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired 
	private ProductDetailRepository productDetailRepo;
	
	public Order createOrder(Customer customer,PaymentMethod paymentMethod,OrderDTO orderDTO) {
		 
		Order newOrder=new Order();
		
		Product product=productRepo.findById(orderDTO.getProductId()).get();
		ProductDetail productDetail=productDetailRepo.findById(orderDTO.getProductDetailId()).get();
		
		newOrder.setOrderTime(new Date(Calendar.getInstance().getTime().getTime()));
		newOrder.setPaymentMethod(paymentMethod);
		newOrder.setCustomer(customer);
		newOrder.setProductCost(productDetail.getValue().floatValue()*orderDTO.getQuantityProduct());
		
		newOrder.setShippingCost(orderDTO.getShippingPrice());
		newOrder.setTotal(newOrder.getShippingCost()+newOrder.getProductCost());
		newOrder.setFullName(orderDTO.getFullName());
		newOrder.setPhoneNumber(orderDTO.getPhoneNumber());
		newOrder.setAddress(orderDTO.getAddress());
		newOrder.setStatus(OrderStatus.NEW);
		newOrder.setDeliverDays(5);
		Set<OrderDetail> orderDetails = newOrder.getOrderDetails();
		
		OrderDetail newOrderDetail=new OrderDetail(orderDTO.getQuantityProduct(),product.getName()+"|"+productDetail.getName(),product.getImage(),newOrder,productDetail.getValue(),productDetail.getId());
		orderDetails.add(newOrderDetail);
		return repo.save(newOrder);
		
	}
	
	public Order findOrderById(int id) {
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
						ProductDetail pd=productDetailRepo.findById(orderDetail.getId()).get();
						if(pd.getStock()-orderDetail.getQuantity()<0) {
							throw new Exception("MAX_STOCK");
						}
						pd.setStock(pd.getStock()-orderDetail.getQuantity());
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
