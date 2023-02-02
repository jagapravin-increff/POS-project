package com.increff.employee.service;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.employee.dao.productDao;
import com.increff.employee.pojo.productPojo;
import com.increff.employee.util.StringUtil;

@Service
public class productService {

	@Autowired
	private productDao dao;
	private Logger logger = Logger.getLogger(productDao.class);

	@Transactional(rollbackOn = ApiException.class)
	public void add(productPojo p,int id) throws ApiException {
		normalize(p);
		if(StringUtil.isEmpty(p.getName())) {
			throw new ApiException("Name cannot be empty");
		}
		if(StringUtil.isEmpty(p.getBarcode())) {
			throw new ApiException("Barcode cannot be empty");
		}
		if (p.getMrp()==0.0d) {
			throw new ApiException("MRP cannot be empty");
		}
		
		//insertCheck(p.getBrand(),p.getCategory());
		dao.insert(p,id);
	}

	@Transactional
	public void delete(int product_id) {
		dao.delete(product_id);
	}

	@Transactional(rollbackOn = ApiException.class)
	public productPojo get(int id) throws ApiException {
		return getCheck(id);
	}

	@Transactional
	public List<productPojo> getAll() throws Exception {
		return dao.selectAll();
	}
	
	@Transactional
	public List<productPojo> get(String brand)  throws ApiException{
		// TODO Auto-generated method stub
		return dao.selectAll(brand);
	}
	

	@Transactional(rollbackOn  = ApiException.class)
	public void update(int id, productPojo p) throws ApiException {
		normalize(p);        
		dao.update(id,p);
	}

	@Transactional
	public productPojo getCheck(int id) throws ApiException {
		productPojo p = dao.select(id);
		if (p == null) {
			throw new ApiException("Product with given ID does not exit, id: " + id);
		}
		return p;
	}
	
	
	protected static void normalize(productPojo p) {
		p.setName(StringUtil.toLowerCase(p.getName()));
		p.setBarcode(StringUtil.toLowerCase(p.getBarcode()));
	}
}