package com.increff.employee.service;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.employee.dao.inventoryDao;
import com.increff.employee.pojo.inventoryPojo;
import com.increff.employee.pojo.productPojo;

@Service
public class InventoryService {

	@Autowired
	private inventoryDao dao;
	private Logger logger = Logger.getLogger(inventoryDao.class);

	@Transactional(rollbackOn = ApiException.class)
	public void add(inventoryPojo p) throws ApiException {
		
		if(p.getQuantity()==0) {
			throw new ApiException("Quantity cannot be empty");
		}
		if(p.getId()==0) {
			throw new ApiException("Quantity cannot be empty");
		}
		invcheck(p.getId());
		dao.insert(p);
	}


	@Transactional(rollbackOn = ApiException.class)
	public inventoryPojo get(int id) throws ApiException {
		return getCheck(id);
	}

	@Transactional
	public List<inventoryPojo> getAll() throws Exception {
		return dao.selectAll();
	}

	@Transactional(rollbackOn  = ApiException.class)
	public void update(int id, inventoryPojo p) throws ApiException {
		updcheck(id);
		dao.update(id,p);
	}
      @Transactional
	public void updcheck(int id) throws ApiException {
		// TODO Auto-generated method stub
    	  productPojo pr=dao.findid(id);
		  if (pr==null) {
				logger.info("delete_id");
				throw new ApiException("Product with given Product ID does not exit, id: "+ id);
			}
		
	}


	@Transactional
	public inventoryPojo getCheck(int id) throws ApiException {
		inventoryPojo p = dao.select(id);
		if (p == null) {
			throw new ApiException("inventory with given ID does not exit, id: " + id);
		}
		return p;
	}
	
	@Transactional
	public void invcheck(int id) throws ApiException {
		productPojo pr=dao.findid(id);
		  if (pr==null) {
				logger.info("delete_id");
				throw new ApiException("Product with given Product ID does not exit, id: "+ id);
			}	
		inventoryPojo i=dao.select(id);
		if (i!=null) {
			throw new ApiException("Inventory details for the given Product ID already exists");
		}
	}
	
	
	

}
