

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class Hibernate {
	
	private static SessionFactory sessionFactory;
	private static Session session;
	private static Transaction transaction;

	public static void configure() {
		Configuration config=new Configuration();
		config.configure("hibernate.cfg.xml");
		
		config.addAnnotatedClass(employeePojo.class);
		
		ServiceRegistry serviceRegistryObj= new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
		
		 sessionFactory = config.buildSessionFactory(serviceRegistryObj);
	}
	
	public void CreateSession() {
		if (session!= null && session.isOpen()) {
			return;
		}
		session=sessionFactory.openSession();
	}
	
	public void beginTransaction() {
		transaction=session.beginTransaction();
	}
	
	public Session  gerSession() {
		return session;
	}
	public void commitTransaction() {
		if (transaction!=null){
			transaction.commit();
		}

	}
	public void closeSession() {
		if (session!=null) {
			session.close();
		}
	}
	}
