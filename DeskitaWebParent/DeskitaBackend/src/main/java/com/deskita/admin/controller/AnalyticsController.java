package com.deskita.admin.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.deskita.admin.service.OrderService;
import com.deskita.common.entity.Order;
import com.deskita.export.ExcelOrder;

@Controller
public class AnalyticsController {

	@Autowired
	OrderService order;
	
	@GetMapping("/analytics")
	public String analyticsPage() {
		return "analytics/analytic";
	}
	
	@GetMapping("/export/excel/order")
	public void exportToExcel(HttpServletResponse response,
			@RequestParam(name="startDate",required = false) String startDate,
			@RequestParam(name="endDate",required = false) String endDate
			) throws IOException {
		System.out.println(startDate+"|"+endDate);
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
