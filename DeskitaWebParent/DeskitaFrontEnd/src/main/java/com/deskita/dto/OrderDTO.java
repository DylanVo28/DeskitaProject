package com.deskita.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.deskita.common.entity.Product;
import com.deskita.common.entity.ProductDetail;


public class OrderDTO {

	private int quantityProduct;
	
	private String fullName;
	
	private String address;
	
	private String phoneNumber;
	
	private String paymentMethod;
	
	private float totalPriceOrder;

	private float shippingPrice;
	
	private int productId;
	
	private int productDetailId;
	
	public int getQuantityProduct() {
		return quantityProduct;
	}

	public void setQuantityProduct(int quantityProduct) {
		this.quantityProduct = quantityProduct;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public float getTotalPriceOrder() {
		return totalPriceOrder;
	}

	public void setTotalPriceOrder(float totalPriceOrder) {
		this.totalPriceOrder = totalPriceOrder;
	}

	public float getShippingPrice() {
		return shippingPrice;
	}

	public void setShippingPrice(float shippingPrice) {
		this.shippingPrice = shippingPrice;
	}
	
	

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getProductDetailId() {
		return productDetailId;
	}

	public void setProductDetailId(int productDetailId) {
		this.productDetailId = productDetailId;
	}

	public OrderDTO(int quantityProduct, String fullName, String address, String phoneNumber, String paymentMethod,
			float totalPriceOrder,float shippingPrice,int productId,int productetailId) {
		super();
		this.quantityProduct = quantityProduct;
		this.fullName = fullName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.paymentMethod = paymentMethod;
		this.totalPriceOrder = totalPriceOrder;
		this.shippingPrice=shippingPrice;
		this.productId=productId;
		this.productDetailId=productetailId;
		
	}

	@Override
	public String toString() {
		return "OrderDTO [quantityProduct=" + quantityProduct + ", fullName=" + fullName + ", address=" + address
				+ ", phoneNumber=" + phoneNumber + ", paymentMethod=" + paymentMethod + ", totalPriceOrder="
				+ totalPriceOrder + ", shippingPrice=" + shippingPrice + ", productId=" + productId
				+ ", productDetailId=" + productDetailId + "]";
	}
	
	
	
	
	
}
