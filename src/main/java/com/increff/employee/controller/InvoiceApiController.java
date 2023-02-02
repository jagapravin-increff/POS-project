package com.increff.employee.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.employee.service.orderitemService;
import com.increff.employee.model.orderData;
import com.increff.employee.model.orderxmlForm;
import com.increff.employee.pojo.orderItemPojo;
import com.increff.employee.service.invoiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class InvoiceApiController {

	@Autowired
	private orderitemService orderService;
	@Autowired
	private invoiceService invoiceService;
	private Logger logger = Logger.getLogger(InvoiceApiController.class);

	@ApiOperation(value = "Adds an product")
	@RequestMapping(path = "/api/invoice/{id}", method = RequestMethod.GET)
	public void add(@PathVariable int id, HttpServletResponse response) throws Exception {
		List<orderItemPojo> orderList=orderService.get(id);
		List<orderData> d=new ArrayList<>();
		for(orderItemPojo o:orderList) {
		    d.add( convert(o));
		}
		for(orderData o:d) {
		    logger.info(o.getName());
		}
		logger.info(invoiceService.hello());
		logger.info(response);
		orderxmlForm invoicexml=new orderxmlForm();
		invoicexml.setOrder_id(id);
		invoicexml.setOrderInvoiceData(d);
		logger.info(invoicexml.getOrder_id());
		byte bytes[]=invoiceService.generatePdf(invoicexml);
		logger.info("fdsfsdfwsrg");
		createPdfResponse(bytes,response);
		logger.info("fdsf");
	}

	//Creates PDF
    public void createPdfResponse(byte[] bytes, HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setContentLength(bytes.length);

        response.getOutputStream().write(bytes);
        response.getOutputStream().flush();
    }
 public static orderData convert(orderItemPojo orderitem) {
	orderData d=new orderData();
	d.setName(orderitem.getName());
	d.setQuantity(orderitem.getQuantity());
	d.setMrp(orderitem.getPrice());
	d.setPrice(orderitem.getPrice()*orderitem.getQuantity());
	return d;
 }
 }
