import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;


 

public class HelloWorldServelet{
	
	public static employeeHibernate a;
	private static final long serialVersionUID = 1L;
	
	/*static {
		Hibernate.configure();
		a = new employeeHibernate();
	}*/
	public static void main (String[] args) throws Exception{
		System.out.println("H");
		Hibernate.configure();
		employeeHibernate hib = new employeeHibernate();
		
		hib.deleteAll();
		for (int i=0;i<5;i++) {
			employeePojo p=new employeePojo();
			p.setAge(i+20);
			p.setId(i);
			p.setName("username"+i);
			hib.insert(p);
			
		}
		hib.printAll();
		
	}

	
	
}
