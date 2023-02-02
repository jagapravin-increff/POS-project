package com.increff.employee.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.employee.model.EmployeeData;
import com.increff.employee.model.EmployeeForm;
import com.increff.employee.model.brandData;
import com.increff.employee.model.brandForm;
import com.increff.employee.pojo.EmployeePojo;
import com.increff.employee.pojo.brandPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.EmployeeService;
import com.increff.employee.service.brandService;

@Service
public class employeeDto
{   
	@Autowired
	private EmployeeService service;
	
	public void add(EmployeeForm form) throws ApiException
	{
		EmployeePojo p = convert(form);
		service.add(p);
	}
	
	public void delete(int id)
	{
		service.delete(id);
	}
	
	public EmployeeData get(int id) throws ApiException
	{
		EmployeePojo p = service.get(id);
		return convert(p);
	}
	
	public List<EmployeeData> getAll() {
		List<EmployeePojo> list = service.getAll();
		List<EmployeeData> list2 = new ArrayList<EmployeeData>();
		for (EmployeePojo p : list) {
			list2.add(convert(p));
		}
		return list2;
	}
	
	public void update(int id,EmployeeForm f) throws ApiException
	{
		EmployeePojo p = convert(f);
		service.update(id, p);
	}

	private static EmployeeData convert(EmployeePojo p) {
		EmployeeData d = new EmployeeData();
		d.setAge(p.getAge());
		d.setName(p.getName());
		d.setId(p.getId());
		return d;
	}

	private static EmployeePojo convert(EmployeeForm f) {
		EmployeePojo p = new EmployeePojo();
		p.setAge(f.getAge());
		p.setName(f.getName());
		return p;
	}
}
