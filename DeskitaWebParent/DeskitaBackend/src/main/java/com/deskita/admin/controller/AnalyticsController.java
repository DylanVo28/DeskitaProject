package com.deskita.admin.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

import javax.servlet.http.HttpServletResponse;

import com.deskita.admin.model.DataChartOrder;
import com.deskita.common.entity.type.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.deskita.admin.service.OrderService;
import com.deskita.common.entity.Order;
import com.deskita.export.ExcelOrder;
import static java.time.temporal.TemporalAdjusters.firstDayOfYear;

@Controller
public class AnalyticsController {

	@Autowired
	OrderService order;
	
	@GetMapping("/analytics")
	public String analyticsPage(Model model) {
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		String currentDateTime = dateFormatter.format(new Date());
		LocalDate now = LocalDate.now(); // 2015-11-23
		LocalDate ldFirstDay = now.with(firstDayOfYear()); // 2015-01-01
		Date dFirstDay = Date.from(ldFirstDay.atStartOfDay(ZoneId.systemDefault()).toInstant());
		String firstDay= dateFormatter.format(dFirstDay);
		List<Order> orders = order.exportOrders(java.sql.Date.valueOf(firstDay),java.sql.Date.valueOf(currentDateTime));
		HashMap<String,Integer> hmDataChart= new HashMap<>();
		HashMap<Integer, Float> hmRevenue=new HashMap<>();
		for(int i=1;i<=now.getMonthValue();i++){
			LocalDate date=now.withMonth(i);
			LocalDate fldFirstDay=date.withDayOfMonth(1);
			LocalDate fldLastDay=date.with(TemporalAdjusters.lastDayOfMonth());
			List<Order> lOrders=order.exportOrders(java.sql.Date.valueOf(fldFirstDay),java.sql.Date.valueOf(fldLastDay));
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
 
}
