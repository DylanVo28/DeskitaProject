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
		Set<OrderDetail> orderDetails = newOrder.getOrderDetails();
		
		OrderDetail newOrderDetail=new OrderDetail(orderDTO.getQuantityProduct(),product.getName()+"|"+productDetail.getName(),product.getImage(),newOrder,productDetail.getValue());
		orderDetails.add(newOrderDetail);
		return repo.save(newOrder);
		
	}
	
	public Order findOrderById(int id) {
		return repo.findById(id).get();
	}
}
