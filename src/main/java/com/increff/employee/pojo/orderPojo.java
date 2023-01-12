package com.increff.employee.pojo;

import java.time.LocalDateTime; 
import java.time.format.DateTimeFormatter;  
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class orderPojo {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String date_time;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getDate_time() {
		return date_time;
	}
	public void setDate_time() {
		LocalDateTime myDateObj = LocalDateTime.now();    
	    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	    this.date_time = myDateObj.format(myFormatObj);  
		
	}
	
	

	
	
}
