

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.query.Query;
import org.hibernate.Session;



public class employeeHibernate {
   private static final Logger logger = Logger.getLogger(employeeHibernate.class);
   private static Hibernate hbu;
   public employeeHibernate() {
	   hbu = new Hibernate();
   }
   public void insert(employeePojo p) throws SQLException {
	   logger.info("Inserting employees");
	   hbu.CreateSession();
	   hbu.beginTransaction();
	   Session s=hbu.gerSession();
	   s.save(p);
	   hbu.commitTransaction();
	   hbu.closeSession();
   }
   public employeePojo select(int id) throws SQLException {
	   logger.info("Selecting employees");
	   hbu.CreateSession();
	   hbu.beginTransaction();
	   Session s=hbu.gerSession();
	   employeePojo p =s.find(employeePojo.class,id);
	   hbu.commitTransaction();
	   hbu.closeSession();
	   return p;
   }
   public List<employeePojo> selectAll() throws SQLException{	   
	   hbu.CreateSession();
   logger.info("Selecting employees");
	   hbu.beginTransaction();
	   Session s=hbu.gerSession();
	   Query q=s.createQuery("Select o from employeePojo o");
	   List<employeePojo> list = q.getResultList();
	   hbu.commitTransaction();
	   hbu.closeSession();
	   return list;
   }
   
   public void update(employeePojo p) throws SQLException {
	   logger.info("Selecting employees");
	   hbu.CreateSession();
	   hbu.beginTransaction();
	   Session s=hbu.gerSession();
	   s.update(p);
	   hbu.commitTransaction();
	   hbu.closeSession();
   }
   
   public void delete(int id) throws SQLException {
	   logger.info("Deleting employees");
	 hbu.CreateSession();
	 hbu.beginTransaction();
	 Session s=hbu.gerSession();
	 employeePojo p = s.find(employeePojo.class, id);
	 s.delete(p);
	 hbu.commitTransaction();
	 hbu.closeSession();
   }
   
   public void deleteAll() throws Exception{
	   hbu.CreateSession();
	   hbu.beginTransaction();
	   Session s=hbu.gerSession();
	   Query q=s.createQuery("Select o from employeePojo o");
	   List<employeePojo> List= q.getResultList();
	   for (employeePojo p: List) {
		   s.delete(p); 
	   }
	   hbu.commitTransaction();
	   hbu.closeSession();
	   
   }
   
   public void printAll() throws SQLException{
	   List<employeePojo> List= selectAll();
	   for (employeePojo p:List) {
		   logger.warn("ID : "+p.getId()+" Name : "+p.getName() + " Age : "+p.getAge());
	   }
   }
}
