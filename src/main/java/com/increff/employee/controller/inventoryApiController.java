package com.increff.employee.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.employee.dto.inventoryDto;
import com.increff.employee.model.inventoryData;
import com.increff.employee.model.inventoryForm;
import com.increff.employee.model.productData;
import com.increff.employee.pojo.inventoryPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class inventoryApiController {
	private Logger logger = Logger.getLogger(inventoryApiController.class);

	@Autowired
	private inventoryDto idt;

	@ApiOperation(value = "Adds an product")
	@RequestMapping(path = "/api/inventory", method = RequestMethod.POST)
	public void add(@RequestBody inventoryForm form) throws ApiException {
		idt.add(form);
	}
	


	@ApiOperation(value = "Gets an inventory by ID")
	@RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.GET)
	public inventoryData get(@PathVariable int id) throws ApiException {
		return idt.get(id);
	}

	@ApiOperation(value = "Gets list of all inventorys")
	@RequestMapping(path = "/api/inventory", method = RequestMethod.GET)
	public List<inventoryData> getAll() throws Exception {

		return idt.getAll();
	}

	@ApiOperation(value = "Updates an inventory")
	@RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable int id, @RequestBody inventoryForm f) throws ApiException {
		
	     idt.update(id, f);
	}
}
	
