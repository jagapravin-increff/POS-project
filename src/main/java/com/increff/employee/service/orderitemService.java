package com.increff.employee.service;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.employee.dao.orderitemDao;
import com.increff.employee.model.booForm;
import com.increff.employee.pojo.inventoryPojo;
import com.increff.employee.pojo.orderPojo;
import com.increff.employee.pojo.orderItemPojo;
import com.increff.employee.pojo.productPojo;
import com.increff.employee.util.StringUtil;

import javassist.compiler.ast.Pair;;

@Service
public class orderitemService {

	@Autowired
	private orderitemDao dao;
	private Logger logger = Logger.getLogger(orderitemDao.class);
	
	@Transactional(rollbackOn = ApiException.class)
	public int check(orderItemPojo o,productPojo p) throws ApiException {
            	 inventoryPojo i=dao.prodquantity(p.getBrand_category(),p.getName());
            	 if (i==null)
            	 {
            		 return -1;
            	 }
                 if (o.getQuantity()>i.getQuantity()) {
                	 return -1;
             }

             return i.getQuantity()-o.getQuantity();
	}

	public productPojo checkprod(orderItemPojo o) throws ApiException {
	     normalize(o);
        return dao.check(o.getBarcode());
        }
	
	@Transactional(rollbackOn = ApiException.class)
	public void add(orderItemPojo o) throws ApiException {
		productPojo p=dao.check(o.getBarcode());
		inventoryPojo i=dao.prodquantity(p.getBrand_category(),p.getName());
		int quantity=i.getQuantity()-o.getQuantity();
		if (quantity==0) {
			dao.del_inv(p.getProduct_id());
		}
		dao.upd(quantity,p.getBrand_category(),p.getName());
		dao.insert(o);
	}

	@Transactional
	public int create() {
		return dao.create();
	}
	
	@Transactional
	public List<orderPojo> getAll() throws Exception {
		return dao.selectAll();
	}
	
	@Transactional
	public orderPojo getorder(int id) throws Exception {
		return dao.selectid(id);
	}
	
	
	@Transactional(rollbackOn = ApiException.class)
	public List<orderItemPojo> get(int id) throws ApiException {
		return dao.selectitem(id);
	}
	
	public orderItemPojo getid(int id) throws ApiException {
		return dao.select(id);
	}
	
	@Transactional(rollbackOn  = ApiException.class)
	public void update(int id, orderPojo o) throws ApiException {
		dao.update(id,o);
	}
	
	@Transactional(rollbackOn  = ApiException.class)
	public void update(int id, orderItemPojo o, int quantity,productPojo p) throws ApiException {
		dao.upd(quantity,p.getBrand_category(),p.getName());

		dao.update(id,o);
	}

	
	/*@Transactional
	public void delete(int product_id) {
		dao.delete(product_id);
	}
	
	
	@Transactional
	public productDTO getCheck(int id) throws ApiException {
		productDTO p = dao.select(id);
		if (p == null) {
			throw new ApiException("Product with given ID does not exit, id: " + id);
		}
		return p;
	}
	
	public brandPojo ref(int id) {
		return dao.findid(id);
	}
	
	public void bar(String barcode) throws ApiException{
		productDTO p =dao.selectbar(barcode);
		if(p!=null) {
			throw new ApiException("Barcode must be unique");
		}
	}*/
	

	

	protected static void normalize(orderItemPojo p) {
		p.setBarcode(StringUtil.toLowerCase(p.getBarcode()));
	}
}