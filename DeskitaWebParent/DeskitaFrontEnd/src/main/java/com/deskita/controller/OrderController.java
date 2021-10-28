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
		Customer customer = null;
		Order order=null;
		try {
			customer=customerService.getCustomerByEmail(loggedUser.getUsername()) ;
			order=orderService.createOrder(customer, paymentMethodOrder, orderDTO);
			model.addAttribute("order",order);
			return "order/order_complete";
		}catch(Exception ex) {
			if(customer==null) {
				return "redirect:/login";
			}
			model.addAttribute("title_error","Không thể tạo đơn hàng");
			return "error/404_NotFound";
		}
		
		

		
	}
	
	@PostMapping("/update-status")
	public String updateStatus(@RequestParam(name="orderId",required = false) Integer id,
			@RequestParam(name="status",required=false) String status
			) {
		
		
		try {
			Order order=orderService.findOrderById(id);
			orderService.updateStatus(order, status);
			return "redirect:/order/"+order.getId();
		} catch (Exception e) {
			
			return "redirect:/login";
		}
		
	}
	
	@GetMapping("/my-order")
	public String myOrder(@AuthenticationPrincipal DeskitaCustomerDetails loggedUser,Model model) {
		Set<Order> orders = null;
		Customer customer = null;
		try {
			customer=customerService.getCustomerByEmail(loggedUser.getUsername()) ;
			orders=customer.getOrders();
			model.addAttribute("orders",orders);
			return "order/my_order";
		}catch(Exception ex) {
			if(customer==null) {
				return "redirect:/login";
			}
			model.addAttribute("title_error","Lõi hệ thống");
			return "error/404_NotFound";
			
		}
		
		
	}
	
	@GetMapping("/order/{orderId}")
	public String orderDetail(@AuthenticationPrincipal DeskitaCustomerDetails loggedUser,Model model,@PathVariable(name = "orderId") Integer id) {
		Customer customer=null;
		Order order=null;
		try {
			customer=customerService.getCustomerByEmail(loggedUser.getUsername()) ;
			order=orderService.findOrderById(id);
			
			model.addAttribute("order",order);
			model.addAttribute("customer",customer);
			return "order/order_detail";
		}catch(Exception ex) {
			if(customer==null) {
				return "redirect:/login";
			}
			model.addAttribute("title_error","Không tìm thấy đơn hàng");
			return "error/404_NotFound";
		}
		
	}
}
