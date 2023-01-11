package ara_ara;

import java.util.*;
import java.lang.*;
import java.io.*;

class GFG {
	public static void main (String[] args) {
Motorcycle mc=new Motorcycle(40,4,false);
System.out.println(mc.neutral);
}
}
 class Cycle
{
	public int speed;
	public int gear;
	Cycle(int speed,int gear)
	{
		this.speed=speed;
		this.gear=gear;
	}
	
	
	public void speed_up(int speed,int gear)
	{
		this.speed+=speed;
		this.gear+=gear;
	}
	
	public void speed_down(int speed,int gear)
	{
		this.speed-=speed;
		this.gear-=gear;
	}
}
 
 class Motorcycle extends Cycle
 {
	 boolean neutral;
	 
	 Motorcycle(int speed,int gear,boolean neutral)
	 {
		 super(speed,gear);
		 this.neutral=neutral;
		 
	 }
	 
	 public void speed_up(int speed,int gear)
	 {
		 this.speed+=speed;
			this.gear+=gear;
			
			if(this.gear!=0)
			{
				neutral=false;
			}
	 }
	 public void speed_down(int speed,int gear)
		{
			this.speed-=speed;
			this.gear-=gear;
		
	 if(this.gear!=0)
		{
			neutral=false;
		}
	 else {
		 neutral=true;
	 }
	 
 }
 }