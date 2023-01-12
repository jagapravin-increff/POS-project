package com.increff.employee.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.increff.employee.pojo.inventoryPojo;
import com.increff.employee.pojo.productPojo;
import com.increff.employee.service.ApiException;

	@Repository
	public class inventoryDao extends AbstractDao {

		private Logger logger = Logger.getLogger(inventoryDao.class);

		private static String select_id = "select pc from inventoryPojo pc where id=:id";
		private static String select_all = "select pc from inventoryPojo pc";
		private static String update_id="update inventoryPojo set quantity=:quantity where id=:id";
		
		
		@Transactional
		public void insert(inventoryPojo p) throws ApiException {
				em().persist(p);	
		}


		public inventoryPojo select(int id) {
			TypedQuery<inventoryPojo> query = getQuery(select_id, inventoryPojo.class);
			query.setParameter("id", id);
			return getSingle(query);
		}



		public List<inventoryPojo> selectAll() throws ApiException{
            TypedQuery<inventoryPojo> query = getQuery(select_all, inventoryPojo.class);
			List<inventoryPojo> p= query.getResultList();
			//throw new ApiException("DF");
			for(inventoryPojo l:p) {
				logger.info(l.getId()+" ");
			}
			return p;
		}
		
		public productPojo findid(int id) throws ApiException{
		         return em().find(productPojo.class, id);
			   
		}


		public void update(int id,inventoryPojo p) throws ApiException{
			Query query=getQuery(update_id);
			query.setParameter("id",id);
			query.setParameter("quantity", p.getQuantity());
			logger.info(query.executeUpdate());
		}


	}


