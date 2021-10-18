package com.deskita.controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.deskita.common.entity.Customer;
import com.deskita.common.entity.Order;
import com.deskita.common.entity.Product;
import com.deskita.common.entity.ProductDetail;
import com.deskita.common.entity.type.OrderStatus;
import com.deskita.common.entity.type.PaymentMethod;
import com.deskita.dto.OrderDTO;
import com.deskita.security.CustomerAuthentication;
import com.deskita.security.DeskitaCustomerDetails;
import com.deskita.service.CustomerService;
import com.deskita.service.OrderService;

@Controller
public class OrderController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private OrderService orderService;
	
	@PostMapping("/order-now")
	public String viewOrder(
			@RequestParam(name="quantity_product",required = false) Integer quantityProduct,
			@RequestParam(name="fullname",required = false) String fullname,
			@RequestParam(name="address",required=false) String address,
			@RequestParam(name="phone_number",required = false) String phoneNumber,
			@RequestParam(name="payment_method",required = false) String paymentMethod,
			@RequestParam(name="total_price_order",required = false) String totalPriceOrder,
			@RequestParam(name="shipping_price",required = false) String shippingPrice,
			@RequestParam(name="product_id",required = false) Integer productId,
			@RequestParam(name="product_detail_id",required=false) Integer productDetailId,
			
			@AuthenticationPrincipal DeskitaCustomerDetails loggedUser,
			Model model
			) {
		
		
		PaymentMethod paymentMethodOrder= PaymentMethod.valueOf(paymentMethod);

		OrderDTO orderDTO=new OrderDTO(quantityProduct, fullname, address, phoneNumber, paymentMethod, Float.parseFloat(totalPriceOrder),Float.parseFloat(shippingPrice),productId,productDetailId);
	
		Customer customer=customerService.getCustomerByEmail(loggedUser.getUsername()) ;

		Order order=orderService.createOrder(customer, paymentMethodOrder, orderDTO);
		model.addAttribute("order",order);
		
		return "order/order_complete";
	}
	
	@GetMapping("/code-ui")
	public String viewCodeUI() {
		return "order/order_detail";
	}
	
	@GetMapping("/my-order")
	public String myOrder(@AuthenticationPrincipal DeskitaCustomerDetails loggedUser,Model model) {
		Customer customer=customerService.getCustomerByEmail(loggedUser.getUsername()) ;
		Set<Order> orders=customer.getOrders();
		model.addAttribute("orders",orders);
//		System.out.println(customer.getOrders());
		return "order/my_order";
	}
	
	@GetMapping("/order/{orderId}")
	public String orderDetail(@AuthenticationPrincipal DeskitaCustomerDetails loggedUser,Model model,@PathVariable(name = "orderId") Integer id) {
		Order order=orderService.findOrderById(id);
		Customer customer=customerService.getCustomerByEmail(loggedUser.getUsername()) ;
		model.addAttribute("order",order);
		model.addAttribute("customer",customer);
		return "order/order_detail";
	}
}
