package com.increff.employee;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Logger;

public class helloWorld {
	
	private static final Logger logger=Logger.getLogger(helloWorld.class);
public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException
{Properties prop=new Properties();
 InputStream instream=new FileInputStream("employee.properties");
  prop.load(instream);
  
	Class.forName(prop.getProperty("jdbc.driver"));
	Connection con=DriverManager.getConnection(  
			prop.getProperty("jdbc.url"),prop.getProperty("jdbc.username"),prop.getProperty("jdbc.password"));  
	      Statement sat=con.createStatement();
	      ResultSet rs=sat.executeQuery("select * from employee");
	      while(rs.next())
	      {
	    	  System.out.println(rs.getInt(1)+" "+rs.getString(2)+ " "+ rs.getInt(3));
	      }
	      sat.executeUpdate("delete from employee where id=10");
	      ResultSet re=sat.executeQuery("select * from employee");
	      while(re.next())
	      {
	    	  System.out.println(re.getInt(1)+" "+re.getString(2)+ " "+ re.getInt(3));
	      }
	      con.close();
}
}
