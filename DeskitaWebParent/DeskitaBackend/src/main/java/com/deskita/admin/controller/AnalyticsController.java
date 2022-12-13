package com.deskita.admin.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

import javax.servlet.http.HttpServletResponse;

import com.deskita.admin.model.DataChartOrder;
import com.deskita.common.entity.type.OrderStatus;

import org.hibernate.annotations.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.deskita.admin.service.CategoryService;
import com.deskita.admin.service.InventoryService;
import com.deskita.admin.service.OrderService;
import com.deskita.admin.service.ProductDetailService;
import com.deskita.admin.service.ProductService;
import com.deskita.common.entity.Category;
import com.deskita.common.entity.Income;
import com.deskita.common.entity.Order;
import com.deskita.common.entity.OrderDetail;
import com.deskita.common.entity.Product;
import com.deskita.common.entity.ProductDetail;
import com.deskita.export.ExcelOrder;
import static java.time.temporal.TemporalAdjusters.firstDayOfYear;

@Controller
public class AnalyticsController {
	public String dataNhan;
	@Autowired
	OrderService order;
	@Autowired  CategoryService categoryService;
	@Autowired ProductDetailService productDetailService;
	@Autowired ProductService productService;
	@Autowired InventoryService inventoryService;
	@GetMapping("/analytics")
	public String analyticsPage(Model model) {
		List<Category> categories=categoryService.listAll();		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		String currentDateTime = dateFormatter.format(new Date());
		LocalDate now = LocalDate.now(); // 2022-11-21
		LocalDate ldFirstDay = now.with(firstDayOfYear()); // 2022-01-01
		Date dFirstDay = Date.from(ldFirstDay.atStartOfDay(ZoneId.systemDefault()).toInstant());
		String firstDay= dateFormatter.format(dFirstDay);
		List<Order> ordersCurrent=order.findAll();
		List<Income> incomes=new ArrayList();
		if(dataNhan!=null)
		{
		
		for (Order order : ordersCurrent) {
			int flag=0;
			if(order.getStatus()==OrderStatus.PAID){
				OrderDetail orderDetail= order.getOrderDetails().iterator().next();
				ProductDetail productDetail=productDetailService.findByID(orderDetail.getProductDetailId());
				Product product=productService.findByID(productDetail.getProductId());
				Product productTemp;
				System.out.println("sanpham:"+product);
				if(product.getCategory().getId()==Integer.parseInt(dataNhan))
				{	
					for (Income incomex : incomes) {
						if(incomex.getTenSP()==product.getName())
						{
							flag=1;
							incomex.setSoluongBan(orderDetail.getQuantity()+incomex.getSoluongBan());
							incomex.setThuNhap(incomex.getThuNhap()+(orderDetail.getQuantity()*orderDetail.getValue().doubleValue()));
						}
						
					}
					if(flag==0)
					{
						Income income=new Income();
							income.setTenSP(product.getName());
							income.setSoluongBan(orderDetail.getQuantity());
							income.setThuNhap(orderDetail.getQuantity()*orderDetail.getValue().doubleValue());
							incomes.add(income);
					}
				}
				if(product.getCategory().getId()==Integer.parseInt(dataNhan))
				{
					for (Income income : incomes) {
						BigDecimal giaTrungBinh=inventoryService.averagePriceByProduct(product);
						Double giaTrungBinhDB=giaTrungBinh.doubleValue();
						System.out.println("gia trung binh"+giaTrungBinhDB);
						Integer soluongBan=income.getSoluongBan();
						Double thuNhap=income.getThuNhap()-giaTrungBinhDB*soluongBan;
						thuNhap=Math.ceil((thuNhap * 100) / 100);
						income.setThuNhap(thuNhap);
					}
				}
				}
				

			}
		}
			
		List<Order> orders = order.exportOrders(java.sql.Date.valueOf(firstDay),java.sql.Date.valueOf(currentDateTime));
		HashMap<String,Integer> hmDataChart= new HashMap<>();
		HashMap<Integer, Float> hmRevenue=new HashMap<>(); //CHECK XEM THÁNG ĐÓ CÓ Ở TRONG MAP K
		for(int i=1;i<=now.getMonthValue();i++){
			LocalDate date=now.withMonth(i);
			LocalDate fldFirstDay=date.withDayOfMonth(1);
			LocalDate fldLastDay=date.with(TemporalAdjusters.lastDayOfMonth());
			List<Order> lOrders=order.exportOrders(java.sql.Date.valueOf(fldFirstDay),java.sql.Date.valueOf(fldLastDay)); //Lay cac don tu ngay 1 den ngay cuoi thang
			int month=now.withMonth(i).getMonthValue();
			if(lOrders.size()==0){
				hmRevenue.put(month,Float.valueOf("0"));
			}
			else{
				for(Order order:lOrders){
					if(order.getStatus()==OrderStatus.PAID && hmRevenue.get(month)!=null){

						hmRevenue.put(month,hmRevenue.get(month)+order.getTotal());
					}
					else if(order.getStatus()==OrderStatus.PAID && hmRevenue.get(month)==null) {
						hmRevenue.put(month,order.getTotal());

					}
				}
			}

		}
		int[] iStatusOrders=new int[11];
		for(int i=0;i<iStatusOrders.length;i++){
			iStatusOrders[i]=0;
		}
		for(Order order: orders){

			if(order.getStatus()== OrderStatus.NEW){
				iStatusOrders[0]++;
			}
			else if(order.getStatus()==OrderStatus.CANCELLED){
				iStatusOrders[1]++;
			}
			else if(order.getStatus()==OrderStatus.PROCESSING){
				iStatusOrders[2]++;
			}
			else if(order.getStatus()==OrderStatus.PACKAGED){
				iStatusOrders[3]++;
			}
			else if(order.getStatus()==OrderStatus.PICKED){
				iStatusOrders[4]++;
			}
			else if(order.getStatus()==OrderStatus.SHIPPING){
				iStatusOrders[5]++;
			}
			else if(order.getStatus()==OrderStatus.DELIVERED){
				iStatusOrders[6]++;
			}
			else if(order.getStatus()==OrderStatus.RETURNED){
				iStatusOrders[7]++;
			}
			else if(order.getStatus()==OrderStatus.PAID){
				iStatusOrders[8]++;
			}
			else if(order.getStatus()==OrderStatus.REFUNDED){
				iStatusOrders[9]++;
			}
			else if(order.getStatus()==OrderStatus.CONFIRMED){
				iStatusOrders[10]++;
			}
		}
		//NEW,CANCELLED,PROCESSING,PACKAGED,PICKED,SHIPPING,DELIVERED,RETURNED,PAID,REFUNDED,CONFIRMED
		hmDataChart.put("NEW",iStatusOrders[0]);
		hmDataChart.put("CANCELLED",iStatusOrders[1]);
		hmDataChart.put("PROCESSING",iStatusOrders[2]);
		hmDataChart.put("PACKAGED",iStatusOrders[3]);
		hmDataChart.put("PICKED",iStatusOrders[4]);
		hmDataChart.put("SHIPPING",iStatusOrders[5]);
		hmDataChart.put("DELIVERED",iStatusOrders[6]);
		hmDataChart.put("RETURNED",iStatusOrders[7]);
		hmDataChart.put("PAID",iStatusOrders[8]);
		hmDataChart.put("REFUNDED",iStatusOrders[9]);
		hmDataChart.put("CONFIRMED",iStatusOrders[10]);

		
		model.addAttribute("hmRevenue",hmRevenue);
		model.addAttribute("hmDataChart",hmDataChart);
		model.addAttribute("categories", categories);
		model.addAttribute("dataSend", dataNhan);
		model.addAttribute("thuNhapList", incomes);



		return "analytics/analytic";
	}

	@GetMapping("/export/excel/order")
	public void exportToExcel(HttpServletResponse response,
			@RequestParam(name="startDate",required = false) String startDate,
			@RequestParam(name="endDate",required = false) String endDate
			) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=orders_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        
        List<Order> listOrder = order.exportOrders(java.sql.Date.valueOf(startDate),java.sql.Date.valueOf(endDate));
         
        ExcelOrder excelExporter = new ExcelOrder(listOrder);
         
        excelExporter.export(response);    
    }  

	@GetMapping(value="getdata")
	public String getIncomeData(@RequestParam(name = "categoryChoose",required = false) String categoryChoose){
		try {
			System.out.println("dataNhan"+categoryChoose);
			dataNhan=categoryChoose;
			return "redirect:/analytics";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "redirect:/analytics";

		}
		
		
	}
	
 
}
