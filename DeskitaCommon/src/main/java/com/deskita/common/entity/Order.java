package com.deskita.common.entity;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.deskita.common.entity.type.OrderStatus;
import com.deskita.common.entity.type.PaymentMethod;

@Entity
@Table(name="orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="full_name",nullable=false,length=45)
	private String fullName;
	
	@Column(name="phone_number",nullable = false,length=45)
	private String phoneNumber;
	
	@Column(name="address",nullable = false, length=120)
	private String address;
	
	private Date orderTime;
	
	private float shippingCost;
	private float productCost;
	
	private float total;
	
	private int deliverDays;
	
	private Date deliverDate;
	
	@Enumerated(EnumType.STRING)
	private PaymentMethod paymentMethod;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	@ManyToOne
	@JoinColumn(name="customer_id")
	private Customer customer;
	
	@OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
	private Set<OrderDetail> orderDetails=new HashSet<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public float getShippingCost() {
		return shippingCost;
	}

	public void setShippingCost(float shippingCost) {
		this.shippingCost = shippingCost;
	}

	public float getProductCost() {
		return productCost;
	}

	public void setProductCost(float productCost) {
		this.productCost = productCost;
	}

	

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public int getDeliverDays() {
		return deliverDays;
	}

	public void setDeliverDays(int deliverDays) {
		this.deliverDays = deliverDays;
	}

	public Date getDeliverDate() {
		return deliverDate;
	}

	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Set<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(Set<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public Order() {
		super();
	}

	public Order(String fullName, String phoneNumber, String address, Date orderTime, float shippingCost,
			float productCost, float subTotal, float total, int deliverDays, Date deliverDate,
			PaymentMethod paymentMethod, OrderStatus status, Customer customer, Set<OrderDetail> orderDetails) {
		super();
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.orderTime = orderTime;//
		this.shippingCost = shippingCost;//
		this.productCost = productCost;//
	
		this.total = total;//
		this.deliverDays = deliverDays;
		this.deliverDate = deliverDate;
		this.paymentMethod = paymentMethod;//
		this.status = status;
		this.customer = customer;//
		this.orderDetails = orderDetails;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", fullName=" + fullName + ", phoneNumber=" + phoneNumber + ", address=" + address
				+ ", orderTime=" + orderTime + ", shippingCost=" + shippingCost + ", productCost=" + productCost
				+ ", total=" + total + ", deliverDays=" + deliverDays + ", deliverDate=" + deliverDate
				+ ", paymentMethod=" + paymentMethod + ", status=" + status + ", customer=" + customer
				+ ", orderDetails=" + orderDetails + "]";
	}

	
	
}
