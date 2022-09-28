package com.deskita.export;



import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.deskita.common.entity.Order;
import com.deskita.common.entity.OrderDetail;
import com.deskita.common.entity.Product;
import com.deskita.common.entity.ProductDetail;



public class ExcelOrder {

	
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;

	private List<Order> listOrder;
	
	public ExcelOrder(List<Order> orders) {
		this.listOrder=orders;
		workbook=new XSSFWorkbook();
	}
	
	private void writeHeaderLine() {
		sheet=workbook.createSheet("Order");
		Row row=sheet.createRow(0);
		
		CellStyle style=workbook.createCellStyle();
		XSSFFont font=workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);
		
		createCell(row, 0, "ID", style);
		createCell(row, 1, "FULLNAME", style);
		createCell(row, 2, "PHONE NUMBER", style);
		createCell(row, 3, "PAYMENT METHOD", style);
		createCell(row, 4, "STATUS", style);
		createCell(row, 5, "ORDER TIME", style);
		createCell(row, 6, "DELIVER DATE", style);
		createCell(row, 7, "PRODUCT", style);
		createCell(row, 8, "QUANTITY", style);
		createCell(row, 9, "PRODUCT COST", style);
		createCell(row, 10, "SHIPPING COST", style);
		createCell(row, 11, "TOTAL", style);
	}
	
	 private void createCell(Row row, int columnCount, Object value, CellStyle style) {
	        sheet.autoSizeColumn(columnCount);
	        Cell cell = row.createCell(columnCount);
	        if (value instanceof Integer) {
	            cell.setCellValue((Integer) value);
	        } else if (value instanceof Boolean) {
	            cell.setCellValue((Boolean) value);
	        }else {
	            cell.setCellValue((String) value);
	        }
	        cell.setCellStyle(style);
	    }
	 
	private void writeDataLines() {
		int rowCount=1;
		CellStyle style=workbook.createCellStyle();
		XSSFFont font=workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);
		for(Order o:listOrder) {
			
			for(OrderDetail od:o.getOrderDetails()) {
				 Row row = sheet.createRow(rowCount++);
		         int columnCount = 0;
		         createCell(row, columnCount++, od.getId(), style);
		         createCell(row, columnCount++, o.getFullName(), style);
		         createCell(row, columnCount++, o.getPhoneNumber(), style);
		         createCell(row, columnCount++, o.getPaymentMethod().name(), style);
		         createCell(row, columnCount++, o.getStatus().name(), style);
		         createCell(row, columnCount++, o.getOrderTime().toString(), style);
		         if(o.getDeliverDate()!=null) {
		        	 createCell(row, columnCount++, o.getDeliverDate().toString(), style);
		         }
		         else {
		        	 createCell(row, columnCount++, "", style);
		         }
		         createCell(row, columnCount++, od.getName(), style);
		         createCell(row, columnCount++, od.getQuantity(), style);
		         createCell(row, columnCount++, String.valueOf(o.getProductCost()) , style);
		         createCell(row, columnCount++, String.valueOf(o.getShippingCost()) , style);
		         createCell(row, columnCount++,String.valueOf(o.getTotal()) , style);
			}
		}
	}
	 public void export(HttpServletResponse response) throws IOException {
	        writeHeaderLine();
	        writeDataLines();
	         
	        ServletOutputStream outputStream = response.getOutputStream();
	        workbook.write(outputStream);
	        workbook.close();
	         
	        outputStream.close();
	         
	    }
	
}
